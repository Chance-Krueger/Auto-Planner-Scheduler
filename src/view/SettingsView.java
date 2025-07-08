package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.JTextPane;
import javax.swing.JComboBox;
import java.awt.Cursor;

public class SettingsView {

	private JFrame frame;
	private JButton backArrowButton;
	private JTextPane accountUserText;
	private JButton resetPasswordButton;
	private JButton blockOffDatesButton;
	private JButton logOutButton;
	private JButton themeColorButton;
	private JButton accentColorButton;
	private String acct;
	private String[] sArray;
	private JComboBox<String> themeColorDrop;
	private JComboBox<String> accentColorDrop;
	private String themeColor;
	private String accentColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length == 1) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SettingsView window = new SettingsView(args[0]);
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (args.length > 1) {
			// CALL OTHER CONSTRUCTOR
		} else {

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SettingsView window = new SettingsView();
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
	public SettingsView() {
		this.acct = "thisUser@example.net";
		sArray = new String[1];
		sArray[0] = "";
		this.themeColor = "DARK_GRAY";
		this.accentColor = "LIGHT_GRAY";
		initialize();
	}

	public SettingsView(String email, String theme, String accent) {
		this.acct = email;
		sArray = new String[1];
		sArray[0] = email;
		this.themeColor = theme;
		this.accentColor = accent;
		initialize();
	}

	public SettingsView(String email) {
		this.acct = email;
		sArray = new String[1];
		sArray[0] = email;
		this.themeColor = "DARK_GRAY";
		this.accentColor = "LIGHT_GRAY";
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(63, 63, 63));
		frame.setBackground(new Color(63, 63, 63));
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String[] colors = { "DARK_GRAY", "WHITE", "GRAY", "BLACK", "PINK", "YELLOW", "MAGENTA", "BLUE", "LIGHT_GRAY",
				"RED", "ORANGE", "GREEN", "CYAN" };

		String[] colorArray = makeColorArray(colors, this.themeColor);

		themeColorDrop = new JComboBox<String>(colorArray);
		themeColorDrop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		themeColorDrop.setOpaque(true);
		themeColorDrop.setBounds(643, 337, 99, 48);
		frame.getContentPane().add(themeColorDrop);

		colorArray = makeColorArray(colors, this.accentColor);

		accentColorDrop = new JComboBox<String>(colorArray);
		accentColorDrop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		accentColorDrop.setOpaque(true);
		accentColorDrop.setBounds(643, 394, 99, 48);
		frame.getContentPane().add(accentColorDrop);

		this.backArrowButton = new JButton("");
		backArrowButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backArrowButton.setBorder(null);
		backArrowButton.setBounds(257, 57, 199, 48);
		frame.getContentPane().add(backArrowButton);

		this.accountUserText = new JTextPane();
		accountUserText.setBackground(new Color(240, 238, 240));
		accountUserText.setFont(new Font("PT Sans", Font.PLAIN, 18));
		accountUserText.setText(this.acct);
		accountUserText.setEditable(false);
		accountUserText.setBounds(282, 186, 445, 31);
		accountUserText.setAlignmentX(1);
		accountUserText.setAlignmentY(1);
		frame.getContentPane().add(accountUserText);

		this.resetPasswordButton = new JButton("");
		resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetPasswordButton.setBorder(null);
		resetPasswordButton.setBounds(257, 234, 188, 31);
		frame.getContentPane().add(resetPasswordButton);

		this.blockOffDatesButton = new JButton("");
		blockOffDatesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		blockOffDatesButton.setBorder(null);
		blockOffDatesButton.setBounds(267, 287, 475, 40);
		frame.getContentPane().add(blockOffDatesButton);

		this.logOutButton = new JButton("");
		logOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logOutButton.setBorder(null);
		logOutButton.setBounds(257, 461, 104, 31);
		frame.getContentPane().add(logOutButton);

//		this.themeColorButton = new JButton("Dark Grey");
//		themeColorButton.setOpaque(true);
//		themeColorButton.setBorder(null);
//		themeColorButton.setFont(new Font("PT Sans Caption", Font.BOLD, 16));
//		themeColorButton.setForeground(Color.WHITE);
//		themeColorButton.setBackground(new Color(31, 110, 230));
//		themeColorButton.setBounds(652, 347, 75, 31);
//		frame.getContentPane().add(themeColorButton);
//
//		this.accentColorButton = new JButton("Light Grey");
//		accentColorButton.setBorder(null);
//		accentColorButton.setOpaque(true);
//		accentColorButton.setFont(new Font("PT Sans Caption", Font.BOLD, 16));
//		accentColorButton.setForeground(Color.WHITE);
//		accentColorButton.setBackground(new Color(31, 110, 230));
//		accentColorButton.setBounds(652, 403, 80, 31);
//		frame.getContentPane().add(accentColorButton);

		JLabel settingsBackgroundImage = new JLabel("New label");
		settingsBackgroundImage.setOpaque(true);
		settingsBackgroundImage.setBackground(new Color(63, 63, 63));
		settingsBackgroundImage.setIcon(
				new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/SettingsImage.png"));
		settingsBackgroundImage.setBounds(189, 0, 651, 548);
		frame.getContentPane().add(settingsBackgroundImage);

		// ACTION LISTENERS
		this.resetPasswordButton.addActionListener(e -> resetPassword());
		this.blockOffDatesButton.addActionListener(e -> bod());
		this.logOutButton.addActionListener(e -> logOut());
		this.themeColorDrop.addActionListener(e -> themeColor());
		this.accentColorDrop.addActionListener(e -> accentColor());
		this.backArrowButton.addActionListener(e -> backArrow());
	}

	private String[] makeColorArray(String[] colors, String c) {

		System.out.println("THE CURRENT COLOR IS: " + c.toString());

		String[] colorArray = new String[13];

		colorArray[0] = c.toString();
		int n = 1;
		for (int i = 0; i < colors.length; i++) {
			if (!colors[i].equals(c.toString())) {
				System.out.println(colors[i] + " " + colors[i].equals(c.toString()));
				colorArray[n] = colors[i];
				n++;
			}
		}
		return colorArray;
	}

	private void backArrow() {

		this.frame.dispose();
		MainMenuView.main(sArray);

	}

	private void accentColor() {

		Color color = null;
		try {
			Field field = Color.class
					.getField(this.accentColorDrop.getItemAt((this.accentColorDrop.getSelectedIndex()))); // Case-insensitive
			color = (Color) field.get(null);
			this.accentColor = this.accentColorDrop.getItemAt((this.accentColorDrop.getSelectedIndex()));
		} catch (Exception e) {
			// Handle the case where the color name is not found
			System.err.println("Color was not Found.");
			System.exit(0);
		}
	}

	private void themeColor() {

		Color color = null;
		try {
			Field field = Color.class.getField(this.themeColorDrop.getItemAt((this.themeColorDrop.getSelectedIndex()))); // Case-insensitive
			color = (Color) field.get(null);
			this.themeColor = this.themeColorDrop.getItemAt((this.themeColorDrop.getSelectedIndex()));
		} catch (Exception e) {
			// Handle the case where the color name is not found
			System.err.println("Color was not Found.");
			System.exit(0);
		}
	}

	private void logOut() {

		// ADD CONFIRMATION TO LOGOUT
		this.frame.dispose();
		LoginView.main(sArray);

	}

	private void bod() {
		this.frame.dispose();
		String[] newArray = { sArray[0], "SettingsView" };
		BlockOffDatesView.main(newArray);

	}

	private void resetPassword() {
		this.frame.dispose();
		ForgotPasswordResetPassword.main(sArray);

	}
}
