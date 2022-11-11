/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * The Queue interface
 * 
 * @param <E> the generic object type of the elements in the queue
 * 
 * @author Adharsh Rajagopal
 * @author Jimin Yu
 * @author Davis Bryant
 *
 */
public interface Queue<E> {

	/**
	 * Adds the given element to the back of the queue
	 * 
	 * @param element the element to add to the queue
	 * @throws IllegalArgumentException if the capacity for the queue has been
	 *                                  reached
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the Queue
	 * 
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	E dequeue();

	/**
	 * Returns true if the Queue is empty
	 * 
	 * @return true if the queue is empty
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the queue.
	 * 
	 * @return the number of elements in the queue
	 */
	int size();

	/**
	 * Sets the Queue’s capacity
	 * 
	 * @param capacity the capacity to set to
	 * @throws IllegalArgumentException if the actual parameter is negative or if it
	 *                                  is less than the number of elements in the
	 *                                  Queue
	 */
	void setCapacity(int capacity);
}
