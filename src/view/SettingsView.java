package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Component;

public class SettingsView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public SettingsView() {
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
		
		JButton backArrowButton = new JButton("");
		backArrowButton.setBorder(null);
		backArrowButton.setBounds(257, 57, 44, 48);
		frame.getContentPane().add(backArrowButton);
		
		JTextPane accountUserText = new JTextPane();
		accountUserText.setBackground(new Color(240, 238, 240));
		accountUserText.setFont(new Font("PT Sans", Font.PLAIN, 18));
		accountUserText.setText("thisUser@example.net");
		accountUserText.setEditable(false);
		accountUserText.setBounds(282, 186, 445, 31);
		accountUserText.setAlignmentX(1);
		accountUserText.setAlignmentY(1);
		frame.getContentPane().add(accountUserText);
		
		JButton resetPasswordButton = new JButton("");
		resetPasswordButton.setBorder(null);
		resetPasswordButton.setBounds(257, 234, 188, 31);
		frame.getContentPane().add(resetPasswordButton);
		
		JButton blockOffDatesButton = new JButton("");
		blockOffDatesButton.setBorder(null);
		blockOffDatesButton.setBounds(707, 287, 35, 40);
		frame.getContentPane().add(blockOffDatesButton);
		
		JButton logOutButton = new JButton("");
		logOutButton.setBorder(null);
		logOutButton.setBounds(257, 461, 104, 31);
		frame.getContentPane().add(logOutButton);
		
		JButton themeColorButton = new JButton("Dark Grey");
		themeColorButton.setFont(new Font("PT Sans Caption", Font.BOLD, 16));
		themeColorButton.setOpaque(true);
		themeColorButton.setForeground(Color.WHITE);
		themeColorButton.setBackground(new Color(31, 110, 230));
		themeColorButton.setBounds(644, 339, 98, 48);
		frame.getContentPane().add(themeColorButton);
		
		JButton accentColorButton = new JButton("Light Grey");
		accentColorButton.setFont(new Font("PT Sans Caption", Font.BOLD, 16));
		accentColorButton.setOpaque(true);
		accentColorButton.setForeground(Color.WHITE);
		accentColorButton.setBackground(new Color(31, 110, 230));
		accentColorButton.setBounds(644, 395, 98, 48);
		frame.getContentPane().add(accentColorButton);
		
		JLabel settingsBackgroundImage = new JLabel("New label");
		settingsBackgroundImage.setOpaque(true);
		settingsBackgroundImage.setBackground(new Color(63, 63, 63));
		settingsBackgroundImage.setIcon(new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/SettingsImage.png"));
		settingsBackgroundImage.setBounds(189, 0, 651, 548);
		frame.getContentPane().add(settingsBackgroundImage);
	}
}
