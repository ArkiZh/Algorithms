package com.arki.algorithms.unionfind;

public abstract class AbstractUnionFind {

    /**
     * Initialize an array to represent the relationship of all elements.
     * the index represent the actual element.
     * the value is used to judge the relationship.
     */
    public abstract int[] initFlags(int[] flags);

    /**
     * Intialize an array like 0,1,2...
     * @param a
     * @return
     */
    public abstract  int[] initFlagsSimply(int a);

    /**
     * Get the flags array.
     * @return
     */
    public abstract int[]  getFlags();


    /**
     * check whether p and q are connected
     * @param p
     * @param q
     * @return
     */
    public abstract boolean connected(int p, int q);


    /**
     * q is to be connected while p is already in the component
     * @param p
     * @param q
     * @return
     */
    public abstract void union(int p, int q);

}
