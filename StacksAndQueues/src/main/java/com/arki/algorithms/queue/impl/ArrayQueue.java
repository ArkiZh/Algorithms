package com.arki.algorithms.queue.impl;

import com.arki.algorithms.queue.Queue;

import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {

    private T[] items;

    private int first = -1;
    private int last = -1;
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
        return last >= first ? last - first + 1 : last - first + items.length - 1;
    }

    public T first() {
        return null;
    }

    public T last() {
        return null;
    }

    private boolean isFull(){
        return false;
    }

    public Iterator<T> iterator() {
        return null;
    }
}
