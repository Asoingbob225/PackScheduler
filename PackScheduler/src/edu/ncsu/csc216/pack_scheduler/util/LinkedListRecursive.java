/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import javax.crypto.spec.DHPrivateKeySpec;
import javax.management.loading.PrivateMLet;

import org.junit.validator.PublicClassValidator;

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
		
		private boolean contains(E element) {
			if(data.equals(element)) {
				return true;
			} else if (next != null){
				return next.contains(element);
			}
			return false;
			
		}
		
		private boolean add(E element) {
			if(next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			}
			return next.add(element);
			
		}
		
		private boolean add(int idx, E element) {
			if(idx < 0) {
				return false;
			} else if(idx == 0) {
				next = new ListNode(element, next);
				size++;
				return true;
			} else if(next == null) {
				return false;
			} else {
				 return next.add(idx - 1, element);
			}
		}
		
		private E get(int idx) {
			if(idx < 0) {
				return null;
			} else if(idx == 0) {
				return data;
			} else if(next == null) {
				return null;
			} else {
				 return next.get(idx - 1);
			}
			
		}
		
		private E remove(int idx) {
			if(idx <= 0) {
				return null;
			} else if(idx == 1) {
				 E temp = next.data;
				 next = next.next;
				 return temp;
			} else if(next == null) {
				return null;
			} else {
				 return next.remove(idx - 1);
			}
		}
		
		private boolean remove(E element) {
			if(next != null) {
				if(next.data.equals(element)) {
					 next = next.next;
					 return true;
				} else {
					return next.remove(element);
				}
			}
			return false;
			
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

		return front.contains(element);
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
		return front.add(element);
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
		
		return front.add(idx, element);
		
	}
	
	public E get(int idx) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		return front.get(idx);
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
		return front.remove(idx);
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
		
		return front.remove(element);
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
