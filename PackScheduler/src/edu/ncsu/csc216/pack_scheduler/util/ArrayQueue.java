package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A Queue data structure that implements the functionality of an ArrayList.
 * 
 * @author yujim
 *
 * @param <E> the generic object type of the elements in the list
 */
public class ArrayQueue<E> implements Queue<E> {

	/** Generic ArrayList for holding queue elements */
	private ArrayList<E> queue;

	/** The maximum number of elements this queue can hold **/
	private int capacity;
	
//	/** The size of this queue */
//	private int size;

	/**
	 * Constructor for ArrayList-based queue
	 * @param capacity the initial capacity of the queue
	 */
	public ArrayQueue(int capacity) {
		queue = new ArrayList<E>();
		setCapacity(capacity);
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
		if (size() < capacity) {

			if (size() == 0) {
				queue.add(element);
				
			} else {
				queue.add(size() - 1, element);
				
			}
			
			capacity++;

		} else {
			throw new IllegalArgumentException();
		}
		
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
		capacity--; 
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
		this.capacity = capacity;
	}
	
	/**
	 * Checks if the list contains a given element
	 * @param e1 the type of E
	 * @return true or false based on if a given E is contained in the list
	 */
	public boolean contains(E e1)
	{
		return queue.contains(e1);
	}

}
