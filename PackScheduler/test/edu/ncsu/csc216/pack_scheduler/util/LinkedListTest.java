package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * 
 * 
 * @author Davis Bryant
 */
public class LinkedListTest {
	
	/**
	 * 
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
		assertEquals("A", list.get(0));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "1"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(list.size() + 1, "1"));
		
		
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
		assertThrows(IndexOutOfBoundsException.class, () ->  list.get(9));

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
		for (int i = 0; i < 10; i++) {
			list.add(i, String.valueOf(i));
		}

		for (int i = 0; i < 9; i++) {
			assertEquals(String.valueOf(i), list.set(i, String.valueOf(i + 100)));
			assertEquals(String.valueOf(i + 100), list.get(i));
		}
		assertThrows(NullPointerException.class, () -> list.set(1, null));

		assertThrows(IllegalArgumentException.class, () -> list.set(2, "9"));

		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "oieo"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(list.size() + 1, "adsjlk"));
	}
	
	
}
