package com.arki.algorithms.stack.impl;

import com.arki.algorithms.stack.Stack;

import java.util.Iterator;

public class ArrayStack<T> implements Stack<T> {
    private T[] items;
    private int defaultCapacity;

    public  ArrayStack(){
        items = (T[]) new Object[defaultCapacity];
    }


    private int size;

    public void push(T t) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size++] = t;
    }

    public T pop() {
        T item = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public T peek() {
        return items[size-1];
    }

    public int size() {
        return size;
    }

    private void resize(int max){
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T>{

        private int current = size;

        public boolean hasNext() {
            return current!=0;
        }

        public T next() {
            return items[--current];
        }

        public void remove() {

        }
    }
}
