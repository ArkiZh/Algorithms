package com.arki.algorithms.sort;

import java.lang.reflect.Array;

/**
 * The scenario:<br/>
 * Combine multi well-sorted input streams into one well-sorted output stream.
 * @param <Item>
 */
public class IndexMinPriorityQueue <Item extends Comparable<Item>>{

    private int[] priorityQueueArray; //index: the priority queue sequence. element: the stream order.

    private Item[] candidateArray; // index: the stream order. element: element selected from the stream.

    private int[] locationArray; // index: the stream order. element: the priority queue sequence.

    private int size; // size of the priority queue.

    private int streamCount; // count of streams.

    public IndexMinPriorityQueue(int streamCount) {
        priorityQueueArray = new int[streamCount + 1];
        candidateArray = (Item[]) Array.newInstance(Comparable.class, streamCount);
        locationArray = new int[streamCount];
        for (int i = 0; i < streamCount; i++) {
            locationArray[i] = -1;
        }
        this.streamCount = streamCount;
    }

    /**
     * Insert the element item of the k'th stream into priority queue.
     * @param k
     * @param item
     */
    public void insert(int k, Item item) {
        //Extend size of priority array
        size++;
        //Add steam order to the last element of priority array
        priorityQueueArray[size] = k;
        //Set stream element to candidate array
        candidateArray[k] = item;
        //Mark location of this stream in the priority array
        locationArray[k] = size;
        //Rebuild the priority array
        swim(k);
    }

    /**
     * Change the old element of the <code>k</code>'th stream which is already a member of priority queue to <code>item</code>.
     * @param k the stream order
     * @param item the new element to replace the old one in the priority queue
     */
    public void change(int k, Item item) {
        if (!contains(k)) return;
        if (greater(item, candidateArray[k])) sink(k);
        else swim(k);
    }

    /**
     * Judge whether the <code>k</code>'th stream is a member of priority queue.
     * @param k the stream order
     * @return
     */
    public boolean contains(int k) {
        return locationArray[k] != -1;
    }

    /**
     * Delete the current element of the <code>k</code>'th stream in the priority queue.
     * @param k the stream order
     */
    public void delete(int k) {
        if(!contains(k))  return;
        int lastIndex = priorityQueueArray[size--];
        int location = locationArray[k];
        priorityQueueArray[location] = lastIndex;
        locationArray[lastIndex] = location;
        locationArray[k] = -1;
        Item old = candidateArray[k];
        candidateArray[k] = null;
        if(greater(candidateArray[lastIndex],candidateArray[k])) sink(k);
        else swim(k);
    }

    /**
     * Get the minimum element of the priority queue.
     * @return
     */
    public Item min() {
        if(isEmpty()) return null;
        else return candidateArray[priorityQueueArray[1]];
    }

    /**
     * Get the stream order whose element is the minimum in the priority queue.
     * @return
     */
    public int minIndex() {
        if(isEmpty())return -1;
        else return priorityQueueArray[1];
    }

    /**
     * Delete the minimum element of the priority, and return the stream order.
     * @return the stream order of the deleted element
     */
    public int delMin() {
        if(isEmpty()) return -1;
        // Get the min stream order
        int minIndex = priorityQueueArray[1];
        // Get the stream order of the last priority queue sequence
        int lastIndex = priorityQueueArray[size];
        // Exchange these 2 elements,
        exchange(minIndex, lastIndex);
        // then empty the min element and decrease the size.
        locationArray[minIndex] = -1;
        candidateArray[minIndex] = null;
        priorityQueueArray[size] = -1;
        size--;

        // Have the need to rebuild priority queue when size >= 2
        if (size >= 2) {
            sink(lastIndex);
        }
        return minIndex;
    }

    /**
     * Judge whether the priority queue is empty.
     * @return true -empty.<br/>
     *          false else.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The size of priority queue.
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     * Compare a to b
     * @param a
     * @param b
     * @return true if a>b, false if a<=b
     */
    private boolean greater(Item a, Item b) {
        return a.compareTo(b) > 0;
    }

    /**
     * Rebuild the priority queue by swimming.
     * @param k the stream order
     */
    private void swim(int k) {
        //Record current sequence of priority array
        int kLocation = locationArray[k];
        while (kLocation >= 2) {
            // !Must not be >=1.
            // If ==1:
            // then fLocation==0 and priorityQueueArray[fLocation]==null,
            // cause NullPointerException.
            int fLocation = kLocation / 2;
            if (greater(candidateArray[priorityQueueArray[fLocation]], candidateArray[k])) {
                exchange(k, priorityQueueArray[fLocation]);
                kLocation = fLocation;
            }else break;
        }
    }

    /**
     * Rebuild the priority queue by sinking
     * @param k the stream order
     */
    private void sink(int k) {
        int kLocation = locationArray[k];
        while (kLocation * 2 <= size) {
            int minChildLocation = kLocation * 2;
            if (kLocation * 2 + 1 <= size && greater(candidateArray[priorityQueueArray[kLocation * 2]], candidateArray[priorityQueueArray[kLocation * 2 + 1]])) {
                minChildLocation++;
            }
            if (greater(candidateArray[k], candidateArray[priorityQueueArray[minChildLocation]])) {
                exchange(k, priorityQueueArray[minChildLocation]);
                kLocation = minChildLocation;
            } else break;

        }
    }

    private void exchange(int indexA, int indexB) {
        int locationA = locationArray[indexA];
        int locationB = locationArray[indexB];
        locationArray[indexA] = locationB;
        locationArray[indexB] = locationA;
        priorityQueueArray[locationA] = indexB;
        priorityQueueArray[locationB] = indexA;
    }
}
