package model;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Account.Question;

public class DataBase {

	public DataBase() {
		makeConnection();
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

	public static boolean addEventToUserCalendar(String email, Event e) {
		String title = e.getTitle();
		String location = e.getLocation();
		String repeat = e.getRepeat().toString();
		String notes = e.getNotes();
		String url = e.getUrl();
		String eventType = (e instanceof MeetingAppt) ? "MeetingAppt"
				: (e instanceof ProjAssn) ? "ProjAssn" : "Unknown";

		try (Connection con = makeConnection()) {
			int userId = getUserID(email);
			if (userId <= 0) {
				System.err.println("Invalid user ID for email: " + email);
				return false;
			}

			// Step 1: Insert into events table
			String insertEvent = "INSERT INTO events (user_id, title, location, `repeat`, notes, url, event_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement psEvent = con.prepareStatement(insertEvent, Statement.RETURN_GENERATED_KEYS);
			psEvent.setInt(1, userId);
			psEvent.setString(2, title);
			psEvent.setString(3, location);
			psEvent.setString(4, repeat);
			psEvent.setString(5, notes);
			psEvent.setString(6, url);
			psEvent.setString(7, eventType);
			psEvent.executeUpdate();

			ResultSet rsEvent = psEvent.getGeneratedKeys();
			int eventId = rsEvent.next() ? rsEvent.getInt(1) : -1;
			if (eventId <= 0)
				return false;

			// Step 2: Insert into type-specific table
			if (e instanceof MeetingAppt m) {
				String date = m.getDate().toString();
				String startTime = m.getStartTime().toString();
				String endTime = m.getEndTime().toString();

				String insertMeeting = "INSERT INTO meeting_appt_events (cal_id, user_id, date, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement psMeeting = con.prepareStatement(insertMeeting);
				psMeeting.setInt(1, eventId);
				psMeeting.setInt(2, userId);
				psMeeting.setString(3, date);
				psMeeting.setString(4, startTime);
				psMeeting.setString(5, endTime);
				psMeeting.executeUpdate();

			} else if (e instanceof ProjAssn p) {
				String due = p.getDue().toString();
				String priority = String.valueOf(p.getPriority());
				String time = p.getTime().toString();

				String insertProject = "INSERT INTO project_assn_events (cal_id, user_id, priority, time, due) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement psProj = con.prepareStatement(insertProject);
				psProj.setInt(1, eventId);
				psProj.setInt(2, userId);
				psProj.setString(3, priority);
				psProj.setString(4, time);
				psProj.setString(5, due);
				psProj.executeUpdate();
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static int getUserID(String email) {
		try (Connection con = makeConnection()) {
			String query = "SELECT user_id FROM user WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			return rs.next() ? rs.getInt("user_id") : -1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	public static Calendar getUserCalendar(String email) {
		// TODO

		return null;
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
				// Grab the newly inserted user_id
				Statement idQuery = con.createStatement();
				ResultSet rs = idQuery.executeQuery("SELECT LAST_INSERT_ID()");
				if (rs.next()) {
					int userId = rs.getInt(1);
					addUserToBOD(userId); // Initialize blocked-off dates
					System.out.println("User added successfully. User ID: " + userId);
					return true;
				}
			}

			System.out.println("User insertion failed.");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void addUserToBOD(int userID) {
		Connection con = makeConnection();

		try {
			// Generate a space-separated string of all 15-minute intervals in 24 hours
			StringBuilder allTimes = new StringBuilder();
			LocalTime cur = LocalTime.MIDNIGHT;
			for (int i = 0; i < 96; i++) { // 24 hrs * 4 (quarter-hour increments)
				allTimes.append(cur.toString()).append(" ");
				cur = cur.plusMinutes(15);
			}
			String times = allTimes.toString().trim();

			String query = "INSERT INTO blocked_off_dates (user_id, sunday, monday, tuesday, wednesday, thursday, friday, saturday) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userID);

			// Fill all weekdays with the full timestring
			for (int i = 2; i <= 8; i++) {
				ps.setString(i, times);
			}

			int rowsInserted = ps.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Blocked-off dates initialized with full schedule for user " + userID);
			} else {
				System.out.println("Failed to initialize blocked-off dates.");
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	public static boolean verifyUser(String email, String password) {

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
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
				return true;
			} else {
				System.out.println("Login failed: credentials not found.");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	public static Settings getSettings(String email) {

		Connection con = makeConnection();

		try {
			String query = "SELECT b.sunday, b.monday, b.tuesday, b.wednesday, b.thursday, b.friday, b.saturday FROM blocked_off_dates b JOIN user u ON b.user_id = u.user_id WHERE u.email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Settings set = new Settings();
				Map<Repeat, Set<LocalTime>> hm = set.getBod().getBlockedDates();

				for (Repeat day : Repeat.values()) {

					if (day.DAY == -1)
						continue;

					String timesRaw = rs.getString(day.toString().toLowerCase());
					Set<LocalTime> timeSet = new HashSet<>();

					if (timesRaw != null && !timesRaw.isEmpty()) {
						for (String lt : timesRaw.split(" ")) {
							if (!lt.equals("null") && !lt.isEmpty()) {
								timeSet.add(LocalTime.parse(lt));
							}
						}
					}

					hm.put(day, timeSet);
				}
				System.out.println(set.getBod().getBlockedDates().toString());
				return set;
			} else {
				System.out.println("No blocked-off dates found for user.");
				return null;
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public static void main(String[] args) {
//		DataBase.getSettings("chancekrueger@arizona.edu");
//	}

	public static boolean adjustBOD(String email, Settings set) {
		Connection con = makeConnection();

		try {
			// Step 1: get user_id using email
			String userIdQuery = "SELECT user_id FROM user WHERE email = ?";
			PreparedStatement psId = con.prepareStatement(userIdQuery);
			psId.setString(1, email);
			ResultSet rsId = psId.executeQuery();

			if (!rsId.next()) {
				System.out.println("Email not found, cannot update blocked-off dates.");
				return false;
			}

			int userId = rsId.getInt("user_id");

			// Step 2: prepare blocked-off strings
			Map<Repeat, Set<LocalTime>> bod = set.getBod().getBlockedDates();
			String[] days = { "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };
			String[] values = new String[7];

			for (int i = 0; i < days.length; i++) {
				Set<LocalTime> blocked = bod.getOrDefault(Repeat.dayOfWeek(i), new HashSet<>());
				StringBuilder sb = new StringBuilder();

				for (LocalTime time : blocked) {
					sb.append(time.toString()).append(" ");
				}

				values[i] = sb.toString().trim(); // Clean trailing space
			}

			// Step 3: update blocked_off_dates table
			String updateQuery = "UPDATE blocked_off_dates SET sunday = ?, monday = ?, tuesday = ?, wednesday = ?, thursday = ?, friday = ?, saturday = ? WHERE user_id = ?";
			PreparedStatement psUpdate = con.prepareStatement(updateQuery);

			for (int i = 0; i < 7; i++) {
				psUpdate.setString(i + 1, values[i]);
			}
			psUpdate.setInt(8, userId);

			int rowsAffected = psUpdate.executeUpdate();

			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
