package com.arki.algorithms.sort;

import com.arki.algorithms.common.ArrayUtil;
import com.arki.algorithms.common.Logger;
import org.junit.Test;

public class ArrayInsertionSortTest {

    @Test
    public void testSort(){
        Integer[] a = new Integer[15];
        for (int i = 0; i < 15; i++) {
            a[i] = 15 - i;
        }
        Logger.info("The array to sort is: {}", ArrayUtil.transferArrayToString(a));
        ArraySelectionSort.sort(a);
        Logger.info("Result is: {}", ArrayUtil.transferArrayToString(a));
        Logger.info("Is sorted? [{}]",ArraySelectionSort.isSorted(a));
    }

}
