package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

public class ArrayQueue<E> implements Queue<E> {

	private ArrayList<E> queue;
	
	private int capacity;
	
	public ArrayQueue(int capacity) {
		queue = new ArrayList<E>();
		setCapacity(capacity);
	}
	
	@Override
	public void enqueue(E element) {
		if (size() < capacity) {
			
			if(size() == 0)
			{
				queue.add(element);
			}
			else
			{
				queue.add(size() - 1, element);
				capacity++;
			}
			
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		capacity--;
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
		this.capacity = capacity;
	}

}
