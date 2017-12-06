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
        return null;
    }

    private class LinkedIterator implements Iterator<T>{

        public boolean hasNext() {
            return false;
        }

        public T next() {
            return null;
        }

        public void remove() {

        }
    }
}
