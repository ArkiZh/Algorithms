package com.arki.algorithms.bag;

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
            Logger.info("Traversal: " + iterator.next());
        }
    }
}
