/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Implements stack interface list using LinkedAbstractList
 * 
 * @author stbeuav
 * @param <E> generic element
 */
public class LinkedStack<E> implements Stack<E> {
	
	/**
	 * Linked list to hold stack data
	 */
	private LinkedAbstractList<E> stack;
	
	/**
	 * Constructor for LinkedStack
	 * 
	 * @param capacity capacity of list
	 */
	public LinkedStack(int capacity) {
		stack = new LinkedAbstractList<E>(capacity);
	}
	
	/** 
	 * Adds element to top of stack
	 * 
	 * @param element element to be added
	 */
	@Override
	public void push(E element) {
		stack.add(element);
	}

	/**
	 * Removes element from top of list
	 * 
	 * @return element from top of list
	 * @throws EmptyStackException if LinkedStack is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
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
		stack.setCapacity(capacity);
	}

}
