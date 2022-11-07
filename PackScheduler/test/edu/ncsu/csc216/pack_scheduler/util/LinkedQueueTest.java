/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author adharsh
 *
 */
class LinkedQueueTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#push(java.lang.Object)}.
	 */
	@Test
	void testPush() {
		LinkedStack<Integer> s = new LinkedStack<Integer>(10);
		assertEquals(0, s.size());
		s.push(9);
		assertEquals(1, s.size());
		s.push(6);
		assertEquals(2, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#pop()}.
	 */
	@Test
	void testPop() {
		LinkedStack<Integer> s = new LinkedStack<Integer>(10);
		assertEquals(0, s.size());
		s.push(9);
		assertEquals(1, s.size());
		s.push(6);
		assertEquals(2, s.size());
		s.pop();
		assertEquals(1, s.size());
		s.pop();
		assertEquals(0, s.size());

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#isEmpty()}.
	 */
	@Test
	void testIsEmpty() {
		LinkedStack<Integer> s = new LinkedStack<Integer>(10);
		s.push(9);
		s.pop();
		assertTrue(s.isEmpty());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#size()}.
	 */
	@Test
	void testSize() {
		LinkedStack<Integer> s = new LinkedStack<Integer>(10);
		s.push(9);
		s.push(2);
		s.push(4);
		assertEquals(3, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#setCapacity(int)}.
	 */
	@Test
	void testSetCapacity() {
		LinkedStack<Integer> s = new LinkedStack<Integer>(10);
		s.push(9);
		s.push(2);
		s.push(4);
		assertThrows(IllegalArgumentException.class, () -> s.setCapacity(1));
	}

}
