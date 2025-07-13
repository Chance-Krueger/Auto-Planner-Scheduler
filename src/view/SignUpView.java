package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;

import model.Account;
import model.Account.Question;
import model.AccountDataBase;
import model.DataBase;
import model.Settings;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Cursor;

public class SignUpView {

	private JFrame frame;
	private JTextField emailText;
	private JPasswordField passwordText;
	private JLabel SecurityQuestionLabel;
	private JTextField answerText;
	private JButton signupButton;
	private JComboBox<String> secQuestionDropDown;

	// CLASSES
	private JLabel lblNewLabel;
	private JButton backArrowButton;
	private JLabel errorMessageEmail;
	private JLabel errorMessagePassword;
	private JLabel errorMessageSecurity;
	private JLabel errorSecAnswer;
	private JLabel errorAllLeftBlank;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpView window = new SignUpView();
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
	public SignUpView() {
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

		errorAllLeftBlank = new JLabel("One of the Text Fields was left Blank.");
		errorAllLeftBlank.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		errorAllLeftBlank.setForeground(Color.RED);
		errorAllLeftBlank.setBounds(370, 89, 293, 16);
		frame.getContentPane().add(errorAllLeftBlank);
		errorAllLeftBlank.setVisible(false);

		errorMessageEmail = new JLabel("This Email already Exists, please try another Email.");
		errorMessageEmail.setForeground(Color.RED);
		errorMessageEmail.setBounds(379, 117, 333, 16);
		frame.getContentPane().add(errorMessageEmail);
		errorMessageEmail.setVisible(false);

		errorMessagePassword = new JLabel(
				"<html><p>Password needs: at least 8 characters, Contain at least one number, Contain at least one special character, Contain at least one uppercase letter.</p></html>");
		errorMessagePassword.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		errorMessagePassword.setForeground(Color.RED);
		errorMessagePassword.setBounds(419, 191, 293, 40);
		frame.getContentPane().add(errorMessagePassword);
		errorMessagePassword.setVisible(false);

		errorMessageSecurity = new JLabel("Please pick a Question.");
		errorMessageSecurity.setForeground(Color.RED);
		errorMessageSecurity.setBounds(480, 297, 268, 16);
		frame.getContentPane().add(errorMessageSecurity);
		errorMessageSecurity.setVisible(false);

		errorSecAnswer = new JLabel("Invalid Input. Please Enter the Answer.");
		errorSecAnswer.setForeground(Color.RED);
		errorSecAnswer.setBounds(402, 382, 283, 16);
		frame.getContentPane().add(errorSecAnswer);
		errorSecAnswer.setVisible(false);

		backArrowButton = new JButton("");
		backArrowButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backArrowButton.setBorder(null);
		backArrowButton.setBounds(277, 48, 170, 50);
		frame.getContentPane().add(backArrowButton);

		lblNewLabel = new JLabel("<");
		lblNewLabel.setFont(new Font("PT Sans", Font.BOLD, 40));
		lblNewLabel.setBounds(277, 52, 40, 34);
		frame.getContentPane().add(lblNewLabel);

		SecurityQuestionLabel = new JLabel("Security Question");
		SecurityQuestionLabel.setFont(new Font("PT Sans", Font.PLAIN, 22));
		SecurityQuestionLabel.setBackground(Color.WHITE);
		SecurityQuestionLabel.setOpaque(true);
		SecurityQuestionLabel.setBounds(304, 291, 199, 27);
		frame.getContentPane().add(SecurityQuestionLabel);

		this.signupButton = new JButton("");
		signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signupButton.setBorder(null);
		signupButton.setBounds(419, 467, 162, 50);
		frame.getContentPane().add(signupButton);

		emailText = new JTextField();
		emailText.setFont(new Font("PT Sans Caption", Font.PLAIN, 17));
		emailText.setBorder(null);
		emailText.setBounds(314, 145, 371, 40);
		frame.getContentPane().add(emailText);
		emailText.setColumns(10);

		answerText = new JTextField();
		answerText.setFont(new Font("PT Sans Caption", Font.PLAIN, 17));
		answerText.setColumns(10);
		answerText.setBorder(null);
		answerText.setBounds(314, 410, 371, 40);
		frame.getContentPane().add(answerText);

		passwordText = new JPasswordField();
		passwordText.setFont(new Font("PT Sans Caption", Font.PLAIN, 17));
		passwordText.setColumns(10);
		passwordText.setBorder(null);
		passwordText.setBounds(314, 232, 371, 40);
		frame.getContentPane().add(passwordText);

		String[] secQ = { "What is your mother's maiden name?", "What is your place of birth?",
				"What is the name of your first pet?", "What is the name of your first school?" };

		this.secQuestionDropDown = new JComboBox<String>(secQ);
		secQuestionDropDown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		secQuestionDropDown.setAutoscrolls(true);
		secQuestionDropDown.setBackground(Color.WHITE);
		secQuestionDropDown.setOpaque(true);
		secQuestionDropDown.setFont(new Font("PT Sans", Font.PLAIN, 16));
		secQuestionDropDown.setBounds(314, 330, 371, 27);
		frame.getContentPane().add(secQuestionDropDown);
		secQuestionDropDown.addItem("");

		JLabel signUpBackground = new JLabel("");
		signUpBackground.setBackground(Color.BLACK);
		signUpBackground.setAutoscrolls(true);
		signUpBackground.setIcon(
				new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/signUpImage.png"));
		signUpBackground.setBounds(180, 0, 640, 548);
		frame.getContentPane().add(signUpBackground);

		// ACTION LISTENERS
		this.signupButton.addActionListener(e -> signUp());
		this.backArrowButton.addActionListener(e -> backArrow());

	}

	private void backArrow() {

		this.frame.dispose();
		LoginView.main(null);

	}

	private void signUp() {

		this.errorAllLeftBlank.setVisible(false);
		this.errorSecAnswer.setVisible(false);
		this.errorMessagePassword.setVisible(false);
		this.errorMessageSecurity.setVisible(false);
		this.errorMessageEmail.setVisible(false);

		AccountDataBase adb = new AccountDataBase();

		String email = this.emailText.getText();
		String password = this.passwordText.getText();
		String secAnswer = this.answerText.getText();
		int question = this.secQuestionDropDown.getSelectedIndex() + 1;

		if (email.length() == 0 || password.length() == 0 || secAnswer.length() == 0) {
			this.errorAllLeftBlank.setVisible(true);
			System.err.println("Invalid Input.");
			return;
		}

		if (!adb.verifyStrongPassword(password)) {
			System.err.println("Password not Strong enough.");
			this.errorMessagePassword.setVisible(true);
			return;
		}

		if (secAnswer.length() == 0) {
			this.errorSecAnswer.setVisible(true);
			System.err.println("No Chosen Input.");
			return;
		}

		if (question > 4) {
			System.err.println("Please pick a Question.");
			this.errorMessageSecurity.setVisible(true);
			return;
		}

		// NOT IN DATA BASE YET
		if (DataBase.verifyUser(email, password) == null) {
			Account account = new Account(email, password, secAnswer, Question.fromInt(question));
			DataBase.addUser(account, new Settings());
			this.frame.dispose();
			LoginView.main(null);
		} else {
			this.errorMessageEmail.setVisible(true);
			System.err.println("This Email already Exists, please try another Email.");
		}
	}
}
