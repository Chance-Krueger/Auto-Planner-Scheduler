package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;

public class BlockOffDatesView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public BlockOffDatesView() {
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
		
		JButton cancelButton = new JButton("");
		cancelButton.setBorder(null);
		cancelButton.setBounds(386, 462, 91, 29);
		frame.getContentPane().add(cancelButton);
		
		JButton confirmButton = new JButton("");
		confirmButton.setBorder(null);
		confirmButton.setBounds(501, 450, 188, 58);
		frame.getContentPane().add(confirmButton);
		
		JButton backButton = new JButton("");
		backButton.setBorder(null);
		backButton.setBounds(290, 45, 42, 46);
		frame.getContentPane().add(backButton);
		
		JLabel blockOffDateImage = new JLabel("");
		blockOffDateImage.setIcon(new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/BlockOffDatesImage.png"));
		blockOffDateImage.setBounds(172, 0, 640, 548);
		frame.getContentPane().add(blockOffDateImage);
	}

}
