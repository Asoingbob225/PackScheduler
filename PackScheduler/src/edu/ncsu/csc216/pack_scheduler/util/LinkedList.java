package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This is a LinkedList class which inherits AbstractSequentialList to provide
 * functionality for all standard list methods by implementing them in terms of
 * an Iterator
 * 
 * @author Jimin Yu, Davis Bryant, Adharsh Rajagopal
 *
 * @param <E> the parameter object's generic type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** The object reference for this node */
	private E data;

	/** Reference to next node in the list */
	private ListNode front;

	/** Reference to next node in the list */
	private ListNode back;

	/** Reference to the size of the list */
	private int size;

	/**
	 * Constructor for a LinkedList object. The LinkedList now has a back field,
	 * which points to the last element in the list
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * This method constructs a LinkedListIterator object
	 * 
	 * @return a ListIteratorthat is positioned such that a call to
	 *         ListIterator.next() will return the element at given index
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		LinkedListIterator s1 = new LinkedListIterator(index);
		return s1;
	}

	/**
	 * This method adds an element to the list at the given index utilized a
	 * ListIterator while overriding LinkedList.add(int, E) to check for duplicates.
	 * 
	 * @throws IllegalArgumentException if the element to add is a duplicate of an
	 *                                  element already in the list
	 */
	@Override
	public void add(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}

	/**
	 * This method sets an element to the list at the given index utilized a
	 * ListIterator while overriding LinkedList.set(int, E) to check for duplicates.
	 * 
	 * @throws IllegalArgumentException if the element to set is a duplicate of an
	 *                                  element already in the list
	 */
	@Override
	public E set(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	/**
	 * The ListNode is a member of this LinkedList. It maintains a reference to a
	 * generic-typed object and a reference to the next node in the list.
	 * 
	 * @author Jimin
	 */
	private class ListNode {

		/** The object reference for this node */
		private E data;

		/** Reference to next node in the list */
		private ListNode next;

		/** Reference to next node in the list */
		private ListNode prev;

		/**
		 * Creates a node that is not linked to another node.
		 * 
		 * @param data the object the node will reference
		 */
		public ListNode(E data) {
			this.data = data;
			next = null;
			prev = null;
		}

		/**
		 * Creates a node that is linked to another node.
		 * 
		 * @param data the object the node will reference
		 * @param next the node that comes after the new node
		 * @param prev the node that comes before the new node
		 */
		public ListNode(E data, ListNode next, ListNode prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

	}

	/**
	 * This class creates a LinkedListIterator object which represents current
	 * location of the iterator in the LinkedList. This class implements the
	 * ListIterator interface. The LinkedListIterator can move forward or backward
	 * in the list during traversal.
	 * 
	 * @author Jimin Yu, Davis Bryant, Adharsh Rajagopal
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** Reference to next node in the list */
		private ListNode previousListNode;

		/** Reference to next node in the list */
		private ListNode nextListNode;

		/** Reference to next node in the list */
		private ListNode lastRetrieved;

		/** Reference to next node in the list */
		private int previousIndex;

		/** Reference to next node in the list */
		private int nextIndex;

		/**
		 * This is a constructor for the LinkedListIterator. It receives an index for
		 * positioning the iterator in the list.
		 * 
		 * @param index the index to traverse the iterator to
		 * @throws IndexOutOfBounds if the target index is out of bounds for the list
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size() - 1) {
				throw new IndexOutOfBoundsException();
			}

			previousIndex = -1;
			nextIndex = 0;

			previousListNode = front;
			nextListNode = previousListNode.next;

			while (nextIndex < index) {
				next();
			}

			lastRetrieved = null;

		}

		/**
		 * Returns true if this list iterator has more elements when traversing the list
		 * in the forward direction.
		 * 
		 * @return true if next() returns an element rather than throwing an exception,
		 *         otherwise return false
		 */
		@Override
		public boolean hasNext() {
			if (next() != null) {
				return true;
			}

			return false;
		}

		/**
		 * Returns the next element in the list and advances the cursor position.
		 * 
		 * @return the next element in the list before advancing the cursor position
		 * @throws NoSuchElementException if there are no more elements in the list
		 *                                while traversing in the forward direction
		 */
		@Override
		public E next() {
			if (hasNext()) {
				E temp = nextListNode.data;
				nextListNode = nextListNode.next;
				previousListNode = previousListNode.next;
				nextIndex++;
				previousIndex++;

				lastRetrieved = previousListNode;

				return temp;
			} else {
				throw new NoSuchElementException();
			}

		}

		/**
		 * Returns true if this list iterator has more elements when traversing the list
		 * in the backwards direction.
		 * 
		 * @return true if previous() returns an element rather than throwing an
		 *         exception, otherwise return false
		 */
		@Override
		public boolean hasPrevious() {
			if (previous() != null) {
				return true;
			}

			return false;
		}

		/**
		 * Returns the previous element in the list and moves the cursor position
		 * backwards.
		 * 
		 * @return the previous element in the list before moving the cursor position
		 * @throws NoSuchElementException if there are no more elements in the list
		 *                                while traversing in the backwards direction
		 */
		@Override
		public E previous() {
			if (hasPrevious()) {
				E temp = previousListNode.data;
				previousListNode = previousListNode.prev;
				nextListNode = nextListNode.prev;
				nextIndex--;
				previousIndex--;

				lastRetrieved = nextListNode;

				return temp;
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 * Return the index of the next node in the list. Return the size of the list if
		 * the iterator is at the end of the list.
		 * 
		 * @return the index of the next node in the list, or the size of the list if
		 *         the iterator is at the end of the list.
		 */
		@Override
		public int nextIndex() {
			if (!hasNext()) {
				return size();
			}
			return nextIndex;
		}

		/**
		 * Return the index of the previous node in the list. Return -1 if the iterator
		 * is at the beginning of the list.
		 * 
		 * @return the index of the previous node in the list, or -1 if the iterator is
		 *         at the end of the list.
		 */
		@Override
		public int previousIndex() {
			if (!hasPrevious()) {
				return -1;
			}
			return previousIndex;
		}

		/**
		 * Inserts the specified element into the list (optional operation). The element
		 * is inserted immediately before the element that would be returned by next(),
		 * if any, and after the element that would be returned by previous(), if any.
		 * (If the list contains no elements, the new element becomes the sole element
		 * on the list.) The new element is inserted before the implicit cursor: a
		 * subsequent call to next would be unaffected, and a subsequent call to
		 * previous would return the new element. (This call increases by one the value
		 * that would be returned by a call to nextIndex or previousIndex.)
		 * 
		 * @throws NullPointerException if the element to add is null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}

			ListNode temp = new ListNode(e);

			if (size == 0) {
				temp.prev = front;
				previousListNode = temp;
				back = nextListNode;
				back.prev = previousListNode;

			} else {
				temp.prev = nextListNode.prev;
				previousListNode = temp;
				nextListNode.prev = previousListNode;
			}

			nextIndex++;
			previousIndex++;
			size++;

			lastRetrieved = null;

		}

		/**
		 * Replaces the last element returned by next() or previous() with the specified
		 * element (optional operation). This call can be made only if neither remove()
		 * nor add(E) have been called after the last call to next or previous.
		 * 
		 * @throws IllegalArgumentException if remove() or add(E) was the last call on
		 *                                  the list or there has not been a call to
		 *                                  next() or previous()
		 * @throws NullPointerException     if the element to set is null
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalArgumentException();
			}

			if (e == null) {
				throw new NullPointerException();
			}

			if (hasNext()) {
				previousListNode.data = e;
			}

			else if (hasPrevious()) {
				nextListNode.data = e;
			}
		}

		/**
		 * Removes from the list the last element that was returned by next() or
		 * previous() (optional operation). This call can only be made once per call to
		 * next or previous. It can be made only if add(E) has not been called after the
		 * last call to next or previous.
		 * 
		 * @throws IllegalArgumentException if meaning that remove() or add(E) was the
		 *                                  last call on the list or there has not been
		 *                                  a call to next() or previous()
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalArgumentException();
			}

			if (hasNext()) {
				previousListNode = nextListNode.prev.prev;
				previousListNode.next = nextListNode;
			}

			else if (hasPrevious()) {
				nextListNode = previousListNode.next.next;
				nextListNode.prev = previousListNode;
			}

			size--;

		}

	}

}
