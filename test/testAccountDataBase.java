package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Account;
import model.Account.Question;
import model.AccountDataBase;

class testAccountDataBase {
	AccountDataBase db = new AccountDataBase();

	@Test
	void testAddUser() {
		Account user = new Account("username", "password", "answer", Question.ONE);
		db.addUser(user);
	}

	@Test
	void testGetUser() {
		Account user = new Account("username", "password", "answer", Question.ONE);
		assertEquals(db.getUser("username").getUsername(), "username");
	}

	@Test
	void testUpdateUser() {
		Account user = new Account("username", "password", "answer", Question.ONE);
		db.updateUser("username", user);
		assertEquals(user.getUsername(), "username");
		assertEquals(user.getLoginAttempts(), 0);
		assertEquals(user.getHashedPassword(), AccountDataBase.hashPassword("password", user.getSalt()));
	}

	@Test
	void testVerifyUniqueUsername() {
		Account user = new Account("username", "password", "answer", Question.ONE);
		assertTrue(db.verifyUniqueUsername("username2"));
		assertFalse(db.verifyUniqueUsername("username"));
	}

	@Test
	void testVerifyStrongPassword() {
		assertTrue(db.verifyStrongPassword("Password!123"));
		assertFalse(db.verifyStrongPassword("password"));
	}

	@Test
	void testAuthenticate() {
		Account user = new Account("username", "password", "answer", Question.ONE);
		assertTrue(db.authenticate("username", "password"));
		assertFalse(db.authenticate("un", "password"));
		assertFalse(db.authenticate("username", "wrongPassword"));
	}

	@Test
	void testAuthenticateSecurityQuestion() {
		Account user = new Account("username", "password", "answer", Question.ONE);
		assertTrue(db.authenticateSecurityQuestion("username", "answer"));
		assertFalse(db.authenticateSecurityQuestion("username", "wrongAnswer"));
	}

}