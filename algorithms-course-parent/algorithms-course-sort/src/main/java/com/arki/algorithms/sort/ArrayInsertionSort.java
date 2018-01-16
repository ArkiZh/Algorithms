package com.arki.algorithms.sort;

import com.arki.algorithms.common.DrawUtil;
import com.arki.algorithms.common.FileUtil;
import com.arki.algorithms.common.Logger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ArrayInsertionSort {
    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort){
        for (int i = 1; i < arrayToSort.length; i++) {
            for (int j = i; j >0 && less(arrayToSort[j],arrayToSort[j-1]); j--) {
                exchange(arrayToSort, j, j - 1);
            }
        }
    }

    public static void sortWithHistogram(Integer[] arrayToSort) {
        ArrayList<DrawUtil.HistogramData> histogramDataArrayList = new ArrayList<>();
        for (int i = 1; i < arrayToSort.length; i++) {
            for (int j = i; j - 1 >= 0 && less(arrayToSort[j], arrayToSort[j - 1]); j--) {
                DrawUtil.histogramDataListAdd(histogramDataArrayList, arrayToSort, new Integer[]{j, j - 1}, Color.RED);
                exchange(arrayToSort, j, j - 1);
            }
        }
        DrawUtil.histogramDataListAdd(histogramDataArrayList, arrayToSort, null, null);
        DrawUtil.Pen pen = DrawUtil.drawHistogramList(histogramDataArrayList);
        File save = pen.save("ArrayInsertionSort.sortWithHistogram.png");
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
