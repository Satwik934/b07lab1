import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    private double[] coeffi;
    private int[] exponential;

    public Polynomial() {
        coeffi = new double[]{0};
        exponential = new int[]{0};
    }

    public Polynomial(double[] coeff2, int[] expo2) {
        coeffi = coeff2;
        exponential = expo2;
    }

    private void ressizesarr(int size11) {
        double[] sizedrecoeff = new double[size11];
        int[] sizedreexpo = new int[size11];

        System.arraycopy(coeffi, 0, sizedrecoeff, 0, size11);
        System.arraycopy(exponential, 0, sizedreexpo, 0, size11);

        coeffi = sizedrecoeff;
        exponential = sizedreexpo;
    }

    private String readPoyfile(File file1) throws IOException {
        Scanner scanner1 = new Scanner(file1);
        String polnystr = scanner1.nextLine();
        scanner1.close();
        return polnystr;
    }

    private void parspolystr(String polnystr) {
        int maxnumes = polnystr.length() / 2 + 1;
        coeffi = new double[maxnumes];
        exponential = new int[maxnumes];

        String[] numterms = polnystr.split("(?=\\+)|(?=\\-)");
        int numcount = 0;

        for (String term1 : numterms) {
            term1 = term1.trim();
            if (term1.contains("x")) {
                String[] part1 = term1.split("x\\^");
                double coeff23 = part1[0].isEmpty() || part1[0].equals("+") ? 1 :
                                 part1[0].equals("-") ? -1 : Double.parseDouble(part1[0]);
                int exp22er = part1.length > 1 ? Integer.parseInt(part1[1]) : 1;

                coeffi[numcount] = coeff23;
                exponential[numcount] = exp22er;
                numcount++;
            } else {
                coeffi[numcount] = Double.parseDouble(term1);
                exponential[numcount] = 0;
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
            if (exponential[per] == coeff2.exponential[qer]) {
                addition = coeffi[per] + coeff2.coeffi[qer];
                if (addition != 0) {
                    resultantCoeff[rer] = addition;
                    resultantExpo[rer] = exponential[per];
                    rer++;
                }
                per++;
                qer++;
            } else if (this.exponential[per] > coeff2.exponential[qer]) {
                resultantCoeff[rer] = coeffi[per];
                resultantExpo[rer] = exponential[per];
                rer++;
                per++;
            } else {
                resultantCoeff[rer] = coeff2.coeffi[qer];
                resultantExpo[rer] = coeff2.exponential[qer];
                rer++;
                qer++;
            }
        }

        while (per < numm1) {
            resultantCoeff[rer] = coeffi[per];
            resultantExpo[rer] = exponential[per];
            per++;
            rer++;
        }

        while (qer < numm2) {
            resultantCoeff[rer] = coeff2.coeffi[qer];
            resultantExpo[rer] = coeff2.exponential[qer];
            qer++;
            rer++;
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

    private int muleveryterm(double[] coeff1, int[] expo, Polynomial coeff3, double[] partcoeff, int[] partexpo, int ressize) {
        for (int j = 0; j < coeff3.coeffi.length; j++) {
            for (int i = 0; i < coeff1.length; i++) {
                double knewcoeff = coeff1[i] * coeff3.coeffi[j];
                int knewexp = expo[i] + coeff3.exponential[j];
                ressize = addcombterm(knewcoeff, knewexp, partcoeff, partexpo, ressize);
            }
        }
        return ressize;
    }

    public Polynomial multiply(Polynomial coeff3) {
        int nummax2 = coeffi.length * coeff3.coeffi.length;
        double[] partcoeff = new double[nummax2];
        int[] partexpo = new int[nummax2];
        int overallsize = 0;

        for (int i = 0; i < coeffi.length; i++) {
            overallsize = muleveryterm(coeffi, exponential, coeff3, partcoeff, partexpo, overallsize);
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
            double num3 = Math.pow(x1, exponential[i]);
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

            if (exponential[ij] == 0) {
                polystr.append(coeffi[ij]);
            } else {
                polystr.append(coeffi[ij]).append("x^").append(exponential[ij]);
            }
            ij++;
        }

        writr.write(polystr.toString());
        writr.close();
    }
}
