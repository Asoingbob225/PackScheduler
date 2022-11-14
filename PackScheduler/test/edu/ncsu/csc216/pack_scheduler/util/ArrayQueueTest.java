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
class ArrayQueueTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#push(java.lang.Object)}.
	 */
	@Test
	void testPush() {
		ArrayQueue<Integer> s = new ArrayQueue<Integer>(10);
		assertEquals(0, s.size());
		s.enqueue(9);
		assertEquals(1, s.size());
		s.enqueue(6);
		assertEquals(2, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#pop()}.
	 */
	@Test
	void testPop() {
		ArrayQueue<Integer> s = new ArrayQueue<Integer>(10);
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
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#isEmpty()}.
	 */
	@Test
	void testIsEmpty() {
		ArrayQueue<Integer> s = new ArrayQueue<Integer>(10);
		s.enqueue(9);
		s.dequeue();
		assertTrue(s.isEmpty());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#size()}.
	 */
	@Test
	void testSize() {
		ArrayQueue<Integer> s = new ArrayQueue<Integer>(10);
		s.enqueue(9);
		s.enqueue(2);
		s.enqueue(4);
		assertEquals(3, s.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayStack#setCapacity(int)}.
	 */
	@Test
	void testSetCapacity() {
		ArrayQueue<Integer> s = new ArrayQueue<Integer>(10);
		s.enqueue(9);
		s.enqueue(2);
		s.enqueue(4);
		assertThrows(IllegalArgumentException.class, () -> s.setCapacity(1));
	}

}
