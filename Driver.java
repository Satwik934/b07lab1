import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Test the no-argument constructor
        Polynomial p1 = new Polynomial();
        System.out.println("p1(0) = " + p1.evaluate(0)); // Expected: 0.0

        // Test constructor with coefficients and exponents arrays
        double[] d1 = { 3, 4, -1 }; // Represents 3 + 4x + (-1)x^2
        int[] f1 = { 0, 1, 2 };
        Polynomial p2 = new Polynomial(d1, f1);
        System.out.println("p2(2) = " + p2.evaluate(2)); // Expected: 11

        // Test constructor with another set of coefficients and exponents
        double[] d22 = { 1, 0, -3, 2 }; // Represents 1 - 3x^2 + 2x^3
        int[] f22 = { 0, 1, 2, 3 };
        Polynomial p3 = new Polynomial(d22, f22);

        // Test addition of two polynomials
        Polynomial sum1 = p2.add(p3);
        System.out.println("sum1(1) = " + sum1.evaluate(1)); // Expected: Evaluation of the sum at x = 1

        // Test root check for the sum polynomial
        double tstroot = 1;
        if (sum1.hasRoot(tstroot)) {  // Change from 'sum' to 'sum1'
            System.out.println(tstroot + " is a root of sum");
        } else {
            System.out.println(tstroot + " is not a root of sum");
        }

        // Test multiplication of two polynomials
        Polynomial prd1 = p2.multiply(p3);
        System.out.println("prd1(0) = " + prd1.evaluate(0)); // Expected: Evaluation of the product at x = 0

        // Test constructor from file
        File file2 = new File("polynomial.txt");
        if (file2.exists()) {
            try {
                Polynomial pfile1 = new Polynomial(file2);
                System.out.println("pfile1(3) = " + pfile1.evaluate(3)); // Example evaluation
            } catch (IOException e) {
                System.err.println("An error occurred while reading from the file: " + e.getMessage());
            }
        } else {
            System.out.println("file polynomial.txt is not present.");
        }

        // Test saving a polynomial to a file
        try {
            sum1.saveToFile("sum_output.txt");
            System.out.println("polynomial sum has saved to sum_output.txt");
        } catch (IOException e1) {
            System.err.println("An error occurred : " + e1.getMessage());
        }
    }
}
