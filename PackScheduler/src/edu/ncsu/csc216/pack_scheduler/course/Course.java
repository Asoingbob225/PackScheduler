/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Extension of Activity representing a course in the WolfScheduler with a
 * specific section, instructor, and meeting times.
 * 
 * Maintains the course's name, title, section, how many credit hours it is
 * worth, the id of the instructor, and what days/times it meets (or whether it
 * is arranged without set meeting times). Allows user to update fields
 * excluding the name (since the name only corresponds to one course, so there
 * is no use case for doing so), retrieve values or formatted representations of
 * fields, and test for equality between itself and another object.
 * 
 * @author abcoste2
 * @author rcmidget
 * @author Sarah Heckman
 */
public class Course extends Activity implements Comparable<Course> {

	/** Minimum length of Course name */
	private static final int MIN_NAME_LENGTH = 4;
	/** Max length of Course name */
	private static final int MAX_NAME_LENGTH = 8;
	/** Digits in section */
	private static final int SECTION_LENGTH = 3;
	/** Minimum Course credit hours */
	private static final int MIN_CREDITS = 1;
	/** Max Course credit hours */
	private static final int MAX_CREDITS = 5;
	/** Valid day letters for activity meeting days */
	private static final char[] VALID_DAYS = { 'M', 'T', 'W', 'H', 'F' };

	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Validator for course's name */
	CourseNameValidator validator;
	/** Roll object to store course enrollment cap */
	private CourseRoll roll;

	/**
	 * Creates a Course object with values for all fields. This is for courses that
	 * are not arranged, so unlike the constructor for arranged courses, there will
	 * also be start times and end times.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap Maximum number of allowed students
	 * @param meetingDays   string of characters for weekdays when course meets
	 * @param startTime     end time of course meeting (in military time)
	 * @param endTime       end time of course meeting (in military time)
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		setEnrollmentCap(enrollmentCap);
	}

	/**
	 * Returns the roll for this course
	 * @return the CourseRoll for this instance
	 */
	public CourseRoll getCourseRoll() {
	    return roll;
	}
	
	private void setEnrollmentCap(int enrollmentCap) {
        roll = new CourseRoll(this, enrollmentCap);
        
    }

