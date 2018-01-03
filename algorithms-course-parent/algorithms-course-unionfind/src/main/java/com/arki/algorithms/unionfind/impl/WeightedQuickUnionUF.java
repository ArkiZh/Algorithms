package com.arki.algorithms.unionfind.impl;

import com.arki.algorithms.unionfind.AbstractUnionFind;

public class WeightedQuickUnionUF extends AbstractUnionFind{

    private int[] flags;
    private int[] sizeOfTree;

    @Deprecated
    @Override
    public int[] initFlags(int[] flags) {
        this.flags = flags;
        return this.flags;
    }

    @Override
    public int[] initFlagsSimply(int a) {
        this.flags = new int[a];
        for (int i = 0; i < a; i++) {
            this.flags[i] = i;
        }
        return this.flags;
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
        if(connected(rootP,rootQ)) return;
        if (sizeOfTree[rootP] <= sizeOfTree[rootQ]) {
            // P is smaller.
            flags[rootP] = rootQ;
            sizeOfTree[rootQ] += sizeOfTree[rootP];
        }else{
            // Q is smaller.
            flags[rootQ] = rootP;
            sizeOfTree[rootP] += sizeOfTree[rootQ];
        }
    }

    private int root(int p){
        while(this.flags[p]!=p){
            p = flags[p];
        }
        return p;
    }
}
