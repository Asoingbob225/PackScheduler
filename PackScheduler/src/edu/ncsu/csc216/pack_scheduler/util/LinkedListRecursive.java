/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * @author Jimin Yu
 *
 */
public class LinkedListRecursive<E> {
	/**
	 * The ListNode is a member of this LinkedList. It maintains a reference to a
	 * generic-typed object and a reference to the next node in the list.
	 * 
	 * @author Jimin Yu
	 */
	private class ListNode {

		/** The object reference for this node */
		private E data;

		/** Reference to next node in the list */
		private ListNode next;

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
		
	}
	
	
	private ListNode front;
	
	private int size;
	
	
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean contains(E element) {
		if (isEmpty()) {
			return false;
		}

		//recursion
		return false;
	}
	
	public boolean add(E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		}
		//recursion
		return false;
	}
	
	public boolean add(int idx, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (idx == 0) {
			front = new ListNode(element, front);
			size++;
			return true;
		}
		//recursion
		return false;
	}
	
	public E get(int idx) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		//recursion
		return null;
	}
	
	public E remove(int idx) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E removedData;
		
		if (idx == 0) {
			removedData = front.data;
			front = front.next;
			size--;
			return removedData;
		}
		//recursion
		return null;
	}
	
	public boolean remove(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		
		if(!contains(element) || isEmpty()) {
			return false;
		}
		
		if (front.data == element) {
			front = front.next;
			size--;
			return true;
		}
		
		//recursion
		return false;
	}
	
	public E set(int idx, E element) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E previousData;
		
		if (idx == 0) {
			previousData = front.data;
			front.data = element;
			return previousData;
		}
		//recursion
		return null;
	}
	
}
