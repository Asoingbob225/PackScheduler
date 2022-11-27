/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;

/**
 * Tests the faculty object
 * @author Adharsh Rajagopal
 *
 */
public class FacultyTest {
	/** Test Faculty's first name. */
	private String firstName = "first";
	/** Test Faculty's last name */
	private String lastName = "last";
	/** Test Faculty's id */
	private String id = "flast";
	/** Test Faculty's email */
	private String email = "first_last@ncsu.edu";
	/** Test Faculty's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	// This is a block of code that is executed when the StudentTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the StudentTest
	// object is
	// constructed. By automating the hash of the plaintext password, we are
	// not tied to a specific hash implementation. We can change the algorithm
	// easily.

	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}

//	/**
//	 * Test Faculty's.compareTo() implementation against different students.
//	 */
//	@Test
//	void testCompareTo() {
//		// Students should be sorted in order of their initialization
//		Student s1 = new Student("zzz", "aaa", "zzz", "email@email.com", "pw", 15);
//		Student s2 = new Student("aaa", "zzz", "zzz", "email@email.com", "pw", 15);
//		Student s3 = new Student("zzz", "zzz", "aaa", "email@email.com", "pw", 15);
//		Student s4 = new Student("zzz", "zzz", "zzz", "email@email.com", "pw", 15);
//
//		// The instance is less than the parameter -> return negative
//		assertTrue(0 < s2.compareTo(s1));
//		assertTrue(0 < s3.compareTo(s2));
//		assertTrue(0 < s4.compareTo(s3));
//		assertTrue(0 < s4.compareTo(s1));
//
//		// The instance is greater than the parameter -> return positive
//		assertTrue(0 > s1.compareTo(s2));
//		assertTrue(0 > s2.compareTo(s3));
//		assertTrue(0 > s3.compareTo(s4));
//		assertTrue(0 > s1.compareTo(s4));
//	}

