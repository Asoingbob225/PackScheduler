package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verify function of the switch-case implementation of the state pattern.
 * Identical to the tests for the inner class implementation, since function
 * should be the same.
 * 
 * @author cbthomp3
 */
public class CourseNameValidatorFSMTest {

	/**
	 * Verifies that the FSM returns true for isValid(String) when provided a valid
	 * course name, even if used consecutively.
	 * 
	 * @param courseName a valid course name string
	 */
	@ParameterizedTest
	@ValueSource(strings = { "A100", "AA100", "ABC999", "ABC100", "ABCD100A", "CS116", "C116", "asd000" })
	public void testValidCourse(String courseName) {
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		boolean valid = assertDoesNotThrow(() -> fsm.isValid(courseName));
		assertTrue(valid);

		valid = assertDoesNotThrow(() -> fsm.isValid(courseName));
		assertTrue(valid);
	}

	/**
	 * Verifies that the FSM returns false for isValid(String) when provided an
	 * invalid course name which doesn't reach an error state, even if used
	 * consecutively. The provided values should not throw exceptions because they
	 * terminate before reaching a valid end state.
	 * 
	 * @param courseName an invalid course name string
	 */
	@ParameterizedTest
	@ValueSource(strings = { "A", "A1", "ABC", "AB10", "ABC10", "ABCD10", })
	public void testInvalidCourseFalse(String courseName) {
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		boolean valid = assertDoesNotThrow(() -> fsm.isValid(courseName));
		assertFalse(valid);

		valid = assertDoesNotThrow(() -> fsm.isValid(courseName));
		assertFalse(valid);
	}

	/**
	 * Verifies that the FSM throws a {@link InvalidTransitionException} when
	 * isValid(String) is provided an invalid course name which does reach an error
	 * state, even if used consecutively.
	 * 
	 * @param courseName       an invalid course name string
	 * @param exceptionMessage the expected exception message describing the issue
	 *                         with the courseName
	 */
	@ParameterizedTest
	@CsvSource(value = { "'^100', 'Course name can only contain letters and digits.'",
			"'1000', 'Course name must start with a letter.'",
			"'A-100', 'Course name can only contain letters and digits.'",
			"'AA-200', 'Course name can only contain letters and digits.'",
			"'AAA-100', 'Course name can only contain letters and digits.'",
			"'CSCA2-0', 'Course name can only contain letters and digits.'",
			"'CSC20-', 'Course name can only contain letters and digits.'",
			"'CSCA20-', 'Course name can only contain letters and digits.'",
			"'CSC1000', 'Course name can only have 3 digits.'",
			"'IWODH100','Course name cannot start with more than 4 letters.'",
			"'CSC1A', 'Course name must have 3 digits.'", "'CSC22A', 'Course name must have 3 digits.'",
			"'CSC200AA', 'Course name can only have a 1 letter suffix.'",
			"'CSC200A1', 'Course name cannot contain digits after the suffix.'" })
	public void testInvalidCourseException(String courseName, String exceptionMessage) {
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
		Exception e = assertThrows(InvalidTransitionException.class, () -> fsm.isValid(courseName));
		assertEquals(exceptionMessage, e.getMessage());

		e = assertThrows(InvalidTransitionException.class, () -> fsm.isValid(courseName));
		assertEquals(exceptionMessage, e.getMessage());
	}
}
