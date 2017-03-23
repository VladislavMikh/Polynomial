import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/** Tests
 * Created by HP on 09.03.2017.
 */
public class Tests {

    @Test
    public void testGetCoeff() {
        Polynomial input = new Polynomial(new int[] {-11,6,-4,3,0,9}); // 9x^5 + 3x^3 - 4x^2 + 6x - 11
        assertEquals(9, input.getCoeff(5));
        assertEquals(-11, input.getCoeff(0));
        assertEquals(0,input.getCoeff(4));
        assertEquals(0,input.getCoeff(20));
    }

    @Test
    public void testSetCoeff() {
        Polynomial input = new Polynomial(new int[] {-11,6,-4,3,0,9}); // 9x^5 + 3x^3 - 4x^2 + 6x - 11
        input.setCoeff(4,7);
        assertEquals(input.getCoeff(4),7); // 9x^5 + 7x^4 + 3x^3 - 4x^2 + 6x - 11
    }

    @Test
    public void testPlus() {
        Polynomial inputOne = new Polynomial(new int[] {-6,4,0,-3}),           // -3x^3 + 4x - 6
                   inputTwo = new Polynomial(new int[] {0,0,11,3,7,9}),       // 9x^5 + 7x^4 + 3x^3 + 11x^2
                   expected = new Polynomial(new int[] {-6,4,11,0,7,9});
        assertEquals(expected, inputOne.plus(inputTwo));
    }

    @Test
    public void testUnaryMinus() {
        Polynomial input = new Polynomial(new int[] {-6,4,0,-3}),    // -3x^3 + 4x - 6
                   expected = new Polynomial(new int[] {6,-4,0,3}); // 3x^3 - 4x + 6
        assertEquals(expected, input.unaryMinus());
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

//    @Test
//    public void testDiv() {
//        Polynomial dividend = new Polynomial(new int[] {1,0,2}),
//                   divider = new Polynomial(new int[] {0,1}),
//                   expected = new Polynomial(new int[] {0,2});
//        assertEquals(expected,dividend.div(divider));
//    }

    //@Test
    //public void testMod() {
        //Polynomial dividend = new Polynomial(new int[] {1,0,2}),
        // divider = new Polynomial(new int[] {0,1}),
        //expected = new Polynomial(new int[] {1});
        //assertEquals(expected,dividend.mod(divider));
    //}

    @Test
    public void testEvaluate() {
        Polynomial inputOne = new Polynomial(new int[] {1,0,2} ); // 2x^2 + 1
        int result = 9;
        assertEquals(result,inputOne.evaluate(2));
    }

    @Test
    public void testEquals() {
        Polynomial inputOne = new Polynomial(new int[] {1,0,2} ), // 2x^2 + 1
                   inputTwo = new Polynomial(new int[] {1,0,2} ); // 2x^2 + 1
        assertEquals(true,inputOne.equals(inputTwo));
    }

    @Test
    public void testToString() {
        Polynomial inputOne = new Polynomial(new int[] {1,0,2} ); // 2x^2 + 1
        assertEquals("2x^2+1x^0",inputOne.toString());
    }
}
