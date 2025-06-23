package model;

/**
 * The {@code Account} class represents a user account within the calendar
 * system. It manages user authentication details such as hashed passwords,
 * security questions, and salt for hashing, along with login tracking.
 *
 * <p>
 * All sensitive information is stored using salted hashing to improve security.
 * The class supports account creation, password changes, and login attempt
 * tracking. It also includes an inner {@code Question} enum to represent preset
 * security questions.
 *
 * <p>
 * This class is closely tied to the {@code AccountDataBase} class, which
 * handles password hashing and salt generation.
 *
 * <p>
 * <b>Instance Variables:</b>
 * <ul>
 * <li>{@code acctName} — the account's username</li>
 * <li>{@code salt} — unique cryptographic salt for hashing</li>
 * <li>{@code acctPassword} — hashed password</li>
 * <li>{@code loginAttempts} — count of failed login attempts</li>
 * <li>{@code question} — selected security question</li>
 * <li>{@code hashedSecurityAnswer} — hashed security answer</li>
 * </ul>
 *
 * @see AccountDataBase
 * @see model.Calendar
 * 
 * @author Teddie Anest - (used in group project)
 */
public class Account {

	/**
	 * The {@code Question} enum represents preset security questions identified by
	 * an integer value (1–4). Provides methods to convert between string/int values
	 * and enum constants.
	 */
	public enum Question {

		ONE(1), TWO(2), THREE(3), FOUR(4);

		private final int value;

		/**
		 * Constructs a {@code Question} enum with the given numerical value.
		 *
		 * @param value the question number
		 */
		Question(int value) {
			this.value = value;
		}

		/**
		 * Returns the question's number as a string.
		 *
		 * @return string value of the question number
		 */
		public String getValue() {
			return String.valueOf(value);
		}

		/**
		 * Converts an integer to its corresponding {@code Question} enum.
		 *
		 * @param num integer question number
		 * @return corresponding {@code Question}
		 * @throws IllegalArgumentException if the number is invalid
		 */
		public static Question fromInt(int num) {
			for (Question q : Question.values()) {
				if (q.value == num) {
					return q;
				}
			}
			throw new IllegalArgumentException("Invalid Question number: " + num);
		}

		/**
		 * Converts a string to its corresponding {@code Question} enum.
		 *
		 * @param num string representation of the question number
		 * @return corresponding {@code Question}
		 * @throws IllegalArgumentException if the string is invalid
		 */
		public static Question fromString(String num) {
			for (Question q : Question.values()) {
				if (q.getValue().equals(num)) {
					return q;
				}
			}
			throw new IllegalArgumentException("Invalid Question number: " + num);
		}
	}

	private String acctName;
	private String salt;
	private String acctPassword;
	private int loginAttempts;
	private Question question;
	private String hashedSecurityAnswer;

	/**
	 * Constructs a new account and performs hashing of password and security
	 * answer.
	 *
	 * @param acctName       the username
	 * @param acctPassword   the plain-text password
	 * @param securityAnswer the plain-text answer to the selected security question
	 * @param question       the selected security question
	 */
	public Account(String acctName, String acctPassword, String securityAnswer, Question question) {
		this.salt = AccountDataBase.generateSalt();
		this.acctName = acctName;
		this.acctPassword = AccountDataBase.hashPassword(acctPassword, salt);
		this.hashedSecurityAnswer = AccountDataBase.hashPassword(securityAnswer, salt);
		this.loginAttempts = 0;
		this.question = question;
	}

	/**
	 * Full constructor for rehydrating existing accounts from saved data.
	 *
	 * @param acctName       the username
	 * @param acctPassword   the hashed password
	 * @param salt           the saved salt value
	 * @param securityAnswer the hashed security answer
	 * @param question       the selected security question
	 * @param loginAttempts  the number of previous failed login attempts
	 */
	public Account(String acctName, String acctPassword, String salt, String securityAnswer, Question question,
			int loginAttempts) {
		this.acctName = acctName;
		this.salt = salt;
		this.acctPassword = acctPassword;
		this.loginAttempts = loginAttempts;
		this.hashedSecurityAnswer = securityAnswer;
		this.question = question;
	}

	public String getUsername() {
		return acctName;
	}

	public String getSalt() {
		return salt;
	}

	public String getHashedPassword() {
		return acctPassword;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public String getHashedSecurityAnswer() {
		return hashedSecurityAnswer;
	}

	public Question getQuestion() {
		return question;
	}

	/**
	 * Increments the login attempts counter by one.
	 */
	public void addLoginAttempt() {
		this.loginAttempts += 1;
	}

	/**
	 * Sets the number of login attempts to a specific value.
	 *
	 * @param attempts the number to set
	 */
	public void setLoginAttempts(int attempts) {
		this.loginAttempts = attempts;
	}

	/**
	 * Directly sets the hashed password. This should generally be avoided in favor
	 * of {@code changePassword()}.
	 *
	 * @param hashedPassword the new hashed password
	 */
	public void setHashedPassword(String hashedPassword) {
		this.acctPassword = hashedPassword;
	}

	/**
	 * Changes the user's password using a new plain-text password, re-hashing it
	 * with the existing salt.
	 *
	 * @param plainTextPassword the new plain-text password
	 */
	public void changePassword(String plainTextPassword) {
		this.acctPassword = AccountDataBase.hashPassword(plainTextPassword, this.salt);
	}
}
