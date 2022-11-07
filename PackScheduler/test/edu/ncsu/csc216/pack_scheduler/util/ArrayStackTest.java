/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author stbeuav
 *
 */
class ArrayStackTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#push(java.lang.Object)}.
	 */
	@Test
	void testPush() {
		ArrayStack<Integer> s = new ArrayStack<Integer>(10);
		assertEquals(0, s.size());
		s.push(9);
		assertEquals(1, s.size());
		s.push(6);
		assertEquals(2, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#pop()}.
	 */
	@Test
	void testPop() {
		ArrayStack<Integer> s = new ArrayStack<Integer>(10);
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
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#isEmpty()}.
	 */
	@Test
	void testIsEmpty() {
		ArrayStack<Integer> s = new ArrayStack<Integer>(10);
		s.push(9);
		s.pop();
		assertTrue(s.isEmpty());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#size()}.
	 */
	@Test
	void testSize() {
		ArrayStack<Integer> s = new ArrayStack<Integer>(10);
		s.push(9);
		s.push(2);
		s.push(4);
		assertEquals(3, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#setCapacity(int)}.
	 */
	@Test
	void testSetCapacity() {
		ArrayStack<Integer> s = new ArrayStack<Integer>(10);
		s.push(9);
		s.push(2);
		s.push(4);
		assertThrows(IllegalArgumentException.class, () -> s.setCapacity(1));
	}

}
