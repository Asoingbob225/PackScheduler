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
		LinkedQueue<Integer> s = new LinkedQueue<Integer>(10);
		assertEquals(0, s.size());
		s.enqueue(9);
		assertEquals(1, s.size());
		s.enqueue(6);
		assertEquals(2, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#pop()}.
	 */
	@Test
	void testPop() {
		LinkedQueue<Integer> s = new LinkedQueue<Integer>(10);
		assertEquals(0, s.size());
		s.enqueue(9);
		assertEquals(1, s.size());
		s.enqueue(6);
		assertEquals(2, s.size());
		assertEquals(9, s.dequeue());
		assertEquals(1, s.size());
		assertEquals(6, s.dequeue());
		assertEquals(0, s.size());

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#isEmpty()}.
	 */
	@Test
	void testIsEmpty() {
		LinkedQueue<Integer> s = new LinkedQueue<Integer>(10);
		s.enqueue(9);
		s.dequeue();
		assertTrue(s.isEmpty());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#size()}.
	 */
	@Test
	void testSize() {
		LinkedQueue<Integer> s = new LinkedQueue<Integer>(10);
		s.enqueue(9);
		s.enqueue(2);
		s.enqueue(4);
		assertEquals(3, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedStack#setCapacity(int)}.
	 */
	@Test
	void testSetCapacity() {
		LinkedQueue<Integer> s = new LinkedQueue<Integer>(10);
		s.enqueue(9);
		s.enqueue(2);
		s.enqueue(4);
		assertThrows(IllegalArgumentException.class, () -> s.setCapacity(1));
	}

}
