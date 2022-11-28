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
 * Tests FacultyDirectory
 * @author adharsh
 *
 */
public class FacultyDirectoryTest {

	/** Valid faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid faculty records */
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
	/** Test max courses */
	private static final int MAX_COURSES = 3;
	/** Expected faculty to load from file (now sorted) */
	private static final String[] CORRECT_RECORDS = new String[] { "Ashely,Witt,awitt", "Fiona,Meadows,fmeadow",
			"Brent,Brewer,bbrewer", "Halla,Aguirre,haguirr", "Kevyn,Patel,kpatel", "Elton,Briggs,ebriggs",
			"Norman,Brady,nbrady", "Lacey,Walls,lwalls"};

	/**
	 * Resets faculty_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory initialization. Expected to create an empty list.
	 */
	@Test
	public void testFacultyDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		FacultyDirectory sd = new FacultyDirectory();
		assertFalse(sd.removeFaculty("sesmith5"));
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile(). Ensures that the values are
	 * the same as in the file by comparing with literal array values.
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid file
		sd.loadFacultyFromFile(validTestFile); // faculty_records.txt
		assertEquals(8, sd.getFacultyDirectory().length);

		String[][] records = sd.getFacultyDirectory();

		// Compare each entry to the expected values
		for (int i = 0; i < 8; i++) {
			String entry = records[i][0] + "," + records[i][1] + "," + records[i][2];
			assertEquals(CORRECT_RECORDS[i], entry,
					"Entry " + String.valueOf(i) + " loaded from file does not match expected values.");
		}
	}

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile() with an invalid file name.
	 */
	@Test
	public void testLoadFacultyFromFileInvalid() {
		FacultyDirectory sd = new FacultyDirectory();

		// Attempt to load a file
		Exception e = assertThrows(IllegalArgumentException.class, () -> sd.loadFacultyFromFile(missingFile),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Unable to read file " + missingFile, e.getMessage(),
				"Incorrect exception message for IllegalArgumentException");
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory()
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are faculty in the directory, they
		// are removed after calling newFacultyDirectory().
		FacultyDirectory sd = new FacultyDirectory();

		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);

		sd.newFacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.addFaculty(). Ensures that adding valid faculty still
	 * corrects invalid max courses values.
	 */
	@Test
	public void testAddStudent() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid faculty
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String[][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);

		// Add faculty with duplicate ID
		assertFalse(sd.addFaculty("Sarah", LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES),
				"expected return value false for non-unique ID");
		facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);

//		// Test adding student with invalid max courses. Unlike other invalid values,
//		// this should be ignored and maxCredits is set to default value.
//		// Credits too large:
//		sd.addFaculty(FIRST_NAME, LAST_NAME, "sdent1", EMAIL, PASSWORD, PASSWORD, 4);
//		facultyDirectory = sd.getFacultyDirectory();
//		assertEquals(2, facultyDirectory.length);
//		assertEquals("sdent1", facultyDirectory[1][2]);
//		// courses too small:
//		sd.addFaculty(FIRST_NAME, LAST_NAME, "sdent2", EMAIL, PASSWORD, PASSWORD, 0);
//		facultyDirectory = sd.getFacultyDirectory();
//		assertEquals(3, facultyDirectory.length);
//		assertEquals("sdent2", facultyDirectory[2][2]);
	}

	/**
	 * Tests FacultyDirectory.addFaculty() password value checking by providing null
	 * and empty entries for both password and repeatPasswords and by providing
	 * valid passwords that do not match.
	 *
	 * @param invalidPassword null and empty passwords
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddFacultyInvalidPassword(String invalidPassword) {
		FacultyDirectory sd = new FacultyDirectory();
		// invalid password
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, invalidPassword, PASSWORD, MAX_COURSES),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Invalid password", e1.getMessage(), "Incorrect exception message for IllegalArgumentException");

		// invalid repeat password
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, invalidPassword, MAX_COURSES),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Invalid password", e2.getMessage(), "Incorrect exception message for IllegalArgumentException");

		// passwords do not match
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "verygoodpassword", MAX_COURSES),
				"Expected IllegalArgumentException, but was not thrown.");
		assertEquals("Passwords do not match", e3.getMessage(),
				"Incorrect exception message for IllegalArgumentException");

	}

	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add students and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertTrue(sd.removeFaculty("nbrady"));
		String[][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Elton", facultyDirectory[5][0]);
		assertEquals("Briggs", facultyDirectory[5][1]);
		assertEquals("ebriggs", facultyDirectory[5][2]);
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory() output against expected file.
	 */
	@Test
	public void testSaveStudentDirectory() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add a student
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		sd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory(). Ensures that an invalid path
	 * throws the correct exception.
	 */
	@Test
	public void testSaveStudentDirectoryInvalid() {
		FacultyDirectory sd = new FacultyDirectory();

		// Attempt to save to an illegal path name
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> sd.saveFacultyDirectory("invalid path name/actual_faculty_records.txt"),
				"Expected IllegalArgumentException for illegal save path, but was not thrown.");
		assertEquals("Unable to write to file invalid path name/actual_faculty_records.txt", e.getMessage(),
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
