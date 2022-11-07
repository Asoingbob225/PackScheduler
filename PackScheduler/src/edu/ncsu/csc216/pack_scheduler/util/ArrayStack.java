/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * @author stbeuav
 *
 */
public class ArrayStack<E> implements Stack<E>{
	
	private ArrayList<E> stack;
	
	private int capacity;

	
	public ArrayStack(int capacity) {
		stack = new ArrayList<E>();
		setCapacity(capacity);
	}
	
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

	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		capacity--;
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
		this.capacity = capacity;
	}

}
