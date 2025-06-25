package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;

public class BlockOffDatesView {

	private JFrame frame;
	private String email;
	private String cameFrom;
	private JButton cancelButton;
	private JButton confirmButton;
	private JButton backButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length == 2) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						BlockOffDatesView window = new BlockOffDatesView(args[0], args[1]);
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
						BlockOffDatesView window = new BlockOffDatesView();
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
	public BlockOffDatesView() {
		this.cameFrom = "";
		this.email = "";
		initialize();
	}

	public BlockOffDatesView(String email, String cameFrom) {
		this.cameFrom = cameFrom;
		this.email = email;
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

		this.cancelButton = new JButton("");
		cancelButton.setBorder(null);
		cancelButton.setBounds(386, 462, 91, 29);
		frame.getContentPane().add(cancelButton);

		this.confirmButton = new JButton("");
		confirmButton.setBorder(null);
		confirmButton.setBounds(501, 450, 188, 58);
		frame.getContentPane().add(confirmButton);

		this.backButton = new JButton("");
		backButton.setBorder(null);
		backButton.setBounds(290, 45, 301, 46);
		frame.getContentPane().add(backButton);

		JLabel blockOffDateImage = new JLabel("");
		blockOffDateImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/BlockOffDatesImage.png"));
		blockOffDateImage.setBounds(172, 0, 640, 548);
		frame.getContentPane().add(blockOffDateImage);

		this.cancelButton.addActionListener(e -> cancel());
		this.confirmButton.addActionListener(e -> confrim());
		this.backButton.addActionListener(e -> backArrow());
	}

	private void backArrow() {

		try {
			if (this.cameFrom.equals("SettingsView")) {
				String[] sArray = new String[1];
				sArray[0] = this.email;
				this.frame.dispose();
				SettingsView.main(sArray);
			} else {
				String[] sArray = new String[1];
				sArray[0] = this.email;
				this.frame.dispose();
				MainMenuView.main(sArray);
			}

		} catch (Exception e) {
			System.err.println("An error has occured");
		}

	}

	private void confrim() {
		// TODO Auto-generated method stub

	}

	private void cancel() {
		// TODO Auto-generated method stub

	}

}
