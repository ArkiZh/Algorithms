package com.arki.algorithms.stack.impl;

import com.arki.algorithms.stack.Stack;

import java.util.Iterator;

public class LinkedStack<T> implements Stack<T> {

    private class Node<M>{
        M item;
        Node<M> next;
    }

    private Node<T> first;
    private int size;

    public int size(){ return size;}
    public void push(T t) {
        Node<T> oldFirst = first;
        first = new Node<T>();
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
        return null;
    }

}
