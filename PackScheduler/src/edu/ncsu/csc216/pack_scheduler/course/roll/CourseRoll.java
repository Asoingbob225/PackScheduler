package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
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
	
	/** Course variable */
	private Course course;
	
	/** List of students */
	private ArrayQueue<Student> waitlist;

	/** Minimum allowed enrollment */
	private static final int MIN_ENROLLMENT = 10;

	/** Maximum allowed enrollment */
	private static final int MAX_ENROLLMENT = 250;
	
	/**
	 * Constructor for CourseRoll
	 * @param course type of Course
	 * @param enrollmentCap maximum number of enrolled students
	 * @throws IllegalArgumentException if course is null
	 */
	public CourseRoll(Course course, int enrollmentCap) {
		if(course == null)
		{
			throw new IllegalArgumentException();
		}
		setEnrollmentCap(enrollmentCap);
		roll = new LinkedAbstractList<>(enrollmentCap);
		waitlist = new ArrayQueue<Student>(10);
		this.course = course;
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
	 * @throws IllegalArgumentException if enrollment cap is above the max enrollment or below the 
	 * min enrollment cap. It is also thrown if enrollmentCap is null and if enrollementCap is less than the size of the roll.
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
		if(roll.size() == enrollmentCap)
		{
			if(waitlist.size() < 10)
			{
				waitlist.enqueue(s);
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
		else
		{
			roll.add(s);
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
		
				
		if (waitlist.contains(s)) {
			Student first = waitlist.dequeue();
			boolean finished = false;
			if (first.equals(s)) {
				finished = true;
			} else {
				waitlist.enqueue(first);
			}
			while (!finished) {
				Student current = waitlist.dequeue();
				if (!s.equals(current)) {
					waitlist.enqueue(current);
					if (current.equals(first)) {
						finished = true;
					}
				}
			}
		}
		
		if(roll.remove(s) && waitlist.size() > 0)
		{
			Student s1 = waitlist.dequeue();
			roll.add(s1);
			Schedule schedule = s1.getSchedule(); 
			schedule.addCourseToSchedule(course);
		}
	}
	
	/**
	 * Checks to see if student can enroll in class
	 * 
	 * @param s the student to be enrolled
	 * @return true if the student can enroll, false otherwise
	 */
	public boolean canEnroll(Student s) {
		for(int i = 0; i < roll.size(); i++) {
			if(s.equals(roll.get(i))) {
				return false;
			}
		}
		return !(waitlist.size() == 10);
		
	}
	
	/**
	 * returns the number of Students on the wait list
	 * @return the number of students on the wait list
	 */
	public int getNumberOnWaitlist()
	{
		return waitlist.size();
	}
	
}
