/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the construction of ConflictException. Verifies that the exception
 * message is set appropriately for default and parameterized versions.
 * 
 * @author abcoste2
 */
class ConflictExceptionTest {

	/**
	 * Test method for ConflictException() constructor Makes sure that the
	 * parameterless constructor sets the message to the default.
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
		assertEquals("Schedule conflict.", ce.getMessage());
	}

	/**
	 * Test method for ConflictException(String) constructor. Makes sure that the
	 * String parameterized constructor sets the ConflictException's message to the
	 * parameter.
	 */
	@Test
	public void testConflictExceptionString() {
		ConflictException ce = new ConflictException("Custom exception message");
		assertEquals("Custom exception message", ce.getMessage());
	}

}
