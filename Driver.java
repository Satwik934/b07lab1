import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial();
        System.out.println("p1(0) = " + p1.evaluate(0));

        double[] d1 = { 3, 4, -1 };
        int[] f1 = { 0, 1, 2 };
        Polynomial p2 = new Polynomial(d1, f1);
        System.out.println("p2(2) = " + p2.evaluate(2));

        double[] d22 = { 1, 0, -3, 2 };
        int[] f22 = { 0, 1, 2, 3 };
        Polynomial p3 = new Polynomial(d22, f22);

        double[] d23 = { 1, 0, 1, 1 };
        int[] f23 = { 0, 1, 2, 3 };
        Polynomial p4 = new Polynomial(d23, f23);

        Polynomial sum1 = p2.add(p3);
        System.out.println("sum1(1) = " + sum1.evaluate(1));

	Polynomial sum2 = p3.add(p4);
        System.out.println("sum2(1) = " + sum2.evaluate(1));

        double tstroot = 1;
        if (sum1.hasRoot(tstroot)) {  
            System.out.println(tstroot + " is a root of equation");
        } else {
            System.out.println(tstroot + " is not a root of equation");
        }

        Polynomial prd1 = p2.multiply(p3);
        System.out.println("prd1(0) = " + prd1.evaluate(0));

	Polynomial prd2 = p2.multiply(p4);
        System.out.println("prd2(1) = " + prd2.evaluate(1));
	
	double tstroot2 = 2;
        if (prd1.hasRoot(tstroot2)) {  
            System.out.println(tstroot + " is a root of equation");
        } else {
            System.out.println(tstroot + " is not a root of equation");
        }

        File file2 = new File("polynomial.txt");
        if (file2.exists()) {
            try {
                Polynomial pfile1 = new Polynomial(file2);
                System.out.println("pfile1(3) = " + pfile1.evaluate(3));
            } catch (IOException e) {
                System.err.println("An error occurred while reading from the file: " + e.getMessage());
            }
        } else {
            System.out.println("file polynomial.txt is not present.");
        }

        try {
            sum1.saveToFile("addition_out.txt");
            System.out.println("polynomial sum has saved to addition_out.txt");
        } catch (IOException e1) {
            System.err.println("An error occurred : " + e1.getMessage());
        }
    }
}
