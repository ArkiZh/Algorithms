package com.arki.algorithms.stack;

import com.arki.algorithms.common.Logger;
import com.arki.algorithms.queue.impl.LinkedQueue;
import com.arki.algorithms.stack.impl.LinkedStack;
import org.junit.Test;

import java.util.Iterator;

public class StackTest {

    /**
     * Method Description: Test linked stack.
     */
    @Test
    public void testLinkedStack(){
        LinkedStack<Integer> linkedStack = new LinkedStack<Integer>();
        for (int i = 0; i < 10; i++) {
            linkedStack.push(i);
        }
        Iterator<Integer> iterator = linkedStack.iterator();
        while (iterator.hasNext()) {
            Logger.info("Traversal: " + iterator.next());
        }
        while (linkedStack.size() != 0) {
            Integer pop = linkedStack.pop();
            Logger.info("Pop: " + pop);
        }
    }
}
