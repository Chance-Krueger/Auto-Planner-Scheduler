package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import model.DataBase;
import model.Event;
import model.MeetingAppt;
import model.Repeat;

import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Cursor;

public class ApptMeetView {

	private JFrame frame;
	private JTextField titleText;
	private JTextField locationText;
	private JLabel dateLabel;
	private JTextField urlText;
	private JTextField notesText;
	private JComboBox<String> repeatDropDown;
	private JSpinner startSpinner;
	private JSpinner endSpinner;
	private LocalDate dateOfEvent;
	private String email;
	private JButton backButton;
	private JButton confirmButton;
	private JButton cancelButton;
	private String[] acct;
	private JLabel repeatError;
	private JLabel timeError;
	private JLabel titleError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ApptMeetView window = new ApptMeetView();
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
						ApptMeetView window = new ApptMeetView(args[0]);
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
						ApptMeetView window = new ApptMeetView(args[0], args[1]);
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
	public ApptMeetView() {
		this.acct = new String[1];
		this.acct[0] = "";
		this.dateOfEvent = LocalDate.now(); // TESTING
		this.email = "chancekrueger@arizona.edu"; // TESTING
		initialize();
	}

	public ApptMeetView(String email) {
		this.acct = new String[1];
		this.acct[0] = email;
		this.email = email;
		this.dateOfEvent = LocalDate.now(); // TESTING
		initialize();
	}

	public ApptMeetView(String email, String date) {
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
		titleText.setBounds(321, 95, 403, 39);
		frame.getContentPane().add(titleText);
		titleText.setColumns(10);

		locationText = new JTextField();
		locationText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		locationText.setColumns(10);
		locationText.setBorder(null);
		locationText.setBounds(357, 159, 367, 39);
		frame.getContentPane().add(locationText);

		urlText = new JTextField();
		urlText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		urlText.setColumns(10);
		urlText.setBorder(null);
		urlText.setBounds(321, 458, 403, 39);
		frame.getContentPane().add(urlText);

		notesText = new JTextField();
		notesText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		notesText.setColumns(10);
		notesText.setBorder(null);
		notesText.setBounds(336, 396, 393, 39);
		frame.getContentPane().add(notesText);

		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		startSpinner = new JSpinner(sm);
		startSpinner.setBounds(378, 286, 108, 26);
		JSpinner.DateEditor de = new JSpinner.DateEditor(startSpinner, "HH:mm");
		startSpinner.setEditor(de);
		frame.getContentPane().add(startSpinner);

		Date date2 = new Date();
		SpinnerDateModel sm2 = new SpinnerDateModel(date2, null, null, Calendar.HOUR_OF_DAY);
		endSpinner = new JSpinner(sm2);
		endSpinner.setBounds(616, 286, 108, 26);
		JSpinner.DateEditor de2 = new JSpinner.DateEditor(endSpinner, "HH:mm");
		endSpinner.setEditor(de2);
		frame.getContentPane().add(endSpinner);

		String[] s = { "None", "Everyday", "Every Week", "Every 2 Weeks", "Every Month", "Every Year" };
		repeatDropDown = new JComboBox<String>(s);
		repeatDropDown.setBounds(347, 343, 382, 29);
		frame.getContentPane().add(repeatDropDown);

		backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(257, 34, 424, 39);
		frame.getContentPane().add(backButton);

		confirmButton = new JButton("Confirm");
		confirmButton.setForeground(Color.BLACK);
		confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confirmButton.setBackground(Color.WHITE);
		confirmButton.setBounds(612, 509, 117, 29);
		frame.getContentPane().add(confirmButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setBounds(483, 509, 117, 29);
		frame.getContentPane().add(cancelButton);

		timeError = new JLabel("Start time must start before End Time");
		timeError.setForeground(Color.RED);
		timeError.setFont(new Font("PT Sans", Font.PLAIN, 13));
		timeError.setBounds(257, 257, 467, 16);
		this.timeError.setVisible(false);
		frame.getContentPane().add(timeError);

		dateLabel = new JLabel(this.dateOfEvent.getMonth() + " " + this.dateOfEvent.getDayOfMonth() + ", "
				+ this.dateOfEvent.getYear(), SwingConstants.CENTER);
		dateLabel.setFont(new Font("PT Sans", Font.PLAIN, 20));
		dateLabel.setBackground(Color.WHITE);
		dateLabel.setOpaque(true);
		dateLabel.setBounds(257, 225, 472, 29);
		frame.getContentPane().add(dateLabel);

		titleError = new JLabel("Title can't be Empty");
		titleError.setForeground(Color.RED);
		titleError.setFont(new Font("PT Sans", Font.PLAIN, 13));
		titleError.setBounds(257, 70, 467, 16);
		this.titleError.setVisible(false);
		frame.getContentPane().add(titleError);

		repeatError = new JLabel("Must choose an Option");
		repeatError.setForeground(Color.RED);
		repeatError.setFont(new Font("PT Sans", Font.PLAIN, 13));
		repeatError.setBounds(257, 324, 467, 16);
		this.repeatError.setVisible(false);
		frame.getContentPane().add(repeatError);

		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/appt-meetingImage.png"));
		backgroundImage.setBounds(173, 0, 640, 548);
		frame.getContentPane().add(backgroundImage);

		this.confirmButton.addActionListener(e -> addEvent());
		this.cancelButton.addActionListener(e -> createEventView());
		this.backButton.addActionListener(e -> createEventView());
	}

	private void addEvent() {

		// title the title of the meeting
		// date the LocalDateTime representing the date of the meeting
		// startTime the LocalTime when the meeting starts
		// endTime the LocalTime when the meeting ends

		this.titleError.setVisible(false);
		this.timeError.setVisible(false);
		this.repeatError.setVisible(false);

		if (this.titleText.getText().isEmpty()) {
			this.titleError.setVisible(true);
			return;
		}

		String first = this.startSpinner.getValue().toString();
		first = first.substring(11, 16);
		LocalTime start = LocalTime.parse(first);

		String second = this.endSpinner.getValue().toString();
		second = second.substring(11, 16);
		LocalTime end = LocalTime.parse(second);

		// Check to see if start and end are legal (start is first and end is second
		// with time)
		if (start.isAfter(end) || start.equals(end)) {
			this.timeError.setVisible(true);
			return;
		}

		if (this.repeatError.getText().isEmpty()) {
			this.repeatError.setVisible(true);
			return;
		}

		MeetingAppt maEvent = new MeetingAppt(this.titleText.getText(), this.dateOfEvent, start, end);
		maEvent.setLocation(this.locationText.getText());
		Repeat type = Repeat.checkRepeatFromString(this.repeatDropDown.getSelectedItem().toString());
		maEvent.setRepeat(type);
		maEvent.setNotes(this.notesText.getText());
		maEvent.setUrl(this.urlText.getText());

		// ADD EVENT TO USERS HASHMAP AND PUT INTO THEIR DATA
		if (DataBase.addEventToUserCalendar(email, maEvent, true)) {
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
}
