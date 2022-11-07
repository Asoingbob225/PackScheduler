package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
	 * Tests the constructor for courseRoll, both using a valid enrollmentCap, and also 
	 * with invalid enrollmentCap(s)
	 */
	@Test
	void testCourseRoll() {
		CourseRoll cRoll = new CourseRoll(100);
		assertEquals(100, cRoll.getEnrollmentCap());
		assertEquals(100, cRoll.getOpenSeats());
		assertThrows(IllegalArgumentException.class,
				() -> new CourseRoll(MAX_ENROLLMENT + 1));
		assertThrows(IllegalArgumentException.class,
				() -> new CourseRoll(MIN_ENROLLMENT - 1));
	}
	
	/**
	 * Test enroll method, with duplicate and null students. Also fills class to capacity and tries to add more.
	 */
	@Test 
	void testEnroll() {
		CourseRoll cRoll = new CourseRoll(11);
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
		Student k = new Student("Student 11", lastName, id, email, hashPW, 15);
		Student l = new Student("Student 12", lastName, id, email, hashPW, 15);
		
		//Enroll Duplicates
		assertTrue(cRoll.canEnroll(a));
		cRoll.enroll(a);
		assertFalse(cRoll.canEnroll(a));
		assertThrows(IllegalArgumentException.class,
				() -> cRoll.enroll(a));
		
		//Add Null
		assertThrows(IllegalArgumentException.class,
				() -> cRoll.enroll(null));
		
		//Add more than enrollmentCap
		assertTrue(cRoll.canEnroll(b));
		cRoll.enroll(b);
		assertTrue(cRoll.canEnroll(c));
		cRoll.enroll(c);
		assertTrue(cRoll.canEnroll(d));
		cRoll.enroll(d);
		assertTrue(cRoll.canEnroll(e));
		cRoll.enroll(e);
		assertTrue(cRoll.canEnroll(f));
		cRoll.enroll(f);
		assertTrue(cRoll.canEnroll(g));
		cRoll.enroll(g);
		assertTrue(cRoll.canEnroll(h));
		cRoll.enroll(h);
		assertTrue(cRoll.canEnroll(i));
		cRoll.enroll(i);
		assertTrue(cRoll.canEnroll(j));
		cRoll.enroll(j);
		assertTrue(cRoll.canEnroll(k));
		cRoll.enroll(k);
		assertFalse(cRoll.canEnroll(l));
		assertThrows(IllegalArgumentException.class,
				() -> cRoll.enroll(l));
		assertThrows(IllegalArgumentException.class,
				() -> cRoll.setEnrollmentCap(MIN_ENROLLMENT));
		
		
	}
	
	/**
	 * Tests drop method with null value and valid student
	 */
	@Test
	void testDrop() {
		CourseRoll cRoll = new CourseRoll(10);
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
		
		//Remove Null
		assertThrows(IllegalArgumentException.class,
				() -> cRoll.drop(null));
		
		//Remove Student
		assertEquals(0, cRoll.getOpenSeats());
		cRoll.drop(j);
		assertEquals(1, cRoll.getOpenSeats());
		
	}
	

}
