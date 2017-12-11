package com.arki.algorithms.queue.impl;

import com.arki.algorithms.queue.Queue;

import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {

    private T[] items;

    private int first;
    private int last;
    private int defaultCapacity = 10;

    public ArrayQueue(){
        items = (T[])new Object[defaultCapacity];
    }

    public void enqueue(T t) {

    }

    public T dequeue() {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public T first() {
        return null;
    }

    public T last() {
        return null;
    }

    public Iterator<T> iterator() {
        return null;
    }
}
