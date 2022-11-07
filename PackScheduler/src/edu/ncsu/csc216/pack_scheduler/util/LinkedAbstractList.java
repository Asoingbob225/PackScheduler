/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Implements the functionality of a generic AbstractList with a linked list.
 * This linked list has limited capacity which can be modified and does not
 * allow duplicates. The LinkedAbstractList maintains its current size and max
 * capacity along with a reference to the first {@link ListNode} of the list.
 * Allows clients to add, remove, and modify elements specified by index.
 * 
 * @author Aidan
 * @param <E> the type contained by nodes in the list
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** Amount of nodes in the linked list */
	private int size;

	/** The first node in the linked list */
	private ListNode front;

	/** The maximum amount of nodes that this list can hold */
	private int capacity;

	/**
	 * The ListNode is a member of this LinkedList. It maintains a reference to a
	 * generic-typed object and a reference to the next node in the list.
	 * 
	 * @author Aidan
	 */
	private class ListNode {

		/** The object reference for this node */
		private E data;

		/** Reference to next node in the list */
		private LinkedAbstractList<E>.ListNode next;

		/**
		 * Creates a node that is linked to another node.
		 * 
		 * @param data the object the node will reference
		 * @param next the node that comes after the new node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Creates a node that is not linked to another node.
		 * 
		 * @param data the object the node will reference
		 */
		public ListNode(E data) {
			this.data = data;
			next = null;
		}
	}

	/**
	 * Creates a new instance of LinkedAbstractList with specified capacity. The new
	 * list has a size of 0 and no initial node.
	 * 
	 * @param capacity the maximum nodes the new list can hold
	 */
	public LinkedAbstractList(int capacity) {
		size = 0;
		front = null;
		this.capacity = capacity;
	}

	/**
	 * Sets data of ListNode at specified index to a new value and return the old
	 * value.
	 * 
	 * @param index   the index of the node to change
	 * @param newData the new data to set
	 * @return the data previously in the node
	 */
	@Override
	public E set(int index, E newData) {
		if (newData == null) {
			throw new NullPointerException();
		}

		if (index < 0 || index >= size() - 1) {
			throw new IndexOutOfBoundsException();
		}

		ListNode temp = front;
		E oldData = null;

		for (int i = 0; i < size() - 1; i++) {
			if (i == index) {
				oldData = temp.data;
				temp.data = newData;
			}

			temp = temp.next;

			if (newData.equals(temp.data)) {
				throw new IllegalArgumentException();
			}
		}

		return oldData;
	}

	/**
	 * Adds a new node to the specified index. Checks that the index is not beyond
	 * the current size, the list is not at max capacity, and that the new node
	 * would not be a duplicate.
	 * 
	 * @param index   the index of the new node
	 * @param newData the data for the new node
	 * @throws NullPointerException      if the newData parameter is {@code null}.
	 * @throws IndexOutOfBoundsException if the index parameter is negative or
	 *                                   greater than the size of the list.
	 * @throws IllegalArgumentException  if the list is already at capacity, or the
	 *                                   new data is a duplicate of an existing
	 *                                   node's data.
	 */
	@Override
	public void add(int index, E newData) {
		if (newData == null) {
			throw new NullPointerException();
		}

		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		if (size() == capacity) {
			throw new IllegalArgumentException();
		}

		if (front == null) {
			front = new ListNode(newData);
		}

		ListNode temp = front;
		for (int i = 0; i < size(); i++) {
			if (newData.equals(temp.data)) {
				throw new IllegalArgumentException();
			}

			if (temp.next == null) {
				temp.next = new ListNode(newData);
				break;
			}

			if (i == index - 1) {
				ListNode newNode = new ListNode(newData, temp.next);
				temp.next = newNode;
			}

			temp = temp.next;
		}

		size++;
	}

	/**
	 * Remove the node at the specified index and return its value.
	 * 
	 * @param index the index of the node to be removed
	 * @return the data of the removed node
	 * @throws IndexOutOfBoundsException if the index parameter is negative or
	 *                                   greater than the last index of the list.
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index > size() - 1) {
			throw new IndexOutOfBoundsException();
		}

		ListNode temp = front;

		for (int i = 0; i < index - 1; i++) {
			temp = temp.next;
		}

		ListNode removedNode = temp.next;

		temp.next = temp.next.next;

		this.size--;
		return removedNode.data;
	}

	/**
	 * Return the amount of nodes in the list.
	 * 
	 * @return current size of list
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Retrieves the data of the node at the specified index.
	 * 
	 * @param index the index of the node to access
	 * @return the data in the specified node.
	 * @throws IndexOutOfBoundsException if the index parameter is negative or
	 *                                   greater than the last index of the list.
	 */
	@Override
	public E get(int index) {
		ListNode temp = front;

		if (index < 0 || index > size() - 1) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}

		return temp.data;
	}

	/**
	 * Set the maximum amount of nodes this list can hold, checking that the list is
	 * not in excess of the new capacity.
	 * 
	 * @param newCapacity the capacity to set
	 * @throws IllegalArgumentException if the capacity is less than the size of the
	 *                                  list.
	 */
	public void setCapacity(int newCapacity) {
		if (newCapacity < size()) {
			throw new IllegalArgumentException();
		}

		capacity = newCapacity;
	}
}
