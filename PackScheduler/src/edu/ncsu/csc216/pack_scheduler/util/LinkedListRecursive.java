package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Linked List implementation using recursion
 * 
 * @author Jimin Yu, Davis Bryant
 * @param <E> generic type
 */
public class LinkedListRecursive<E> {
	
	/**
	 * The ListNode is a member of this LinkedList. It maintains a reference to a
	 * generic-typed object and a reference to the next node in the list.
	 * 
	 * @author Jimin Yu, Davis Bryant
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
		
		/**
		 * Checks to see if element is in list
		 * 
		 * @param element element to be searched for in list
		 * @return true if element is in list, false otherwise
		 */
		public boolean contains(E element) {
			if(data.equals(element)) {
				return true;
			} else if (next != null){
				return next.contains(element);
			}
			return false;
			
		}
		
		/**
		 * Adds element to end of index
		 * 
		 * @param element element to be added
		 * @return true if element could be added, false if not
		 */
		public boolean add(E element) {
			if(next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			}
			return next.add(element);
			
		}
		
		/**
		 * Adds element at specified index
		 * 
		 * @param idx desired index 
		 * @param element element to be added
		 * @return true if element could be added, false if not
		 */
		public boolean add(int idx, E element) {
			if(idx <= 0) {
				return false;
			} else if(idx == 1) {
				next = new ListNode(element, next);
				size++;
				return true;
			} else {
				 return next.add(idx - 1, element);
			}
		}
		
		/**
		 * Gets element at desired index
		 * 
		 * @param idx desired index 
		 * @return element at index
		 */
		public E get(int idx) {
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
		
		/**
		 * Removes element at desired index
		 * 
		 * @param idx desired index
		 * @return element at index
		 */
		public E remove(int idx) {
			if(idx <= 0) {
				return null;
			} else if(idx == 1) {
				 E temp = next.data;
				 next = next.next;
				 size--;
				 return temp;
			} else {
				 return next.remove(idx - 1);
			}
			 
		}
		
		/**
		 * Removes specified element
		 * 
		 * @param element element to be removed
		 * @return true if element could be removed, false if not
		 */
		public boolean remove(E element) {
			if(next != null) {
				if(next.data.equals(element)) {
					 next = next.next;
					 size--;
					 return true;
				} else {
					return next.remove(element);
				}
			}
			return false;
			
		}
		
		/**
		 * Replaces element at specified index
		 * 
		 * @param idx desired index
		 * @param element element to be set at index
		 * @return element that is replaced
		 */
		public E set(int idx, E element) {
			if(idx <= 0) {
				return null;
			} else if(idx == 1) {
				 E temp = next.data;
				 next.data = element;
				 return temp;
			} else {
				 return next.set(idx - 1, element);
			}
			
		}
		
	}
	
	/** Represents ListNode at front of list */
	private ListNode front;
	
	/** Size of list */
	private int size; 
	
	
	/**
	 * Constructor for LinkedListRecursive, initializes fields
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}
	
	
	/**
	 * Getter for size
	 * 
	 * @return size of list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks to see if list is empty
	 * 
	 * @return true if list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Checks to see if element is in list
	 * 
	 * @param element element to be searched for in list
	 * @return true if element is in list, false otherwise
	 */
	public boolean contains(E element) {
		if (isEmpty()) {
			return false;
		}
		return front.contains(element);
	}
	
	/**
	 * Adds element to end of index
	 * 
	 * @param element element to be added
	 * @return true if element could be added, false if not
	 */
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
	
	/**
	 * Adds element at specified index
	 * 
	 * @param idx desired index 
	 * @param element element to be added
	 * @return true if element could be added, false if not
	 */
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
	
	/**
	 * Gets element at desired index
	 * 
	 * @param idx desired index 
	 * @return element at index
	 */
	public E get(int idx) {
		if (idx < 0 || idx > size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		return front.get(idx);
	}
	
	/**
	 * Removes element at desired index
	 * 
	 * @param idx desired index
	 * @return element at index
	 */
	public E remove(int idx) {
		if (idx < 0 || idx > size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		if(isEmpty()) {
			return null;
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
	
	/**
	 * Removes specified element
	 * 
	 * @param element element to be removed
	 * @return true if element could be removed, false if not
	 */
	public boolean remove(E element) {
//		if (element == null) {
//			throw new NullPointerException();
//		}
		
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
	
	/**
	 * Replaces element at specified index
	 * 
	 * @param idx desired index
	 * @param element element to be set at index
	 * @return element that is replaced
	 */
	public E set(int idx, E element) {
		if (idx < 0 || idx > size() - 1 || isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		E previousData;
		if (idx == 0) {
			previousData = front.data;
			front.data = element;
			return previousData;
		}
		return front.set(idx, element);
	}
	
}
