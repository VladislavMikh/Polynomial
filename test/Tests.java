import org.junit.Test;
import static org.junit.Assert.*;

//Тесты от старого варианта, просто скопированный файл, переделать под новый
public class Tests {

    @Test
    public void testPlus() {
        Polynomial inputOne = new Polynomial(new int[] {-6,4,0,-3}),           // -3x^3 + 4x - 6
                   inputTwo = new Polynomial(new int[] {0,0,11,3,7,9}),       // 9x^5 + 7x^4 + 3x^3 + 11x^2
                   expected = new Polynomial(new int[] {-6,4,11,0,7,9});      // 9x^5 + 7x^4 + 11x^2 + 4x - 6
        assertEquals(expected, inputOne.plus(inputTwo));
    }

    @Test
    public void testMinus() {
        Polynomial 	minuend = new Polynomial(new int[] {-6,4,0,3}),           // 3x^3 + 4x - 6
                subtrahend = new Polynomial(new int[] {0,0,11,3,7,9}),       // 9x^5 + 7x^4 + 3x^3 + 11x^2
                    expected = new Polynomial(new int[] {-6,4,-11,0,-7,-9});
        assertEquals(expected, minuend.minus(subtrahend));
    }

    @Test
    public void testMultiply() {
        Polynomial inputOne = new Polynomial(new int[] {1,0,2}), // 2x^2 + 1
                   inputTwo = new Polynomial(new int[] {0,1}), // x
                   expected = new Polynomial(new int[] {0,1,0,2}); // 3x^3 + x
        assertEquals(expected,inputOne.multiply(inputTwo));
    }

    @Test
    public void testDiv() {
        Polynomial dividend = new Polynomial(new int[] {1,0,2}), // 2x^2 + 1
                   divider = new Polynomial(new int[] {0,1}), // x
                   expected = new Polynomial(new int[] {0,2});
        assertEquals(expected,dividend.div(divider));
    }

    @Test
    public void testMod() {
        Polynomial dividend = new Polynomial(new int[] {1,0,2}), // 2x^2 + 1
         divider = new Polynomial(new int[] {0,1}), // x
        expected = new Polynomial(new int[] {1});
        assertEquals(expected,dividend.mod(divider));
    }

    @Test
    public void testEvaluate() {
        Polynomial inputOne = new Polynomial(new int[] {1,0,2} ); // 2x^2 + 1
        int result = 9;
        assertEquals(result,inputOne.evaluate(2));
    }

    @Test
    public void testEquals() {
        int[] inputOne = {5, 4, 2, 8, 1};
        int[] inputTwo = {5, 4, 2, 8, 1};
        assertEquals(true, new Polynomial(inputOne).equals( new Polynomial(inputTwo) ));

        int[] inputAlpha = {2, 1, 3};
        int[] inputBeta ={2, 1, 0, 1, 2};
        assertEquals(true, new Polynomial(inputAlpha).equals( new Polynomial(inputBeta) ));
    }

    @Test
    public void testToString() {
        Polynomial inputOne = new Polynomial(new int[] {1,0,2} ); // 2x^2 + 1
        assertEquals("2x^2+1x^0",inputOne.toString());
    }
}
