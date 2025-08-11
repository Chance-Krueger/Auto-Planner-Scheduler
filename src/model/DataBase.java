package model;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	public static boolean addEventToUserCalendar(String email, Event e, boolean isOriginal) {
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

			// Step 3: Handle repeat logic
			if (isOriginal) {
				expandRepeats(email, e);
			}
			return true;
		} catch (

		Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private static void expandRepeats(String email, Event e) {
		String repeat = e.getRepeat().toString();
		if (repeat.equals("None"))
			return;

		LocalDate startDate;
		List<LocalDate> repeatDates = new ArrayList<>();
		int currentYear = LocalDate.now().getYear();

		if (e instanceof MeetingAppt m) {
			startDate = m.getDate();
		} else if (e instanceof ProjAssn p) {
			startDate = p.getDue().toLocalDate();
		} else {
			return;
		}

		int targetDay = startDate.getDayOfMonth(); // Used for monthly repeat

		switch (repeat) {
		case "Everyday": {
			int maxRepeats = 365;
			int count = 0;
			for (LocalDate d = startDate.plusDays(1); d.getYear() == currentYear
					&& count < maxRepeats; d = d.plusDays(1), count++) {
				repeatDates.add(d);
			}
			break;
		}
		case "Every Week":
			for (LocalDate d = startDate.plusWeeks(1); d.getYear() == currentYear; d = d.plusWeeks(1)) {
				repeatDates.add(d);
			}
			break;
		case "Every 2 Weeks":
			for (LocalDate d = startDate.plusWeeks(2); d.getYear() == currentYear; d = d.plusWeeks(2)) {
				repeatDates.add(d);
			}
			break;
		case "Every Month":
			for (LocalDate d = startDate.plusMonths(1); d.getYear() == currentYear; d = d.plusMonths(1)) {
				int lastDay = d.lengthOfMonth();
				int dayToUse = Math.min(targetDay, lastDay);
				LocalDate adjustedDate = LocalDate.of(d.getYear(), d.getMonth(), dayToUse);
				repeatDates.add(adjustedDate);
			}
			break;
		case "Every Year":
			for (LocalDate d = startDate.plusYears(1); d.getYear() == currentYear; d = d.plusYears(1)) {
				repeatDates.add(d);
			}
			break;
		}

		for (LocalDate d : repeatDates) {
			if (eventExists(email, e.getTitle(), d))
				continue;

			Event repeatEvent = null;

			if (e instanceof MeetingAppt m) {
				repeatEvent = new MeetingAppt(m.getTitle(), d, m.getStartTime(), m.getEndTime());
			} else if (e instanceof ProjAssn p) {
				repeatEvent = new ProjAssn(p.getTitle(), p.getPriority(), p.getTime(),
						LocalDateTime.of(d, p.getDue().toLocalTime()));
			}

			if (repeatEvent != null) {
				repeatEvent.setLocation(e.getLocation());
				repeatEvent.setUrl(e.getUrl());
				repeatEvent.setNotes(e.getNotes());
				repeatEvent.setRepeat(e.getRepeat());
				addEventToUserCalendar(email, repeatEvent, false); // Prevent recursive expansion
			}
		}
	}

	public static boolean eventExists(String email, String title, LocalDate date) {
		String query = """
				    SELECT COUNT(*) FROM events e
				    JOIN meeting_appt_events m ON e.cal_id = m.cal_id
				    JOIN user u ON e.user_id = u.user_id
				    WHERE u.email = ? AND e.title = ? AND m.date = ?
				""";

		try (Connection conn = DataBase.makeConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, email);
			stmt.setString(2, title);
			stmt.setString(3, date.toString());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
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
		Connection con = makeConnection();
		Calendar cal = new Calendar();

		try {
			// Step 1: Get user_id
			PreparedStatement psId = con.prepareStatement("SELECT user_id FROM user WHERE email = ?");
			psId.setString(1, email);
			ResultSet rsId = psId.executeQuery();
			if (!rsId.next())
				return null;

			int userId = rsId.getInt("user_id");

			// Step 2: Get all events for user
			String eventQuery = "SELECT * FROM events WHERE user_id = ?";
			PreparedStatement psEvents = con.prepareStatement(eventQuery);
			psEvents.setInt(1, userId);
			ResultSet rsEvents = psEvents.executeQuery();

			while (rsEvents.next()) {
				int calId = rsEvents.getInt("cal_id");
				String title = rsEvents.getString("title");
				String url = rsEvents.getString("url");
				String notes = rsEvents.getString("notes");
				String location = rsEvents.getString("location");
				String repeat = rsEvents.getString("repeat");
				String type = rsEvents.getString("event_type");

				if ("ProjAssn".equalsIgnoreCase(type)) {
					PreparedStatement psProj = con
							.prepareStatement("SELECT * FROM project_assn_events WHERE cal_id = ?");
					psProj.setInt(1, calId);
					ResultSet rsProj = psProj.executeQuery();
					if (rsProj.next()) {
						Priority priority = Priority.fromToString(rsProj.getString("priority"));
						String estimate = rsProj.getString("time");
						LocalDateTime due = LocalDateTime.parse(rsProj.getString("due"));
						Duration duration = Duration.parse(estimate); // parse ISO-8601 format safely

						ProjAssn pa = new ProjAssn(title, priority, duration, due);

						pa.setLocation(location);
						pa.setUrl(url);
						pa.setNotes(notes);
						pa.setRepeat(Repeat.checkRepeatFromString(repeat));
						cal.addEventToCalendar(pa);
					}
				} else if ("MeetingAppt".equalsIgnoreCase(type)) {
					PreparedStatement psMeet = con
							.prepareStatement("SELECT * FROM meeting_appt_events WHERE cal_id = ?");
					psMeet.setInt(1, calId);
					ResultSet rsMeet = psMeet.executeQuery();
					if (rsMeet.next()) {
						LocalDate date = rsMeet.getDate("date").toLocalDate();
						LocalTime start = LocalTime.parse(rsMeet.getString("start_time"));
						LocalTime end = LocalTime.parse(rsMeet.getString("end_time"));

						MeetingAppt ma = new MeetingAppt(title, date, start, end);
						ma.setLocation(location);
						ma.setUrl(url);
						ma.setNotes(notes);
						ma.setRepeat(Repeat.checkRepeatFromString(repeat));
						cal.addEventToCalendar(ma);
					}
				} else {
					System.err.println("Unknown event type for cal_id: " + calId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cal;
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
//		Calendar c = DataBase.getUserCalendar("chancekrueger@arizona.edu");
//		System.out.println(c.getCalendar().toString());
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

	public static boolean updateEventInUserCalendar(String email, Event e, ProjAssn updated) {
		try (Connection con = makeConnection()) {
			int userId = getUserID(email);
			if (userId <= 0)
				return false;

			// Step 1: Get cal_id from events table
			String query = "SELECT cal_id FROM events WHERE user_id = ? AND title = ? AND event_type = ?";
			PreparedStatement psFind = con.prepareStatement(query);
			psFind.setInt(1, userId);
			psFind.setString(2, e.getTitle());
			psFind.setString(3, "ProjAssn");

			ResultSet rs = psFind.executeQuery();
			if (!rs.next())
				return false;

			int eventId = rs.getInt("cal_id");

			// Step 2: Update shared event data
			String updateEvent = "UPDATE events SET title = ?, location = ?, `repeat` = ?, notes = ?, url = ? WHERE cal_id = ? AND user_id = ?";
			PreparedStatement psEvent = con.prepareStatement(updateEvent);
			psEvent.setString(1, updated.getTitle());
			psEvent.setString(2, updated.getLocation());
			psEvent.setString(3, updated.getRepeat().toString());
			psEvent.setString(4, updated.getNotes());
			psEvent.setString(5, updated.getUrl());
			psEvent.setInt(6, eventId);
			psEvent.setInt(7, userId);
			psEvent.executeUpdate();

			// Step 3: Update project_assn_events table
			String updateProj = "UPDATE project_assn_events SET priority = ?, time = ?, due = ? WHERE cal_id = ? AND user_id = ?";
			PreparedStatement psProj = con.prepareStatement(updateProj);
			psProj.setString(1, updated.getPriority().toString());
			psProj.setString(2, updated.getTime().toString());
			psProj.setString(3, updated.getDue().toString());
			psProj.setInt(4, eventId);
			psProj.setInt(5, userId);
			psProj.executeUpdate();

			// Is a repeat
			if (!updated.getRepeat().TOSTRING.toString().equals("None")) {
				// FIX REPEAT LOGIC
				// IF DAILY, WEEKLY, EVERY OTHER WEEK -> Make it end by End of Year of when
				// event made
				// IF MONTHY -> ONLY DO 10 YEARS
				// IF YEALRY, ONLY DO IT FOR 100 YEARS
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean updateEventInUserCalendar(String email, Event original, MeetingAppt updated,
			boolean applyToAllFuture) {
		try (Connection con = makeConnection()) {
			int userId = getUserID(email);
			if (userId <= 0)
				return false;

			boolean repeatChanged = !original.getRepeat().equals(updated.getRepeat());

			if (applyToAllFuture || repeatChanged) {
				// Step 1: Delete current and future events
				deleteEventsFromDataBase(email, original, true);

				// Step 2: Save the updated event
				addEventToUserCalendar(email, updated, false); // false = don't expand yet

				// Step 3: Expand new repeat logic
				expandRepeats(email, updated);
			} else {
				// Update only the current instance
				String query = "SELECT cal_id FROM events WHERE user_id = ? AND title = ? AND event_type = ?";
				PreparedStatement psFind = con.prepareStatement(query);
				psFind.setInt(1, userId);
				psFind.setString(2, original.getTitle());
				psFind.setString(3, "MeetingAppt");

				ResultSet rs = psFind.executeQuery();
				if (!rs.next())
					return false;

				int eventId = rs.getInt("cal_id");

				String updateEvent = "UPDATE events SET title = ?, location = ?, `repeat` = ?, notes = ?, url = ? WHERE cal_id = ? AND user_id = ?";
				PreparedStatement psEvent = con.prepareStatement(updateEvent);
				psEvent.setString(1, updated.getTitle());
				psEvent.setString(2, updated.getLocation());
				psEvent.setString(3, updated.getRepeat().toString());
				psEvent.setString(4, updated.getNotes());
				psEvent.setString(5, updated.getUrl());
				psEvent.setInt(6, eventId);
				psEvent.setInt(7, userId);
				psEvent.executeUpdate();

				String updateMeeting = "UPDATE meeting_appt_events SET date = ?, start_time = ?, end_time = ? WHERE cal_id = ? AND user_id = ?";
				PreparedStatement psMeeting = con.prepareStatement(updateMeeting);
				psMeeting.setString(1, updated.getDate().toString());
				psMeeting.setString(2, updated.getStartTime().toString());
				psMeeting.setString(3, updated.getEndTime().toString());
				psMeeting.setInt(4, eventId);
				psMeeting.setInt(5, userId);
				psMeeting.executeUpdate();
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// MAKE IT TO DELTE
	public static void deleteEventsFromDataBase(String email, Event e, boolean deleteAllFuture) {
		try (Connection con = makeConnection()) {
			int userId = getUserID(email);
			if (userId <= 0) {
				System.err.println("Invalid user ID for email: " + email);
				return;
			}

			String baseQuery = "SELECT cal_id, event_type FROM events WHERE user_id = ? AND title = ? AND location = ? AND `repeat` = ? AND notes = ? AND url = ?";
			PreparedStatement psFind = con.prepareStatement(baseQuery);
			psFind.setInt(1, userId);
			psFind.setString(2, e.getTitle());
			psFind.setString(3, e.getLocation());
			psFind.setString(4, e.getRepeat().toString());
			psFind.setString(5, e.getNotes());
			psFind.setString(6, e.getUrl());

			ResultSet rs = psFind.executeQuery();

			while (rs.next()) {
				int calId = rs.getInt("cal_id");
				String type = rs.getString("event_type");

				boolean shouldDelete = false;

				if ("MeetingAppt".equalsIgnoreCase(type)) {
					PreparedStatement psDate = con
							.prepareStatement("SELECT date FROM meeting_appt_events WHERE cal_id = ?");
					psDate.setInt(1, calId);
					ResultSet rsDate = psDate.executeQuery();
					if (rsDate.next()) {
						LocalDate eventDate = LocalDate.parse(rsDate.getString("date"));
						LocalDate targetDate = ((MeetingAppt) e).getDate();
						shouldDelete = deleteAllFuture ? !eventDate.isBefore(targetDate) : eventDate.equals(targetDate);
					}
				} else if ("ProjAssn".equalsIgnoreCase(type)) {
					PreparedStatement psDate = con
							.prepareStatement("SELECT due FROM project_assn_events WHERE cal_id = ?");
					psDate.setInt(1, calId);
					ResultSet rsDate = psDate.executeQuery();
					if (rsDate.next()) {
						LocalDate eventDate = LocalDateTime.parse(rsDate.getString("due")).toLocalDate();
						LocalDate targetDate = ((ProjAssn) e).getDue().toLocalDate();
						shouldDelete = deleteAllFuture ? !eventDate.isBefore(targetDate) : eventDate.equals(targetDate);
					}
				}

				if (shouldDelete) {
					if ("MeetingAppt".equalsIgnoreCase(type)) {
						PreparedStatement psDeleteMeet = con
								.prepareStatement("DELETE FROM meeting_appt_events WHERE cal_id = ? AND user_id = ?");
						psDeleteMeet.setInt(1, calId);
						psDeleteMeet.setInt(2, userId);
						psDeleteMeet.executeUpdate();
					} else if ("ProjAssn".equalsIgnoreCase(type)) {
						PreparedStatement psDeleteProj = con
								.prepareStatement("DELETE FROM project_assn_events WHERE cal_id = ? AND user_id = ?");
						psDeleteProj.setInt(1, calId);
						psDeleteProj.setInt(2, userId);
						psDeleteProj.executeUpdate();
					}

					PreparedStatement psDeleteEvent = con
							.prepareStatement("DELETE FROM events WHERE cal_id = ? AND user_id = ?");
					psDeleteEvent.setInt(1, calId);
					psDeleteEvent.setInt(2, userId);
					psDeleteEvent.executeUpdate();
				}
			}

			System.out.println("Event deletion completed for user: " + email);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
