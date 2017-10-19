import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Draw {
    public static void main(String[] args) {
        /*int N= 100;
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0,N);
		StdDraw.setPenRadius(.01);
		for (int i = 0; i <= N; i++) {
			StdDraw.point(i, i);
			StdDraw.point(i, i*i);
			StdDraw.point(i, i*Math.log(i));
		}*/

        int N = 50;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0,N);
        double[] a = new double[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform(1, N);
        }
        Arrays.sort(a);
        for (int i = 0; i < N; i++) {
            double x = 1.0*(i+0.5);
            double y = 0;
            double rw = 0.25;
            double rh = a[i];
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }
}
