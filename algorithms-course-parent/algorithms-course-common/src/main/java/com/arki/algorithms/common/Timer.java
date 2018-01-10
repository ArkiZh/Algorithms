package com.arki.algorithms.common;

import java.util.ArrayList;
import java.util.List;

public class Timer {

    private List<Long> records = new ArrayList<>();

    private boolean pause = true;

    /**
     * Start the timer.
     */
    public void start(){
        if (pause) {
            pause = false;
            records.add(System.currentTimeMillis());
        }
    }

    /**
     * Stop the timer.
     */
    public void stop(){
        if(!pause){
            pause = true;
            records.add(System.currentTimeMillis());
        }
    }

    /**
     * Get the sum of all valid time periods.
     * @return Sum of time in second unit.
     */
    public double elapsedTime(){
        int temp = pause ? records.size() : records.size() - 1;
        long time = 0;
        for (int i = 0; i < temp; i++) {
            time += i % 2 == 0 ? -records.get(i) : records.get(i);
        }
        return ((double) time) / 1000;
    }

    /**
     * Get all the valid time periods ordered by record time.
     * @return Time periods in second unit.
     */
    public List<Double> elapsedTimeSnippets(){
        List<Double> snippets = new ArrayList<>();
        double temp = 0;
        for (int i = 0; i < records.size(); i++) {
            double t = ((double) records.get(i)) / 1000;
            if (i % 2 == 0) temp = t;
            else snippets.add(t - temp);
        }
        return snippets;
    }

}
