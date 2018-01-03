package com.arki.algorithms.queue;

import com.arki.algorithms.common.Logger;
import com.arki.algorithms.queue.impl.ArrayQueue;
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
            Logger.info("Linked queue traversal: " + iterator.next());
        }
        while (!linkedQueue.isEmpty()) {
            Integer dequeue = linkedQueue.dequeue();
            Logger.info("Linked queue dequeue: " + dequeue);
        }
    }

    @Test
    public void testArrayQueue(){
        ArrayQueue<Integer> arrayQueue = new ArrayQueue<Integer>();
        for (int i = 0; i < 33; i++) {
            arrayQueue.enqueue(i);
        }
        Iterator<Integer> iterator = arrayQueue.iterator();
        while (iterator.hasNext()) {
            Logger.info("Array-based queue traversal: " + iterator.next());
        }
        while (!arrayQueue.isEmpty()) {
            Integer dequeue = arrayQueue.dequeue();
            Logger.info("Array-based queue dequeue: " + dequeue);
        }
    }
}
