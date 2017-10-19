import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class Union {

    public static int[] ids;

    static {
        generateArr(StdIn.readInt());
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(ids));
        while (StdIn.hasNextLine()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if(!checkUnion(p,q)) union(p,q);
            System.out.println(Arrays.toString(ids));
        }

    }

    /**
     * Check whether p is connected to q.
     * @param p
     * @param q
     * @return
     */
    public static boolean checkUnion(int p, int q){
        return ids[p]==ids[q];
    }

    /**
     * Connect q to p. p is already in the component.
     * @param p
     * @param q
     */
    public static void  union(int p, int q){
        int pId = ids[p];
        int qId = ids[q];
        for (int i = 0; i < ids.length; i++) {
            if(ids[i]==pId) ids[i] = qId;
        }
    }

    public static  void generateArr(int n){
        ids = new int[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
    }
}
