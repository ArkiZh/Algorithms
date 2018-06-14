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
        a = readIntegerArrayFromFile(file);
    }
    private static Integer[] readIntegerArrayFromFile(File file){
        Logger.info("Read data from [{}]", FileUtil.getCanonicalPath(file));
        Integer[] integers = null;
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            try {
                FileReader reader = new FileReader(file);
                int read = 0;
                while(read!=-1){
                    read = reader.read();
                    if(read!=-1) sb.append((char)read);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] split = sb.toString().split(",");
            integers = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                integers[i] = Integer.valueOf(split[i]);
            }
        }
        return integers;
    }

    @Test
    public void testSortCompare() throws Exception {
        int min = 0;
        int max = 1000000;
        int count = 1000000;
        String pathname = dataToSortDirectory + "RandomInt_[" + min + "," + max + "]x" + count + ".txt";
        Logger.info("Integers for test: {}", pathname);
        Integer[] integers = readIntegerArrayFromFile(new File(pathname));
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
        int N = 1000000;
        int min = 0;
        int max = 1000000;
        generateIntegers(N, min, max);
    }

    /**
     * Generate integers for test.
     * @param count The amount of integers.
     * @param min The min value.
     * @param max The max value.
     * @return The file contains integers for test, separated by commas, named by "RandomInt_[{min},{max}]x{count}.txt"
     */
    private static File generateIntegers(int count, int min, int max) {
        File file = FileUtil.createFileAccessIfExists("algorithms-course-parent" + FileUtil.fileSeparator + "algorithms-course-sort"
                + FileUtil.fileSeparator + dataToSortDirectory + "RandomInt_[" + min + "," + max + "]x" + count + ".txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < count - 1; i++) {
                writer.write(MathUtil.randomInt(min, max) + ",");
            }
            writer.write(MathUtil.randomInt(min, max) + "");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
