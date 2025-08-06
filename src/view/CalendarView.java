package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import model.Calendar;
import model.DataBase;
import model.Event;
import model.MeetingAppt;
import model.Priority;
import model.ProjAssn;
import model.Repeat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Cursor;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerDateModel;

public class CalendarView {

	private JFrame frame;
	private JButton leftButton;
	private JButton rightButton;
	private JLabel dayMonthLabel;
	private LocalDate curTime;

	private Calendar calendar;
	private String[] acct;
	private ArrayList<JLabel> dayLabels = new ArrayList<JLabel>();
	private ArrayList<Event> displayedEvents = new ArrayList<>();
	private JButton backButton;
	private JList<String> eventList;
	private JButton addButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length == 1) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CalendarView window = new CalendarView(args[0]);
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (args.length == 2) {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					try {
						CalendarView window = new CalendarView(args[0], args[1]);
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CalendarView window = new CalendarView();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	/**
	 * Create the application.
	 */
	public CalendarView() {
		this.acct = new String[2];
		this.acct[0] = "chancekrueger@arizona.edu";
		curTime = LocalDate.now();
		this.acct[1] = curTime.toString();
		this.calendar = DataBase.getUserCalendar("chancekrueger@arizona.edu"); // TESTING
		initialize();

	}

	public CalendarView(String email) {
		this.acct = new String[2];
		this.acct[0] = email;
		this.calendar = DataBase.getUserCalendar(email);
		curTime = LocalDate.now();
		this.acct[1] = curTime.toString();
		initialize();

	}

	public CalendarView(String email, String date) {
		this.acct = new String[2];
		this.acct[0] = email; // -> NEED TO GO THROUGH SQL TO GRAB DATA? OR SHOULD I MAKE IT IN THE ACCOUNT?
		this.calendar = DataBase.getUserCalendar(email);
		curTime = LocalDate.parse(date);
		this.acct[1] = curTime.toString();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildUIComponents();

		this.leftButton.addActionListener(e -> goBackInTime());
		this.rightButton.addActionListener(e -> goFowardinTime());
		this.backButton.addActionListener(e -> mainMenu());
		this.addButton.addActionListener(e -> addEvent());
	}

	private void redo() {
		frame.getContentPane().removeAll(); // Clear existing components
		frame.revalidate();
		frame.repaint();

		buildUIComponents();

		this.leftButton.addActionListener(e -> goBackInTime());
		this.rightButton.addActionListener(e -> goFowardinTime());
		this.backButton.addActionListener(e -> mainMenu());
		this.addButton.addActionListener(e -> addEvent());

	}

	private void buildUIComponents() {
		// Rebuild components
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setLayout(null);

		createDates();

		JLabel coveringOfDates = new JLabel("");
		coveringOfDates.setBackground(Color.WHITE);
		coveringOfDates.setOpaque(true);
		coveringOfDates.setBounds(255, 205, 276, 181);
		frame.getContentPane().add(coveringOfDates);

		leftButton = new JButton("");
		leftButton.setBorder(null);
		leftButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leftButton.setBounds(242, 128, 29, 29);
		frame.getContentPane().add(leftButton);

		rightButton = new JButton("");
		rightButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rightButton.setBorder(null);
		rightButton.setBounds(502, 128, 29, 29);
		frame.getContentPane().add(rightButton);

		dayMonthLabel = new JLabel(curTime.getMonth() + " " + curTime.getYear(), SwingConstants.CENTER);
		dayMonthLabel.setFont(new Font("PT Sans Caption", Font.PLAIN, 20));
		dayMonthLabel.setBounds(295, 128, 181, 34);
		frame.getContentPane().add(dayMonthLabel);

		backButton = new JButton("< Main Menu");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(210, 418, 117, 29);
		frame.getContentPane().add(backButton);

		eventList = new JList<String>(createEventList(this.curTime));

		eventList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = eventList.locationToIndex(e.getPoint());
				if (index >= 0 && index < displayedEvents.size()) {
					Event selectedEvent = displayedEvents.get(index);
					System.out.println("Clicked event: " + selectedEvent.getTitle());
					openEditPopup(selectedEvent);
				}
			}
		});

		eventList.setBackground(new Color(244, 248, 251));
		eventList.setBorder(null);
		eventList.setBounds(575, 179, 188, 112);
		frame.getContentPane().add(eventList);

		addButton = new JButton("+");
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setFont(new Font("PT Sans Caption", Font.PLAIN, 30));
		addButton.setBorder(null);
		addButton.setBounds(564, 133, 45, 29);
		frame.getContentPane().add(addButton);

		JLabel calendarBackground = new JLabel("");
		calendarBackground.setBackground(Color.DARK_GRAY);
		calendarBackground.setIcon(
				new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/CalendarImage.png"));
		calendarBackground.setBounds(185, 0, 640, 548);
		frame.getContentPane().add(calendarBackground);
	}

	private void openEditPopup(Event e) {
		JDialog dialog = new JDialog(frame, "Edit Event", true);
		dialog.setLayout(null);
		dialog.setSize(420, 460); // Increased height
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);

		// Title
		JLabel titleLabel = new JLabel("Title:");
		titleLabel.setBounds(20, 20, 100, 25);
		dialog.add(titleLabel);

		JTextField titleField = new JTextField(e.getTitle());
		titleField.setBounds(130, 20, 250, 25);
		dialog.add(titleField);

		// Location
		JLabel locationLabel = new JLabel("Location:");
		locationLabel.setBounds(20, 55, 100, 25);
		dialog.add(locationLabel);

		JTextField locationField = new JTextField(e.getLocation());
		locationField.setBounds(130, 55, 250, 25);
		dialog.add(locationField);

		// URL
		JLabel urlLabel = new JLabel("URL:");
		urlLabel.setBounds(20, 90, 100, 25);
		dialog.add(urlLabel);

		JTextField urlField = new JTextField(e.getUrl());
		urlField.setBounds(130, 90, 250, 25);
		dialog.add(urlField);

		// Notes
		JLabel notesLabel = new JLabel("Notes:");
		notesLabel.setBounds(20, 125, 100, 25);
		dialog.add(notesLabel);

		JTextArea notesArea = new JTextArea(e.getNotes());
		notesArea.setLineWrap(true);
		notesArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(notesArea);
		scrollPane.setBounds(130, 125, 250, 60);
		dialog.add(scrollPane);

		// Repeat moved further down
		JLabel repeatLabel = new JLabel("Repeat:");
		repeatLabel.setBounds(20, 210, 100, 25);
		dialog.add(repeatLabel);

		String[] repeatOptions = { "None", "Everyday", "Every Week", "Every 2 Weeks", "Every Month", "Every Year" };
		JComboBox<String> repeatDropdown = new JComboBox<>(repeatOptions);
		repeatDropdown.setSelectedItem(e.getRepeat().toString());
		repeatDropdown.setBounds(130, 210, 250, 25);
		dialog.add(repeatDropdown);

		java.util.Date now = new java.util.Date();

		// Save Button adjusted vertically
		JButton saveBtn = new JButton("Save");
		saveBtn.setBounds(300, 370, 100, 30);
		dialog.add(saveBtn);

		// Delete Button
		JButton delBtn = new JButton("Delete");
		delBtn.setBounds(20, 370, 100, 30);
		dialog.add(delBtn);

		if (e instanceof MeetingAppt m) {
			// Start Time
			JLabel startLabel = new JLabel("Start Time:");
			startLabel.setBounds(20, 250, 100, 25);
			dialog.add(startLabel);

			SpinnerDateModel smStart = new SpinnerDateModel(now, null, null, java.util.Calendar.HOUR_OF_DAY);
			JSpinner startSpinner = new JSpinner(smStart);
			startSpinner.setBounds(130, 250, 100, 25);
			JSpinner.DateEditor deStart = new JSpinner.DateEditor(startSpinner, "HH:mm");
			startSpinner.setEditor(deStart);
			startSpinner.setValue(java.sql.Time.valueOf(m.getStartTime()));
			dialog.add(startSpinner);

			// End Time
			JLabel endLabel = new JLabel("End Time:");
			endLabel.setBounds(20, 285, 100, 25);
			dialog.add(endLabel);

			SpinnerDateModel smEnd = new SpinnerDateModel(now, null, null, java.util.Calendar.HOUR_OF_DAY);
			JSpinner endSpinner = new JSpinner(smEnd);
			endSpinner.setBounds(130, 285, 100, 25);
			JSpinner.DateEditor deEnd = new JSpinner.DateEditor(endSpinner, "HH:mm");
			endSpinner.setEditor(deEnd);
			endSpinner.setValue(java.sql.Time.valueOf(m.getEndTime()));
			dialog.add(endSpinner);

			// ASK IF USER WANTS TO UPDATE EVENT OR ALL FUTURE EVENTS
			saveBtn.addActionListener(a -> {
				System.out.println("SAVE BUTTON PUSED");
				try {
					LocalTime start = LocalTime.parse(new SimpleDateFormat("HH:mm").format(startSpinner.getValue()));
					LocalTime end = LocalTime.parse(new SimpleDateFormat("HH:mm").format(endSpinner.getValue()));

					MeetingAppt updated = new MeetingAppt(titleField.getText(), m.getDate(), start, end);
					updated.setLocation(locationField.getText());
					updated.setUrl(urlField.getText());
					updated.setNotes(notesArea.getText());
					updated.setRepeat(Repeat.checkRepeatFromString((String) repeatDropdown.getSelectedItem()));

					DataBase.updateEventInUserCalendar(acct[0], e, updated);
					dialog.dispose();
					this.frame.dispose();
					CalendarView.main(acct);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});

			// ADD POP UP DIALOG TO CONFIRM
			// THIS DIALOG WILL HAVE LIKE APPLE -> IF ON REPEAT ASK USER TO DELETE EVENT OR
			// EVENTS
			delBtn.addActionListener(a -> {
				System.out.println("DELETE BUTTON PUSHED");
				try {
					LocalTime start = LocalTime.parse(new SimpleDateFormat("HH:mm").format(startSpinner.getValue()));
					LocalTime end = LocalTime.parse(new SimpleDateFormat("HH:mm").format(endSpinner.getValue()));

					MeetingAppt updated = new MeetingAppt(titleField.getText(), m.getDate(), start, end);
					updated.setLocation(locationField.getText());
					updated.setUrl(urlField.getText());
					updated.setNotes(notesArea.getText());
					updated.setRepeat(Repeat.checkRepeatFromString((String) repeatDropdown.getSelectedItem()));

					DataBase.deleteEventFromDataBase(acct[0], updated);
					dialog.dispose();
					this.frame.dispose();
					CalendarView.main(acct);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			});

		} else if (e instanceof ProjAssn p) {
			// Due Time
			JLabel dueLabel = new JLabel("Due Time:");
			dueLabel.setBounds(20, 250, 100, 25);
			dialog.add(dueLabel);

			SpinnerDateModel smDue = new SpinnerDateModel(now, null, null, java.util.Calendar.HOUR_OF_DAY);
			JSpinner dueBySpinner = new JSpinner(smDue);
			dueBySpinner.setBounds(130, 250, 100, 25);
			JSpinner.DateEditor deDue = new JSpinner.DateEditor(dueBySpinner, "HH:mm");
			dueBySpinner.setEditor(deDue);
			dueBySpinner.setValue(java.sql.Time.valueOf(p.getDue().toLocalTime()));
			dialog.add(dueBySpinner);

			// Estimate Duration
			JLabel estimateLabel = new JLabel("Estimate:");
			estimateLabel.setBounds(20, 285, 100, 25);
			dialog.add(estimateLabel);

			String[] generatedOptions = ProjAssnView.generateTimeOptions(0.25, 48.0, 0.25);
			String[] sArray = new String[generatedOptions.length + 1];
			sArray[0] = String.format("%.2f hours", (double) p.getTime().toMinutes() / 60);
			System.arraycopy(generatedOptions, 0, sArray, 1, generatedOptions.length);

			JComboBox<String> estimateDropdown = new JComboBox<>(sArray);
			estimateDropdown.setSelectedItem(sArray[0]);
			estimateDropdown.setBounds(130, 285, 120, 25);
			dialog.add(estimateDropdown);

			// Priority
			JLabel priorityLabel = new JLabel("Priority:");
			priorityLabel.setBounds(20, 320, 100, 25);
			dialog.add(priorityLabel);

			JComboBox<Priority> priorityDropdown = new JComboBox<>(Priority.values());
			priorityDropdown.setSelectedItem(p.getPriority());
			priorityDropdown.setBounds(130, 320, 120, 25);
			dialog.add(priorityDropdown);

			// ASK IF USER WANTS TO UPDATE EVENT OR ALL FUTURE EVENTS
			saveBtn.addActionListener(a -> {
				System.out.println("SAVE BUTTON PUSED");
				try {
					LocalTime dueTime = LocalTime.parse(new SimpleDateFormat("HH:mm").format(dueBySpinner.getValue()));

					Duration dur = Duration.ofMinutes((long) (Double.parseDouble(
							estimateDropdown.getSelectedItem().toString().replace("hours", "").trim()) * 60));

					ProjAssn updated = new ProjAssn(titleField.getText(), (Priority) priorityDropdown.getSelectedItem(),
							dur, LocalDateTime.of(p.getDue().toLocalDate(), dueTime));
					updated.setLocation(locationField.getText());
					updated.setUrl(urlField.getText());
					updated.setNotes(notesArea.getText());
					updated.setRepeat(Repeat.checkRepeatFromString((String) repeatDropdown.getSelectedItem()));

					DataBase.updateEventInUserCalendar(acct[0], e, updated);
					dialog.dispose();
					this.frame.dispose();
					CalendarView.main(acct);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});

			// ADD POP UP DIALOG TO CONFIRM
			// THIS DIALOG WILL HAVE LIKE APPLE -> IF ON REPEAT ASK USER TO DELETE EVENT OR
			// EVENTS
			delBtn.addActionListener(a -> {
				System.out.println("DELETE BUTTON PUSHED");
				try {
					LocalTime dueTime = LocalTime.parse(new SimpleDateFormat("HH:mm").format(dueBySpinner.getValue()));

					Duration dur = Duration.ofMinutes((long) (Double.parseDouble(
							estimateDropdown.getSelectedItem().toString().replace("hours", "").trim()) * 60));

					ProjAssn updated = new ProjAssn(titleField.getText(), (Priority) priorityDropdown.getSelectedItem(),
							dur, LocalDateTime.of(p.getDue().toLocalDate(), dueTime));
					updated.setLocation(locationField.getText());
					updated.setUrl(urlField.getText());
					updated.setNotes(notesArea.getText());
					updated.setRepeat(Repeat.checkRepeatFromString((String) repeatDropdown.getSelectedItem()));

					DataBase.deleteEventFromDataBase(acct[0], updated);
					dialog.dispose();
					this.frame.dispose();
					CalendarView.main(acct);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		}
		dialog.setVisible(true);
	}

	private ListModel<String> createEventList(LocalDate selectedDate) {
		DefaultListModel<String> model = new DefaultListModel<>();
		displayedEvents.clear(); // reset the list

		ArrayList<Event> events = this.calendar.getCalendar().get(selectedDate);

		if (events == null || events.size() == 0) {
			model.add(0, "<html><b>No Events Have Been Added</b></html>");
			model.add(1, "<html><b>Yet on " + selectedDate.getMonth() + " " + selectedDate.getDayOfMonth() + ", "
					+ selectedDate.getYear() + "</b></html>");
			return model;
		}

		Collections.sort(events);

		for (Event event : events) {
			String label = formatEventSummary(event);
			model.addElement(label);
			displayedEvents.add(event); // keep actual event
		}

		return model;
	}

	private String formatEventSummary(Event event) {

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		String title = event.getTitle();
		String time;

		if (event instanceof ProjAssn) {
			time = "Due @ " + ((ProjAssn) event).getDue().format(timeFormatter); // full date-time
		} else if (event instanceof MeetingAppt) {
			time = ((MeetingAppt) event).getStartTime().format(timeFormatter) + "-"
					+ ((MeetingAppt) event).getEndTime().format(timeFormatter); // just time
		} else {
			time = "Time unknown";
		}

		return title + " | " + time;
	}

	private void createDates() {
		clearOldLabels();

		LocalDate firstDay = LocalDate.of(curTime.getYear(), curTime.getMonth(), 1);
		int startOffset = calculateDayOffset(firstDay);
		int daysInMonth = curTime.lengthOfMonth();

		int dayNumber = 1;
		for (int i = 0; i < 42; i++) {
			if (i >= startOffset && dayNumber <= daysInMonth) {
				int[] pos = calculateGridPosition(i);
				addDayLabel(dayNumber++, pos[0], pos[1]);
			}
		}

		frame.revalidate();
		frame.repaint();
	}

	private void clearOldLabels() {
		for (JLabel lb : dayLabels) {
			frame.getContentPane().remove(lb);
		}
		dayLabels.clear();
	}

	private int calculateDayOffset(LocalDate firstDay) {
		String[] weekOfDays = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		for (int i = 0; i < weekOfDays.length; i++) {
			if (firstDay.getDayOfWeek().toString().equals(weekOfDays[i]))
				return i;
		}
		return 0; // fallback
	}

	private int[] calculateGridPosition(int index) {
		int row = index / 7;
		int col = index % 7;

		int y = 205 + (row * 37);
		int x;
		switch (col) {
		case 0:
			x = 252;
			break;
		case 1:
			x = 298;
			break;
		case 2:
			x = 342;
			break;
		case 3:
			x = 384;
			break;
		case 4:
			x = 423;
			break;
		case 5:
			x = 460;
			break;
		case 6:
			x = 497;
			break;
		default:
			x = 252;
		}

		return new int[] { x, y };
	}

	private void addDayLabel(int day, int x, int y) {
		JLabel lb = new DayLabel(String.valueOf(day), x, y, Color.GRAY, Color.BLACK, true);

		LocalDate labelDate = LocalDate.of(curTime.getYear(), curTime.getMonth(), day);
		LocalDate today = LocalDate.now();

		if (labelDate.isEqual(today)) {
			lb.setBackground(new Color(135, 206, 250)); // highlight today
		} else if (labelDate.isEqual(curTime)) {
			lb.setBackground(new Color(173, 216, 230)); // keep today red
		} else {
			lb.setBackground(Color.WHITE); // default for other days
		}

		lb.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				onDateClicked(day);

				LocalDate today = LocalDate.now();

				// Reset all labels
				for (JLabel label : dayLabels) {
					int labelDay = Integer.parseInt(label.getText());
					LocalDate labelDate = LocalDate.of(curTime.getYear(), curTime.getMonth(), labelDay);

					if (labelDate.isEqual(today)) {
						label.setBackground(new Color(135, 206, 250)); // keep today red
					} else {
						label.setBackground(Color.WHITE); // default reset
					}
				}

				// Highlight the clicked label (even if it's today, this will override)
				lb.setBackground(new Color(173, 216, 230));
			}
		});

		frame.getContentPane().add(lb);
		dayLabels.add(lb);
	}

	private void onDateClicked(int day) {
		LocalDate clickedDate = LocalDate.of(curTime.getYear(), curTime.getMonth(), day);

		this.curTime = LocalDate.of(clickedDate.getYear(), clickedDate.getMonth(), clickedDate.getDayOfMonth());
		this.acct[1] = this.curTime.toString();
		this.eventList.setVisible(false);
		this.eventList.setListData(convertListModelToArray(createEventList(clickedDate)));
		this.eventList.setVisible(true);
	}

	private String[] convertListModelToArray(ListModel<String> model) {
		String[] arr = new String[model.getSize()];
		for (int i = 0; i < model.getSize(); i++) {
			arr[i] = model.getElementAt(i);
		}
		return arr;
	}

	private void goFowardinTime() {

		this.dayMonthLabel.setVisible(false);

		this.curTime = this.curTime.plusMonths(1);
		this.acct[1] = this.curTime.toString();
		this.dayMonthLabel.setText(curTime.getMonth() + " " + curTime.getYear());

		this.dayMonthLabel.setVisible(true);
		redo(); // Refresh the calendar

	}

	private void goBackInTime() {

		this.dayMonthLabel.setVisible(false);

		this.curTime = this.curTime.minusMonths(1);
		this.acct[1] = this.curTime.toString();
		this.dayMonthLabel.setText(curTime.getMonth() + " " + curTime.getYear());

		this.dayMonthLabel.setVisible(true);
		redo(); // Refresh the calendar
	}

	private void mainMenu() {
		this.frame.dispose();
		MainMenuView.main(this.acct);

	}

	private void addEvent() {
		this.frame.dispose();
		CreateEventView.main(this.acct);
	}
}