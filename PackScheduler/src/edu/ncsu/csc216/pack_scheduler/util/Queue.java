/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * The Queue interface
 * @author Adharsh Rajagopal
 * @author Jimin Yu
 * @author Davis Bryant
 *
 */
public interface Queue<E> {
	
	void enqueue(E element);
	
	E dequeue();
	
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the queue.
	 * @return the number of elements in the queue
	 */
	int size();
	
	/**
	 * Sets the queue's capacity
	 * @param capacity the capacity to set
	 */
	void setCapacity(int capacity);
}
