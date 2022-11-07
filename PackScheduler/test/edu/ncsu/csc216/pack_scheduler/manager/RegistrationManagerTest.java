package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Class doc
 * 
 * @author abcoste2
 */
public class RegistrationManagerTest {

	/** Instance to be shared across tests */
	private RegistrationManager manager;
	/** Password to test registrar login */
	private static String registrarPW;
	/** ID to test registrar login */
	private static String registrarId;

	/** Path of the registrar properties */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Retrieves properties for login testing
	 */
	@BeforeAll
	public static void getProperties() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			registrarId = prop.getProperty("id");
			registrarPW = prop.getProperty("pw");
		} catch (IOException e) {
			fail("Unable to load properties");
		}
	}

	/**
	 * Sets up the RegistrationManager and clears the data along with any logged in
	 * user.
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
		manager.logout();
	}

	/**
	 * Test getCourseCatalog to make sure it returns a pointer to the correct object
	 * and the same one each time.
	 */
	@Test
	public void testGetCourseCatalog() {
		assertDoesNotThrow(() -> manager.getCourseCatalog());

		// Check that same address is returned each time
		CourseCatalog cc1 = manager.getCourseCatalog();
		CourseCatalog cc2 = manager.getCourseCatalog();
		assertSame(cc1, cc2);

		assertEquals(0, cc1.getCourseCatalog().length);

		cc1.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(13, cc1.getCourseCatalog().length);

		// Calling getCourseCatalog again should not result in different values
		cc2 = manager.getCourseCatalog();
		assertEquals(cc1.getCourseCatalog().length, cc2.getCourseCatalog().length);
	}

	/**
	 * Test getStudentDirectory to make sure it returns a pointer to the correct
	 * object and the same one each time.
	 */
	@Test
	public void testGetStudentDirectory() {
		assertDoesNotThrow(() -> manager.getStudentDirectory());

		// Check that same address is returned each time
		StudentDirectory sd1 = manager.getStudentDirectory();
		StudentDirectory sd2 = manager.getStudentDirectory();
		assertSame(sd1, sd2);

		assertEquals(0, sd1.getStudentDirectory().length);

		sd1.loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, sd1.getStudentDirectory().length);

		// Calling getStudentDirectory again should not result in different values
		sd2 = manager.getStudentDirectory();
		assertEquals(sd1.getStudentDirectory().length, sd2.getStudentDirectory().length);
	}

	/**
	 * Test that student id/password results in that student logging in. Registrar
	 * id/password (from properties file) results in the registrar logging in. A
	 * user should not be able to login if another is currently logged in. No user
	 * logged in should be represented by a null value.
	 */
	@Test
	public void testLogin() {
		StudentDirectory sd = manager.getStudentDirectory();
		sd.loadStudentsFromFile("test-files/student_records.txt");

		User cu = manager.getCurrentUser();
		// No user logged in
		assertNull(cu);
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> manager.login("fakeId", "fakepw"));
		assertEquals("User doesn't exist.", e1.getMessage());
		
		// Make specific students current user
		assertTrue(manager.login("zking", "pw"), "Unable to login with valid student credentials");
		cu = manager.getCurrentUser();
		assertTrue(cu instanceof Student);
		Student s1 = sd.getStudentById("zking");
		assertEquals(s1, cu);
		manager.logout();

		assertTrue(manager.login("cschwartz", "pw"), "Unable to login with valid student credentials");
		cu = manager.getCurrentUser();
		assertTrue(cu instanceof Student);
		Student s2 = sd.getStudentById("cschwartz");
		assertEquals(s2, cu);

		// Failed login doesn't change user
		assertFalse(manager.login("fakeId", "fakepw"), "Able to login with invalid credentials");
		cu = manager.getCurrentUser();
		assertEquals(s2, cu);

		// Registrar and user cannot login with student already logged in
		assertFalse(manager.login(registrarId, registrarPW), "Shouldn't be able to login with student logged in");
		assertFalse(manager.login("zking", "pw"), "Shouldn't be able to login with another student logged in");

		manager.logout();

		// back to registrar with properties login
		assertTrue(manager.login(registrarId, registrarPW), "Unable to login with valid registrar credentials");
		cu = manager.getCurrentUser();

		// Since registrar is a private type, check that there is a current user but it
		// isn't a student
		assertTrue(cu instanceof User);
		assertFalse(cu instanceof Student);

		// Registrar and user cannot login with student already logged in
		assertFalse(manager.login(registrarId, registrarPW), "Shouldn't be able to login with registrar logged in");
		assertFalse(manager.login("zking", "pw"), "Shouldn't be able to login with registrar logged in");
	}

	/**
	 * Logout should always return current user to null
	 */
	@Test
	public void testLogout() {
		StudentDirectory sd = manager.getStudentDirectory();
		sd.loadStudentsFromFile("test-files/student_records.txt");

		User cu = manager.getCurrentUser();
		assertNull(cu);

		// Logout without anyone logged in
		manager.logout();
		cu = manager.getCurrentUser();
		assertNull(cu);

		// Logout registrar
		assertTrue(manager.login(registrarId, registrarPW));
		manager.logout();
		cu = manager.getCurrentUser();
		assertNull(cu);

		// Logout student
		assertTrue(manager.login("zking", "pw"));
		manager.logout();
		cu = manager.getCurrentUser();
		assertNull(cu);
	}

	/**
	 * Test getCurrentUser to make sure it returns a pointer to the correct object
	 * and the same one each time.
	 */
	@Test
	public void testGetCurrentUser() {
		StudentDirectory sd = manager.getStudentDirectory();
		sd.loadStudentsFromFile("test-files/student_records.txt");

		assertDoesNotThrow(() -> manager.getCurrentUser());

		// Check that same pointer is returned each time
		User cu1 = manager.getCurrentUser();
		User cu2 = manager.getCurrentUser();
		assertSame(cu1, cu2);

		manager.login("zking", "pw");
		cu1 = manager.getCurrentUser();
		assertSame(cu1, sd.getStudentById("zking"));
		manager.logout();

		manager.login(registrarId, registrarPW);
		cu1 = manager.getCurrentUser();
		assertTrue(cu1 instanceof User);

		// Calling getCurrentUser again should not result in different values
		cu2 = manager.getCurrentUser();
		assertSame(cu1, cu2);
	}

}
