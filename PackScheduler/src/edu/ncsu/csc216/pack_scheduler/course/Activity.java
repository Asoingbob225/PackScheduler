/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Unimplemented Object representing an activity in the WolfScheduler with a
 * title and meeting days/times. This is the parent to the Course and Event
 * classes, which will both extend their shared functionality to represent
 * different kinds of schedule members.
 * 
 * Maintains the activity's title and what days/times it meets (or whether it is
 * arranged without set meeting times). Allows user to retrieve and update
 * fields and test for equality between itself and another object. Implements
 * the Conflict interface, so it can also check if an activity has a time
 * conflict with this instance.
 * 
 * @author abcoste2
 */
public abstract class Activity implements Conflict {

	/** Max hour value for meeting times */
	private static final int UPPER_HOUR = 24;
	/** Max minute value for meeting times */
	private static final int UPPER_MINUTE = 60;
	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Creates a new Activity with a title and meeting days/times.
	 * 
	 * @param title       title of Activity
	 * @param meetingDays string of characters for weekdays when activity meets
	 * @param startTime   start time of activity (in military time)
	 * @param endTime     end time of activity (in military time)
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Generates a hashCode for Activity using all fields
	 * 
	 * @return hashCode for activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given Object to this Activity for equality on all fields
	 * 
	 * @param obj the Object to compare against
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Checks a given Activity to see if has a time conflict with this instance of
	 * Activity. This is used in WolfScheduler when adding activities to a schedule
	 * to prevent the addition of activities that would make the schedule impossible
	 * for the user to stick to.
	 * 
	 * According to the requirements: two Activity objects are in conflict if there
	 * is at least one day with an overlapping time. A time is overlapping if the
	 * minute is the same. If the Activity from the parameter is conflicting a
	 * checked ConflictException is thrown, so that the client is required to handle
	 * the situation rather than adding a conflicting activity.
	 * 
	 * @param possibleConflictingActivity the Activity that may be conflicting
	 * @throws ConflictException when the possibleConflictingActivity has meetings
	 *                           that overlap with this instance's meetings
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		// Arranged activities cannot conflict
		if ("A".equals(this.meetingDays) || "A".equals(possibleConflictingActivity.meetingDays)) {
			return;
		}

		// Find length of each activity separately
		int possibleLength = possibleConflictingActivity.endTime - possibleConflictingActivity.startTime;
		int thisLength = this.endTime - this.startTime;
		// Length the two span together
		int combinedLength = Math.max(this.endTime - possibleConflictingActivity.startTime,
				possibleConflictingActivity.endTime - this.startTime);

		// If the two activities don't span more time than the sum of their durations,
		// then they must be overlapping. Otherwise, we don't have to enter the loop to
		// check days.
		if (combinedLength <= thisLength + possibleLength) {
			// Check if any days in possible match any of this activity's
			for (int i = 0; i < this.meetingDays.length(); i++) {
				if (possibleConflictingActivity.meetingDays.contains(this.meetingDays.substring(i, i + 1))) {
					throw new ConflictException("Schedule conflict.");
				}
			}
		}
	}

	/**
	 * WolfScheduler will need to compare activities, including those of different
	 * types, to find duplicates. This comparison is distinct from an equals()
	 * comparison, because subclasses have criteria for duplicate courses that don't
	 * strictly mean matching on all fields. Like equals(), returns true if the
	 * activity passed is the same as this activity.
	 * 
	 * @param activity Activity object to compare against this
	 * @return true if the parameter and this Activity are duplicates
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Returns an array of information about an activity. This is intended as a
	 * brief representation of an activity for use in the 4-column course catalog
	 * and schedule views.
	 * 
	 * @return a String[] of fields for activity
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Returns a detailed array of information about an activity. This is intended
	 * to display all applicable details of a given activity in the 7-column course
	 * detail and final schedule views.
	 * 
	 * @return a String[] of fields for activity
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Return the Activity's title.
	 * 
	 * @return String of the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the Activity's title. Activity names must be neither null nor empty, so
	 * an IllegalArgumentException will be thrown otherwise.
	 * 
	 * @param title the title String to set
	 * @throws IllegalArgumentException if title parameter is null or empty
	 */
	public void setTitle(String title) {
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Return the Activity's meetingDays
	 * 
	 * @return String of the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Return the Activity's startTime
	 * 
	 * @return integer of the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Return the Activity's endTime
	 * 
	 * @return integer of the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Set meeting days and times, validating that the times are both valid and that
	 * times are omitted when activity times are arranged. Day/time combinations
	 * that are outside of the range of a day, arranged activities with times, and
	 * duplicate meeting days will result in throwing an IllegalArgumentException
	 * (see throws tag for specific violations). Child classes have different
	 * requirements for valid meeting days, so they will check that they are valid
	 * and not null before calling this method for the remaining checks.
	 *
	 * @param meetingDays the (already checked) meetingDays to set
	 * @param startTime   the startTime to set
	 * @param endTime     the endTime to set
	 *
	 * @throws IllegalArgumentException when:
	 *                                  <ul>
	 *                                  <li>the start time is not between 0000 and
	 *                                  2359 or an invalid military time
	 *                                  <li>the end time is not between 0000 and
	 *                                  2359 or an invalid military time
	 *                                  <li>the end time is less than the start time
	 *                                  (i.e., no overnight activities)
	 *                                  </ul>
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// Activities cannot continue through multiple days
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		// Check that start and end are valid military times
		int startMinute = startTime % 100;
		int startHour = startTime / 100;
		int endMinute = endTime % 100;
		int endHour = endTime / 100;

		if (startMinute >= UPPER_MINUTE || startMinute < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startHour >= UPPER_HOUR || startHour < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endMinute >= UPPER_MINUTE || endMinute < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endHour >= UPPER_HOUR || endHour < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		this.startTime = startTime;
		this.endTime = endTime;
		this.meetingDays = meetingDays;
	}

	/**
	 * Return meeting times in the format: "HH:MM[AM/PM]-HH:MM[AM/PM] [Days]" or
	 * "Arranged" for arranged activities (ones without a set time). For example,
	 * Mondays and Wednesdays from 11:45am to 1:00pm would be "11:45AM-1:00PM MW"
	 * 
	 * @return meetingString for Activity
	 */
	public String getMeetingString() {
		String startString = getTimeString(startTime);
		String endString = getTimeString(endTime);

		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		return String.format("%s %s-%s", this.meetingDays, startString, endString);
	}

	/**
	 * Convert military time to standard time to be used in the meetingString
	 * 
	 * @param time a time in military (24 hour) time
	 * @return standardTime a time in standard format
	 */
	private static String getTimeString(int time) {
		int minute = time % 100;
		int hour = time / 100;
		String suffix = "AM";

		if (hour > 12) {
			suffix = "PM";
			hour -= 12;
		} else if (hour == 12) {
			suffix = "PM";
		}

		return String.format("%d:%02d%s", hour, minute, suffix);
	}

}