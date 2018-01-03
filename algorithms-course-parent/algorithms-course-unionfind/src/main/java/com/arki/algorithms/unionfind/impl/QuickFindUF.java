package com.arki.algorithms.unionfind.impl;

import com.arki.algorithms.unionfind.AbstractUnionFind;

public class QuickFindUF extends AbstractUnionFind {

    private int[] flags;

    @Override
    public int[] initFlagsSimply(int n){
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = i;
        }
        return this.initFlags(ints);
    }

    @Override
    public int[] initFlags(int[] flags) {
        this.flags = flags;
        return this.flags;
    }

    @Override
    public int[] getFlags() {
        return this.flags;
    }


    @Override
    public boolean connected(int p, int q) {
        return flags[p] == flags[q];
    }

    @Override
    public void union(int p, int q) {
        if (connected(p, q)) return;
        int pFlag = flags[p];
        int qFlag = flags[q];
        for (int i = 0; i < flags.length; i++) {
            if (pFlag == flags[i]) {
                flags[i] = qFlag;
            }
        }
    }
}
