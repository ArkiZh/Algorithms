package com.arki.algorithms.sort;

public class ArraySelectionSort {
    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort){
        for (int i = 0; i < arrayToSort.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arrayToSort.length; j++) {
                if(less(arrayToSort[j],arrayToSort[minIndex])) minIndex = j;
            }
            exchange(arrayToSort, i, minIndex);
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
