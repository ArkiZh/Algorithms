package com.arki.algorithms.sort;

import com.arki.algorithms.common.ArrayUtil;
import com.arki.algorithms.common.DrawUtil;
import com.arki.algorithms.common.FileUtil;
import com.arki.algorithms.common.Logger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArrayMergeRecurseSort {



    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort){
        Comparable[] aux = new Comparable[arrayToSort.length];
        sort(arrayToSort, 0, arrayToSort.length - 1, aux);
    }

    private static void sort(Comparable[] arrayToSort, int lo, int hi, Comparable[] aux) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(arrayToSort, lo, mid, aux);
        sort(arrayToSort, mid + 1, hi, aux);
        merge(arrayToSort, lo, mid, hi, aux);
    }

    private static void merge(Comparable[] arrayToSort, int lo, int mid, int hi, Comparable[] aux) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = arrayToSort[i];
        }

        int m = lo; // Index of lo -> mid
        int n = mid + 1; // Index of mid+1 -> hi

        for (int i = lo; i <= hi; i++) {
            if (m > mid) {
                arrayToSort[i] = aux[n++];
            } else if (n > hi) {
                arrayToSort[i] = aux[m++];
            } else if (less(aux[n], aux[m])) {
                arrayToSort[i] = aux[n++];
            } else {
                arrayToSort[i] = aux[m++];
            }
        }
    }

    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sortWithHistogram(Integer[] arrayToSort){
        Integer[] aux = new Integer[arrayToSort.length];
        ArrayList<DrawUtil.HistogramData> list = new ArrayList<>();
        sortWithHistogram(arrayToSort, 0, arrayToSort.length - 1, aux,list);
        DrawUtil.histogramDataListAdd(list, arrayToSort, null, null);
        DrawUtil.Pen pen = DrawUtil.drawHistogramList(list);
        File save = pen.save("ArrayMergeRecurseSort.sortWithHistogram.png");
        Logger.info("SortWithHistogram saved to: [{}]", FileUtil.getCanonicalPath(save));
    }

    private static void sortWithHistogram(Integer[] arrayToSort, int lo, int hi, Comparable[] aux, List<DrawUtil.HistogramData> list) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        sortWithHistogram(arrayToSort, lo, mid, aux, list);
        sortWithHistogram(arrayToSort, mid + 1, hi, aux, list);

        DrawUtil.HistogramData histogramData = new DrawUtil.HistogramData();
        Map<Integer, Color> colorMap = histogramData.getColorMap();
        histogramData.setDataArray(ArrayUtil.copyArray(arrayToSort));
        for (int i = lo; i <= mid; i++) colorMap.put(i,Color.RED);
        for (int i = mid + 1; i <= hi; i++) colorMap.put(i, Color.PINK);
        list.add(histogramData);
        merge(arrayToSort, lo, mid, hi, aux);
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
