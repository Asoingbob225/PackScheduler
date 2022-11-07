/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * To ensure that the WolfScheduler client handles possible schedule conflicts,
 * ConflictException is thrown by implementations of the Conflict interface.
 * This makes it impossible to ignore a schedule conflict.
 * 
 * @author Aidan
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a ConflictException instance with the default message.
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}

	/**
	 * Creates a ConflictException instance with the provided message.
	 * 
	 * @param message String of the message to be set
	 */
	public ConflictException(String message) {
		super(message);
	}

}
