package com.arki.algorithms.sort;

import com.arki.algorithms.common.*;
import org.junit.Test;

import java.io.*;

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
        Logger.info("Read data from [{}]", FileUtil.getCanonicalPath(file));
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
            a = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                a[i] = Integer.valueOf(split[i]);
            }
        }
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
        int N = 30;
        int min = 1;
        int max = 100;
        File file = FileUtil.createFileAccessIfExists("algorithms-course-parent" + FileUtil.fileSeparator + "algorithms-course-sort"
                + FileUtil.fileSeparator + dataToSortDirectory + "RandomInt_[" + min + "," + max + "]x" + N + ".txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < N - 1; i++) {
                writer.write(MathUtil.randomInt(min, max) + ",");
            }
            writer.write(MathUtil.randomInt(min, max) + "");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
