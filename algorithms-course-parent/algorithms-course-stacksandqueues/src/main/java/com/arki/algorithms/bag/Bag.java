package com.arki.algorithms.bag;

public interface Bag<T> extends Iterable<T>{
    void add(T t);

    boolean isEmpty();

    int size();
}
