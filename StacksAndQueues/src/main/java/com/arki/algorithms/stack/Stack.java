package com.arki.algorithms.stack;

public interface Stack<T> extends Iterable<T>{
    void push(T t);
    T pop();
    boolean isEmpty();
    T peek();
    int size();
}
