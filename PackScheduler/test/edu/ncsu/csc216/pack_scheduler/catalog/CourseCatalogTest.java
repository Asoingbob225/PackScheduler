package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the methods of CourseCatalog
 * 
 * @author ctmatias
 * @author abcoste2
 * @author rcmidget
 */
public class CourseCatalogTest {

	/**
	 * Expected output of getCourseCatalog if loaded from
	 * test-files/course_records.txt
	 */
	private static final String[][] EXPECTED_RECORDS = new String[][] {
			{ "CSC116", "001", "Intro to Programming - Java", "MW 9:10AM-11:00AM" },
			{ "CSC116", "002", "Intro to Programming - Java", "MW 11:20AM-1:10PM" },
			{ "CSC116", "003", "Intro to Programming - Java", "TH 11:20AM-1:10PM" },
			{ "CSC216", "001", "Software Development Fundamentals", "TH 1:30PM-2:45PM" },
			{ "CSC216", "002", "Software Development Fundamentals", "MW 1:30PM-2:45PM" },
			{ "CSC216", "601", "Software Development Fundamentals", "Arranged" },
			{ "CSC217", "202", "Software Development Fundamentals Lab", "M 10:40AM-12:30PM" },
			{ "CSC217", "211", "Software Development Fundamentals Lab", "T 8:30AM-10:20AM" },
			{ "CSC217", "223", "Software Development Fundamentals Lab", "W 3:00PM-4:50PM" },
			{ "CSC217", "601", "Software Development Fundamentals Lab", "Arranged" },
			{ "CSC226", "001", "Discrete Mathematics for Computer Scientists", "MWF 9:35AM-10:25AM" },
			{ "CSC230", "001", "C and Software Tools", "MW 11:45AM-1:00PM" },
			{ "CSC316", "001", "Data Structures and Algorithms", "MW 8:30AM-9:45AM" } };

	/**
	 * Tests CourseCatalog constructor to make sure it is an empty list so it will
	 * have length 0 and nothing to remove.
	 */
	@Test
	public void testCourseCatalog() {
		assertDoesNotThrow(() -> new CourseCatalog());

		CourseCatalog c = new CourseCatalog();
		assertEquals(0, c.getCourseCatalog().length);
	}

