package com.arki.algorithms.sort;

import com.arki.algorithms.common.ArrayUtil;
import com.arki.algorithms.common.Logger;

public class ArrayQuickSort {
    /**
     * The key procedure to sort the array.
     *
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort) {

        quickSort(arrayToSort, 0, arrayToSort.length - 1);

    }

    public static void quickSort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int partition = partition(a, lo, hi);
        quickSort(a, lo, partition - 1);
        quickSort(a, partition + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        Comparable c = a[lo];
        //Logger.info("^^^^^^ Cut value: {}   lo: {}   hi: {}", c, lo, hi);
        //Logger.info("Source: {}", ArrayUtil.transferArrayToString(ArrayUtil.copyArray(a, lo, hi)));
        int m = lo+1;
        int n = hi;
        while (true) {
            while (a[m].compareTo(c) <= 0) {
                m++;
                if(m>=hi) break;
            }
            while (a[n].compareTo(c) >= 0) {
                n--;
                if(n==lo) break;
            }
            if(m<n) exchange(a, m, n);
            else break;
        }
        exchange(a, lo, n);
        //Logger.info("Result: {}", ArrayUtil.transferArrayToString(ArrayUtil.copyArray(a, lo, hi)));

        return n;
    }

    /**
     * If v<w return true, else return false;
     *
     * @param v
     * @param w
     * @return
     */
    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    /**
     * Exchange the two specific element of the array.
     * @param a
     * @param i
     * @param j
     */
    private static void exchange(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Test whether the array is well sorted as
     * each element is smaller or equal to the next element.
     * @param a
     * @return
     */
    public static boolean isSorted(Comparable[] a){
        for (int i = 1; i < a.length; i++) {
            if(less(a[i],a[i-1])) return false;
        }
        return true;
    }
}