	/**
	 * Test invalid ID handling for null and empty input
	 * 
	 * @param invalidId a null or empty id
	 */
	@ParameterizedTest
	@NullAndEmptySource
	void testSetIdInvalid(String invalidId) {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Faculty("first", "last", invalidId, "email@email.com", "pw", 2),
				"Expected exception but none was thrown");
		assertEquals("Invalid id", e.getMessage(), "Wrong exception message for IllegalArgumentException");
	}

	/**
	 * Test invalid last name handling for null and empty input
	 * 
	 * @param invalidLastName a null or empty last name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	void testSetLastNameInvalid(String invalidLastName) {
		User s = new Faculty("first", "last", "id", "email@email.com", "pw", 2);

		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setLastName(invalidLastName),
				"Expected exception but none was thrown");
		assertEquals("Invalid last name", e.getMessage(), "Wrong exception message for IllegalArgumentException");
	}

	/**
	 * Tests hashCode
	 */
	@Test
	void testHashCode() {
		User a = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User b = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User c = new Faculty("name", lastName, id, email, hashPW, 2);
		User d = new Faculty(firstName, "name", id, email, hashPW, 2);
		User e = new Faculty(firstName, lastName, "rcmidg", email, hashPW, 2);
		User f = new Faculty(firstName, lastName, id, "email@email.com", hashPW, 2);
		User g = new Faculty(firstName, lastName, id, email, "pw", 2);
		User h = new Faculty(firstName, lastName, id, email, hashPW, 1);

		// identical should have same hash
		assertEquals(a.hashCode(), b.hashCode());

		// different students different hash
		assertNotEquals(b.hashCode(), c.hashCode());
		assertNotEquals(c.hashCode(), d.hashCode());
		assertNotEquals(d.hashCode(), e.hashCode());
		assertNotEquals(e.hashCode(), f.hashCode());
		assertNotEquals(f.hashCode(), g.hashCode());
		assertNotEquals(g.hashCode(), h.hashCode());
		assertNotEquals(h.hashCode(), a.hashCode());

	}

	/**
	 * Tests Faculty constructor with all fields
	 */
	@Test
	void testFacultyStringStringStringStringStringInt() {
		Faculty validFaculty = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, hashPW, 3),
				"Valid student should not throw exception");
		assertEquals("first", validFaculty.getFirstName(), "Invalid first name");
		assertEquals("last", validFaculty.getLastName(), "");
		assertEquals(id, validFaculty.getId());
		assertEquals(email, validFaculty.getEmail());
		assertEquals(hashPW, validFaculty.getPassword());
		assertEquals(3, validFaculty.getMaxCourses());

	}

	/**
	 * Tests setEmail with valid input
	 */
	@Test
	void testSetEmailValid() {
		User s = new Faculty("first", "last", "id", "email@email.com", "pw", 2);

		assertDoesNotThrow(() -> s.setEmail("alsovalid@rmail.com"));
		assertEquals("alsovalid@rmail.com", s.getEmail(), "incorrect email");
	}

	/**
	 * Tests setEmail with emails that hit all the invalid qualifications
	 * 
	 * @param invalidEmail an email not meeting email requirements to be valid
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "name", "email.com@name", "email.com", "Name@email" })
	void testSetEmailInvalid(String invalidEmail) {
		User s = new Faculty("first", "last", "id", "email@email.com", "pw", 2);

		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail(invalidEmail));
		assertEquals("Invalid email", e.getMessage(),
				"Incorrect exception thrown with invalid email - " + invalidEmail);

		assertEquals("email@email.com", s.getEmail());
	}

	/**
	 * Tests setPassword for valid and invalid values
	 * 
	 * @param invalidPassword a null or empty password
	 */
	@ParameterizedTest
	@NullAndEmptySource
	void testSetPassword(String invalidPassword) {
		User s = new Faculty(firstName, lastName, id, email, hashPW, 2);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setPassword(invalidPassword));
		assertEquals("Invalid password", e1.getMessage()); // Check correct exception message
		assertEquals(hashPW, s.getPassword()); // Check that first name didn't change
	}

	/**
	 * Tests setMaxCourses with an invalid value over 3
	 */
	@Test
	void testSetMaxCoursesInvalidOver() {
		Faculty s = new Faculty("first", "last", "id", "email@email.com", "pw", 2);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setMaxCourses(5));
		assertEquals("Invalid max courses", e1.getMessage());

	}

	/**
	 * Tests setMaxCourses with an invalid value under 3
	 */
	@Test
	void testSetMaxCoursesInvalidUnder() {
		Faculty s = new Faculty("first", "last", "id", "email@email.com", "pw", 2);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setMaxCourses(0));
		assertEquals("Invalid max courses", e1.getMessage());
	}

	/**
	 * Tests setfirstName with an invalid and empty input
	 */
	@Test
	void testSetFirstNameInvalidEmpty() {
		User s = new Faculty("first", "last", "id", "email@email.com", "pw", 3);

		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setFirstName(""));
		assertEquals("Invalid first name", e.getMessage());

		assertEquals("first", s.getFirstName());
	}

	/**
	 * Tests setFirstNameInvalidNull with an invalid and null input
	 */
	@Test
	void testSetFirstNameInvalidNull() {
		User s = new Faculty("first", "last", "id", "email@email.com", "pw", 2);

		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setFirstName(null));
		assertEquals("Invalid first name", e.getMessage());

		assertEquals("first", s.getFirstName());
	}

	/**
	 * Tests Equals
	 */
	@Test
	void testEqualsObject() {
		User a = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User b = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User c = new Faculty("name", lastName, id, email, hashPW, 2);
		User d = new Faculty(firstName, "name", id, email, hashPW, 2);
		User e = new Faculty(firstName, lastName, "rcmidg", email, hashPW, 2);
		User f = new Faculty(firstName, lastName, id, "email@email.com", hashPW, 2);
		User g = new Faculty(firstName, lastName, id, email, "pw", 2);
		User h = new Faculty(firstName, lastName, id, email, hashPW, 1);

		User nullFaculty = null;
		StudentDirectory sd = new StudentDirectory();

		// object is equal to itself
		assertTrue(a.equals(a));

		// equal objects should be equal in both directions
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));

		assertTrue(!firstName.equals(c.getFirstName()));
		assertTrue(!lastName.equals(d.getLastName()));
		assertTrue(!id.equals(e.getId()));
		assertTrue(!email.equals(f.getEmail()));
		assertTrue(!hashPW.equals(g.getPassword()));

		// should not be equal to null objects
		assertFalse(a.equals(nullFaculty));

		// should not be equal to object of different class	
		assertFalse(a.equals(sd));

		// not equal object shouldn't be equal
		assertFalse(a.equals(c));
		assertFalse(a.equals(d));
		assertFalse(a.equals(e));
		assertFalse(a.equals(f));
		assertFalse(a.equals(g));
		assertFalse(a.equals(h));
	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Faculty(firstName, lastName, id, email, hashPW, 2);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",2", s1.toString());
	}
}
