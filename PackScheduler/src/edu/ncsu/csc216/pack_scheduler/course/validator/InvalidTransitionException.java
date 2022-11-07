/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This checked exception is thrown in the case of an invalid transitions so
 * that clients of the CourseNameFSM must prevent or handle them.
 * 
 * @author abcoste2
 */
public class InvalidTransitionException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates using the Exception superclass's constructor with the default
	 * message: "Invalid FSM Transition."
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}

	/**
	 * Instantiates using the Exception superclass's constructor with the
	 * parameterized message
	 * 
	 * @param message the message for the new InvalidTransitionException
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
}
