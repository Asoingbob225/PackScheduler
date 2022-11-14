/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for Stack list concept
 * 
 * @author stbeuav
 * @param <E> generic element
 */
public interface Stack<E> {
	
	/** 
	 * Adds element to top of stack
	 * 
	 *@param element element to be added
	 */
	void push(E element);
	
	/**
	 * Removes element from top of list
	 * 
	 * @return element from top of list
	 */
	E pop();
	
	/**
	 * Checks if stack is empty
	 * 
	 * @return true if empty, false otherwise
	 */
	boolean isEmpty();
	
	/**
	 * Returns size of stack
	 * 
	 * @return size
	 */
	int size();
	
	/**
	 * Sets capacity of stack
	 * 
	 * @param capacity capacity of stack
	 */
	void setCapacity(int capacity);
	

}
