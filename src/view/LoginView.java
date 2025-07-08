package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import model.AccountDataBase;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Cursor;

public class LoginView {

	private JFrame frame;
	private JTextField labelEmailAddress;
	private JPasswordField txtPassword;

	// BUTTONS
	private JButton loginButton;
	private JButton signupButton;
	private JButton forgotButton;

	private JLabel loginImage;
	private JLabel backgroundColor;

	private boolean isError;

	// CLASSES
	private AccountDataBase adb;
	private JLabel errorMessage;

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
		isError = false;
		adb = new AccountDataBase();
		initialize();
	}

	// IF TRUE, NEED TO SEND TO NEXT SCREEN, (Main Menu Screen)
	private void attemptLogin() {

		errorMessage.setVisible(false);

		String email = this.labelEmailAddress.getText();
		String password = this.txtPassword.getText();

		String[] sArray = { email, password };

		if (!this.adb.authenticate(email, password)) {
			incorrectLogin();
		} else {
			this.frame.dispose();
			MainMenuView.main(sArray);
		}
	}

	private void incorrectLogin() {
		this.isError = true;
		System.err.println("Incorrect Information. Please Try Again.");
		errorMessage.setVisible(true);
		return;
	}

	private void forgotPassword() {
		this.frame.dispose();
		ForgotPasswordEmailConfirmation.main(null);
	}

	private void signUp() {
//		// Hide the current screen
		this.frame.dispose(); // This closes LoginView cleanly
		SignUpView.main(null);
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

		errorMessage = new JLabel("Incorrect Information. Please Try Again.");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(379, 235, 268, 16);
		frame.getContentPane().add(errorMessage);
		errorMessage.setVisible(false);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(312, 325, 335, 42);
		frame.getContentPane().add(txtPassword);
		frame.getContentPane().add(txtPassword);

		labelEmailAddress = new JTextField();
		labelEmailAddress.setForeground(Color.DARK_GRAY);
		labelEmailAddress.setFont(new Font("PT Sans", Font.PLAIN, 13));
		labelEmailAddress.setBackground(new Color(255, 255, 255));
		labelEmailAddress.setBounds(312, 263, 335, 42);
		frame.getContentPane().add(labelEmailAddress);
		labelEmailAddress.setColumns(10);

		this.loginButton = new JButton("");
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setBorder(null);
		loginButton.setBounds(265, 379, 382, 51);
		frame.getContentPane().add(loginButton);

		this.signupButton = new JButton("");
		signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signupButton.setBorder(null);
		signupButton.setBounds(394, 470, 133, 29);
		frame.getContentPane().add(signupButton);

		this.forgotButton = new JButton("");
		forgotButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotButton.setBorder(null);
		forgotButton.setBounds(379, 434, 155, 35);
		frame.getContentPane().add(forgotButton);

		this.loginImage = new JLabel("");
		loginImage.setVerticalAlignment(SwingConstants.TOP);
		loginImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/loginPanelImage4.png"));
		loginImage.setBounds(58, 0, 1536, 1024);
		frame.getContentPane().add(loginImage);

		this.backgroundColor = new JLabel("");
		backgroundColor.setBackground(new Color(63, 63, 63));
		backgroundColor.setBounds(58, 0, 982, 548);
		frame.getContentPane().add(backgroundColor);

		// ACTION LISTENERS
		this.loginButton.addActionListener(e -> attemptLogin());
		this.forgotButton.addActionListener(e -> forgotPassword());
		this.signupButton.addActionListener(e -> signUp());

	}
}
