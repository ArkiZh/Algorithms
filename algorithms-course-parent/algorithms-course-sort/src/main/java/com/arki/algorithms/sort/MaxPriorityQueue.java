package com.arki.algorithms.sort;

public class MaxPriorityQueue<Key extends Comparable<Key>> {
    MaxPriorityQueue(){}
    MaxPriorityQueue(int max){}
    MaxPriorityQueue(Key[] a){}
    void insert(Key v){}
    Key max(){ return null; }
    Key deleteMax(){return null;}
    boolean isEmpty(){return true;}
    int size(){return 0;}

    private void swim(int k){}
    private void sink(int k){}
    private boolean less(Key i, Key j){return false;}
    private void exchange(int i, int j){}
}
