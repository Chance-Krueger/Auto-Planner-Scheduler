package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.Account;
import model.AccountDataBase;
import model.DataBase;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Cursor;

public class ForgotPasswordResetPassword {

	private JFrame frame;

	// CLASSES
//	private AccountDataBase adb;
	private Account acct;
	private JTextField answerText;
	private JTextField newPasswordText;
	private String question;

	private JButton backButton;

	private JButton resetPasswordButton;
	private JLabel errorPassword;
	private JLabel errorAnswer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ForgotPasswordResetPassword window = new ForgotPasswordResetPassword(args[0]);
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
						ForgotPasswordResetPassword window = new ForgotPasswordResetPassword();
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
	public ForgotPasswordResetPassword() {
//		this.adb = new AccountDataBase();
		this.acct = null;
		this.question = "THIS IS FOR TESTING.";
		initialize();
	}

	public ForgotPasswordResetPassword(String email) {
		this.acct = DataBase.getUser(email);
		if (this.acct == null) {
			System.err.println("User Does not Exist in DataBase.");
		}

		switch (Integer.parseInt(this.acct.getQuestion().getValue())) {

		case 1:
			this.question = "What is your mother's maiden name?";
			break;

		case 2:
			this.question = "What is your place of birth?";
			break;

		case 3:
			this.question = "What is the name of your first pet?";
			break;

		case 4:
			this.question = "What is the name of your first school?";
			break;

		default:
			System.err.println("Question does not exist");
			break;

		}

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

		errorPassword = new JLabel(
				"<html><p>Password needs: at least 8 characters, Contain at least one number, Contain at least one special character, Contain at least one uppercase letter.</p></html>");
		errorPassword.setForeground(Color.RED);
		errorPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		errorPassword.setBounds(484, 351, 232, 56);
		frame.getContentPane().add(errorPassword);
		errorPassword.setVisible(false);

		errorAnswer = new JLabel("I'm Sorry, that is the Incorrect Answer.");
		errorAnswer.setForeground(Color.RED);
		errorAnswer.setBounds(402, 269, 277, 24);
		frame.getContentPane().add(errorAnswer);
		errorAnswer.setVisible(false);

		JTextArea securityQuestionText = new JTextArea();
		securityQuestionText.setBorder(null);
		securityQuestionText.setLineWrap(true);
		securityQuestionText.setEditable(false);
		securityQuestionText.setText(this.question);
		securityQuestionText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		securityQuestionText.setBounds(317, 170, 357, 67);
		frame.getContentPane().add(securityQuestionText);

		this.backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(309, 55, 314, 49);
		frame.getContentPane().add(backButton);

		answerText = new JTextField();
		answerText.setBorder(null);
		answerText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		answerText.setBounds(317, 305, 357, 41);
		frame.getContentPane().add(answerText);
		answerText.setColumns(10);

		newPasswordText = new JTextField();
		newPasswordText.setFont(new Font("PT Sans", Font.PLAIN, 20));
		newPasswordText.setColumns(10);
		newPasswordText.setBorder(null);
		newPasswordText.setBounds(317, 409, 357, 41);
		frame.getContentPane().add(newPasswordText);

		this.resetPasswordButton = new JButton("");
		resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetPasswordButton.setBorder(null);
		resetPasswordButton.setBounds(389, 466, 216, 49);
		frame.getContentPane().add(resetPasswordButton);

		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/ResetPasswordImage.png"));
		backgroundImage.setBounds(177, 0, 640, 548);
		frame.getContentPane().add(backgroundImage);

		// ACTION LISTENERS
		this.resetPasswordButton.addActionListener(e -> resetPassword());
		this.backButton.addActionListener(e -> backArrow());
	}

	private void resetPassword() {

		errorAnswer.setVisible(false);
		errorPassword.setVisible(false);

		// 1st. Check to see if the security answer is right
		if (!AccountDataBase.hashPassword(this.answerText.getText(), acct.getSalt())
				.equals(acct.getHashedSecurityAnswer())) {
			errorAnswer.setVisible(true);
			System.err.println("Incorrect Answer.");
			return;
		}

		// 2nd check to see if new password is secure
		if (!AccountDataBase.verifyStrongPassword(this.newPasswordText.getText())) {
			System.err.println("Password is not Secure Enough.");
			errorPassword.setVisible(true);
			return;
		}

		this.acct.changePassword(this.newPasswordText.getText());
		DataBase.adjustPassword(this.acct.getUsername(), this.newPasswordText.getText());
		this.frame.dispose();
		LoginView.main(null);
	}

	private void backArrow() {

		this.frame.dispose();
		LoginView.main(null);
	}
}
