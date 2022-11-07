package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The student class maintains information about a given student's first names,
 * last names, ids, emails, passwords, and max credits. Allow users to view and
 * update fields, with the exception of the Student's ID, which shouldn't be
 * updated.
 *
 * @author Raven Midgett
 * @author abcoste2
 * @author rdbryan2
 */
public class Student extends User implements Comparable<Student> {

	/** constant for the max number of credits a student can have. */
	public static final int MAX_CREDITS = 18;

	/** constant for the minimum number of credits a student can have. */
	public static final int MIN_CREDITS = 3;

	/** Students max credits. */
	private int maxCredits;
	
	/** Student's Schedule */
	private Schedule schedule;

	/**
	 * Creates a Student with the given first name, last name, id, email, and
	 * password.
	 *
	 * @param firstName students first name
	 * @param lastName  students last name
	 * @param id        students id number as a string
	 * @param email     students email
	 * @param password  students password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(MAX_CREDITS);
		schedule = new Schedule();
	}

	/**
	 * Constructs a Student object with values for all fields.
	 * 
	 * @param firstName  students first name
	 * @param lastName   students last name
	 * @param id         students id number as a string
	 * @param email      students email
	 * @param password   students password
	 * @param maxCredits the max number of student credits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * gets Students maxCredits.
	 *
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * sets Students maxCredits.
	 *
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if maxCredits is less than 3 or greater than
	 *                                  18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < MIN_CREDITS || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Overrides the toString method to have all fields in a comma separated list.
	 *
	 * @return a string of all fields separated by commas
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," 
	     + this.getEmail() + "," + this.getPassword() + "," + maxCredits;
	}

	/**
	 * Students in a directory must be sorted alphabetically by last name, then
	 * first name, then unity id. This adds the ability to compare a specified
	 * student against this one in order to store students in a SortedList.
	 * 
	 * @param s Student object to compare to this student
	 * @return integer with values less than 0, 0, or greater than 0 when this
	 *         student is less than, equal, or greater (respectively) than the
	 *         parameter student
	 */
	@Override
	public int compareTo(Student s) {
		// Use string's compareTo to compare the fields in the order they are used to
		// rank. The first non-zero value is the comparison value.
		int compareLastName = this.getLastName().compareToIgnoreCase(s.getLastName());
		if (compareLastName != 0) {
			return compareLastName;
		}
		
		int compareFirstName = this.getFirstName().compareToIgnoreCase(s.getFirstName());
		if (compareFirstName != 0) {
			return compareFirstName;
		}

		// If this is zero, each field must be equal. This should not happen, because
		// ID's are unique
		return this.getId().compareToIgnoreCase(s.getId());
	}

	/**
	 * Generates a hashCode for Student using all fields.
	 *
	 * @return hashCode for Student
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}

	/**
	 * Compares a given object to this Student for equality on all fields.
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
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * Getter for schedule
	 * 
	 * @return schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Method used to check if a course can be added to a student's schedule, taking into account
	 * their current schedule and max credits
	 * @param c the course to be checked
	 * @return true if the course can be added, false if it cannot be added
	 */
	public boolean canAdd(Course c) {
	    if (c == null) {
	        return false;
	    }
	    boolean scheduleAdd = schedule.canAdd(c);
	    if (!scheduleAdd) {
	        return false;
	    }
	    
	    int totalCredits = schedule.getScheduleCredits() + c.getCredits();
	    
	    return totalCredits <= maxCredits;
	}
}
