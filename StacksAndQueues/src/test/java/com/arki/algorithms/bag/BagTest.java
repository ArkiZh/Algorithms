package com.arki.algorithms.bag;

import com.arki.algorithms.bag.impl.ArrayBag;
import com.arki.algorithms.bag.impl.LinkedBag;
import com.arki.algorithms.common.Logger;
import org.junit.Test;

import java.util.Iterator;

public class BagTest {

    @Test
    public void testLinkedBag(){
        LinkedBag<Integer> linkedBag = new LinkedBag<Integer>();
        for (int i = 0; i < 10; i++) {
            linkedBag.add(i);
        }
        Iterator<Integer> iterator = linkedBag.iterator();
        while (iterator.hasNext()){
            Logger.info("Linked bag traversal: " + iterator.next());
        }
    }

    @Test
    public void testArrayBag(){
        ArrayBag<Integer> arrayBag = new ArrayBag<Integer>();
        for (int i = 0; i < 15; i++) {
            arrayBag.add(i);
        }
        Iterator<Integer> iterator = arrayBag.iterator();
        while (iterator.hasNext()) {
            Logger.info("Array-based bag traversal: " + iterator.next());
        }
    }
}
