package model;

public class Account {

	public enum Question {

		ONE(1), TWO(2), THREE(3), FOUR(4);

		private final int value;

		// Constructor
		Question(int value) {
			this.value = value;
		}

		// getter to retrieve the integer value
		public String getValue() {
			return String.valueOf(value);
		}

		// convert int to Enum
		public static Question fromInt(int num) {
			for (Question q : Question.values()) {
				if (q.value == num) {
					return q;
				}
			}
			throw new IllegalArgumentException("Invalid Question number: " + num);
		}

		// convert String to Enum
		public static Question fromString(String num) {
			for (Question q : Question.values()) {
				if (q.getValue().equals(num)) {
					return q;
				}
			}
			throw new IllegalArgumentException("Invalid Question number: " + num);
		}

	}

//	Account -> which will determine which Calendar to be used.
//	
//	Parse Data
//	Save Data
//	Account Data (Will read the account file)
//	
//	- Everything needs to be salted and hashed to ensure privacy

	private String acctName;
	private String salt;
	private String acctPassword;
	private int loginAttempts;
	private Question question;
	private String hashedSecurityAnswer;

	public Account(String acctName, String acctPassword, String securityAnswer, Question question) {
		this.salt = AccountDataBase.generateSalt();
		this.acctName = acctName; // AccountDataBase.hashPassword(acctName, salt);
		this.acctPassword = AccountDataBase.hashPassword(acctPassword, salt);
		this.hashedSecurityAnswer = AccountDataBase.hashPassword(securityAnswer, salt);
		this.loginAttempts = 0;
		this.question = question;

	}

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

	public void addLoginAttempt() {
		this.loginAttempts += 1;
	}

	public void setLoginAttempts(int attempts) {
		this.loginAttempts = attempts;
	}

	public void setHashedPassword(String hashedPassword) {
		this.acctPassword = hashedPassword;
	}

	public void changePassword(String plainTextPassword) {
		this.acctPassword = AccountDataBase.hashPassword(plainTextPassword, this.salt);
	}

}
