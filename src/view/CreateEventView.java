package view;

import java.awt.EventQueue;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Calendar;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Cursor;

public class CreateEventView {

	private JFrame frame;
	private JButton apptMeetButton;
	private JButton projAssnButton;
	private JButton backButton;
	private String[] acct;
	private LocalDate date;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length == 1) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CreateEventView window = new CreateEventView(args[0]);
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
						CreateEventView window = new CreateEventView(args[0], args[1]);
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
						CreateEventView window = new CreateEventView();
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
	public CreateEventView() {
		this.acct = new String[1];
		this.acct[0] = "";
		initialize();
	}

	public CreateEventView(String email) {
		this.acct = new String[1];
		this.acct[0] = email;
		initialize();
	}

	public CreateEventView(String email, String date) {
		this.acct = new String[2];
		this.acct[0] = email;
		this.acct[1] = date;
		this.date = LocalDate.parse(date);
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

		apptMeetButton = new JButton("");
		apptMeetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		apptMeetButton.setBorder(null);
		apptMeetButton.setBounds(253, 129, 472, 70);
		frame.getContentPane().add(apptMeetButton);

		projAssnButton = new JButton("");
		projAssnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		projAssnButton.setBorder(null);
		projAssnButton.setBounds(253, 211, 472, 76);
		frame.getContentPane().add(projAssnButton);

		backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(253, 46, 46, 46);
		frame.getContentPane().add(backButton);

		JLabel createEventBackground = new JLabel("");
		createEventBackground.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/createEventImage.png"));
		createEventBackground.setBounds(168, 0, 640, 548);
		frame.getContentPane().add(createEventBackground);

		this.apptMeetButton.addActionListener(e -> makeApptMeetEvent());
		this.projAssnButton.addActionListener(e -> makeProjAssnEvent());
		this.backButton.addActionListener(e -> calendarView());
	}

	private void makeApptMeetEvent() {
		this.frame.dispose();
		ApptMeetView.main(this.acct);
	}

	private void makeProjAssnEvent() {
		// TODO Auto-generated method stub
	}

	private void calendarView() {
		this.frame.dispose();
		CalendarView.main(this.acct);
	}

}
