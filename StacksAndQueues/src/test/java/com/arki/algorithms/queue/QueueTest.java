package com.arki.algorithms.queue;

import com.arki.algorithms.common.Logger;
import com.arki.algorithms.queue.impl.LinkedQueue;
import org.junit.Test;

import java.util.Iterator;

public class QueueTest {
    @Test
    public void testLinkedQueue(){
        LinkedQueue<Integer> linkedQueue = new LinkedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            linkedQueue.enqueue(i);
        }
        Iterator<Integer> iterator = linkedQueue.iterator();
        while (iterator.hasNext()) {
            Logger.info("Traversal: " + iterator.next());
        }
        while (!linkedQueue.isEmpty()) {
            Integer dequeue = linkedQueue.dequeue();
            Logger.info("Dequeue: " + dequeue);
        }
    }
}
