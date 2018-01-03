package com.arki.algorithms.bag.impl;

import com.arki.algorithms.bag.Bag;

import java.util.Iterator;

public class LinkedBag <T> implements Bag<T>{

    private class Node{
        T item;
        Node next;
    }

    private Node first;
    private int size;

    public void add(T t) {
        Node oldFirst = this.first;
        first=new Node();
        first.item = t;
        first.next = oldFirst;
        size++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T>{

        private Node current = first;

        public boolean hasNext() {
            return current!=null;
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
