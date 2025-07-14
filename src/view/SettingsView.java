package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextPane;

import model.Account;
import model.DataBase;

import javax.swing.JComboBox;
import java.awt.Cursor;

public class SettingsView {

	private JFrame frame;
	private JButton backArrowButton;
	private JTextPane accountUserText;
	private JButton resetPasswordButton;
	private JButton blockOffDatesButton;
	private JButton logOutButton;
	private String acct;
	private String[] sArray;
	private JComboBox<String> themeColorDrop;
	private JComboBox<String> accentColorDrop;
	private String themeColor;
	private String accentColor;

	private static final Map<String, String> colorMap = new HashMap<>();

	static {
		colorMap.put(Color.BLACK.toString(), "BLACK");
		colorMap.put(Color.BLUE.toString(), "BLUE");
		colorMap.put(Color.CYAN.toString(), "CYAN");
		colorMap.put(Color.DARK_GRAY.toString(), "DARK_GRAY");
		colorMap.put(Color.GRAY.toString(), "GRAY");
		colorMap.put(Color.GREEN.toString(), "GREEN");
		colorMap.put(Color.LIGHT_GRAY.toString(), "LIGHT_GRAY");
		colorMap.put(Color.MAGENTA.toString(), "MAGENTA");
		colorMap.put(Color.ORANGE.toString(), "ORANGE");
		colorMap.put(Color.PINK.toString(), "PINK");
		colorMap.put(Color.RED.toString(), "RED");
		colorMap.put(Color.WHITE.toString(), "WHITE");
		colorMap.put(Color.YELLOW.toString(), "YELLOW");

	}

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

	public SettingsView(String email) {
		this.acct = email;

		Account a = DataBase.getUser(email);

		Color tc = DataBase.getThemeColor(email);
		Color ac = DataBase.getAccentColor(email);

		sArray = new String[1];
		sArray[0] = email;
		this.themeColor = colorMap.getOrDefault(tc.toString(), tc.toString());
		this.accentColor = colorMap.getOrDefault(ac.toString(), ac.toString());
		initialize();
	}

	public static String getColorName(Color c) {
		for (Map.Entry<String, String> entry : colorMap.entrySet()) {
			if (entry.getValue().equals(c.toString())) {
				return entry.getKey();
			}
		}
		// If not found, return the RGB representation
		return String.format("rgb(%d,%d,%d)", c.getRed(), c.getGreen(), c.getBlue());
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

		String[] colorArray = new String[13];

		colorArray[0] = c.toString();
		int n = 1;
		for (int i = 0; i < colors.length; i++) {
			if (!colors[i].equals(c.toString())) {
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

		String selected = this.accentColorDrop.getItemAt(this.accentColorDrop.getSelectedIndex());

		if (colorMap.containsValue(selected)) {
			this.accentColor = selected;
			// You can use 'color' if needed further
		} else {
			System.err.println("Color was not found in map: " + selected);
			System.exit(0);
		}
	}

	private void themeColor() {

		String selected = this.themeColorDrop.getItemAt(this.themeColorDrop.getSelectedIndex());

		if (colorMap.containsValue(selected)) {
			this.themeColor = selected;
			// You can use 'color' if needed further
		} else {
			System.err.println("Color was not found in map: " + selected);
			System.exit(0);
		}
	}

	private void logOut() {

		// ADD CONFIRMATION TO LOGOUT
		this.frame.dispose();
		changeColor();
		LoginView.main(sArray);

	}

	private void changeColor() {

		String theme = "";
		String accent = "";

		for (Map.Entry<String, String> entry : colorMap.entrySet()) {
			if (entry.getValue().equals(this.themeColor)) {
				theme = entry.getKey();
			} else if (entry.getValue().equals(this.accentColor)) {
				accent = entry.getKey();

			}
		}

		if (theme != "" && accent != "") {
			if (DataBase.adjustColors(this.acct, theme, accent)) {
			} else {
				System.err.println("Colors weren't changed.");
			}
		} else {
			System.err.println("Value not found in the map.");
		}
	}

	private void bod() {
		this.frame.dispose();
		String[] newArray = { sArray[0], "SettingsView" };
		changeColor();
		BlockOffDatesView.main(newArray);

	}

	private void resetPassword() {
		this.frame.dispose();
		changeColor();
		ForgotPasswordResetPassword.main(sArray);

	}
}
