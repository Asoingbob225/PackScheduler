/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * LinkedQueue class
 * @author adharsh
 * @param E the generic type
 */
public class LinkedQueue<E> implements Queue<E> {

	private LinkedAbstractList<E> queue;
	
	public LinkedQueue(int capacity) {
		queue = new LinkedAbstractList<E>(capacity);
	}
	
	@Override
	public void enqueue(E element) {
		if(size() == 0)
		{
			queue.add(element);
		}
		else
		{
			queue.add(size() - 1, element);
		}
		
	}

	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return queue.remove(0);
	}

	@Override
	public boolean isEmpty() {	
		return queue.isEmpty();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void setCapacity(int capacity) {
		if (capacity < size() || capacity < 0) {
			throw new IllegalArgumentException();
		}
		queue.setCapacity(capacity);	
	}

}
