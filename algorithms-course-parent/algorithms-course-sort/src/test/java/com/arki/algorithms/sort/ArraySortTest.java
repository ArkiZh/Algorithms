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
        File file = new File(dataToSortDirectory + "RandomInt.txt");
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
    public void testArrayShellSort(){
        Timer timer = new Timer();
        Integer[] a = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayShellSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        timer.start();
        ArrayShellSort.sort(a);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(a));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]", ArrayShellSort.isSorted(a), time);
    }

    @Test
    public void testArrayInsertionSort(){
        Timer timer = new Timer();
        Integer[] a = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayInsertionSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        timer.start();
        ArrayInsertionSort.sort(a);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(a));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]",ArrayInsertionSort.isSorted(a),time);
    }

    @Test
    public void testArraySelectionSort(){
        Timer timer = new Timer();
        Integer[] a = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArraySelectionSort. The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        timer.start();
        ArraySelectionSort.sort(a);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(a));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]",ArraySelectionSort.isSorted(a),time);
    }


    public static void main(String[] args) {
        File file = FileUtil.createFileAccessIfExists("algorithms-course-parent" + FileUtil.fileSeparator + "algorithms-course-sort" + FileUtil.fileSeparator + dataToSortDirectory + "RandomInt.txt");
        // Generate N random integers in [min,max]
        int N = 10000;
        int min = 0;
        int max = 1000;
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
