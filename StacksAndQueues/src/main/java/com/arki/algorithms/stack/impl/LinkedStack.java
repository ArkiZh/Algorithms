package com.arki.algorithms.stack.impl;

import com.arki.algorithms.stack.Stack;

public class LinkedStack<T> implements Stack<T> {

    private Node<T> first;
    private int size;

    public int size(){ return size;}
    public void push(T t) {
        Node<T> oldFirst = first;
        first = new Node<T>();
        first.item = t;
        first.next = oldFirst;
    }

    public T pop() {
        T item = first.item;
        first= first.next;
        return item;
    }

    private class Node<M>{
        M item;
        Node<M> next;
    }
}
