package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

	public static void main(String[] args) {
		Connection con = null;
		try {
			// This line ensures the driver is loaded
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/CalendarDataBase?useSSL=false&serverTimezone=UTC",
				"root",
				"Freeman46!"
			);

			if (con != null) {
				System.out.println("connected successfully to database");
			}

		} catch (Exception e) {
			System.out.println("not connected to database");
			e.printStackTrace();
		}
	}
}
