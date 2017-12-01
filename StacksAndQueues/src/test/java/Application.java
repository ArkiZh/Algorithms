import com.arki.algorithms.stack.impl.LinkedStack;

public class Application {
    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        while (stack.size() > 0) {
            Integer pop = stack.pop();
            System.out.println(pop);
        }
    }
}
