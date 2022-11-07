package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom ArrayList that doesn't allow for null elements or duplicate elements
 *
 * @author rdbryan2
 * @param <E> the generic type of elements for an instance of ArrayList 
 */
public class ArrayList<E> extends AbstractList<E> {

	/** Initial capacity of the list */
	private static final int INIT_SIZE = 10;

	/** Generic array for holding list elements */
	private E[] list;

	/** Number of elements currently in the ArrayList */
	private int size;
	
	/**
	 * Constructor for ArrayList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}
	
	/**
	 * Adds element to array a specified index
	 *
	 * @param index desired index 
	 * @param object generic type object to be added
	 */
	@Override
	public void add(int index, E object) {
		if (object == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		if (size == list.length - 1) {
			growArray();
		}
		for (int i = size - 1; i >= index; i--) {
			list[i + 1] = list[i];
		}
		list[index] = object;
		size++;
	}
	
	/**
	 * Creates new array of double length and transfers elements over
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] tempList = (E[]) new Object[list.length * 2];
		for (int i = 0; i < list.length; i++) {
			tempList[i] = list[i];
		}
		list = tempList;
	}
	
	/**
	 * Removes element at specified index
	 * 
	 * @param index index of element to remove
     * @return element that was removed
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E tempE = list[index];
		for (int i = index; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return tempE;
	}
	
	/**
	 * Sets element at specified index
	 * 
	 * @param index index to set element to
     * @return element that was replaced
	 */
	@Override
	public E set(int index, E object) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (object == null) {
			throw new NullPointerException();
		}
		if (get(index).equals(object)) {
			return object;
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		
		E tempE = list[index];
		list[index] = object;
		return tempE;
	}
	
	/**
	 * Returns element at specified index
	 * 
	 * @param index index of element to return
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
	/**
	 * Getter for size
	 * 
     * @return size of list
	 */
	@Override
	public int size() {
		return size;
	}
}
