package com.arki.algorithms.unionfind.impl;

import com.arki.algorithms.unionfind.AbstractUnionFind;

public class QuickUnionUF extends AbstractUnionFind {

    private int[] flags;

    @Override
    public int[] initFlags(int[] flags) {
        this.flags = flags;
        return this.flags;
    }

    @Override
    public int[] initFlagsSimply(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = i;
        }
        return this.initFlags(ints);
    }

    @Override
    public int[] getFlags() {
        return this.flags;
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        System.out.print("rootP: " + rootP + "\t");
        System.out.println("rootQ: " + rootQ);
        if (rootP == rootQ) return;
        flags[rootP] = rootQ;
    }

    private int root(int p){
        while (flags[p] != p) {
            p = flags[p];
        }
        return p;
    }

}