    /**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap Maximum number of allowed students
	 * @param meetingDays   meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Generates a hashCode for Course using all fields
	 * 
	 * @return hashCode for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given Object to this Course for equality on all fields
	 * 
	 * @param obj the Object to compare against
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields. This is used for
	 * exporting text representations of the courses in ActivityRecordIO
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(this.getMeetingDays())) {
			return getName() + "," + getTitle() + "," + getSection() + "," + getCredits() + "," + getInstructorId() + ","
			        + roll.getEnrollmentCap() + "," + getMeetingDays();
		}

		return getName() + "," + getTitle() + "," + getSection() + "," + getCredits() + "," + getInstructorId() + ","
				+ roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}
	
	/**
	 * Return the Course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. Course names are never changed in the flow of the
	 * program, so there is no need for public access to the setter. Throws an
	 * IllegalArgumentException if the course name is invalid. Course names are
	 * expected to follow the format of 1-4 letters and 3 digits separated such as
	 * 'ENG 331' or 'E 115'.
	 *
	 * @param name the name to set
	 * @throws InvalidTransitionException {@link CourseNameValidator#isValid(String)}
	 *                                    throws an InvalidTransitionException if
	 *                                    the name parameter has an illegal
	 *                                    character or a legal one in the wrong
	 *                                    place.
	 * @throws IllegalArgumentException   when the name parameter:
	 *                                    <ul>
	 *                                    <li>is null
	 *                                    <li>has a length less than 4 or more than
	 *                                    8
	 *                                    <li>contains a space between the letters
	 *                                    and numbers
	 *                                    <li>has less than 1 or greater than 4
	 *                                    letters
	 *                                    <li>doesn't have exactly DIGIT_COUNT
	 *                                    digits at the end
	 *                                    </ul>
	 */
	private void setName(String name) {
		// Throw exception if the name is null
		if (name == null)
			throw new IllegalArgumentException("Invalid course name.");

		// Throw exception if the name is an empty string
		// Throw exception if the name contains less than 5 character or greater than 8
		// characters
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
			throw new IllegalArgumentException("Invalid course name.");

		// Check for valid name
		validator = new CourseNameValidator();
		try {
			if (!validator.isValid(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;
	}

	/**
	 * Return the Course's section.
	 * 
	 * @return String of the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Set the Course's section. A section must be a set length of numeric digits,
	 * so any String with length != SECTION_LENGTH will result in an
	 * IllegalArgumentException.
	 * 
	 * @param section the String section to set
	 * @throws IllegalArgumentException if the new section is null, incorrect
	 *                                  length, or contains non-digit characters
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		} else {
			for (int i = 0; i < SECTION_LENGTH; i++) {
				if (!Character.isDigit(section.charAt(i))) {
					throw new IllegalArgumentException("Invalid section.");
				}
			}
			this.section = section;
		}
	}

	/**
	 * Return the Course's credits.
	 *
	 * @return integer of the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Set the Course's credits to an integer within the maximum and minimum values.
	 * Throws an IllegalArgumentException if integer is outside of bounds.
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits parameter outside of accepted
	 *                                  range,
	 *                                  {@literal MIN_CREDITS < credits < MAX_CREDITS}
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Return the Course's instructorId.
	 * 
	 * @return String of the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Set meeting days and times for the Course, validating that meetingDays does
	 * not contain any duplicate or invalid characters, before calling the
	 * superclass Activity to complete validation and set the values.
	 * Activity.setMeetingDaysAndTime() will validate that the times are both valid,
	 * and that times are omitted when activity times are arranged. Day/time
	 * combinations that are outside of the range of a day, arranged activities with
	 * times, and duplicate meeting days will result in throwing an
	 * IllegalArgumentException (see throws tag for specific violations).
	 *
	 * @param meetingDays the meetingDays to set
	 * @param startTime   the startTime to set
	 * @param endTime     the endTime to set
	 * @throws IllegalArgumentException when:
	 *                                  <ul>
	 *                                  <li>meetingDays consists of any characters
	 *                                  other than 'M', 'T', 'W', 'H', 'F', or 'A'.
	 *                                  (Days that aren't weekdays)
	 *                                  <li>meetingDays has a duplicate character
	 *                                  <li>if 'A' is in the meeting days list, and
	 *                                  it is not the only character
	 *                                  <li>the start time is not between 0000 and
	 *                                  2359 or an invalid military time
	 *                                  <li>the end time is not between 0000 and
	 *                                  2359 or an invalid military time
	 *                                  <li>the end time is less than the start time
	 *                                  (i.e., no overnight activities)
	 *                                  <li>a start time and/or end time is listed
	 *                                  when meeting days is 'A'
	 *                                  </ul>
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		// Arranged activities
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else {
			// Check meeting days are valid
			if (meetingDays.length() > VALID_DAYS.length) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			// Counts the amount of distinct valid day letters in string and if the
			// total is less than length, there must be duplicate or invalid members
			int validCount = 0;
			for (int i = 0; i < VALID_DAYS.length; i++) {
				if (meetingDays.indexOf(VALID_DAYS[i]) >= 0) {
					validCount++;
				}
			}
			if (validCount < meetingDays.length()) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}

		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Set the Course's instructorId. Instructor ID's must be neither null nor
	 * empty, so an IllegalArgumentException will be thrown otherwise.
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorId parameter is null or empty
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Returns an array of identifying information about a course to be displayed in
	 * the schedule and catalog views. The elements corresponding to the columns
	 * will be name, section, title, and meeting days/times.
	 *
	 * @return String[] of length 5 containing course's name, section, title,
	 *         meeting string, and open seats
	 */
	@Override
	public String[] getShortDisplayArray() {
	    String openSeats = String.valueOf(roll.getOpenSeats());
		return new String[] { getName(), getSection(), getTitle(), getMeetingString(), openSeats};
	}

	/**
	 * Returns an array of detailed information about a course. When a user views
	 * the information for a single course, they will be able to see the credits and
	 * instructorId in addition to the name, section, title, and meeting days/times.
	 * Will be displayed alongside events in the final schedule table, so the 7th
	 * element is an empty string corresponding to an eventDetails column.
	 *
	 * @return String[] of length 7 containing course's name, section, title,
	 *         meeting string, credits, instructorId, and an empty string
	 */
	@Override
	public String[] getLongDisplayArray() {
		return new String[] { getName(), getSection(), getTitle(), String.valueOf(getCredits()), getInstructorId(),
				getMeetingString(), "" };
	}

	/**
	 * Compares two activity objects to see if they are both the same course.
	 * Returns true if the activity parameter is an instance of Course and has the
	 * same name as this Course.
	 * 
	 * @param activity Activity object to compare against this Course
	 * @return true if the parameter and this Activity are both Courses and have the
	 *         same name
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if(activity == null) {
			return false;
		}
		
		if (activity.getClass() == this.getClass()) {
			Course c = (Course) activity;

			return this.getName().equals(c.getName());
		}
		return false;
	}

	/**
	 * Compare two courses for the purpose of ordering. Does an ascending
	 * alphabetical comparison on the values of the course names first, if those
	 * match, it compares course section in ascending order.
	 * 
	 * @param c Course object to compare
	 * @return integer with values less than 0, 0, or greater than 0 when this
	 *         course is less than, equal, or greater (respectively) than the
	 *         parameter course. Should never return 2 but if it does its because
	 *         the courses were not able to be compared for some reason
	 */
	@Override
	public int compareTo(Course c) {
		// if courses are identical they are equal
		if (this.equals(c)) {
			return 0;
		}
		// if courses have identical names it compares sections
		if (this.name.equals(c.getName())) {
			if (Integer.parseInt(this.section) == Integer.parseInt(c.getSection())) {
				return 0;
			} else if (Integer.parseInt(this.section) < Integer.parseInt(c.getSection())) {
				return -1;
			} else if (Integer.parseInt(this.section) > Integer.parseInt(c.getSection())) {
				return 1;
			}
		}
		// Next it compares the first character
		else if (this.name.charAt(0) < c.getName().charAt(0)) {
			return -1;
		} else if (this.name.charAt(0) > c.getName().charAt(0)) {
			return 1;
		}

		// if they have more than one character at the front similar this next part
		// should catch it
		// Im using a for loop to compare all characters up to whichever course has the
		// shorter length so Im not throwing out of bounds errors
		int y;
		if (this.name.length() > c.getName().length()) {
			y = c.getName().length();
		} else {
			y = this.name.length();
		}
		for (int x = 1; x < y; x++) {
			if (this.name.charAt(x) < c.getName().charAt(x)) {
				return -1;
			}
			if (this.name.charAt(x) > c.getName().charAt(x)) {
				return 1;
			}
		}

		return 2;

	}
}
