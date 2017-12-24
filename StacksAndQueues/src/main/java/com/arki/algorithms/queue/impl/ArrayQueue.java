package com.arki.algorithms.queue.impl;

import com.arki.algorithms.queue.Queue;

import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {

    private T[] items;

    /**
     * <code>false</code> indicates that the last element index is bigger than the first element.
     */
    private boolean crossFlag = false;
    private int first = -1;
    private int last = -2;
    private int defaultCapacity = 10;

    public ArrayQueue(){
        items = (T[])new Object[defaultCapacity];
    }

    public ArrayQueue(int capacity){
        items = (T[])new Object[capacity];
    }

    public void enqueue(T t) {
        if (first != -1) {
            if (isFull()) items = copyItems(items, items.length * 2, first, last);
            last = getNextIndex(last, true);
            items[last] = t;
        } else {
            first = 0;
            last = 0;
            items[0] = t;
        }
    }

    public T dequeue() {
        T item = items[first];
        items[first] = null;
        first = getNextIndex(first,true);
        // Resize capacity of array.
        int halfLength = items.length / 2;
        if (size() == halfLength) items = copyItems(items, halfLength, first, last);
        return item;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        int size;
        if (!crossFlag) {
            size = last - first + 1;
        } else {
            size = items.length + last - first + 1;
        }
        return size;
    }

    public T first() {
        return items[first];
    }

    public T last() {
        return items[last];
    }

    private boolean isFull(){
        return size()==items.length;
    }

    private int getNextIndex(int current, boolean enableCrossFlagChange){
        int next;
        if(current!=items.length) next=current+1;
        else {
            next = 0;
            if(enableCrossFlagChange) crossFlag = !crossFlag;
        }
        return next;
    }

    private T[] copyItems(T[] origin, int targetLength,int startIndex, int endIndex) {
        T[] target = (T[]) new Object[targetLength];
        if (!crossFlag) {
            for (int i = startIndex; i <= endIndex; i++) {
                target[i-startIndex] = origin[i];
            }
            last = endIndex - startIndex;
        } else {
            for (int i = startIndex; i <origin.length; i++) {
                target[i - startIndex] = origin[i];
            }
            int tempBase = origin.length - startIndex;
            for (int i = 0; i <= endIndex; i++) {
                target[tempBase + i] = origin[i];
            }
            crossFlag = false;
            last = tempBase + endIndex;
        }
        first = 0;
        return target;
    }

    public Iterator<T> iterator() {
        return new ArrayQueueIterator();
    }

    private class ArrayQueueIterator implements Iterator<T>{

        private int current = first;
        private int count = 1;


        public boolean hasNext() {

            return count<=size();
        }

        public T next() {
            T item = items[current];
            current = getNextIndex(current, false);
            count++;
            return item;
        }

        public void remove() {

        }
    }
}
