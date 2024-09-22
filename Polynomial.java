public class Polynomial {
    private double[] coeffi;

    public Polynomial() {
        this.coeffi = new double[]{0};	
    }

    public Polynomial(double[] coeff) {
        coeffi = coeff;
    }

    public Polynomial add(Polynomial coeff2) {
        int num1 = coeffi.length;
        int num2 = coeff2.coeffi.length;
        int greatestnum = Math.max(num1, num2);
        
        double[] resultantarr = new double[greatestnum];
        
        for (int i = 0; i < greatestnum; i++) {
            if (i < Math.min(num1, num2)) {
                resultantarr[i] = coeffi[i] + coeff2.coeffi[i];
            } else if (i >= num2) {
                resultantarr[i] = coeffi[i];
            } else if (i >= num1) {
                resultantarr[i] = coeff2.coeffi[i];
            }
        }
        return new Polynomial(resultantarr);
    }

    public double evaluate(double x1) {
        double rel = 0;
        for (int i = 0; i < coeffi.length; i++) {
	    double rw = Math.pow(x1, i);
            rel = rel + (coeffi[i] * rw);
        }
        return rel;
    }
     
    public boolean hasRoot(double x2) {
        return evaluate(x2) == 0;
    }
}
