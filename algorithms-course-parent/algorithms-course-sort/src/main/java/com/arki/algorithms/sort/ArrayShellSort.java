package com.arki.algorithms.sort;

public class ArrayShellSort {
    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort){
        int length = arrayToSort.length;
        int interval = 0;
        int N = 0;
        while (3 * N + 1 < length) {
            interval = 3 * N + 1;
            N++;
        }
        while (interval >= 1) {
            for (int i = 0; i < interval; i++) {
                for (int j = 1; i + j * interval < length; j++) {
                    int t = i + j * interval;
                    for (int k = j; k > 0 && less(arrayToSort[i + k * interval], arrayToSort[i + (k - 1) * interval]); k--) {
                        exchange(arrayToSort, i + k * interval, i + (k - 1) * interval);
                    }
                }
            }
            interval -= 3;
        }
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
