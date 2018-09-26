package com.arki.algorithms.sort;

import com.arki.algorithms.common.*;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;

public class ArraySortTest {

    public static Integer[] a;
    public static String dataToSortDirectory = "DataToSort"+FileUtil.fileSeparator;

    static {
//        int N = 10000;
//        a = new Integer[N];
//        for (int i = 0; i < N; i++) {
//            a[i] = N - i;
//        }

        // Random int
        File file = new File(dataToSortDirectory + "RandomInt_[1,100]x30.txt");
        a = TestDataUtil.readIntegerArrayFromFile(file);
    }


    @Test
    public void testSortCompare() throws Exception {
        int min = 0;
        int max = 1000000;
        int count = 1000000;
        String pathname = dataToSortDirectory + "RandomInt_[" + min + "," + max + "]x" + count + ".txt";
        Logger.info("Integers for test: {}", pathname);
        Integer[] integers = TestDataUtil.readIntegerArrayFromFile(new File(pathname));
        Class[] sortClazz = new Class[]{ArrayShellSort.class,
                ArrayMergeRecurseSort.class,
                ArrayMergeBottomupSort.class,
                ArrayQuickSort.class};
        Timer timer = new Timer();
        for (int i = 0; i < sortClazz.length; i++) {
            Class clazz = sortClazz[i];
            Method sort = clazz.getDeclaredMethod("sort", Comparable[].class);
            Integer[] arrayToSort = ArrayUtil.copyArray(integers);
            Object[] param = {arrayToSort};
            timer.reset().start();
            long l = System.currentTimeMillis();
            sort.invoke(null, param);
            timer.stop();
            Method isSorted = clazz.getDeclaredMethod("isSorted", Comparable[].class);
            Logger.info("Sort type: {}. Elapsed time:[{}]. Sorted? [{}]", clazz.getSimpleName(), timer.elapsedTime(), isSorted.invoke(null, param));
        }
    }


    @Test
    public void testMaxPriorityQueue() {
        MaxPriorityQueue<Integer> pq = new MaxPriorityQueue<>(a);
        Logger.info("Max priority queue is well structured? [{}]", pq.wellStructured());
        if (pq.wellStructured()) {
            Logger.info("The    largest value = [{}]", pq.deleteMax());
            Logger.info("Second largest value = [{}]",pq.max());
        }
    }


    @Test
    public void testArrayQuickSort() {
        Timer timer = new Timer();
        Integer[] arrayToSort = ArrayUtil.copyArray(a);
        //Integer[] arrayToSort = new Integer[]{2, 3};
        Logger.info("ArrayQuickSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        timer.start();
        ArrayQuickSort.sort(arrayToSort);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]", ArrayQuickSort.isSorted(arrayToSort), time);
    }


    @Test
    public void testArrayMergeBottomupSort() {
        Timer timer = new Timer();
        Integer[] arrayToSort = ArrayUtil.copyArray(a);
        Logger.info("ArrayMergeBottomupSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        timer.start();
        ArrayMergeBottomupSort.sort(arrayToSort);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]", ArrayMergeRecurseSort.isSorted(arrayToSort), time);

    }

    @Test
    public void testArrayMergeBottomupSortWithHistogram() {
        Integer[] arrayToSort = ArrayUtil.copyArray(a);
        Logger.info("ArrayMergeBottomupSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        ArrayMergeBottomupSort.sortWithHistogram(arrayToSort);
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]", ArrayMergeBottomupSort.isSorted(arrayToSort));
    }

    @Test
    public void testArrayMergeRecurseSort() {
        Timer timer = new Timer();
        Integer[] arrayToSort = ArrayUtil.copyArray(a);
        Logger.info("ArrayMergeRecurseSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        timer.start();
        ArrayMergeRecurseSort.sort(arrayToSort);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]", ArrayMergeRecurseSort.isSorted(arrayToSort), time);
    }

    @Test
    public void testArrayMergeRecurseSortWithHistogram() {
        Integer[] arrayToSort = ArrayUtil.copyArray(a);
        Logger.info("ArrayMergeRecurseSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        ArrayMergeRecurseSort.sortWithHistogram(arrayToSort);
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]", ArrayMergeRecurseSort.isSorted(arrayToSort));
    }


    @Test
    public void testArrayShellSort(){
        Timer timer = new Timer();
        Integer[] arrayToSort = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayShellSort. The array to sort is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        timer.start();
        ArrayShellSort.sort(arrayToSort);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]", ArrayShellSort.isSorted(arrayToSort), time);
    }

    @Test
    public void testArrayShellSortWithHistogram(){
        Integer[] arrayToSort = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayShellSort. The array to sort is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        ArrayShellSort.sortWithHistogram(arrayToSort);
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]", ArrayShellSort.isSorted(arrayToSort));
    }

    @Test
    public void testArrayInsertionSort(){
        Timer timer = new Timer();
        Integer[] arrayToSort = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayInsertionSort. The array to sort is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        timer.start();
        ArrayInsertionSort.sort(arrayToSort);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]",ArrayInsertionSort.isSorted(arrayToSort),time);
    }

    @Test
    public void testArrayInsertionSortWithHistogram(){
        Integer[] arrayToSort = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayInsertionSort. The array to sort is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        ArrayInsertionSort.sortWithHistogram(arrayToSort);
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]",ArrayInsertionSort.isSorted(arrayToSort));
    }

    @Test
    public void testArraySelectionSort(){
        Timer timer = new Timer();
        Integer[] arrayToSort = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArraySelectionSort. The array to sort is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        timer.start();
        ArraySelectionSort.sort(arrayToSort);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]",ArraySelectionSort.isSorted(arrayToSort),time);
    }

    @Test
    public void testArraySelectionSortWithHistogram(){
        Integer[] arrayToSort = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArraySelectionSort. The array to sort is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        ArraySelectionSort.sortWithHistogram(arrayToSort);
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(arrayToSort));
        Logger.info("Is sorted? [{}]",ArraySelectionSort.isSorted(arrayToSort));
    }


    public static void main(String[] args) {

        // Generate N random integers in [min,max]
        int N = 100;
        int min = 0;
        int max = 100;
        File file = new File("algorithms-course-parent" + FileUtil.fileSeparator + "algorithms-course-sort"
                + FileUtil.fileSeparator + dataToSortDirectory + "RandomInt_[" + min + "," + max + "]x" + N + ".txt");
        TestDataUtil.generateRandomIntegers(N, min, max, file);
    }
}
