package com.arki.algorithms.sort;

import com.arki.algorithms.common.ArrayUtil;
import com.arki.algorithms.common.DrawUtil;
import com.arki.algorithms.common.FileUtil;
import com.arki.algorithms.common.Logger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayMergeBottomupSort {
    /**
     * The key procedure to sort the array.
     * @param arrayToSort
     */
    public static void sort(Comparable[] arrayToSort) {
        int length = arrayToSort.length;
        Comparable[] aux = new Comparable[length];

        for (int size = 1; size < length; size+=size) {
            for (int i = 0; i + size < length; i += 2 * size) {
                int lo = i;
                int mid = i + size - 1;
                int hi = i + 2 * size - 1 < length - 1 ? i + 2 * size - 1 : length - 1;
                merge(arrayToSort, aux, lo, mid, hi);
            }

        }
    }

    public static void sortWithHistogram(Integer[] arrayToSort) {
        int length = arrayToSort.length;
        Integer[] aux = new Integer[length];

        List<DrawUtil.HistogramData> histogramDataList = new ArrayList<>();
        DrawUtil.histogramDataListAdd(histogramDataList, arrayToSort, null, null);

        for (int size = 1; size < length; size += size) {
            //Sort: n*size -> n*size+size+size-1
            for (int i = 0; i + size < length; i += 2 * size) {
                int lo = i;
                int mid = i + size - 1;
                int hi = i + size + size < length ? i + size + size - 1 : length - 1;

                DrawUtil.HistogramData histogramData = new DrawUtil.HistogramData();
                histogramData.setDataArray(ArrayUtil.copyArray(arrayToSort));
                Map<Integer, Color> colorMap = new HashMap<>();
                for (int j = lo; j <= hi; j++)
                    colorMap.put(j, j <= mid ? Color.RED : Color.BLUE);
                histogramData.setColorMap(colorMap);
                histogramDataList.add(histogramData);

                merge(arrayToSort, aux, lo, mid, hi);
            }
        }

        DrawUtil.histogramDataListAdd(histogramDataList, arrayToSort, null, null);

        DrawUtil.Pen pen = DrawUtil.drawHistogramList(histogramDataList);
        File save = pen.save("ArrayMergeBottomupSort.sortWithHistogram.png");
        Logger.info("SortWithHistogram saved to: [{}]", FileUtil.getCanonicalPath(save));
    }


    private static void merge(Comparable[] arrayToSort, Comparable[] aux, int lo, int mid, int hi) {
        //Copy array.
        for (int j = lo; j <= hi; j++) {
            aux[j] = arrayToSort[j];
        }
        //Merge lo->mid and mid+1->hi.
        int a = lo;
        int b = mid + 1;
        for (int j = lo; j <= hi; j++) {
            if(a>mid) arrayToSort[j] = aux[b++];
            else if(b>hi) arrayToSort[j] = aux[a++];
            else arrayToSort[j] = less(aux[a], aux[b]) ? aux[a++] : aux[b++];
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
