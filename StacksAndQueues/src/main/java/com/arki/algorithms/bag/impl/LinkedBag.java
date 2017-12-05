package com.arki.algorithms.bag.impl;

import com.arki.algorithms.bag.Bag;

import java.util.Iterator;

public class LinkedBag <T> implements Bag<T>{

    private class Node<M>{
        M item;
        Node<M> next;
    }

    private Node<T> first;
    private int size;

    public void add(T t) {
        Node<T> oldFirst = this.first;
        first=new Node<T>();
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
}
