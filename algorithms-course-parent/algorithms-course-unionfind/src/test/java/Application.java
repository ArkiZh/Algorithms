import com.arki.algorithms.unionfind.AbstractUnionFind;
import com.arki.algorithms.unionfind.impl.CompressedQuickUnionUF;
import com.arki.algorithms.unionfind.impl.QuickFindUF;
import com.arki.algorithms.unionfind.impl.QuickUnionUF;

import java.util.Arrays;
import java.util.Scanner;

/**
 *  The entry.
 */
public class Application {

    public static void main(String[] args) {
        //AbstractUnionFind unionFind = new QuickFindUF();
        AbstractUnionFind unionFind = new CompressedQuickUnionUF();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type the scale of array:");
        int n = scanner.nextInt();
        unionFind.initFlagsSimply(n);
        System.out.println(Arrays.toString(unionFind.getFlags()));
        while (true) {
            System.out.println("Input p:");
            int p = scanner.nextInt();
            System.out.println("Input q:");
            int q = scanner.nextInt();
            unionFind.union(p, q);
            //System.out.println(Arrays.toString(unionFind.getFlags()) + "\tAll connected? " + checkAllConnected(unionFind));
            System.out.println(Arrays.toString(unionFind.getFlags()));
        }
    }

    private  static boolean checkAllConnected(AbstractUnionFind unionFind){
        int[] flags = unionFind.getFlags();
        for (int i = 0; i + 1 < flags.length; i++) {
            if (!unionFind.connected(i, i + 1)) {
                return false;
            }
        }
        return true;
    }
}
