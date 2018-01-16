package com.arki.algorithms.sort;

import com.arki.algorithms.common.DrawUtil;
import com.arki.algorithms.common.FileUtil;
import com.arki.algorithms.common.Logger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class ArrayShellSort {
    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort){
        int N = arrayToSort.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h;
        }
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j - h >= 0 && less(arrayToSort[j], arrayToSort[j - h]); j -= h) {
                    exchange(arrayToSort, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    public static void sortWithHistogram(Integer[] arrayToSort){
        ArrayList<DrawUtil.HistogramData> histogramDatalist = new ArrayList<>();
        int N = arrayToSort.length;
        int h = 1;
        while (h < N / 3) h *= 3;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                HashSet<Integer> indexes = new HashSet<>();
                for (int j = i; j - h >= 0 && less(arrayToSort[j], arrayToSort[j - h]); j -= h) {
                    indexes.add(j);
                    indexes.add(j - h);
                    exchange(arrayToSort, j, j - h);
                }
                DrawUtil.histogramDataListAdd(histogramDatalist, arrayToSort, indexes.toArray(new Integer[]{}), Color.RED);
            }
            h /= 3;
        }
        DrawUtil.histogramDataListAdd(histogramDatalist, arrayToSort, null, null);
        DrawUtil.Pen pen = DrawUtil.drawHistogramList(histogramDatalist);
        File save = pen.save("ArrayShellSort.sortWithHistogram.png");
        Logger.info("SortWithHistogram saved to: [{}]", FileUtil.getCanonicalPath(save));
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