	/**
	 * Tests newCourseCatalog to make sure it is an empty list equivalent to a newly
	 * constructed catalog. This test somewhat relies on loading functionality, but
	 * it is appropriate for this case.
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile("test-files/course_records.txt");
		assertDoesNotThrow(() -> c.newCourseCatalog());
		assertFalse(c.removeCourseFromCatalog("CSC 116", "003"));
		assertEquals(0, c.getCourseCatalog().length);
	}

	/**
	 * Tests loadCoursesFromFile to make sure it loads the expected courses in
	 * appropriate order. Then tests it throws the correct exception if file cannot
	 * be found.
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile("test-files/course_records.txt");

		String[][] catalogArray = c.getCourseCatalog();
		assertEquals(EXPECTED_RECORDS.length, catalogArray.length);

		for (int i = 0; i < EXPECTED_RECORDS.length; i++) {
			assertArrayEquals(EXPECTED_RECORDS[i], catalogArray[i]);
		}

		Exception e = assertThrows(IllegalArgumentException.class,
				() -> c.loadCoursesFromFile("test-files/nonexistentfile.jfif"));
		assertEquals("Unable to read file test-files/nonexistentfile.jfif", e.getMessage());
	}

	/**
	 * Tests addCoursesToCatalog that it adds the new course with correct values and
	 * that it is inserted in the correct positions. Tests adding a duplicate course
	 * returns false.
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile("test-files/course_records.txt");

		assertFalse(c.addCourseToCatalog("CSC216", "SOME", "001", 2, "asd", 22, "MFW", 1233, 2100));

		// Add to end
		c.addCourseToCatalog("ZZZ999", "Intro to existentiality", "601", 3, "cccccc9", 30, "M", 900, 1000);
		// Add to beginning
		c.addCourseToCatalog("AAA000", "Intro to introduction", "601", 3, "adacad1", 44, "M", 830, 1450);
		// Between CSC 216 and CSC 230
		c.addCourseToCatalog("CSC282", "Numerical Computation in FORTRAN", "001", 3, "jswolfo2", 69, "TH", 1015, 1130);

		String[][] catalogArray = c.getCourseCatalog();
		assertEquals(16, catalogArray.length);
		assertEquals("AAA000", catalogArray[0][0]);
		assertEquals("CSC282", catalogArray[13][0]);
		assertEquals("ZZZ999", catalogArray[15][0]);
	}

	/**
	 * Tests removeCourseFromCatalog. Adds several courses and then removes them and 
	 * checks that the catalog size has been reduced by one
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog c = new CourseCatalog();
		
		c.addCourseToCatalog("ZZZ999", "Intro to existentiality", "601", 3, "cccccc9", 25, "M", 900, 1000);
		c.addCourseToCatalog("AAA000", "Intro to introduction", "601", 3, "adacad1", 99, "M", 830, 1450);
		c.addCourseToCatalog("CSC282", "Numerical Computation in FORTRAN", "001", 3, "jswolfo2", 222, "TH", 1015, 1130);
		
		assertEquals(3, c.getCourseCatalog().length);
		
		c.removeCourseFromCatalog("ZZZ999", "601");
		assertEquals(2, c.getCourseCatalog().length);
		
		c.removeCourseFromCatalog("AAA000", "601");
		assertEquals(1, c.getCourseCatalog().length);
		
		
	}

	/**
	 * Tests getCourseFromCatalog. Creates new catalog and adds several courses and then checks that you can get valid courses
	 * from the catalog and not invalid courses
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();

		cc.addCourseToCatalog("ST371", "Intro to Prob and Dist Theory", "001", 3, "mtang", 233, "TH", 1145, 1300);
		cc.addCourseToCatalog("SOC211", "Community and Health", "601", 3, "kjicha", 22, "A", 0, 0);
		cc.addCourseToCatalog("MA242", "Calculus III", "009", 4, "jnneuber", 55, "TH", 1500, 1650);
		assertEquals(3, cc.getCourseCatalog().length);

		// Test for a course not in the catalog
		Course c1 = cc.getCourseFromCatalog("CSC216", "001");
		assertNull(c1);

		// Test for a valid course in the catalog
		Course c2 = cc.getCourseFromCatalog("MA242", "009");
		assertEquals(new Course("MA242", "Calculus III", "009", 4, "jnneuber", 55, "TH", 1500, 1650), c2);
	}

	/**
	 * Tests getCourseCatalog by filling a catalog from the test file and then checks several spots to see if the proper 
	 * courses are in the catalog in their appropriate order.
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/course_records.txt");
		
		String[][] catalogArray = cc.getCourseCatalog();
		assertEquals(13, catalogArray.length);
		assertEquals("CSC116", catalogArray[0][0]);
		assertEquals("CSC216", catalogArray[5][0]);
		assertEquals("CSC316", catalogArray[12][0]);
		
		
		
	}

	/**
	 * Tests saveCourseCatalog. Makes a new catalog, saves, checks that the new catalog save file is empty. Then adds courses
	 * to the catalog and saves again, then checks that those courses are actually in the save file. Also tests that the
	 * right exception is thrown if a file cannot be saved
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();

		// Test that saving an empty catalog works correctly
		cc.saveCourseCatalog("test-files/actual_empty_records.txt");
		checkFiles("test-files/expected_empty_records.txt", "test-files/actual_empty_records.txt");

		// Add courses to catalog
		cc.addCourseToCatalog("ST371", "Intro to Prob and Dist Theory", "001", 3, "mtang", 45, "TH", 1145, 1300);
		cc.addCourseToCatalog("SOC211", "Community and Health", "601", 3, "kjicha", 33, "A", 0, 0);
		assertEquals(2, cc.getCourseCatalog().length);

		// Test that new catalog saves correctly
		cc.saveCourseCatalog("test-files/actual_empty_records.txt");
		checkFiles("test-files/expected_empty_records.txt", "test-files/actual_empty_records.txt");

		// Test for an exception to be thrown
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> cc.saveCourseCatalog("/home/sesmith5/actual_student_records.txt"));
		assertEquals("Unable to write to file /home/sesmith5/actual_student_records.txt", exception.getMessage());
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
				Scanner actScanner = new Scanner(new File(actFile));) {

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
