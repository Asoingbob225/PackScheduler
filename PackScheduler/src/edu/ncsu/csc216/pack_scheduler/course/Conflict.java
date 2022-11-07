/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A class implementing conflict must have the ability to check if two members
 * are unable to be valid at the same time and throw a ConflictException if so.
 * In the context of WolfScheduler, activities with overlapping meeting times
 * cannot be in the schedule, so they will implement Conflict to prevent the
 * user creating schedules with conflicts.
 * 
 * @author Aidan
 */
public interface Conflict {
	
	/**
	 * Checks a given Activity to see if has a time conflict with this instance of
	 * Activity. Throws a ConflictException if there is a time conflict.
	 * 
	 * @param possibleConflictingActivity the Activity that may be conflicting
	 * @throws ConflictException when the possibleConflictingActivity has meetings
	 *                           that overlap with this instance's meetings
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
