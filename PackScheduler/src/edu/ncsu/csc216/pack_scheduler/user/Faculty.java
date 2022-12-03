/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

//import java.util.Objects;

//import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * The faculty class maintains information about a given faculty's first names,
 * last names, ids, emails, passwords, and max number of courses. Allow users to view and
 * update fields, with the exception of the faculty's ID, which shouldn't be
 * updated. It also maintains the schedule of the the faculty.
 * 
 * @author Adharsh Rajagopal
 *
 */
public class Faculty extends User {
	/** constant for the max number of courses that a faculty member can teach. */
	public static final int MAX_COURSES = 3;

	/** constant for the minimum number of courses that a faculty member can teach. */
	public static final int MIN_COURSES = 1;

	/** Faculty's max courses. */
	private int maxCourses;
	
	/** schedule of type FacultySchedule */
	private FacultySchedule schedule;

	/**
	 * Constructs a Faculty object with values for all fields.
	 * 
	 * @param firstName  faculty's first name
	 * @param lastName   faculty's last name
	 * @param id         faculty's id number as a string
	 * @param email      faculty's email
	 * @param password   faculty's password
	 * @param maxCourses the max number of faculty's courses
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * gets Faculty's maxCourses.
	 *
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * sets Faculty's maxCourses.
	 *
	 * @param maxCourses the maxCourses to set
	 * @throws IllegalArgumentException if maxCourses is less than 1 or greater than
	 *                                  3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	/**
	 * Returns the schedule of type FacultySchedule
	 * @return FacultySchedule
	 */
	public FacultySchedule getSchedule()
	{
		return schedule;
	}
	
	/**
	 * returns true if the number of scheduled courses is greater than 
	 * the Faculty’s maxCourses
	 * @return true or false based on if
	 * the number of scheduled courses is greater than the Faculty's maxCourses
	 */
	public boolean isOverloaded()
	{
		if(schedule.getScheduledCourses().length > getMaxCourses())
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Generates a hashCode for Faculty using all fields.
	 *
	 * @return hashCode for Faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Compares a given object to this Faculty for equality on all fields.
	 * 
	 * @param obj the Object to compare
	 * @return true if obj and this Student are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return !(maxCourses != other.maxCourses);
			
	}

	/**
	 * Overrides the toString method to have all fields in a comma separated list.
	 *
	 * @return a string of all fields separated by commas
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," 
	     + this.getEmail() + "," + this.getPassword() + "," + maxCourses;
	}
	
	

	
}
