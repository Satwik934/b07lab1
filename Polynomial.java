import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    private double[] coeffi;
    private int[] exponential1;

    public Polynomial() {
        coeffi = new double[]{0};
        exponential1 = new int[]{0};
    }

    public Polynomial(double[] coeff2, int[] expo2) {
        coeffi = coeff2;
        exponential1 = expo2;
    }

    private void ressizesarr(int size11) {
        double[] sizedrecoeff = new double[size11];
        int[] sizedreexpo = new int[size11];

        for (int w = 0; w < size11; w++) {
        	sizedrecoeff[w] = coeffi[w];
        	sizedreexpo[w] = exponential1[w];
    	}	

        coeffi = sizedrecoeff;
        exponential1 = sizedreexpo;
    }

   private String readPoyfile(File file1) throws IOException {
        Scanner scanner1 = new Scanner(file1);
        String polnystr = scanner1.nextLine();
        scanner1.close();
        return polnystr;
    }

   private void parspolystr(String polnystr) {
    int maxnumes = (polnystr.length() / 2) + 1;
    coeffi = new double[maxnumes];
    exponential1 = new int[maxnumes];

    String[] numterms = polnystr.split("(?=\\+)|(?=\\-)");

    int numcount = 0;

    for (String term12 : numterms) {
        term12 = term12.trim();

        if (term12.contains("x")) {
            String[] part1 = term12.split("x");

            double coeff23;
            if (part1[0].isEmpty() || part1[0].equals("+")) {
                coeff23 = 1;
            } else if (part1[0].equals("-")) {
                coeff23 = -1;
            } else {
                coeff23 = Double.parseDouble(part1[0]);
            }

            int exp22er;
	    if (part1.length > 1) {
    		exp22er = Integer.parseInt(part1[1]);
	    } else {
    		exp22er = 1;
	    }

            coeffi[numcount] = coeff23;
            exponential1[numcount] = exp22er;
            numcount++;
            }
	 else {
            coeffi[numcount] = Double.parseDouble(term12);
            exponential1[numcount] = 0;
            numcount++;
        }
    }

    ressizesarr(numcount);
    }


    public Polynomial(File file1) throws IOException {
        String polnystr = readPoyfile(file1);
        parspolystr(polnystr);
    }

    public Polynomial add(Polynomial coeff2) {
        int numm1 = coeffi.length;
        int numm2 = coeff2.coeffi.length;
        int num33 = numm1 + numm2;    
        int greatnumcoeff = num33;

        double[] resultantCoeff = new double[greatnumcoeff];
        int[] resultantExpo = new int[greatnumcoeff];

        int per = 0; 
        int qer = 0; 
        int rer = 0;
        double addition = 0;

        while (per < numm1 && qer < numm2) {
            if (exponential1[per] == coeff2.exponential1[qer]) {
                addition = coeffi[per] + coeff2.coeffi[qer];
                if (addition != 0) {
                    resultantCoeff[rer] = addition;
                    resultantExpo[rer] = exponential1[per];
                    rer++;
                }
                per++;
                qer++;
            } else if (exponential1[per] > coeff2.exponential1[qer]) {
                resultantCoeff[rer] = coeffi[per];
                resultantExpo[rer] = exponential1[per];
                rer++;
                per++;
            } else {
                resultantCoeff[rer] = coeff2.coeffi[qer];
                resultantExpo[rer] = coeff2.exponential1[qer];
                rer++;
                qer++;
            }
        }

        while (per < numm1 || qer < numm2) {
    		if (per < numm1) {
        		resultantCoeff[rer] = coeffi[per];
        		resultantExpo[rer] = exponential1[per];
        		per++;
        		rer++;
    		}
    
    		if (qer < numm2) {
    	    		resultantCoeff[rer] = coeff2.coeffi[qer];
        		resultantExpo[rer] = coeff2.exponential1[qer];
        		qer++;
        		rer++;
    		}
	}

        double[] overallcoeff = new double[rer];
        int[] overallexpo = new int[rer];
        int tt = 0;
        while (tt < rer) {
            overallcoeff[tt] = resultantCoeff[tt];
            overallexpo[tt] = resultantExpo[tt];
            tt++;
        }
        return new Polynomial(overallcoeff, overallexpo);
    }

    private int addcombterm(double knewcoeff, int knewexp, double[] partcoeff, int[] partexpo, int ressize) {
    	for (int k2 = 0; k2 < ressize; k2++) {
    	    if (partexpo[k2] == knewexp) {
            	partcoeff[k2] += knewcoeff;
            	return ressize;
            }
        }
    	partcoeff[ressize] = knewcoeff;
    	partexpo[ressize] = knewexp;
    	return ressize + 1;
    }   

   

    public Polynomial multiply(Polynomial coeff3) {
    	int nummax2 = coeffi.length * coeff3.coeffi.length;
    	double[] partcoeff = new double[nummax2];
    	int[] partexpo = new int[nummax2];
    	int overallsize = 0;

    	for (int i = 0; i < coeffi.length; i++) {
        	for (int j = 0; j < coeff3.coeffi.length; j++) {
            		double knewcoeff = coeffi[i] * coeff3.coeffi[j];
            		int knewexp = exponential1[i] + coeff3.exponential1[j];
            		overallsize = addcombterm(knewcoeff, knewexp, partcoeff, partexpo, overallsize);
        	}
    	}

    	double[] overallcoefff = new double[overallsize];
    	int[] overallexpo2 = new int[overallsize];
    	for (int k1 = 0; k1 < overallsize; k1++) {
        	overallcoefff[k1] = partcoeff[k1];
        	overallexpo2[k1] = partexpo[k1];
    	}

    	return new Polynomial(overallcoefff, overallexpo2);
    }


  

    public double evaluate(double x1) {
        double result = 0;
        for (int i = 0; i < coeffi.length; i++) {
            double num3 = Math.pow(x1, exponential1[i]);
            result += coeffi[i] * num3;
        }
        return result;
    }

    public boolean hasRoot(double x2) {
        return evaluate(x2) == 0;
    }

    public void saveToFile(String filenm1) throws IOException {
        FileWriter writr = new FileWriter(filenm1);
        StringBuilder polystr = new StringBuilder();

        int ij = 0;
        while (ij < coeffi.length) {
            if (coeffi[ij] > 0 && ij != 0) {
                polystr.append("+");
            }

            if (exponential1[ij] == 0) {
                polystr.append(coeffi[ij]);
            } else {
                polystr.append(coeffi[ij]).append("x^").append(exponential1[ij]);
            }
            ij++;
        }

        writr.write(polystr.toString());
        writr.close();
    }
}
