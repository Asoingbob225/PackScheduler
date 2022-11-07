/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * @author stbeuav
 *
 */
public class LinkedStack<E> implements Stack<E> {

	private LinkedAbstractList<E> stack;
	
	public LinkedStack(int capacity) {
		stack = new LinkedAbstractList<E>(capacity);
	}
	
	@Override
	public void push(E element) {
		stack.add(element);
	}

	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return stack.remove(size() - 1);
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public void setCapacity(int capacity) {
		if (capacity < size() || capacity < 0) {
			throw new IllegalArgumentException();
		}
		stack.setCapacity(capacity);
	}

}
