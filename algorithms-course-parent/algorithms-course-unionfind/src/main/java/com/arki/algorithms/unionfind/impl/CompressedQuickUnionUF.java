package com.arki.algorithms.unionfind.impl;

import com.arki.algorithms.unionfind.AbstractUnionFind;

public class CompressedQuickUnionUF extends AbstractUnionFind{
    private int[] flags;
    /**
     * Initialize an array to represent the relationship of all elements.
     * the index represent the actual element.
     * the value is used to judge the relationship.
     *
     * @param flags
     */
    @Override
    public int[] initFlags(int[] flags) {
        this.flags = flags;
        return new int[0];
    }

    /**
     * Intialize an array like 0,1,2...
     *
     * @param a
     * @return
     */
    @Override
    public int[] initFlagsSimply(int a) {
        this.flags = new int[a];
        for (int i = 0; i < a; i++) {
            this.flags[i] = i;
        }
        return this.flags;
    }

    /**
     * Get the flags array.
     *
     * @return
     */
    @Override
    public int[] getFlags() {
        return this.flags;
    }

    /**
     * check whether p and q are connected
     *
     * @param p
     * @param q
     * @return
     */
    @Override
    public boolean connected(int p, int q) {
        return root(p)==root(q);
    }

    /**
     * q is to be connected while p is already in the component
     *
     * @param p
     * @param q
     * @return
     */
    @Override
    public void union(int p, int q) {
        this.flags[root(p)] = root(q);
    }

    private int root(int p){
        System.out.println("Finding root of: " + p);
        while (this.flags[p] != p) {
            int targetNode = this.flags[this.flags[p]];
            this.flags[p] = targetNode;
            System.out.println(" node " + p + " to node " + targetNode);
            p = this.flags[p];
        }
        return p;
    }

}
