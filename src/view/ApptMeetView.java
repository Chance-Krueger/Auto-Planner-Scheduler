package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.time.LocalDate;
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

		dateLabel = new JLabel(this.dateOfEvent.getMonth() + " " + this.dateOfEvent.getDayOfMonth() + ", "
				+ this.dateOfEvent.getYear(), SwingConstants.CENTER);
		dateLabel.setFont(new Font("PT Sans", Font.PLAIN, 20));
		dateLabel.setBackground(Color.WHITE);
		dateLabel.setOpaque(true);
		dateLabel.setBounds(257, 225, 472, 29);
		frame.getContentPane().add(dateLabel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/appt-meetingImage.png"));
		lblNewLabel.setBounds(173, 0, 640, 548);
		frame.getContentPane().add(lblNewLabel);

		this.confirmButton.addActionListener(e -> addEvent());
		this.cancelButton.addActionListener(e -> createEventView());
		this.backButton.addActionListener(e -> createEventView());
	}

	private void addEvent() {
		// TODO Auto-generated method stub
	}

	private void createEventView() {
		this.frame.dispose();
		CreateEventView.main(this.acct);
	}
}
