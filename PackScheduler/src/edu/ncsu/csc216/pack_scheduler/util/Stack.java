/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * @author stbeuav
 *
 */
public interface Stack<E> {
	
	void push(E element);
	
	E pop();
	
	boolean isEmpty();
	
	int size();
	
	void setCapacity(int capacity);
	

}
