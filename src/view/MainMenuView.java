package view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import model.Account;
import model.AccountDataBase;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Cursor;

public class MainMenuView {

	private JFrame frame;
	private JButton backButton;
	private JButton calendarButton;
	private JButton bodButton;
	private JButton statsButton;
	private JButton settingsButton;

	private String[] acct;
	private JLabel mainMenuBackground;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainMenuView window = new MainMenuView(args[0]);
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
						MainMenuView window = new MainMenuView();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public MainMenuView(String email) {
		this.acct = new String[1];
		this.acct[0] = email;
		initialize();

	}

	/**
	 * Create the application.
	 */
	public MainMenuView() {
		this.acct = new String[1];
		this.acct[0] = "";
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

		this.backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(365, 40, 275, 36);
		frame.getContentPane().add(backButton);

		this.calendarButton = new JButton("");
		calendarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		calendarButton.setBorder(null);
		calendarButton.setBounds(365, 175, 258, 29);
		frame.getContentPane().add(calendarButton);

		this.bodButton = new JButton("");
		bodButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bodButton.setBorder(null);
		bodButton.setBounds(365, 223, 258, 36);
		frame.getContentPane().add(bodButton);

		this.statsButton = new JButton("");
		statsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		statsButton.setBorder(null);
		statsButton.setBounds(365, 275, 258, 29);
		frame.getContentPane().add(statsButton);

		this.settingsButton = new JButton("");
		settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		settingsButton.setBorder(null);
		settingsButton.setBounds(365, 319, 258, 36);
		frame.getContentPane().add(settingsButton);

		// Load the original image
		ImageIcon originalIcon = new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/MainMenuImage.png");

		// Resize image
		Image scaledImage = originalIcon.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH);

		ImageIcon resizedIcon = new ImageIcon(scaledImage);

		// Create the JLabel and set its bounds
		mainMenuBackground = new JLabel();
		mainMenuBackground.setBounds(170, 0, 640, 548);
		mainMenuBackground.setIcon(resizedIcon);
		mainMenuBackground.setHorizontalAlignment(SwingConstants.CENTER);
		mainMenuBackground.setVerticalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(mainMenuBackground);
		frame.getContentPane().add(mainMenuBackground);

		// ACTION LISTENERS
		this.calendarButton.addActionListener(e -> calendar());
		this.bodButton.addActionListener(e -> bod());
		this.statsButton.addActionListener(e -> stats());
		this.settingsButton.addActionListener(e -> settings());
		this.backButton.addActionListener(e -> backArrow());
	}

	private void backArrow() {
		// Add pop up window to confirm they want to log out
		// if accepted go back to login window
		// else break;
	}

	private void settings() {
		this.frame.dispose();
		SettingsView.main(this.acct);
	}

	private void stats() {
		// TODO Auto-generated method stub
	}

	private void bod() {
		this.frame.dispose();
		String[] sArray = new String[2];
		sArray[0] = this.acct[0];
		sArray[1] = "MainMenuView";
		BlockOffDatesView.main(sArray);
	}

	private void calendar() {
		this.frame.dispose();
		CalendarView.main(this.acct);

	}

}
