/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the creation of InvalidTransitionException's
 * 
 * @author abcoste2
 */
class InvalidTransitionExceptionTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException()}.
	 */
	@Test
	void testInvalidTransitionException() {
		Exception e = new InvalidTransitionException();

		assertEquals("Invalid FSM Transition.", e.getMessage());
		assertEquals(
				"edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException: Invalid FSM Transition.",
				e.toString());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException(java.lang.String)}.
	 */
	@Test
	void testInvalidTransitionExceptionString() {
		Exception e = new InvalidTransitionException("message");

		assertEquals("message", e.getMessage());
		assertEquals("edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException: message",
				e.toString());
	}

}
