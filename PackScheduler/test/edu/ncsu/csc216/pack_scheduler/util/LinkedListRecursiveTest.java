/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for LinkedListRecursive
 * 
 * @author Jimin Yu, Davis Bryant
 */
class LinkedListRecursiveTest {

	/**
	 * Test method for constructor
	 */
	@Test
	void testLinkedListRecursive() {
		LinkedListRecursive<Integer> l = new LinkedListRecursive<Integer>();
		
		assertEquals(0, l.size());
		assertTrue(l.isEmpty());
		
	}


	/**
	 * Test method for contains() 
	 */
	@Test
	void testContains() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();
		
		assertFalse(l.contains("1"));
		l.add("1");
		assertTrue(l.contains("1"));
	}

	/**
	 * Test method for add() method that uses element parameter
	 */
	@Test
	void testAddE() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();
		assertTrue(l.add("1"));
		assertTrue(l.add("2"));
		assertTrue(l.contains("1"));
		assertTrue(l.contains("2"));
		assertEquals("1", l.get(0));
		assertEquals("2", l.get(1));
		
		
	}

	/**
	 * Test method for add() method that uses element and index parameters
	 */
	@Test
	void testAddIntE() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();
		
		assertThrows(NullPointerException.class, () -> l.add(0, null));
	    assertThrows(IndexOutOfBoundsException.class, () -> l.add(5, "1"));
	    
	    assertTrue(l.add(0, "1"));
	    assertEquals("1", l.get(0));
	    assertTrue(l.add(0, "2"));
	    assertEquals("2", l.get(0));
	    assertTrue(l.add(1, "3"));
	    assertEquals("3", l.get(1));

	}

	/**
	 * Test method for remove() method that uses index parameter
	 */
	@Test
	void testRemoveInt() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();

		assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
		
		l.add("1");
		l.add("2");
		l.add("3");
		assertEquals("2", l.remove(1));
		assertFalse(l.contains("2"));
		
		assertEquals("1", l.remove(0));
	}

	/**
	 * Test method for remove() method that uses element parameter
	 */
	@Test
	void testRemoveE() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();

		//assertThrows(NullPointerException.class, () -> l.remove(null));
		
		l.add("1");
		l.add("2");
		l.add("3");
		assertTrue(l.remove("1"));
		assertFalse(l.contains("1"));
		assertTrue(l.remove("3"));
		assertFalse(l.contains("3"));
	}

	/**
	 * Test method for set()
	 */
	@Test
	void testSet() {
		LinkedListRecursive<String> l = new LinkedListRecursive<String>();
		assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
		l.add("1");
		l.add("2");
		l.add("3");
		assertEquals("2", l.set(1, "A"));
		assertEquals("A", l.get(1));
		assertEquals("1", l.set(0, "B"));
		assertEquals("B", l.get(0));
		
	}

}
