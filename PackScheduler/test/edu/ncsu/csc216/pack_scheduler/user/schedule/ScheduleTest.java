/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test class for Schedule
 * 
 * @author abcoste2
 */
public class ScheduleTest {
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course enrollment cap */
	private static final int ENROLLMENT_CAP = 52;
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/** Valid Course objects to used in multiple tests */
	private static final Course[] COURSES = new Course[] {
			new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME),
			new Course("ZZZ999", "Intro to existentiality", "601", 3, "cccccc9", 60, "F", 900, 1000),
			new Course("AAA000", "Intro to introduction", "601", 3, "adacad1", 104, "A"),
			new Course("CSC282", "Numerical Computation in FORTRAN", "003", 3, "jswolfo2", 71, "TH", 1015, 1130) };

	/**
	 * Expected output of getScheduledCourses() for respective elements in COURSES
	 **/
	private static final String[][] EXPECTED_ARRAY = { { NAME, "001", TITLE, "MW 1:30PM-2:45PM" },
			{ "ZZZ999", "601", "Intro to existentiality", "F 9:00AM-10:00AM" },
			{ "AAA000", "601", "Intro to introduction", "Arranged" },
			{ "CSC282", "003", "Numerical Computation in FORTRAN", "TH 10:15AM-11:30AM" } };

	/**
	 * Should create an empty ArrayList of Courses. The title should be initialized
	 * to �My Schedule�
	 */
	@Test
	public void testScheduleConstructor() {
		Schedule s = assertDoesNotThrow(() -> new Schedule());

		assertEquals("My Schedule", s.getTitle());
	}

	/**
	 * Should add the Course to the end of the schedule and return true if the
	 * Course was added.
	 * 
	 * New courses cannot be duplicate (name conflict), conflicting, or {@code null}
	 */
	@Test
	public void testAddCourseToSchedule() {

		Schedule s = new Schedule();

		assertTrue(s.addCourseToSchedule(COURSES[0]));

		String[][] displayArray = s.getScheduledCourses();
		assertEquals(1, displayArray.length);
		assertArrayEquals(EXPECTED_ARRAY[0], displayArray[0]);

		assertTrue(s.addCourseToSchedule(COURSES[1]));
		assertTrue(s.addCourseToSchedule(COURSES[2]));
		assertTrue(s.addCourseToSchedule(COURSES[3]));
		displayArray = s.getScheduledCourses();
		assertEquals(4, displayArray.length);

		displayArray = s.getScheduledCourses();

		for (int i = 0; i < 4; i++) {
			assertArrayEquals(EXPECTED_ARRAY[i], displayArray[i]);
		}

		// Invalid cases
		Course equalCourse = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME,
				END_TIME);
		Course duplicateNameCourse = new Course(NAME, "Not Software Development Fundamentals", "002", 4, "sesmith7",
				99, "A");
		Course conflictCourse = new Course("DTD327", "Not Software Development Fundamentals" + "1", "002", 4,
				"sesmith7", 76, MEETING_DAYS, 1230, 1400);

		Exception e = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(COURSES[0]));
		assertEquals("You are already enrolled in CSC216", e.getMessage());

		e = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(equalCourse));
		assertEquals("You are already enrolled in CSC216", e.getMessage());

		e = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(duplicateNameCourse));
		assertEquals("You are already enrolled in CSC216", e.getMessage());

		e = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(conflictCourse));
		assertEquals("The course cannot be added due to a conflict.", e.getMessage());

		assertEquals(4, displayArray.length);
	}

	/**
	 * Should remove the Course from the schedule and return true if the Course was
	 * removed and false if there was not a Course to remove.
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();

		s.addCourseToSchedule(COURSES[0]);
		s.addCourseToSchedule(COURSES[1]);
		s.addCourseToSchedule(COURSES[2]);
		s.addCourseToSchedule(COURSES[3]);

		assertTrue(s.removeCourseFromSchedule(COURSES[0]));
		String[][] displayArray = s.getScheduledCourses();
		assertEquals(3, displayArray.length);

		assertTrue(s.removeCourseFromSchedule(COURSES[2]));
		displayArray = s.getScheduledCourses();
		assertEquals(2, displayArray.length);

		assertArrayEquals(EXPECTED_ARRAY[1], displayArray[0]);
		assertArrayEquals(EXPECTED_ARRAY[3], displayArray[1]);

		assertFalse(s.removeCourseFromSchedule(COURSES[0]));
		assertFalse(s.removeCourseFromSchedule(COURSES[2]));
	}

	/**
	 * Should create a new schedule ArrayList and reset the title to the default
	 * value
	 */
	@Test
	public void testResetSchedule() {
		Schedule s = new Schedule();

		s.setTitle("New Title");

		s.addCourseToSchedule(COURSES[0]);
		s.addCourseToSchedule(COURSES[1]);
		s.addCourseToSchedule(COURSES[2]);
		s.addCourseToSchedule(COURSES[3]);

		s.resetSchedule();

		assertEquals("My Schedule", s.getTitle());

		String[][] displayArray = s.getScheduledCourses();
		assertEquals(0, displayArray.length);
	}

	/**
	 * Should return a 2D array in the format of
	 * {@link Course#getShortDisplayArray()}
	 */
	@Test
	public void testGetScheduledCourses() {
		Schedule s = new Schedule();

		for (int i = 0; i < COURSES.length; i++) {
			s.addCourseToSchedule(COURSES[i]);
		}

		String[][] displayArray = s.getScheduledCourses();

		for (int i = 0; i < displayArray.length; i++) {
			assertArrayEquals(EXPECTED_ARRAY[i], displayArray[i]);
		}
	}

	/**
	 * Should set the title to the parameter value. If the title is null, an
	 * IllegalArgumentException is thrown
	 */
	@Test
	public void testSetTitle() {
		Schedule s = new Schedule();

		assertEquals("My Schedule", s.getTitle());

		s.setTitle("new title");
		assertEquals("new title", s.getTitle());

		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setTitle(null));
		assertEquals("Title cannot be null.", e.getMessage());
	}
}
