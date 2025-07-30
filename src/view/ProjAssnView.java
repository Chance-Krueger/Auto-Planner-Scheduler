package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import java.awt.Font;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import model.DataBase;
import model.Priority;
import model.ProjAssn;
import model.Repeat;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Cursor;

public class ProjAssnView {

	private JFrame frame;
	private JTextField titleText;
	private JComboBox<String> estimatedTimeSpinner;
	private JSpinner dueBySpinner;
	private JTextField locationText;
	private JTextField urlText;
	private JComboBox<String> repeatDropDown;
	private JTextArea notesText;
	private JButton confirmButton;
	private JButton cancelButton;
	private JButton backButton;

	private LocalDate dateOfEvent;
	private String email;
	private String[] acct;
	private JLabel titleError;
	private JComboBox<String> priorityDropDown;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ProjAssnView window = new ProjAssnView();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (args.length == 1) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ProjAssnView window = new ProjAssnView(args[0]);
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
						ProjAssnView window = new ProjAssnView(args[0], args[1]);
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
	public ProjAssnView() {
		this.acct = new String[1];
		this.acct[0] = "";
		this.dateOfEvent = LocalDate.now(); // TESTING
		initialize();
	}

	public ProjAssnView(String email) {
		this.acct = new String[1];
		this.acct[0] = email;
		this.email = email;
		this.dateOfEvent = LocalDate.now(); // TESTING
		initialize();
	}

	public ProjAssnView(String email, String date) {
		this.acct = new String[2];
		this.acct[0] = email;
		this.acct[1] = date;
		this.email = email;
		this.dateOfEvent = LocalDate.parse(date); // TESTING
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		titleText = new JTextField();
		titleText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		titleText.setBorder(null);
		titleText.setBackground(Color.WHITE);
		titleText.setBounds(302, 89, 432, 39);
		frame.getContentPane().add(titleText);
		titleText.setColumns(10);

		JLabel dateLabel = new JLabel(this.dateOfEvent.getMonth() + " " + this.dateOfEvent.getDayOfMonth() + ", "
				+ this.dateOfEvent.getYear(), SwingConstants.CENTER);
		dateLabel.setFont(new Font("PT Sans", Font.PLAIN, 20));
		dateLabel.setBackground(Color.WHITE);
		dateLabel.setOpaque(true);
		dateLabel.setBounds(243, 193, 491, 26);
		frame.getContentPane().add(dateLabel);

		priorityDropDown = new JComboBox<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
		priorityDropDown.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		priorityDropDown.setBounds(584, 241, 155, 29);
		frame.getContentPane().add(priorityDropDown);

		String[] timeOptions = generateTimeOptions(0.25, 48.0, 0.25); // or 120.0 if needed
		estimatedTimeSpinner = new JComboBox<String>(timeOptions);
		estimatedTimeSpinner.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		estimatedTimeSpinner.setBounds(400, 241, 85, 26);
		frame.getContentPane().add(estimatedTimeSpinner);

		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		dueBySpinner = new JSpinner(sm);
		dueBySpinner.setBounds(325, 146, 409, 26);
		JSpinner.DateEditor de = new JSpinner.DateEditor(dueBySpinner, "HH:mm");
		dueBySpinner.setEditor(de);
		frame.getContentPane().add(dueBySpinner);

		locationText = new JTextField();
		locationText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		locationText.setColumns(10);
		locationText.setBorder(null);
		locationText.setBackground(Color.WHITE);
		locationText.setBounds(344, 288, 390, 39);
		frame.getContentPane().add(locationText);

		urlText = new JTextField();
		urlText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		urlText.setColumns(10);
		urlText.setBorder(null);
		urlText.setBackground(Color.WHITE);
		urlText.setBounds(302, 341, 432, 39);
		frame.getContentPane().add(urlText);

		String[] s = { "None", "Everyday", "Every Week", "Every 2 Weeks", "Every Month", "Every Year" };
		repeatDropDown = new JComboBox<String>(s);
		repeatDropDown.setBounds(352, 402, 382, 29);
		frame.getContentPane().add(repeatDropDown);

		notesText = new JTextArea();
		notesText.setLineWrap(true);
		notesText.setFont(new Font("PT Sans", Font.PLAIN, 15));
		notesText.setBounds(325, 456, 409, 56);
		frame.getContentPane().add(notesText);

		confirmButton = new JButton("Confirm");
		confirmButton.setForeground(Color.BLACK);
		confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confirmButton.setBackground(Color.WHITE);
		confirmButton.setBounds(617, 519, 117, 29);
		frame.getContentPane().add(confirmButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setBounds(488, 519, 117, 29);
		frame.getContentPane().add(cancelButton);

		backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(243, 28, 445, 39);
		frame.getContentPane().add(backButton);

		titleError = new JLabel("Title can't be Empty");
		titleError.setForeground(Color.RED);
		titleError.setFont(new Font("PT Sans", Font.PLAIN, 13));
		titleError.setBounds(243, 61, 128, 16);
		this.titleError.setVisible(false);
		frame.getContentPane().add(titleError);

		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/proj-assnImage.png"));
		backgroundImage.setBounds(170, 0, 640, 548);
		frame.getContentPane().add(backgroundImage);

		this.confirmButton.addActionListener(e -> addEvent());
		this.cancelButton.addActionListener(e -> createEventView());
		this.backButton.addActionListener(e -> createEventView());

	}

	private void addEvent() {
//
		this.titleError.setVisible(false);

		if (this.titleText.getText().isEmpty()) {
			this.titleError.setVisible(true);
			return;
		}

		String raw = this.estimatedTimeSpinner.getSelectedItem().toString();
		double hours = Double.parseDouble(raw.replace("hours", "").trim());
		Duration dur = Duration.ofMinutes((long) (hours * 60));

		String due = this.dueBySpinner.getValue().toString();
		due = due.substring(11, 16);
		LocalTime dueTime = LocalTime.parse(due);

		ProjAssn pa = new ProjAssn(this.titleText.getText(),
				Priority.fromInteger(Integer.parseInt(this.priorityDropDown.getSelectedItem().toString())), dur,
				LocalDateTime.of(this.dateOfEvent, dueTime));

		pa.setLocation(this.locationText.getText());
		Repeat type = Repeat.checkRepeatFromString(this.repeatDropDown.getSelectedItem().toString());
		pa.setRepeat(type);
		pa.setNotes(this.notesText.getText());
		pa.setUrl(this.urlText.getText());

		System.out.println(pa.toString());

		// ADD EVENT TO USERS HASHMAP AND PUT INTO THEIR DATA
		if (DataBase.addEventToUserCalendar(email, pa)) {
			this.frame.dispose();
			CalendarView.main(this.acct);
		} else {
			System.err.println("Event not Added to User's Calendar");
		}
	}

	private void createEventView() {
		this.frame.dispose();
		CreateEventView.main(this.acct);
	}

	public static String[] generateTimeOptions(double min, double max, double step) {
		List<String> options = new ArrayList<>();
		for (double t = min; t <= max; t += step) {
			boolean flag = false;
			if (flag == false && t >= max / 4.8) {
				flag = true;
				step = 1;
			}

			if (step == 1) {
				options.add(String.format("%d hours", (int) t));
			} else {
				options.add(String.format("%.2f hours", t));
			}
		}
		return options.toArray(new String[0]);
	}
}
