package model;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import model.Account.Question;

public class DataBase {

	public static void main(String[] args) {
		DataBase db = new DataBase();
		Account acct = new Account("chancekrueger@arizona.edu", "Freeman46!", "Freeman", Question.THREE);
		Settings set = new Settings();
		DataBase.addUser(acct, set);
	}

	private static HashMap<Account, Calendar> data;

	public DataBase() {
		makeConnection();
		populateData();
	}

	private static Connection makeConnection() {
		Connection con = null;
		try {
			// This line ensures the driver is loaded
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/CalendarDataBase?useSSL=false&serverTimezone=UTC", "root",
					"Freeman46!");

			if (con != null) {
				System.out.println("connected successfully to database");
				return con;
			}

		} catch (Exception e) {
			System.out.println("not connected to database");
			e.printStackTrace();
		}
		return null;

	}

	private static void populateData() {
		data = null;
	}

	public static boolean addUser(Account acct, Settings set) {

		String email = acct.getUsername();
		String password = acct.getHashedPassword();
		String security_question = acct.getQuestion().toString();
		String security_answer = acct.getHashedSecurityAnswer();
		String theme_color = set.getThemeColor().toString();
		String accent_color = set.getAccentColor().toString();
		String salt = acct.getSalt();
		String login_attempts = String.valueOf(acct.getLoginAttempts());

		Connection con = makeConnection();

		try {
			String query = "INSERT INTO user (email, password, security_question, security_answer, theme_color, accent_color, salt, login_attempts) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, security_question);
			ps.setString(4, security_answer);
			ps.setString(5, theme_color);
			ps.setString(6, accent_color);
			ps.setString(7, salt);
			ps.setString(8, login_attempts);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("User added successfully.");
				populateData();
				return true;
			} else {
				System.out.println("User insertion failed.");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean adjustColors(String email, String theme, String accent) {
		Connection con = makeConnection();

		try {
			String query = "UPDATE user SET theme_color = ?, accent_color = ? WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, theme);
			ps.setString(2, accent);
			ps.setString(3, email);

			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean adjustBOD(String email, Settings set) {
		return false;
	}

	public static boolean adjustPassword(String email, String newPassword) {

		Connection con = makeConnection();

		try {
			String query = "SELECT salt FROM user WHERE	 email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String salt = rs.getString("salt");
				newPassword = AccountDataBase.hashPassword(newPassword, salt);
			} else {
				System.out.println("Login failed: email was not not found.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		try {
			String query = "UPDATE user SET password = ? WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, newPassword);
			ps.setString(2, email);

			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// make sure email is in HashMap and that password is correct in Account
	// if it is correct, give Calendar, else give null, which will tell it was
	// incorrect
	public static Calendar verifyUser(String email, String password) {

		Connection con = makeConnection();

		try {
			String query = "SELECT salt FROM user WHERE	 email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String salt = rs.getString("salt");
				password = AccountDataBase.hashPassword(password, salt);
			} else {
				System.out.println("Login failed: email was not not found.");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		try {
			String query = "SELECT user_id FROM user WHERE email = ? AND password = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");
				System.out.println("Login successful. User ID: " + userId);

				// TODO: retrieve events using userId and build the Calendar object
				Calendar calendar = new Calendar();
				// calendar.populateFromDatabase(userId, con); // Custom method you’ll write

				return calendar;
			} else {
				System.out.println("Login failed: credentials not found.");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean verifyUser(String email) {

		Connection con = makeConnection();

		try {
			String query = "SELECT user_id FROM user WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id"); // TESTING
				System.out.println("Login successful. User ID: " + userId); // TESTING
				return true;
			} else {
				System.out.println("Login failed: credentials not found."); // TESTING
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Account getUser(String email) {

		Account acct = null;

		// String acctName, String acctPassword, String securityAnswer, Question
		// question
		String acctName = email;
		String acctPassword = "";
		String securityAnswer = "";
		String question = "";
		String salt = "";
		String loginAttempts = "";

		Connection con = makeConnection();

		try {
			String query = "SELECT password, security_question, security_answer, salt, login_attempts FROM user WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				acctPassword = rs.getString("password");
				question = rs.getString("security_question");
				securityAnswer = rs.getString("security_answer");
				salt = rs.getString("salt");
				loginAttempts = rs.getString("login_attempts");

				acct = new Account(acctName, acctPassword, salt, securityAnswer, Question.fromStringText(question),
						Integer.parseInt(loginAttempts));

				return acct;
			} else {
				System.out.println("Login failed: email was not not found.");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// update the Account's calendar
	// if any errors return false
	public boolean updateAccountsCalendar(String email, Calendar cal) {

		return false;
	}

	public static Color getThemeColor(String email) {

		Connection con = makeConnection();

		String color = "";

		try {
			String query = "SELECT theme_color FROM user WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				color = rs.getString("theme_color");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return getColor(color);
	}

	public static Color getAccentColor(String email) {

		Connection con = makeConnection();

		String color = "";

		try {
			String query = "SELECT accent_color FROM user WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				color = rs.getString("accent_color");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return getColor(color);
	}

	private static Color getColor(String colorString) {
		try {
			// Example input: java.awt.Color[r=255,g=0,b=0]
			if (colorString == null || !colorString.contains("r=") || !colorString.contains("g=")
					|| !colorString.contains("b=")) {
				throw new IllegalArgumentException("Invalid color string: " + colorString);
			}

			int rIndex = colorString.indexOf("r=") + 2;
			int rEndIndex = colorString.indexOf(",", rIndex);
			int gIndex = colorString.indexOf("g=") + 2;
			int gEndIndex = colorString.indexOf(",", gIndex);
			int bIndex = colorString.indexOf("b=") + 2;
			int bEndIndex = colorString.indexOf("]", bIndex);

			// If any end index is still -1, fail early
			if (rEndIndex == -1 || gEndIndex == -1 || bEndIndex == -1) {
				throw new IllegalArgumentException("Malformed color string: " + colorString);
			}

			int red = Integer.parseInt(colorString.substring(rIndex, rEndIndex));
			int green = Integer.parseInt(colorString.substring(gIndex, gEndIndex));
			int blue = Integer.parseInt(colorString.substring(bIndex, bEndIndex));

			return new Color(red, green, blue);
		} catch (Exception e) {
			System.err.println("Failed to parse color string: " + colorString);
			e.printStackTrace();
			// Optional fallback
			return Color.BLACK;
		}
	}

}
