package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class MainMenuView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public MainMenuView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton calendarButton = new JButton("");
		calendarButton.setBorder(null);
		calendarButton.setBounds(206, 244, 609, 76);
		frame.getContentPane().add(calendarButton);
		
		JButton settingsButton = new JButton("");
		settingsButton.setBorder(null);
		settingsButton.setBounds(206, 361, 609, 76);
		frame.getContentPane().add(settingsButton);
		
		JLabel SettingsLabel = new JLabel("Settings");
		SettingsLabel.setBackground(new Color(250, 251, 251));
		SettingsLabel.setOpaque(true);
		SettingsLabel.setFont(new Font("PT Sans Caption", Font.PLAIN, 40));
		SettingsLabel.setBounds(206, 380, 343, 44);
		frame.getContentPane().add(SettingsLabel);
		
		JLabel mainMenuImage = new JLabel("");
		mainMenuImage.setIcon(new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/MainMenuImage.png"));
		mainMenuImage.setBounds(0, 0, 982, 548);
		frame.getContentPane().add(mainMenuImage);
		
		JButton statsButton = new JButton("");
		statsButton.setBorder(null);
		statsButton.setBounds(206, 466, 609, 76);
		frame.getContentPane().add(statsButton);
	}

}
