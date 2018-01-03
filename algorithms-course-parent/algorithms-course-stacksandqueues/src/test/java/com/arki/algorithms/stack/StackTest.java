package com.arki.algorithms.stack;

import com.arki.algorithms.common.Logger;
import com.arki.algorithms.stack.impl.ArrayStack;
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
            Logger.info("Linked stack traversal: " + iterator.next());
        }
        while (linkedStack.size() != 0) {
            Integer pop = linkedStack.pop();
            Logger.info("Linked stack pop: " + pop);
        }
    }

    /**
     * Method Description: Test array-based stack.
     */
    @Test
    public void testArrayStack(){
        ArrayStack<String> arrayStack = new ArrayStack<String>();
        for (int i = '0'; i < 'F'; i++) {
            arrayStack.push(Character.toString((char)i));
        }
        Iterator<String> iterator = arrayStack.iterator();
        while (iterator.hasNext()) {
            Logger.info("Array-based stack traversal: " + iterator.next());
        }
        while (!arrayStack.isEmpty()) {
            Logger.info("Array-based stack pop: " + arrayStack.pop());
        }
    }
}
