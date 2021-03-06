package com.arki.algorithms.queue.impl;

import com.arki.algorithms.queue.Queue;

import java.util.Iterator;

public class LinkedQueue<T> implements Queue<T>{
    private class Node{
        T item;
        Node next;
    }

    private Node first;
    private Node last;
    private int size;

    public void enqueue(T t) {
        Node oldLast = last;
        last = new Node();
        last.item = t;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    public T dequeue() {
        T item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public T first() {
        return first.item;
    }

    public T last() {
        return last.item;
    }


    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T>{

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {

        }
    }
}
