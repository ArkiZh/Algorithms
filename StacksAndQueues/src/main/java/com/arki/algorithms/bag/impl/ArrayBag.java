package com.arki.algorithms.bag.impl;

import com.arki.algorithms.bag.Bag;

import java.util.Iterator;

public class ArrayBag<T> implements Bag<T> {

    private T[] items;
    private int defaultCapacity = 10;
    private int size;

    public ArrayBag(){
        this.items = (T[]) new Object[defaultCapacity];
    }

    public void add(T t) {
        if(size==items.length) resize(items.length * 2);
        items[size++] = t;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }


    private void resize(int max){
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < items.length; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }


    public Iterator<T> iterator() {
        return new ArrayBagIterator();
    }

    private class ArrayBagIterator implements Iterator{

        private int current;

        public boolean hasNext() {
            return current < size;
        }

        public Object next() {
            T item = items[current];
            current++;
            return item;
        }

        public void remove() {

        }
    }

}
