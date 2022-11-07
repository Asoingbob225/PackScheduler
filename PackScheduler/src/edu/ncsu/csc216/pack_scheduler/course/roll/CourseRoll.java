package edu.ncsu.csc216.pack_scheduler.course.roll;


import java.util.List;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Represents the roll of a class. There is a maximum number of students allowed to enroll in a class,
 * which is the enrollmentCap. The roll is a linked list that handles much of the functionality of adding/dropping
 * students.
 * 
 * @author rdbryan2
 */
public class CourseRoll {
	/** List of students enrolled in class */
	private LinkedAbstractList<Student> roll;

	/** Maximum number of allowed students */
	private int enrollmentCap;

	/** Minimum allowed enrollment */
	private static final int MIN_ENROLLMENT = 10;

	/** Maximum allowed enrollment */
	private static final int MAX_ENROLLMENT = 250;
	
	/**
	 * Constructor for CourseRoll
	 * 
	 * @param enrollmentCap maximum number of enrolled students
	 */
	public CourseRoll(int enrollmentCap) {
		setEnrollmentCap(enrollmentCap);
		roll = new LinkedAbstractList<>(enrollmentCap);
	}

	/**
	 * Getter for enrollmentCap
	 * 
	 * @return the enrollmentCap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Setter for enrollmentCap
	 * 
	 * @param enrollmentCap the maximum number of students allowed to enroll in a class
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if(enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		if(roll != null && enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		this.enrollmentCap = enrollmentCap;
		if(roll != null) {
			roll.setcapacity(enrollmentCap);
		}
		
	}
	
	/**
	 * Gets the number of empty seats
	 * 
	 * @return number of available seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Enrolls student in class
	 * 
	 * @param s the student to be enrolled
	 * @throws IllegalArgumentException if student is null, the class is full, or if the student is already enrolled
	 */
	public void enroll(Student s) {
		if(s == null || !canEnroll(s)) {
			throw new IllegalArgumentException();
		}
		try {
			roll.add(s);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Removes student from class
	 * 
	 * @param s the student to be removed
	 * @throws IllegalArgumentException if student is null
	 */
	public void drop(Student s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
		try {
			roll.remove(s);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Checks to see if student can enroll in class
	 * 
	 * @param s the student to be enrolled
	 * @return true if the student can enroll, false otherwise
	 */
	public boolean canEnroll(Student s) {
		if(roll.size() >= enrollmentCap) {
			return false;
		}
		for(int i = 0; i < roll.size(); i++) {
			if(s.equals(roll.get(i))) {
				return false;
			}
		}
		return true;
	}
	
}
