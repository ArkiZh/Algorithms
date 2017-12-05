package com.arki.algorithms.queue;

public interface Queue<T> extends Iterable<T>{
    void enqueue(T t);
    T dequeue();
    boolean isEmpty();
    int size();
    T first();
    T last();
}
