package com.arki.algorithms.sort;

import com.arki.algorithms.common.ArrayUtil;
import com.arki.algorithms.common.FileUtil;
import com.arki.algorithms.common.Logger;
import com.arki.algorithms.common.TestDataUtil;
import org.junit.Test;

import java.io.File;

public class PriorityQueueTest {

    @Test
    public void testIndexMinPriorityQueue() {
        //Read input data streams into arrays
        Integer[][] streams = new Integer[][]{
                TestDataUtil.readIntegerArrayFromFile(new File("DataToSort" + FileUtil.fileSeparator + "RandomInt_[0,100]x100.txt")),
                TestDataUtil.readIntegerArrayFromFile(new File("DataToSort" + FileUtil.fileSeparator + "RandomInt_[0,100]x125.txt")),
                TestDataUtil.readIntegerArrayFromFile(new File("DataToSort" + FileUtil.fileSeparator + "RandomInt_[0,100]x150.txt"))};
        //Sort each array
        int streamCount = streams.length;
        for (int i = 0; i < streamCount; i++) {
            ArrayQuickSort.sort(streams[i]);
            Logger.info(ArrayUtil.transferArrayToString(streams[i]));
        }
        int[] indexRecord = new int[streamCount];


        IndexMinPriorityQueue<Integer> pq = new IndexMinPriorityQueue<>(streamCount);
        Integer[] container = new Integer[streams[0].length + streams[1].length + streams[2].length];

        //Initialize the priority queue.
        for (int i = 0; i < streamCount; i++) {
            if (indexRecord[i] < streams[i].length) {
                pq.insert(i, streams[i][indexRecord[i]]);
                indexRecord[i] = ++indexRecord[i];
            }
        }

        //Combine the sorted streams into one sorted stream.
        int count = 0;
        int reachEndCount = 0;
        while (true) {
            container[count++] = pq.min();
            int delMin = pq.delMin();
            if (indexRecord[delMin] < streams[delMin].length) {
                pq.insert(delMin, streams[delMin][indexRecord[delMin]]);
                indexRecord[delMin] = ++indexRecord[delMin];
            } else {
                reachEndCount++;
            }
            if (reachEndCount==streamCount) break;
            Logger.info("Result:{}", ArrayUtil.transferArrayToString(container));
        }
        System.out.println();
        Logger.info("Result:{}", ArrayUtil.transferArrayToString(container));

    }

    @Test
    public void testMaxPriorityQueue() {
        Integer[] a = TestDataUtil.readIntegerArrayFromFile(new File("DataToSort" + FileUtil.fileSeparator + "RandomInt_[1,100]x30.txt"));
        MaxPriorityQueue<Integer> pq = new MaxPriorityQueue<>(a);
        Logger.info("Max priority queue is well structured? [{}]", pq.wellStructured());
        if (pq.wellStructured()) {
            Logger.info("The    largest value = [{}]", pq.deleteMax());
            Logger.info("Second largest value = [{}]",pq.max());
        }
    }
}
