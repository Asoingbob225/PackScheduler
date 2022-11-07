package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 * @author abcoste2
 * @author ctmatias
 */
public class StudentDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid course records */
	private final String missingFile = "test-files/not_here.txt";

	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	/** Expected students to load from file (now sorted) */
	private static final String[] CORRECT_RECORDS = new String[] { "Demetrius,Austin,daustin", "Lane,Berg,lberg",
			"Raymond,Brennan,rbrennan", "Emerald,Frost,efrost", "Shannon,Hansen,shansen", "Althea,Hicks,ahicks",
			"Zahir,King,zking", "Dylan,Nolan,dnolan", "Cassandra,Schwartz,cschwartz", "Griffith,Stone,gstone" };

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory initialization. Expected to create an empty list.
	 */
	@Test
	public void testStudentDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile(). Ensures that the values are
	 * the same as in the file by comparing with literal array values.
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid file
		sd.loadStudentsFromFile(validTestFile); // student_records.txt
		assertEquals(10, sd.getStudentDirectory().length);

		String[][] records = sd.getStudentDirectory();

		// Compare each entry to the expected values
		for (int i = 0; i < 10; i++) {
			String entry = records[i][0] + "," + records[i][1] + "," + records[i][2];
			assertEquals(CORRECT_RECORDS[i], entry,
					"Entry " + String.valueOf(i) + " loaded from file does not match expected values.");
		}
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile() with an invalid file name.
	 */
	@Test
	public void testLoadStudentsFromFileInvalid() {
		StudentDirectory sd = new StudentDirectory();

		// Attempt to load a file
		Exception e = assertThrows(IllegalArgumentException.class, () -> sd.loadStudentsFromFile(missingFile),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Unable to read file " + missingFile, e.getMessage(),
				"Incorrect exception message for IllegalArgumentException");
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory()
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();

		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.addStudent(). Ensures that adding valid students still
	 * checks for duplicates and corrects invalid max credit values.
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);

		// Add student with duplicate ID
		assertFalse(sd.addStudent("Sarah", LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS),
				"expected return value false for non-unique ID");
		studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);

		// Test adding student with invalid max credits. Unlike other invalid values,
		// this should be ignored and maxCredits is set to default value.
		// Credits too large:
		sd.addStudent(FIRST_NAME, LAST_NAME, "sdent1", EMAIL, PASSWORD, PASSWORD, 19);
		studentDirectory = sd.getStudentDirectory();
		assertEquals(2, studentDirectory.length);
		assertEquals("sdent1", studentDirectory[1][2]);
		// Credits too small:
		sd.addStudent(FIRST_NAME, LAST_NAME, "sdent2", EMAIL, PASSWORD, PASSWORD, 2);
		studentDirectory = sd.getStudentDirectory();
		assertEquals(3, studentDirectory.length);
		assertEquals("sdent2", studentDirectory[2][2]);
	}

	/**
	 * Tests StudentDirectory.addStudent() password value checking by providing null
	 * and empty entries for both password and repeatPasswords and by providing
	 * valid passwords that do not match.
	 *
	 * @param invalidPassword null and empty passwords
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddStudentInvalidPassword(String invalidPassword) {
		StudentDirectory sd = new StudentDirectory();
		// invalid password
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, invalidPassword, PASSWORD, MAX_CREDITS),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Invalid password", e1.getMessage(), "Incorrect exception message for IllegalArgumentException");

		// invalid repeat password
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, invalidPassword, MAX_CREDITS),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Invalid password", e2.getMessage(), "Incorrect exception message for IllegalArgumentException");

		// passwords do not match
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "verygoodpassword", MAX_CREDITS),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Passwords do not match", e3.getMessage(),
				"Incorrect exception message for IllegalArgumentException");

	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Zahir", studentDirectory[5][0]);
		assertEquals("King", studentDirectory[5][1]);
		assertEquals("zking", studentDirectory[5][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory() output against expected file.
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();

		// Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory(). Ensures that an invalid path
	 * throws the correct exception.
	 */
	@Test
	public void testSaveStudentDirectoryInvalid() {
		StudentDirectory sd = new StudentDirectory();

		// Attempt to save to an illegal path name
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> sd.saveStudentDirectory("invalid path name/actual_student_records.txt"),
				"Expected IllegalArgumentException for illegal save path, but was not thrown.");
		assertEquals("Unable to write to file invalid path name/actual_student_records.txt", e.getMessage(),
				"Incorrect exception message for illegal save path");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}