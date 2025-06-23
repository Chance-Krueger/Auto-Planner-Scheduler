package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Account;

import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class MainMenuView {

	private JFrame frame;

	// CLASSES
	Account account;

	private JButton calendarButton;

	private JButton settingsButton;

	private JButton statsButton;

	private JButton blockOffDatesButton;

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
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		this.calendarButton = new JButton("");
		calendarButton.setBorder(null);
		calendarButton.setBounds(252, 280, 202, 27);
		frame.getContentPane().add(calendarButton);

		this.settingsButton = new JButton("");
		settingsButton.setBorder(null);
		settingsButton.setBounds(252, 374, 202, 27);
		frame.getContentPane().add(settingsButton);

		this.statsButton = new JButton("");
		statsButton.setBorder(null);
		statsButton.setBounds(252, 342, 202, 27);
		frame.getContentPane().add(statsButton);

		this.blockOffDatesButton = new JButton("");
		blockOffDatesButton.setBorder(null);
		blockOffDatesButton.setBounds(252, 319, 202, 21);
		frame.getContentPane().add(blockOffDatesButton);

		JLabel mainMenuImage = new JLabel("");
		mainMenuImage.setIcon(
				new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/MainMenuImage.png"));
		mainMenuImage.setBounds(189, 0, 581, 548);
		frame.getContentPane().add(mainMenuImage);

		this.calendarButton.addActionListener(e -> calendar());
		this.settingsButton.addActionListener(e -> settings());
		this.statsButton.addActionListener(e -> statistics());
		this.blockOffDatesButton.addActionListener(e -> bod());

	}

	private void bod() {
		// TODO Auto-generated method stub
		System.out.println("bod");
	}

	private void statistics() {
		// TODO Auto-generated method stub
		System.out.println("bod");

	}

	private void settings() {
		// TODO Auto-generated method stub
		System.out.println("bod");

	}

	private void calendar() {
		// TODO Auto-generated method stub
		System.out.println("bod");

	}

}
