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

/**
 * The {@code AccountDataBase} class manages persistent user account data,
 * including secure password storage, authentication, and validation. It handles
 * reading from and writing to a backing file (Accounts.txt) and provides
 * utility methods for password hashing with salt, account creation, and user
 * verification.
 *
 * <p>
 * Core responsibilities include:
 * <ul>
 * <li>Adding and retrieving {@link Account} objects</li>
 * <li>Verifying username uniqueness and password strength</li>
 * <li>Hashing passwords and answers with a random salt using SHA-256</li>
 * <li>Saving and loading user data to and from the filesystem</li>
 * </ul>
 *
 * <p>
 * All user credentials and security answers are salted and hashed for privacy.
 * Hashing is handled via the {@link #hashPassword(String, String)} method using
 * SHA-256.
 *
 * @see Account
 * @see Account.Question
 * @see java.security.SecureRandom
 * @see java.security.MessageDigest
 * @see java.util.Base64
 * @see java.io.File
 * 
 * @author Teddie Anest - (used in group project)
 */
public class AccountDataBase {

	private HashMap<String, Account> users; // maps usernames to Account objects
	private final String databaseFile = "Accounts.txt";

	/**
	 * Constructs an {@code AccountDataBase} object and loads existing users from
	 * file.
	 */
	public AccountDataBase() {
		users = new HashMap<>();
		loadUsers();
	}

	/**
	 * Adds a new {@code Account} to the database and saves to file.
	 *
	 * @param user the account to add
	 */
	public void addUser(Account user) {
		users.put(user.getUsername(), user);
		saveUsers();
	}

	/**
	 * Returns a copy of the {@code Account} associated with the given username.
	 * This ensures the returned account is detached from internal mutation.
	 *
	 * @param username the username to retrieve
	 * @return a copy of the matching {@code Account}, or {@code null} if not found
	 */
	public Account getUser(String username) {
		Account user = users.get(username);
		if (user == null)
			return null;
		return new Account(username, user.getHashedPassword(), user.getSalt(), user.getHashedSecurityAnswer(),
				user.getQuestion(), user.getLoginAttempts());
	}

	/**
	 * Updates a user's password and resets login attempts.
	 *
	 * @param username the user to update
	 * @param user     the updated account object
	 */
	public void updateUser(String username, Account user) {
		Account original = users.get(username);
		original.setLoginAttempts(0);
		original.setHashedPassword(user.getHashedPassword());
	}

	/**
	 * Checks whether a given username is unique (i.e., not already in use).
	 *
	 * @param attemptedUsername the username to test
	 * @return {@code true} if the username is unique, {@code false} otherwise
	 */
	public boolean verifyUniqueUsername(String attemptedUsername) {
		return !users.containsKey(attemptedUsername);
	}

	/**
	 * Validates the strength of a password. A strong password must:
	 * <ul>
	 * <li>Be at least 8 characters long</li>
	 * <li>Contain at least one number</li>
	 * <li>Contain at least one special character</li>
	 * <li>Contain at least one uppercase letter</li>
	 * </ul>
	 *
	 * @param plainTextPassword the password to test
	 * @return {@code true} if the password meets all criteria
	 */
	public static boolean verifyStrongPassword(String plainTextPassword) {
		boolean length = plainTextPassword.length() >= 8;
		boolean hasNumber = plainTextPassword.matches(".*[0-9].*");
		boolean hasSpecial = plainTextPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
		boolean hasUppercase = plainTextPassword.matches(".*[A-Z].*");
		return length && hasNumber && hasSpecial && hasUppercase;
	}

	/**
	 * Authenticates a user by comparing a hashed version of the input password with
	 * the stored hashed password.
	 *
	 * @param username          the account username
	 * @param plainTextPassword the password to validate
	 * @return {@code true} if authentication succeeds
	 */
	public boolean authenticate(String username, String plainTextPassword) {
		Account user = users.get(username);
		if (user == null)
			return false;

		String hashToTest = hashPassword(plainTextPassword, user.getSalt());

		if (!hashToTest.equals(user.getHashedPassword())) {
			user.addLoginAttempt();
			return false;
		}

		return true;
	}

	/**
	 * Verifies the user's answer to their security question.
	 *
	 * @param username       the account username
	 * @param securityAnswer the plain-text answer to check
	 * @return {@code true} if the answer is correct
	 */
	public boolean authenticateSecurityQuestion(String username, String securityAnswer) {
		Account user = users.get(username);
		String hashedSAToTest = hashPassword(securityAnswer, user.getSalt());
		return hashedSAToTest.equals(user.getHashedSecurityAnswer());
	}

	/**
	 * Generates a random salt using {@link SecureRandom} and encodes it in Base64.
	 *
	 * @return the generated salt string
	 */
	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	/**
	 * Hashes the given password with the specified salt using SHA-256 and encodes
	 * the result in Base64.
	 *
	 * @param password the plain-text password
	 * @param salt     the salt to use
	 * @return the hashed password string
	 */
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

	/**
	 * Saves all accounts to the file specified by {@code databaseFile}.
	 */
	public void saveUsers() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile))) {
			for (Account user : users.values()) {
				writer.write(user.getUsername() + "," + user.getHashedPassword() + "," + user.getSalt() + ","
						+ user.getHashedSecurityAnswer() + "," + user.getQuestion().getValue() + ","
						+ user.getLoginAttempts());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error saving user data: " + e.getMessage());
		}
	}

	/**
	 * Loads all user accounts from {@code databaseFile} into memory.
	 */
	public void loadUsers() {
		File file = new File(databaseFile);
		if (!file.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String username = parts[0];
				String hashedPassword = parts[1];
				String salt = parts[2];
				String hashedSecurityAnswer = parts[3];
				String question = parts[4];
				int attempts = Integer.parseInt(parts[5]);

				users.put(username, new Account(username, hashedPassword, salt, hashedSecurityAnswer,
						Question.fromString(question), attempts));
			}
		} catch (IOException e) {
			System.err.println("Error loading user data: " + e.getMessage());
		}
	}

	/**
	 * Main method for testing purposes.
	 *
	 * @param args command-line arguments (unused)
	 */
	public static void main(String[] args) {
		// Optional test entry point
	}
}
