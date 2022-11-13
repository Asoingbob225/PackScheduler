package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests courseRoll class
 * 
 * @author rdbryan2
 */
public class CourseRollTest {

	/** Minimum allowed enrollment */
	private static final int MIN_ENROLLMENT = 10;

	/** Maximum allowed enrollment */
	private static final int MAX_ENROLLMENT = 250;

	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW = "password";

	/**
	 * Tests the constructor for courseRoll, both using a valid enrollmentCap, and
	 * also with invalid enrollmentCap(s)
	 */
	@Test
	public void testCourseRoll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// CourseRoll roll = new CourseRoll(10); //Update as below
		CourseRoll roll = c.getCourseRoll();
		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());
		assertEquals(0, roll.getNumberOnWaitlist());

	}

	/**
	 * Test enroll method, with duplicate and null students. Also fills class to
	 * capacity and tries to add more.
	 */
	@Test
	public void testEnroll() {
		Course c1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll cRoll = c1.getCourseRoll();

		Student a = new Student("Student 1", lastName, id, email, hashPW, 15);

		// Enroll Duplicates
		assertTrue(cRoll.canEnroll(a));
		cRoll.enroll(a);
		assertFalse(cRoll.canEnroll(a));
		assertThrows(IllegalArgumentException.class, () -> cRoll.enroll(a));

		// Add Null
		assertThrows(IllegalArgumentException.class, () -> cRoll.enroll(null));
		
		//Fill up Class and Enrollment Cap
		Student s = null;
		for (int i = 1; i < 10; i++) {
			s = new Student("Student" + i, lastName, id, email, hashPW, 15);
			assertTrue(cRoll.canEnroll(s));
			cRoll.enroll(s);
		}
		assertEquals(0, cRoll.getNumberOnWaitlist());
		for (int i = 10; i < 20; i++) {
			s = new Student("Student" + i, lastName, id, email, hashPW, 15);
			assertTrue(cRoll.canEnroll(s));
			cRoll.enroll(s);
		}
		Student s1 = new Student("Student", lastName, id, email, hashPW, 15);
		assertFalse(cRoll.canEnroll(s1));
		assertThrows(IllegalArgumentException.class, () -> cRoll.enroll(s1));
	
	}

	/**
	 * Tests drop method with null value and valid student
	 */
	@Test
	public void testDrop() {
		Course c1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll cRoll = c1.getCourseRoll();
		Student a = new Student("Student 1", lastName, id, email, hashPW, 15);
		Student b = new Student("Student 2", lastName, id, email, hashPW, 15);
		Student c = new Student("Student 3", lastName, id, email, hashPW, 15);
		Student d = new Student("Student 4", lastName, id, email, hashPW, 15);
		Student e = new Student("Student 5", lastName, id, email, hashPW, 15);
		Student f = new Student("Student 6", lastName, id, email, hashPW, 15);
		Student g = new Student("Student 7", lastName, id, email, hashPW, 15);
		Student h = new Student("Student 8", lastName, id, email, hashPW, 15);
		Student i = new Student("Student 9", lastName, id, email, hashPW, 15);
		Student j = new Student("Student 10", lastName, id, email, hashPW, 15);

		cRoll.enroll(a);
		cRoll.enroll(b);
		cRoll.enroll(c);
		cRoll.enroll(d);
		cRoll.enroll(e);
		cRoll.enroll(f);
		cRoll.enroll(g);
		cRoll.enroll(h);
		cRoll.enroll(i);
		cRoll.enroll(j);

		// Remove Null
		assertThrows(IllegalArgumentException.class, () -> cRoll.drop(null));

		// Remove Student
		assertEquals(0, cRoll.getOpenSeats());
		
		
		//Test waitlist Functionality with drop
		Student k = new Student("Student 11", lastName, id, email, hashPW, 15);
		cRoll.enroll(k);
		cRoll.drop(a);
		assertEquals(0, cRoll.getOpenSeats());

	}
	
	/**
	 * Tests setting enrollmentCap
	 */
	@Test 
	void testSetEnrollmentCap(){
		Course c1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll cRoll = c1.getCourseRoll();
		cRoll.setEnrollmentCap(11);
		
		Student s = null;
		for (int i = 1; i <= 11; i++) {
			s = new Student("Student" + i, lastName, id, email, hashPW, 15);
			assertTrue(cRoll.canEnroll(s));
			cRoll.enroll(s);
		}
		
		assertThrows(IllegalArgumentException.class, () -> cRoll.setEnrollmentCap(10));
		assertThrows(IllegalArgumentException.class, () -> cRoll.setEnrollmentCap(MIN_ENROLLMENT - 1));
		assertThrows(IllegalArgumentException.class, () -> cRoll.setEnrollmentCap(MAX_ENROLLMENT + 1));
	}

}
