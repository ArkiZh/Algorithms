package com.arki.algorithms.sort;

import com.arki.algorithms.common.*;
import org.junit.Test;

import java.io.*;

public class ArraySortTest {

    public static Integer[] a;
    static {
//        int N = 10000;
//        a = new Integer[N];
//        for (int i = 0; i < N; i++) {
//            a[i] = N - i;
//        }

        // Random int
        File file = FileUtil.createFileOnlyNew("ArraySortTest.txt");
        if(file!=null){
            try {
                FileWriter writer = new FileWriter(file);
                for (int i = 0; i < 9999; i++) {
                    writer.write(MathUtil.randomInt(0, 1000000) + ",");
                }
                writer.write(MathUtil.randomInt(0, 1000000)+"");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader(new File("ArraySortTest.txt"));
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

    @Test
    public void testArrayShellSort(){
        Timer timer = new Timer();
        Integer[] a = ArrayUtil.copyArray(ArraySortTest.a);
        Logger.info("ArrayShellSort. The array to sort is: {}", ArrayUtil.transferArrayToString(ArraySortTest.a));
        timer.start();
        ArrayShellSort.sort(a);
        timer.stop();
        double time = timer.elapsedTime();
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(ArraySortTest.a));
        Logger.info("Is sorted? [{}]   Elapsed time: [{}]",ArrayShellSort.isSorted(ArraySortTest.a),time);
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
}
