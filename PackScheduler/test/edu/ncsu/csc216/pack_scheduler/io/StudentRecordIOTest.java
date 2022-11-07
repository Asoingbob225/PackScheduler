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

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Test class for testing StudentRecordIO methods
 * 
 * @author ctmatias
 */
class StudentRecordIOTest {

	/** Valid student #1 */
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Valid student #2 */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Valid student #3 */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Valid student #4 */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** Valid student #5 */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** Valid student #6 */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Valid student #7 */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** Valid student #8 */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/** Valid student #9 */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Valid student #10 */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/** String array of valid student records */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };

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

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
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
	 * Tests readStudentRecords
	 */
	@Test
	void testReadStudentRecords() {
		SortedList<Student> testValidStudents = new SortedList<Student>();
		
		try {
			testValidStudents = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}

		// Test for correct number of students
		assertEquals(10, testValidStudents.size());

		// Test for correct state
		assertEquals(validStudents[3], testValidStudents.get(0).toString());
		assertEquals(validStudents[6], testValidStudents.get(1).toString());
		assertEquals(validStudents[4], testValidStudents.get(2).toString());
		assertEquals(validStudents[5], testValidStudents.get(3).toString());
		assertEquals(validStudents[2], testValidStudents.get(4).toString());
		assertEquals(validStudents[8], testValidStudents.get(5).toString());
		assertEquals(validStudents[0], testValidStudents.get(6).toString());
		assertEquals(validStudents[9], testValidStudents.get(7).toString());
		assertEquals(validStudents[1], testValidStudents.get(8).toString());
		assertEquals(validStudents[7], testValidStudents.get(9).toString());

		SortedList<Student> testInvalidStudents = new SortedList<Student>();
		
		try {
			testInvalidStudents = StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		// No students should be read in from invalid_student_records.txt
		assertEquals(0, testInvalidStudents.size());

		// Test for FileNotFoundException for non-existent file
		assertThrows(FileNotFoundException.class, () -> StudentRecordIO.readStudentRecords("some-file.txt"));
	}

	/**
	 * Tests writeStudentRecords
	 */
	@Test
	void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		
		try {
			students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		checkFiles("test-files/expected_full_student_records_sorted.txt", "test-files/actual_student_records.txt");
	}

	/**
	 * Tests writeStudentRecords for writing to an inaccessible location on the file system
	 */
	@Test
	void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

		Exception exception = assertThrows(IOException.class,
				() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
	}
	
	/**
	 * Tests the no argument constructor
	 */
	@Test
	void testNoArgsConstructor() {
		StudentRecordIO expectedStudentRecordIO = new StudentRecordIO();
		StudentRecordIO actualStudentRecordIO = expectedStudentRecordIO;
		assertEquals(expectedStudentRecordIO, actualStudentRecordIO);
	}
}
