package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.DropMode;

public class LoginView {

	private JFrame frame;
	private JTextField labelEmailAddress;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
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
	public LoginView() {
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
		
		txtPassword = new JTextField();
		txtPassword.setToolTipText("");
		txtPassword.setForeground(Color.DARK_GRAY);
		txtPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		txtPassword.setColumns(10);
		txtPassword.setBackground(Color.WHITE);
		txtPassword.setBounds(312, 325, 335, 42);
		frame.getContentPane().add(txtPassword);
		
		labelEmailAddress = new JTextField();
		labelEmailAddress.setForeground(Color.DARK_GRAY);
		labelEmailAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		labelEmailAddress.setBackground(new Color(255, 255, 255));
		labelEmailAddress.setBounds(312, 263, 335, 42);
		frame.getContentPane().add(labelEmailAddress);
		labelEmailAddress.setColumns(10);
		
		JButton loginButton = new JButton("");
		loginButton.setBorder(null);
		loginButton.setBounds(265, 379, 382, 51);
		frame.getContentPane().add(loginButton);
		
		JButton signupButton = new JButton("");
		signupButton.setBorder(null);
		signupButton.setBounds(394, 470, 133, 29);
		frame.getContentPane().add(signupButton);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(379, 434, 155, 35);
		frame.getContentPane().add(btnNewButton);
		
		JLabel loginImage = new JLabel("");
		loginImage.setVerticalAlignment(SwingConstants.TOP);
		loginImage.setIcon(new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/loginPanelImage4.png"));
		loginImage.setBounds(58, 0, 1536, 1024);
		frame.getContentPane().add(loginImage);
		
		JLabel backgroundColor = new JLabel("");
		backgroundColor.setBackground(new Color(63, 63, 63));
		backgroundColor.setBounds(58, 0, 982, 548);
		frame.getContentPane().add(backgroundColor);
	}
}
