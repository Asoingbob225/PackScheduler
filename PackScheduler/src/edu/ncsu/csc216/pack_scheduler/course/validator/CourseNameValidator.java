package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This class contains a FSM used to verify that a created course has a valid
 * name. Uses a FSM object pattern with an instance each of four inner classes,
 * LetterState, SuffixState, InitialState, and NumberState. An reference of
 * their superclass type, State, maintains a reference to the current state. Has
 * one method to validate a String parameter.
 * 
 * @author Davis Bryant
 */
public class CourseNameValidator {

	/**
	 * Final instance of the letterState class, which extends the abstract State
	 * inner class
	 */
	private final State letterState = new LetterState();
	/**
	 * Final instance of the suffixState class, which extends the abstract State
	 * inner class
	 */
	private final State suffixState = new SuffixState();
	/**
	 * Final instance of the initialState class, which extends the abstract State
	 * inner class
	 */
	private final State initialState = new InitialState();
	/**
	 * Final instance of the numberState class, which extends the abstract State
	 * inner class
	 */
	private final State numberState = new NumberState();
	/**
	 * A state object represents the current state of the FSM that checks input to
	 * determine if a course name is valid
	 */
	private State currentState;

	/** Whether an ending state of the FSM is valid or not */
	private boolean validEndState;

	/** The count of letters in the input */
	private int letterCount;
	/** The count of digits in the input */
	private int digitCount;

	/** The maximum number of digits a course name can contain */
	private final static int MAX_DIGITS = 3;
	/** The maximum number of letters a course name can contain */
	private final static int MAX_LETTERS = 4;

	/**
	 * Method used to check whether or not a transition between states is valid or
	 * not
	 * 
	 * @param name the name of the course
	 * @return true if the course name is valid, or false if the course name is
	 *         invalid
	 * @throws InvalidTransitionException when the FSM attempts an invalid
	 */
	public boolean isValid(String name) throws InvalidTransitionException {
		currentState = initialState;
		letterCount = 0;
		digitCount = 0;
		validEndState = false;

		for (int i = 0; i < name.length(); i++) {
			char currentChar = name.charAt(i);
			if (Character.isDigit(currentChar)) {
				currentState.onDigit();
			} else if (Character.isLetter(currentChar)) {
				currentState.onLetter();
			} else {
				currentState.onOther();
			}
		}
		return validEndState;
	}

	/**
	 * Abstract class that represents the current state of the FSM to validate
	 * course names
	 * 
	 * @author Chase Thompson
	 *
	 */
	private abstract class State {
		/**
		 * Checks to see if adding a letter is valid
		 * 
		 * @throws InvalidTransitionException if the transition is invalid
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
		 * Checks to see if adding a digit is valid
		 * 
		 * @throws InvalidTransitionException if the transition is invalid
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Checks to see if the input is an invalid character
		 * 
		 * @throws InvalidTransitionException if the input is not a letter or number
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}

	}

	/**
	 * Private inner class used to represent the state in the FSM where a letter has
	 * just been inputed. Updates counter variables to help keep track of the user
	 * input for course name.
	 * 
	 * @author Davis Bryant
	 *
	 */
	private class LetterState extends State {
		/**
		 * If the user has inputed a letter, the letter count is increased if a letter
		 * can be added to the course name
		 * 
		 * @throws InvalidTransitionException if the course name cannot have a letter as
		 *                                    the next character
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < MAX_LETTERS) {
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		/**
		 * If the user has input a digit, the digit count is increased.
		 */
		@Override
		public void onDigit() {
			digitCount++;
			currentState = numberState;
		}

	}

	/**
	 * Private inner class used to represent the state in the FSM where a suffix has
	 * just been inputed. There should be no more input after the suffix.
	 * 
	 * @author Davis Bryant
	 *
	 */
	private class SuffixState extends State {
		/**
		 * If the user has inputed a letter, the letter count is increased if a letter
		 * can be added to the course name
		 * 
		 * @throws InvalidTransitionException if the course name cannot have a letter as
		 *                                    the next character
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * If the user has input a digit after the suffix, then an exception is thrown.
		 * 
		 * @throws InvalidTransitionException if the course name has a digit after the
		 *                                    suffix
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

		}

	}

	/**
	 * Private inner class used to represent the state in the FSM nothing has just
	 * been inputed yet. Updates counter variables to help keep track of the user
	 * input for course name. The first value should be a letter.
	 * 
	 * @author Davis Bryant
	 *
	 */
	private class InitialState extends State {
		/**
		 * If the user has inputed a letter, the letter count is increased if a letter
		 * can be added to the course name
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = letterState;
		}

		/**
		 * If the first value for a course name is a digit, then it is invalid and an
		 * InvalidTransitionException is thrown
		 * 
		 * @throws InvalidTransitionException if the course name does not start with a
		 *                                    letter
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");

		}
	}

	/**
	 * Private inner class used to represent the state in the FSM where a number has
	 * just been inputed. Updates counter variables to help keep track of the user
	 * input for course name.
	 * 
	 * @author Davis Bryant
	 *
	 */
	private class NumberState extends State {
		/**
		 * If the user has inputed a letter, the letter count is increased if a letter
		 * can be added to the course name
		 * 
		 * @throws InvalidTransitionException if the course name cannot have a letter as
		 *                                    the next character
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == MAX_DIGITS) {
				currentState = suffixState;
				validEndState = true;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		/**
		 * If the user has input a digit, the digit count is increased.
		 * 
		 * @throws InvalidTransitionException if the number of digits exceeds the
		 *                                    maximum
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount < MAX_DIGITS) {
				digitCount++;
			} else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			if (digitCount == MAX_DIGITS) {
				validEndState = true;
			}

		}

	}
}
