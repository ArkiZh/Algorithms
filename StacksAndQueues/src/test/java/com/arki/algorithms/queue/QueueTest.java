package com.arki.algorithms.queue;

import com.arki.algorithms.common.Logger;
import com.arki.algorithms.queue.impl.LinkedQueue;
import org.junit.Test;

public class QueueTest {
    @Test
    public void testLinkedQueue(){
        LinkedQueue<Integer> linkedQueue = new LinkedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            linkedQueue.enqueue(i);
        }
        while (!linkedQueue.isEmpty()) {
            Integer dequeue = linkedQueue.dequeue();
            Logger.info(dequeue.toString());
        }
    }
}
