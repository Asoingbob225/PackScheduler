package edu.ncsu.csc217.collections.list;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

/**
 * Tests SortedList library
 * 
 * @author ctmatias
 */
public class SortedListTest {

	/**
	 * Tests an empty sorted list and ensures the list correctly grows to at least
	 * 11 elements
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());

		for (int i = 0; i < 11; i++) {
			list.add(String.valueOf(i));
		}

		assertEquals(11, list.size());
	}

	/**
	 * Tests that elements were added to the list in the right place
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Test adding to front
		list.add("apple");
		assertEquals("apple", list.get(0));

		// Test adding to middle
		list.add("arizona");
		assertEquals("arizona", list.get(1));

		// Test adding to the end
		list.add("zebra");
		assertEquals("zebra", list.get(3));
		assertEquals(4, list.size());

		// Test adding a null element
		assertThrows(NullPointerException.class, () -> list.add(null));
		assertEquals(4, list.size());

		// Test adding a duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.add("zebra"));
		assertEquals(4, list.size());
	}

	/**
	 * Tests edge/error cases for getting an element from the list
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

		// Add some elements to the list
		list.add("corn");
		list.add("pencil");
		list.add("book");

		// Test getting an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));

		// Test getting an element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
	}

	/**
	 * Tests edge/error cases for removing an element from the list:
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

		// Add some elements to the list - at least 4
		for (int i = 0; i < 5; i++) {
			list.add(String.valueOf(i));
		}

		// Test removing an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

		// Test removing an element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));

		// Test removing a middle element
		assertEquals("1", list.remove(1));

		// Test removing the last element
		assertEquals("4", list.remove(list.size() - 1));

		// Test removing the first element
		assertEquals("0", list.remove(0));

		// Test removing the last element
		assertEquals("3", list.remove(list.size() - 1));
	}

	/**
	 * Tests that the correct index position is returned for the following cases: an
	 * empty list, a null element, valid elements within the list, and invalid
	 * elements not in the list
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// Test indexOf on an empty list
		assertEquals(-1, list.indexOf("foo"));

		// Add some elements
		for (int i = 0; i < 5; i++) {
			list.add(String.valueOf(i));
		}
		

		// Test various calls to indexOf for elements in the list
		assertEquals(0, list.indexOf("0"));
		assertEquals(2, list.indexOf("2"));
		assertEquals(4, list.indexOf("4"));
		
		// and not in the list
		assertEquals(-1, list.indexOf("hello"));
		assertEquals(-1, list.indexOf("there"));
		assertEquals(-1, list.indexOf("0101010101"));
		
		// Test checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
	}

	/**
	 * Tests that the list has been successfully cleared (start with a populated
	 * list)
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		for (int i = 0; i < 5; i++) {
			list.add(String.valueOf(i));
		}

		// Clear the list
		list.clear();

		// Test that the list is empty
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}

	/**
	 * Tests whether the list is empty (list starts out empty then becomes
	 * populated)
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());
		// Add at least one element
		list.add("Element");
		// Check that the list is no longer empty
		assertTrue(!list.isEmpty());
	}

	/**
	 * Tests for contained elements in the following cases: empty list case, true
	 * and false cases
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertFalse(list.contains("Element"));
		// Add some elements
		for (int i = 0; i < 5; i++) {
			list.add(String.valueOf(i));
		}
		// TODO Test some true and false cases
		assertFalse(list.contains("Element"));
	}

	/**
	 * Tests for equality and non equality (two lists are the same and one is
	 * different)
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		SortedList<String> list4 = new SortedList<String>();

		// Make two lists the same and one list different
		for (int i = 0; i < 5; i++) {
			list1.add(String.valueOf(i));
		}
		for (int i = 0; i < 5; i++) {
			list2.add(String.valueOf(i));
		}
		for (int i = 0; i < 6; i++) {
			list3.add(String.valueOf(i));
		}
		for (int i = 10; i < 15; i++) {
			list4.add(String.valueOf(i));
		}
		// Tests if identical lists are identical
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		// Tests that non identical tests are not identical
		assertFalse(list1.equals(list3));
		assertFalse(list1.equals(list4));

	}

	/**
	 * Tests for same and different hasCodes wherein two lists are the same and one
	 * is different
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		SortedList<String> list4 = new SortedList<String>();

		// Make two lists the same and one list different
		for (int i = 0; i < 5; i++) {
			list1.add(String.valueOf(i));
		}
		for (int i = 0; i < 5; i++) {
			list2.add(String.valueOf(i));
		}
		for (int i = 0; i < 6; i++) {
			list3.add(String.valueOf(i));
		}
		for (int i = 10; i < 15; i++) {
			list4.add(String.valueOf(i));
		}
		
		// Tests that identical lists have the same hash code
		assertEquals(list1.hashCode(), list2.hashCode());
		assertEquals(list2.hashCode(), list1.hashCode());
		// Tests that non identical lists have different hash code
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list1.hashCode(), list4.hashCode());
	}

}
