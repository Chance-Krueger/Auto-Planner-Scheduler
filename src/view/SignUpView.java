package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;

import model.Account;
import model.Account.Question;
import model.AccountDataBase;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class SignUpView {

	private JFrame frame;
	private JTextField emailText;
	private JTextField passwordText;
	private JLabel SecurityQuestionLabel;
	private JTextField answerText;
	private JButton signupButton;
	private JComboBox<String> secQuestionDropDown;

	// CLASSES
	private AccountDataBase adb;

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

		SecurityQuestionLabel = new JLabel("Security Question");
		SecurityQuestionLabel.setFont(new Font("PT Sans", Font.PLAIN, 22));
		SecurityQuestionLabel.setBackground(Color.WHITE);
		SecurityQuestionLabel.setOpaque(true);
		SecurityQuestionLabel.setBounds(304, 291, 199, 27);
		frame.getContentPane().add(SecurityQuestionLabel);

		this.signupButton = new JButton("");
		signupButton.setBounds(419, 467, 162, 50);
		frame.getContentPane().add(signupButton);

		emailText = new JTextField();
		emailText.setFont(new Font("PT Sans Caption", Font.PLAIN, 17));
		emailText.setBorder(null);
		emailText.setBounds(314, 142, 380, 47);
		frame.getContentPane().add(emailText);
		emailText.setColumns(10);

		answerText = new JTextField();
		answerText.setFont(new Font("PT Sans Caption", Font.PLAIN, 17));
		answerText.setColumns(10);
		answerText.setBorder(null);
		answerText.setBounds(314, 408, 380, 47);
		frame.getContentPane().add(answerText);

		passwordText = new JTextField();
		passwordText.setFont(new Font("PT Sans Caption", Font.PLAIN, 17));
		passwordText.setColumns(10);
		passwordText.setBorder(null);
		passwordText.setBounds(314, 232, 380, 47);
		frame.getContentPane().add(passwordText);

		String[] secQ = { "What is your mother's maiden name?", "What is your place of birth?",
				"What is the name of your first pet?", "What is the name of your first school?" };
		this.secQuestionDropDown = new JComboBox<String>(secQ);
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
	}

	private void signUp() {
		// TODO Auto-generated method stub
		this.adb = new AccountDataBase();

		String email = this.emailText.getText();
		String password = this.passwordText.getText();
		String secAnswer = this.answerText.getText();
		int question = this.secQuestionDropDown.getSelectedIndex() + 1;

		if (!adb.verifyStrongPassword(password)) {
			System.err.println("Password not Strong enough.");
			return;
		}

		if (email.length() == 0 || password.length() == 0 || secAnswer.length() == 0) {
			System.err.println("Invalid Input.");
			return;
		}

		// NOT IN DATA BASE YET
		if (adb.verifyUniqueUsername(email)) {
			Account account = new Account(email, password, secAnswer, Question.fromInt(question));
			this.adb.addUser(account);
			this.frame.dispose();
			LoginView.main(null);
		} else {
			System.err.println("This Email already Exists, please try another Email.");
		}
	}
}
