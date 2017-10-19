import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Accumulator {

    public static void main(String[] args) {
        int t = 100000;
        Accumulator accumulator = new Accumulator(t, 1.0);
        for (int i = 0; i < t; i++) {
            accumulator.addDataValue(StdRandom.uniform());
        }
        System.out.println(accumulator);
    }

    private double total;
    private int N;
    public Accumulator(int trails, double max){
        StdDraw.setXscale(0, trails+1);
        StdDraw.setYscale(0, max);
        StdDraw.setPenRadius(0.005);
    }

    public void addDataValue(double val){
        N++;
        total += val;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(N, val);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(N, total/N);
    }

    public double mean(){
        return total/N;
    }

    public String toString(){
        return "Mean ("+N+" values): "+String.format("%7.5f", mean());
    }
}
