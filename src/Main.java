import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by HP on 08.03.2017.
 * (Временный, пока нет тестов)
 */
public class Main {
    public static void main(String[] args) {
        int[] i = {1, 1};
        Polynomial a = new Polynomial(i, 0, 1);
        Polynomial b = new Polynomial(i, 0, 1);
        System.out.println(a.plus(b));
        System.out.println(a.minus(b));
        System.out.println(a.multiply(b));
        System.out.println(a.div(b));
    }
}
