package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import model.AccountDataBase;
import model.DataBase;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Cursor;

public class ForgotPasswordEmailConfirmation {

	private JFrame frame;
	private JTextField emailText;
	private JButton resetPasswordButton;
	private JButton backArrowButton;
	private JLabel errorEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPasswordEmailConfirmation window = new ForgotPasswordEmailConfirmation();
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
	public ForgotPasswordEmailConfirmation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setLayout(null);

		errorEmail = new JLabel(
				"<html><p>I'm Sorry, I couldn't find that Email in Our Data Base, Please Try Again or Sign Up.</p></html>");
		errorEmail.setForeground(Color.RED);
		errorEmail.setBounds(358, 129, 296, 44);
		frame.getContentPane().add(errorEmail);
		errorEmail.setVisible(false);

		emailText = new JTextField();
		emailText.setFont(new Font("PT Sans", Font.PLAIN, 17));
		emailText.setBorder(null);
		emailText.setBounds(290, 185, 364, 54);
		frame.getContentPane().add(emailText);
		emailText.setColumns(10);

		this.resetPasswordButton = new JButton("");
		resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetPasswordButton.setBorder(null);
		resetPasswordButton.setBounds(350, 405, 247, 59);
		frame.getContentPane().add(resetPasswordButton);

		this.backArrowButton = new JButton("");
		backArrowButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backArrowButton.setBorder(null);
		backArrowButton.setBounds(279, 58, 331, 59);
		frame.getContentPane().add(backArrowButton);
		this.backArrowButton.addActionListener(e -> backArrow());

		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/ResetPasswordEmailImage.png"));
		backgroundImage.setBounds(153, 0, 640, 548);
		frame.getContentPane().add(backgroundImage);
		frame.setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ACTION LISTENERS
		this.resetPasswordButton.addActionListener(e -> resetPassword());

	}

	private void backArrow() {
		this.frame.dispose();
		LoginView.main(null);
	}

	private void resetPassword() {

		errorEmail.setVisible(false);

		String email = this.emailText.getText();

		// CHECK TO SEE IF EMAIL IS IN DATABASE
		if (DataBase.verifyUser(email)) {
			this.frame.dispose();
			String[] sArray = { email };
			ForgotPasswordResetPassword.main(sArray);

		} else {
			// ELSE GIVE ERROR MESSAGE ONTO SCREEN
			errorEmail.setVisible(true);
			System.err.println("Email was not found in DataBase.");
		}
	}

}
