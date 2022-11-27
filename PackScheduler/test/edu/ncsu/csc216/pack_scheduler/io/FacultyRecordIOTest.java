/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Test class for testing FacultyRecordIO methods
 * @author Adharsh Rajagopal
 *
 */
public class FacultyRecordIOTest {
	/** Valid faculty #1 */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	/** Valid faculty #2 */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	/** Valid faculty #3 */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	/** Valid faculty #4 */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	/** Valid faculty #5 */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	/** Valid faculty #6 */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	/** Valid faculty #7 */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	/** Valid faculty #8 */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

	/** String array of valid faculty records */
	private String[] validFaculty = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7};

	/** Hashed password */
	private String hashPW;
	/** Hashing algorithm used to encrypt password */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Hashes a password to be used before each test
	 */
	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * Tests readFacultyRecords
	 */
	@Test
	void testReadStudentRecords() {
		LinkedList<Faculty> testValidFaculty = new LinkedList<Faculty>();
		
		try {
			testValidFaculty = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}

		// Test for correct number of faculty
		assertEquals(8, testValidFaculty.size());

		// Test for correct state
		assertEquals(validFaculty[0], testValidFaculty.get(0).toString());
		assertEquals(validFaculty[1], testValidFaculty.get(1).toString());
		assertEquals(validFaculty[2], testValidFaculty.get(2).toString());
		assertEquals(validFaculty[3], testValidFaculty.get(3).toString());
		assertEquals(validFaculty[4], testValidFaculty.get(4).toString());
		assertEquals(validFaculty[5], testValidFaculty.get(5).toString());
		assertEquals(validFaculty[6], testValidFaculty.get(6).toString());
		assertEquals(validFaculty[7], testValidFaculty.get(7).toString());

		LinkedList<Faculty> testInvalidFaculty = new LinkedList<Faculty>();
		
		try {
			testInvalidFaculty = FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		// No faculty should be read in from invalid_faculty_records.txt
		assertEquals(0, testInvalidFaculty.size());

		// Test for FileNotFoundException for non-existent file
		assertThrows(FileNotFoundException.class, () -> FacultyRecordIO.readFacultyRecords("some-file.txt"));
	}

	/**
	 * Tests writeFacultyRecords
	 */
	@Test
	void testWriteStudentRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		
		try {
			faculty = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		checkFiles("test-files/expected_full_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

	/**
	 * Tests writeFacultyRecords for writing to an inaccessible location on the file system
	 */
	@Test
	void testWriteFacultyRecordsNoPermissions() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 2));

		Exception exception = assertThrows(IOException.class,
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", faculty));
		assertEquals("/home/sesmith5/actual_faculty_records.txt (No such file or directory)", exception.getMessage());
	}
	
	/**
	 * Tests the no argument constructor
	 */
	@Test
	void testNoArgsConstructor() {
		FacultyRecordIO expectedFacultyRecordIO = new FacultyRecordIO();
		FacultyRecordIO actualFacultyRecordIO = expectedFacultyRecordIO;
		assertEquals(expectedFacultyRecordIO, actualFacultyRecordIO);
	}
}
