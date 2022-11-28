/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Implements stack interface list using ArrayList
 * 
 * @author stbeuav
 * @param <E> generic element
 */
public class ArrayStack<E> implements Stack<E> {  
	
	/**
	 * ArrayList to hold stack data
	 */
	private ArrayList<E> stack;
	
	/**
	 * Capacity of ArrayList
	 */
	private int capacity;

	/**
	 * Constructor for ArrayStack
	 * 
	 * @param capacity capacity of list
	 */
	public ArrayStack(int capacity) {
		stack = new ArrayList<E>();
		setCapacity(capacity);
	}
	
	/** 
	 * Adds element to top of stack
	 * 
	 *@param element element to be added
	 *@throws IllegalArgumentException if size is equal to or bigger than capacity
	 */
	@Override
	public void push(E element) {
		if (size() < capacity) {
			stack.add(element);
			capacity++;
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Removes element from top of list
	 * 
	 * @return element from top of list
	 * @throws EmptyStackException if the ArrayStack is empty.
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		capacity--;
		return stack.remove(size() - 1);
	}
	
	/**
	 * Checks if stack is empty
	 * 
	 * @return true if empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns size of stack
	 * 
	 * @return size
	 */
	@Override
	public int size() {
		return stack.size();
	}
	
	/**
	 * Sets capacity of stack
	 * 
	 * @param capacity capacity of stack
	 * @throws IllegalArgumentException if size is equal to or bigger than capacity
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < size() || capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}
