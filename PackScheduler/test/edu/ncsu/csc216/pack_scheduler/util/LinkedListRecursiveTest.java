/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Jimin Yu
 *
 */
class LinkedListRecursiveTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#LinkedListRecursive()}.
	 */
	@Test
	void testLinkedListRecursive() {
		LinkedListRecursive<Integer> l = new LinkedListRecursive<Integer>();
		
		assertEquals(0, l.size());
		assertTrue(l.isEmpty());
		
	}


	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#contains(java.lang.Object)}.
	 */
	@Test
	void testContains() {
		LinkedListRecursive<Integer> l = new LinkedListRecursive<Integer>();
		
		assertFalse(l.contains(1));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#add(java.lang.Object)}.
	 */
	@Test
	void testAddE() {
		LinkedListRecursive<Integer> l = new LinkedListRecursive<Integer>();
		
		assertTrue(l.add(1));
		
		//assertThrows(IllegalArgumentException.class, () -> l.add(1));
		
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#add(int, java.lang.Object)}.
	 */
	@Test
	void testAddIntE() {
		LinkedListRecursive<Integer> l = new LinkedListRecursive<Integer>();
		
		assertThrows(NullPointerException.class, () -> l.add(0, null));
	    assertThrows(IndexOutOfBoundsException.class, () -> l.add(5, 1));
	    
	    assertTrue(l.add(0, 1));

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#remove(int)}.
	 */
	@Test
	void testRemoveInt() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();

		assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
		
		l.add("1");
		
		assertEquals("1", l.remove(0));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#remove(java.lang.Object)}.
	 */
	@Test
	void testRemoveE() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();

		assertThrows(NullPointerException.class, () -> l.remove(null));
		
		l.add("1");
		
		//assertTrue(l.remove("1"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive#set(int, java.lang.Object)}.
	 */
	@Test
	void testSet() {
		LinkedListRecursive<Integer> l = new LinkedListRecursive<Integer>();

		assertThrows(IndexOutOfBoundsException.class, () -> l.set(-1, 1));
		
		l.add(5);
		
		assertEquals(5, l.set(0,  1));
	}

}
