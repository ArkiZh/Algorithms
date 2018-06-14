package com.arki.algorithms.sort;

import java.lang.reflect.Array;

public class MaxPriorityQueue<Key extends Comparable<Key>> {
    private Key[] keys;
    private int size;
    MaxPriorityQueue(int max){
        keys = (Key[]) new Comparable[max + 1];
    }
    MaxPriorityQueue(Key[] a){
        keys = (Key[]) Array.newInstance(a[0].getClass(), a.length + 1);
        for (int i = 0; i < a.length; i++) {
            insert(a[i]);
        }
        size = a.length;
    }
    void insert(Key v){
        keys[++size] = v;
        swim(size);
    }
    Key max(){ return keys[1]; }
    Key deleteMax(){
        Key max = keys[1];
        keys[1] = keys[size];
        keys[size--] = null;
        sink(1);
        return max;
    }
    boolean isEmpty(){return size==0;}
    int size(){return size;}
    boolean wellStructured() {
        for (int i = 1; i * 2 <= size; i++) {
            if (less(keys[i], keys[i * 2])) {
                return false;
            }
            if (i * 2 + 1 <= size && less(keys[i], keys[i * 2 + 1])) {
                return false;
            }
        }
        return true;
    }

    private void swim(int k){
        while (k >= 2) {
            if (less(keys[k / 2], keys[k])) {
                exchange(k / 2, k);
            } else {
                break;
            }
            k /= 2;
        }
    }
    private void sink(int k){
        while (2 * k + 1 <= size) {
            if (less(keys[k], keys[k * 2]) || less(keys[k], keys[k * 2 + 1])) {
                if (less(keys[k * 2], keys[k * 2 + 1])) {
                    exchange(k, k * 2 + 1);
                    k = k * 2 + 1;
                } else {
                    exchange(k, k * 2);
                    k = k * 2;
                }
            } else {
                break;
            }
        }
    }
    private boolean less(Key i, Key j){return i.compareTo(j)<0;}
    private void exchange(int i, int j){
        Key temp = keys[i];
        keys[i] = keys[j];
        keys[j] = temp;
    }
}
