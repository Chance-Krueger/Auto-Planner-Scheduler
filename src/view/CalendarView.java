package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import model.Calendar;
import model.Event;
import model.MeetingAppt;
import model.ProjAssn;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.JList;
import javax.swing.ListModel;

public class CalendarView {

	private JFrame frame;
	private JButton leftButton;
	private JButton rightButton;
	private JLabel dayMonthLabel;
	private LocalDate curTime;

	private Calendar calendar;
	private String[] acct;
	private ArrayList<JLabel> dayLabels = new ArrayList<JLabel>();
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
		this.acct[0] = "";
		curTime = LocalDate.now();
		this.acct[1] = curTime.toString();
		this.calendar = new Calendar();
		initialize();

	}

	public CalendarView(String email) {
		this.acct = new String[2];
		this.acct[0] = email; // -> NEED TO GO THROUGH SQL TO GRAB DATA? OR SHOULD I MAKE IT IN THE ACCOUNT?
		this.calendar = new Calendar(); // ^^^
		curTime = LocalDate.now();
		this.acct[1] = curTime.toString();
		initialize();

	}

	public CalendarView(String email, String date) {
		this.acct = new String[2];
		this.acct[0] = email; // -> NEED TO GO THROUGH SQL TO GRAB DATA? OR SHOULD I MAKE IT IN THE ACCOUNT?
		this.calendar = new Calendar(); // ^^^
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

	private ListModel<String> createEventList(LocalDate selectedDate) {
		DefaultListModel<String> model = new DefaultListModel<>();

		ArrayList<Event> events = this.calendar.getCalendar().get(selectedDate);

		if (events == null || events.size() == 0) {
			model.add(0, "<html><b>No Events Have Been Added</b></html>");
			model.add(1, "<html><b>Yet on " + selectedDate.getMonth() + " " + selectedDate.getDayOfMonth() + ", "
					+ selectedDate.getYear() + "</b></html>");
			return model;
		}

		for (Event event : events) {
			String label = formatEventSummary(event);
			model.addElement(label);
		}

		return model;
	}

	private String formatEventSummary(Event event) {
		String title = event.getTitle();
		String time = "";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		if (event instanceof ProjAssn) {
			time = ((ProjAssn) event).getDue().format(formatter);
		} else {
			time = ((MeetingAppt) event).getStartTime().format(formatter);

		}

		return time + " — " + title;
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

		lb.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				onDateClicked(day);
			}
		});

		frame.getContentPane().add(lb);
		dayLabels.add(lb);
	}

	private void onDateClicked(int day) {
		LocalDate clickedDate = LocalDate.of(curTime.getYear(), curTime.getMonth(), day);

		// TODO: Load events, show schedule screen,

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
