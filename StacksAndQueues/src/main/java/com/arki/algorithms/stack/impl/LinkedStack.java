package com.arki.algorithms.stack.impl;

import com.arki.algorithms.stack.Stack;

import java.util.Iterator;

public class LinkedStack<T> implements Stack<T> {

    private class Node{
        T item;
        Node next;
    }

    private Node first;
    private int size;

    public int size(){ return size;}
    public void push(T t) {
        Node oldFirst = first;
        first = new Node();
        first.item = t;
        first.next = oldFirst;
        size++;
    }

    public T pop() {
        T item = first.item;
        first= first.next;
        size--;
        return item;
    }

    public boolean isEmpty() {
        return first==null;
    }

    public T peek() {
        return first.item;
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
