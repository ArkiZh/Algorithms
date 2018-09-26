package com.arki.algorithms.sort;


import java.lang.reflect.Array;

/**
 * Priority queue based on binary max-heap using array data structure.
 * @param <Key>
 */
public class MaxPriorityQueue<Key extends Comparable<Key>> {
    private Key[] keys;
    private int size;
    private Class keyClazz = Comparable.class;
    /**
     * Note:<br/>
     * When the keys is full, extend the capacity to defaultExtendMultiple * keys.length.<br/>
     * When the size of keys equals 1/defaultShrinkThreshold * keys.length,<br/>
     * shrink the keys to length of 1/defaultShrinkMultiple * keys.length,<br/>
     * while ensuring the length >= defaultMinCapacity;
     */
    private int defaultMinCapacity = 10;
    private int defaultExtendMultiple = 2;
    private int defaultShrinkThreshold = 3;
    private int defaultShrinkMultiple = 2;


    public MaxPriorityQueue() {
        keys = (Key[]) new Comparable[defaultMinCapacity];
    }

    MaxPriorityQueue(int defaultCapacity){
        keys = (Key[]) new Comparable[defaultCapacity + 1];
    }
    MaxPriorityQueue(Key[] a){
        keyClazz = a[0].getClass();
        keys = (Key[]) Array.newInstance(a[0].getClass(), a.length + 1);
        for (int i = 0; i < a.length; i++) {
            insert(a[i]);
        }
        size = a.length;
    }

    /*public static void main(String[] args) {
        MaxPriorityQueue<Integer> pq = new MaxPriorityQueue<>();
        for (int i = 0; i < 100; i++) {
            pq.insert(i);
        }
        while (pq.size() > 0) {
            pq.deleteMax();
        }
    }*/

    void insert(Key v){
        autoExtendCapacity();
        keys[++size] = v;
        swim(size);
    }

    private void autoExtendCapacity() {
        if (size + 1 == keys.length) {
            //Logger.info("Extend from [{}] to [{}]", keys.length, keys.length * defaultExtendMultiple);
            Key[] temp = (Key[]) Array.newInstance(keyClazz, keys.length * defaultExtendMultiple);
            for (int i = 0; i < size + 1; i++) {
                temp[i] = keys[i];
            }
            keys = temp;
        }
    }

    Key max(){ return keys[1]; }
    Key deleteMax(){
        Key max = keys[1];
        keys[1] = keys[size];
        keys[size--] = null;
        autoShrinkCapacity();
        sink(1);
        return max;
    }

    private void autoShrinkCapacity() {
        if (size + 1 == keys.length / defaultShrinkThreshold) {
            int newCapacity = keys.length / defaultShrinkMultiple > defaultMinCapacity ?
                    keys.length / defaultShrinkMultiple : defaultMinCapacity;
            //Logger.info("Shrink from [{}] to [{}]. Size+1=[{}]", keys.length, newCapacity, size + 1);
            Key[] temp = (Key[]) Array.newInstance(keyClazz, newCapacity);
            for (int i = 0; i < size + 1; i++) {
                temp[i] = keys[i];
            }
            keys = temp;
        }
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
