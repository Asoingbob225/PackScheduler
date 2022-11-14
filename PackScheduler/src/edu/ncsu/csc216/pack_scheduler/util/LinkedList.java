package edu.ncsu.csc216.pack_scheduler.util;

import java.awt.Font;
import java.util.AbstractSequentialList;
import java.util.ListIterator;


public class LinkedList<E> extends AbstractSequentialList<E> {
	
	/** The object reference for this node */
	private E data;

	/** Reference to next node in the list */
	private ListNode front;
	
	/** Reference to next node in the list */
	private ListNode back;
	
	private int size;
	
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	
	

	@Override
	public int size() {
		return size;
	}

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
		private ListNode next;
		
		/** Reference to next node in the list */
		private ListNode prev;
		
		public ListNode(E data) {
			this.data = data;
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
	
	private class LinkedListIterator implements ListIterator<E> {
		
		/** Reference to next node in the list */
		private ListNode previous;
		
		/** Reference to next node in the list */
		private ListNode nextListNode;
		
		/** Reference to next node in the list */
		private ListNode lastRetrieved;
		
		/** Reference to next node in the list */
		private int previousIndex;
		
		/** Reference to next node in the list */
		private int nextIndex;
		
		public LinkedListIterator(int index) {
			//TODO implement later
		}
		
		
		
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(E e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void add(E e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
