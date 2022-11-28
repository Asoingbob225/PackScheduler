package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ListIterator;

import org.junit.jupiter.api.Test;

/**
 * This is the test class for LinkedList and LinkedListIterator
 * 
 * @author Davis Bryant
 */
public class LinkedListTest {
	
	/**
	 * Tests the Constructor for LinkedList
	 */
	@Test
	public void linkedListTest() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
	}
	
	/**
	 * Tests add method by adding both valid and invalid inputs
	 */
	@Test
	public void addListTest() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(0, "A");
		assertEquals(1, list.size());
		list.add(1, "B");
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		
		//assertThrows(NullPointerException.class, () -> list.add(2, null));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "1"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(list.size() + 1, "1"));
		assertThrows(IllegalArgumentException.class, () -> list.add(2, "A"));
		
	}
	
	/**
	 * Tests remove method by removing elements by using both valid and invalid
	 * indexes
	 */
	@Test
	public void removeListTest() {
		LinkedList<String> list = new LinkedList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(i, String.valueOf(i));
		}

		assertEquals("9", list.remove(9));
		assertThrows(IndexOutOfBoundsException.class, () ->  list.get(10));

		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size() + 1));
		assertEquals(9, list.size());
	}
	
	/**
	 * Tests set method by setting objects and checking what was overwritten
	 */
	@Test
	public void setListTest() {
		LinkedList<String> list = new LinkedList<String>();
		
		//assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "1"));
		
		for (int i = 0; i < 10; i++) {
			list.add(i, String.valueOf(i));
		}
		

		
		for (int i = 0; i < 9; i++) {
			assertEquals(String.valueOf(i), list.set(i, String.valueOf(i + 100)));
			assertEquals(String.valueOf(i + 100), list.get(i));
		}
		
		//assertThrows(IllegalArgumentException.class, () -> list.set(3,  "5"));
		
		assertThrows(IllegalArgumentException.class, () -> list.set(0, null));

		assertThrows(IllegalArgumentException.class, () -> list.set(2, "9"));

		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "oieo"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(list.size() + 1, "adsjlk"));
		
		
	}
	
	/**
	 * Tests constructor of Iterator, add, hasPrevious, and hasNext()
	 */
	@Test
	public void iteratorTest() {
		LinkedList<Integer> list = new LinkedList<>();
		
		for (int i = 0; i < 10; i++) {
			list.add(i, i);
		}
		
		ListIterator<Integer> it = list.listIterator(5);
		assertTrue(it.hasPrevious());
		it.previous();
		assertTrue(it.hasNext());
		
	}
	
	
}
