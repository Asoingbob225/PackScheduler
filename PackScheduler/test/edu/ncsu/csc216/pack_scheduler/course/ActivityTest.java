/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Validates conflict checking functionality for cases with no overlap (no
 * conflict), full overlap, and partial overlap.
 * 
 * @author abcoste2
 */
class ActivityTest {

	/**
	 * Test method for Activity.checkConflict(Activity). Verifies that two
	 * non-conflicting activities (same time, different days) will not throw an
	 * exception.
	 */
	@Test
	void testCheckConflictDifferentDay() {
		Activity a1 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 27, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 21, "TH", 1330, 1445);

		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Test method for Activity.checkConflict(Activity). Verifies that arranged
	 * activities (no set meeting times) will not throw an exception with either
	 * arranged or scheduled courses.
	 */
	@Test
	void testCheckConflictArranged() {
		Activity a1 = new Course("CSC216", "SW Development Fundamentals", "601", 3, "sesmith5", 35, "A");
		Activity a2 = new Course("CSC216", "SW Development Fundamentals", "601", 3, "sesmith5", 100, "A");
		Activity a3 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 17, "TH", 1330, 1445);
		
		// Arranged against arranged
		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
		
		// Scheduled against arranged
		assertDoesNotThrow(() -> a3.checkConflict(a1));
	}

	/**
	 * Test method for Activity.checkConflict(Activity). Verifies that two
	 * non-conflicting activities (same days, different times) will not throw an
	 * exception.
	 */
	@Test
	void testCheckConflictDifferentTime() {
		Activity a1 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 33, "MWF", 1330, 1445);
		Activity a2 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 21, "MWF", 930, 1025);

		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Test method for Activity.checkConflict(Activity). Verifies that two
	 * conflicting activities will throw the correct exception.
	 */
	@Test
	public void testCheckConflictWithConflict() {
		Activity a1 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 22, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 34, "M", 1330, 1445);

		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());

		Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
		assertEquals("Schedule conflict.", e2.getMessage());
	}

	/**
	 * Test method for Activity.checkConflict(Activity). Verifies that two
	 * activities with one start time matching another end time will throw the
	 * correct exception.
	 */
	@Test
	public void testCheckConflictStartEndOverlap() {
		Activity a1 = new Course("CSC216", "SW Development Fundamentals", "009", 3, "sesmith5", 11, "M", 1100, 1330);
		Activity a2 = new Course("CSC216", "SW Development Fundamentals", "001", 3, "sesmith5", 33, "M", 1330, 1445);

		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());

		Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
		assertEquals("Schedule conflict.", e2.getMessage());
	}
}
