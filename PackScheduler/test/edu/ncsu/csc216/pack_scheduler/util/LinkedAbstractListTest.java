package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of custom LinkedAbstractList implementation using
 * Strings.
 * 
 * @author cbthomp3, rdbryan2, abcoste2
 */
public class LinkedAbstractListTest {

	/**
	 * Tests constructor
	 */
	@Test
	public void linkedAbstractListTest() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		assertEquals(0, list.size());
	}

	/**
	 * Tests add method by adding both valid and invalid inputs
	 */
	@Test
	public void addListTest() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);

		assertThrows(NullPointerException.class, () -> list.add(null));

		list.add(0, "A");
		assertThrows(IllegalArgumentException.class, () -> list.add(0, "A"));

		for (int i = 0; i < 7; i++) {
			list.add(0, String.valueOf(i));
		}
		
		assertThrows(IllegalArgumentException.class, () -> list.add(0, "0"));
		
		list.add(0, "insert0");
		assertEquals("insert0", list.get(0));
		
		list.add(2, "insert2");
		
		assertEquals("insert2", list.get(2));

		assertThrows(IllegalArgumentException.class, () -> list.add(0, "too many"));

		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "q"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(list.size() + 1, "eoi"));
		
		
		LinkedAbstractList<String> fruitList = new LinkedAbstractList<String>(10);
		
		fruitList.add(0, "orange");
		fruitList.add(1, "banana");
		fruitList.add(2, "apple");
		fruitList.add(3, "kiwi");
		
		assertThrows(IllegalArgumentException.class, () -> fruitList.add(4, "apple"));
		assertThrows(IllegalArgumentException.class, () -> fruitList.add(2, "apple"));
		assertThrows(IllegalArgumentException.class, () -> fruitList.add(0, "apple"));
	}

	/**
	 * Tests remove method by removing elements by using both valid and invalid
	 * indexes
	 */
	@Test
	public void removeListTest() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		for (int i = 0; i < 10; i++) {
			list.add(i, String.valueOf(i));
		}

		assertEquals("9", list.remove(9));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(9));

		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size() + 1));
		assertEquals(9, list.size());
		
		
		LinkedAbstractList<String> fruitList = new LinkedAbstractList<String>(10);
		
		fruitList.add(0, "orange");
		fruitList.add(1, "banana");
		fruitList.add(2, "apple");
		fruitList.add(3, "kiwi");
		
		fruitList.remove(1);
		fruitList.remove(0);
		
		assertEquals("apple", fruitList.get(0));
	}

	/**
	 * Tests set method by setting objects and checking what was overwritten
	 */
	@Test
	public void setListTest() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		for (int i = 0; i < 10; i++) {
			list.add(i, String.valueOf(i));
		}

		for (int i = 0; i < 9; i++) {
			assertEquals(String.valueOf(i), list.set(i, String.valueOf(i + 100)));
			assertEquals(String.valueOf(i + 100), list.get(i));
		}
		assertThrows(NullPointerException.class, () -> list.set(1, null));

		assertThrows(IllegalArgumentException.class, () -> list.set(2, "102"));

		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "oieo"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(list.size() + 1, "adsjlk"));
	}

	/**
	 * Check that changing the capacity of the list allows extra elements. Check
	 * that a value that is less than the size of the list causes an IAE.
	 */
	@Test
	public void setCapacityTest() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		for (int i = 0; i < 10; i++) {
			list.add(i, String.valueOf(i));
		}
		assertThrows(IllegalArgumentException.class, () -> list.add(10, "10"));

		assertThrows(IllegalArgumentException.class, () -> list.setCapacity(5));

		assertDoesNotThrow(() -> list.setCapacity(11));
		assertDoesNotThrow(() -> list.add(10, "10"));

		assertThrows(IllegalArgumentException.class, () -> list.add(11, "11"));
	}
}
