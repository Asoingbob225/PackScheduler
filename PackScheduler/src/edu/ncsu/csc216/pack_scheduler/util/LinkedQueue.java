/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 * A Queue data structure that implements the functionality of a LinkedList.
 * @author adharsh
 * @param <E> the generic object contained by the nodes in the list.
 */
public class LinkedQueue<E> implements Queue<E> {

	/** Generic LinkedList for holding queue elements */
	private LinkedAbstractList<E> queue;
	
	/**
	 * Constructor for LinkedList-based queue
	 * @param capacity the initial capacity of the queue
	 */
	public LinkedQueue(int capacity) {
		queue = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * Adds the given element to the back of the queue
	 * 
	 * @param element the element to add to the queue
	 * @throws IllegalArgumentException if the capacity for the queue has been
	 *                                  reached
	 */
	@Override
	public void enqueue(E element) {
		queue.add(size(), element);
		
	}

	/**
	 * Removes and returns the element at the front of the Queue
	 * 
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return queue.remove(0);
	}

	/**
	 * Returns true if the Queue is empty
	 * 
	 * @return true if the queue is empty
	 */
	@Override
	public boolean isEmpty() {	
		return queue.isEmpty();
	}
	
	/**
	 * Returns the number of elements in the queue.
	 * 
	 * @return the number of elements in the queue
	 */
	@Override
	public int size() {
		return queue.size();
	}

	/**
	 * Sets the Queue’s capacity
	 * 
	 * @param capacity the capacity to set to
	 * @throws IllegalArgumentException if the actual parameter is negative or if it
	 *                                  is less than the number of elements in the
	 *                                  Queue
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < size() || capacity < 0) {
			throw new IllegalArgumentException();
		}
		queue.setCapacity(capacity);	
	}

}
