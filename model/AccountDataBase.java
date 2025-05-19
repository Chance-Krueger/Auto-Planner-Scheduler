package model;

import java.util.Base64;

import java.util.HashMap;

import model.Account.Question;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class AccountDataBase {

	private HashMap<String, Account> users; // maps user names to User objects
	private final String databaseFile = "Accounts.txt";

	public AccountDataBase() {
		users = new HashMap<String, Account>();
		loadUsers();
	}

	// adds a user to the database
	public void addUser(Account user) {
		users.put(user.getUsername(), user);
		saveUsers();
	}

	// users are mutable so we need a copy
	public Account getUser(String username) {
		Account user = users.get(username);
		Account user_copy = new Account(username, user.getHashedPassword(), user.getSalt(),
				user.getHashedSecurityAnswer(), user.getQuestion(), user.getLoginAttempts());
		return user_copy;
	}

	// handles updates if password is changed and resets login attempts
	public void updateUser(String username, Account user) {
		Account original = users.get(username);
		original.setLoginAttempts(0);
		original.setHashedPassword(user.getHashedPassword());
	}

	// determines if a user name exists already
	public boolean verifyUniqueUsername(String attemptedUsername) {
		for (String username : users.keySet()) {
			if (attemptedUsername.equals(username)) {
				return false;
			}
		}
		return true;
	}

	// ensures that the attempted password is strong
	public boolean verifyStrongPassword(String plainTextPassword) {
		boolean length = plainTextPassword.length() >= 8;
		boolean hasNumber = plainTextPassword.matches(".*[0123456789].*");
		boolean hasSpecial = plainTextPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
		boolean hasUppercase = plainTextPassword.matches(".*[A-Z].*");
		return length && hasNumber && hasSpecial && hasUppercase;
	}

	// compares the inputed info with the expected info
	public boolean authenticate(String username, String plainTextPassword) {
		Account user = users.get(username);
		if (user == null) {
			return false;
		}
		String hashToTest = hashPassword(plainTextPassword, user.getSalt());

		// add a log in attempt if the user is not authenticated
		if (hashToTest.equals(user.getHashedPassword()) == false) {
			user.addLoginAttempt();
		}

		return hashToTest.equals(user.getHashedPassword());
	}

	// authenticates the security question
	public boolean authenticateSecurityQuestion(String username, String securityAnswer) {
		Account user = users.get(username);
		String hashedSAToTest = hashPassword(securityAnswer, user.getSalt());
		if (hashedSAToTest.equals(user.getHashedSecurityAnswer())) {
			return true;
		} else {
			return false;
		}
	}

	// generates a random salt, which is appended to passwords
	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	// hashes a password (and uses the salt)
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String saltedPassword = salt + password;
			byte[] hash = digest.digest(saltedPassword.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			throw new RuntimeException("Error hashing password", e);
		}
	}

	// saves user data to DATABASE.txt file (user name, password, salt)
	public void saveUsers() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile))) {
			for (Account user : users.values()) {
				writer.write(user.getUsername() + "," + user.getHashedPassword() + "," + user.getSalt() + ","
						+ user.getHashedSecurityAnswer() + "," + user.getQuestion().getValue() + ","
						+ String.valueOf(user.getLoginAttempts()));
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error saving user data: " + e.getMessage());
		}
	}

	// loads user data from DATABASE.txt (populates HashMap)
	public void loadUsers() {
		File file = new File(databaseFile);
		if (!file.exists()) {
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String username = parts[0];
				String hashedPassword = parts[1];
				String salt = parts[2];
				String hashedSecurityAnswer = parts[3];
				String question = parts[4];
				String attempts = parts[5];
				users.put(username, new Account(username, hashedPassword, salt, hashedSecurityAnswer,
						Question.fromString(question), Integer.valueOf(attempts)));
			}
		} catch (IOException e) {
			System.err.println("Error loading user data: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
	}
}
