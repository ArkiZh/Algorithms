/**
 * Test array of which the capacity can auto adjust with the amount of elements.
 */
public class AutoExtendArray {
    private Object[] a = new Object[1];

    private int size = 0;

    public static void main(String[] args) {
        AutoExtendArray o = new AutoExtendArray();
        for (int i = 0; i < 1000; i++) {
            o.autoExtend();
            o.a[i] = i;
            o.size++;
        }
        for (int i = o.size - 1; i >= 0; i--) {
            o.a[i] = null;
            o.size--;
            o.autoShrink();
        }
    }

    private void autoExtend() {
        if (this.size == this.a.length) {
            System.out.println("Auto extend: size " + this.size + "   capacity " + this.a.length);
            Object[] temp = new Object[this.a.length * 2];
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.a[i];
            }
            this.a = temp;
        }
    }

    private void autoShrink() {
        if (this.size == this.a.length / 3) {
            System.out.println("Auto shrink: size " + this.size + "   capacity " + this.a.length);
            Object[] temp = new Object[this.size];
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.a[i];
            }
            this.a = temp;
        }
    }
}