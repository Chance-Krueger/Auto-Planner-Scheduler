package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;

public class SettingsView {

	private JFrame frame;
	private JButton backArrowButton;
	private JTextPane accountUserText;
	private JButton resetPasswordButton;
	private JButton blockOffDatesButton;
	private JButton logOutButton;
	private JButton themeColorButton;
	private JButton accentColorButton;
	private String acct;
	private String[] sArray;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SettingsView window = new SettingsView(args[0]);
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
						SettingsView window = new SettingsView();
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
	public SettingsView() {
		this.acct = "thisUser@example.net";
		sArray = new String[1];
		sArray[0] = "";
		initialize();
	}

	public SettingsView(String email) {
		this.acct = email;
		sArray = new String[1];
		sArray[0] = email;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(63, 63, 63));
		frame.setBackground(new Color(63, 63, 63));
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		this.backArrowButton = new JButton("");
		backArrowButton.setBorder(null);
		backArrowButton.setBounds(257, 57, 199, 48);
		frame.getContentPane().add(backArrowButton);

		this.accountUserText = new JTextPane();
		accountUserText.setBackground(new Color(240, 238, 240));
		accountUserText.setFont(new Font("PT Sans", Font.PLAIN, 18));
		accountUserText.setText(this.acct);
		accountUserText.setEditable(false);
		accountUserText.setBounds(282, 186, 445, 31);
		accountUserText.setAlignmentX(1);
		accountUserText.setAlignmentY(1);
		frame.getContentPane().add(accountUserText);

		this.resetPasswordButton = new JButton("");
		resetPasswordButton.setBorder(null);
		resetPasswordButton.setBounds(257, 234, 188, 31);
		frame.getContentPane().add(resetPasswordButton);

		this.blockOffDatesButton = new JButton("");
		blockOffDatesButton.setBorder(null);
		blockOffDatesButton.setBounds(267, 287, 475, 40);
		frame.getContentPane().add(blockOffDatesButton);

		this.logOutButton = new JButton("");
		logOutButton.setBorder(null);
		logOutButton.setBounds(257, 461, 104, 31);
		frame.getContentPane().add(logOutButton);

		this.themeColorButton = new JButton("Dark Grey");
		themeColorButton.setOpaque(true);
		themeColorButton.setBorder(null);
		themeColorButton.setFont(new Font("PT Sans Caption", Font.BOLD, 16));
		themeColorButton.setForeground(Color.WHITE);
		themeColorButton.setBackground(new Color(31, 110, 230));
		themeColorButton.setBounds(652, 347, 75, 31);
		frame.getContentPane().add(themeColorButton);

		this.accentColorButton = new JButton("Light Grey");
		accentColorButton.setBorder(null);
		accentColorButton.setOpaque(true);
		accentColorButton.setFont(new Font("PT Sans Caption", Font.BOLD, 16));
		accentColorButton.setForeground(Color.WHITE);
		accentColorButton.setBackground(new Color(31, 110, 230));
		accentColorButton.setBounds(652, 403, 80, 31);
		frame.getContentPane().add(accentColorButton);

		JLabel settingsBackgroundImage = new JLabel("New label");
		settingsBackgroundImage.setOpaque(true);
		settingsBackgroundImage.setBackground(new Color(63, 63, 63));
		settingsBackgroundImage.setIcon(
				new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/SettingsImage.png"));
		settingsBackgroundImage.setBounds(189, 0, 651, 548);
		frame.getContentPane().add(settingsBackgroundImage);

		// ACTION LISTENERS
		this.resetPasswordButton.addActionListener(e -> resetPassword());
		this.blockOffDatesButton.addActionListener(e -> bod());
		this.logOutButton.addActionListener(e -> logOut());
		this.themeColorButton.addActionListener(e -> themeColor());
		this.accentColorButton.addActionListener(e -> accentColor());
		this.backArrowButton.addActionListener(e -> backArrow());
	}

	private void backArrow() {

		this.frame.dispose();
		MainMenuView.main(sArray);

	}

	private void accentColor() {
		// TODO Auto-generated method stub

	}

	private void themeColor() {
		// TODO Auto-generated method stub

	}

	private void logOut() {

		// ADD CONFIRMATION TO LOGOUT
		this.frame.dispose();
		LoginView.main(sArray);

	}

	private void bod() {
		this.frame.dispose();
		String[] newArray = { sArray[0], "SettingsView" };
		BlockOffDatesView.main(newArray);

	}

	private void resetPassword() {
		this.frame.dispose();
		ForgotPasswordResetPassword.main(sArray);

	}
}
