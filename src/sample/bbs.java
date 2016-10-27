package sample;

/**
 * Created by NOTE on 26.10.2016.
 */
public class bbs {
    private double p, q, bloomNumber, seed, actual;
    public bbs(double p, double q, double seed) {
        this.p = p;
        this.q = q;
        this.bloomNumber = p*q;
        this.seed = seed;
        this.actual = seed;
    }

    public double getrandom() {
        double r = actual*actual%bloomNumber;
        actual = r;
        return r*r/bloomNumber %2;
    }

    private double gcd(double a, double b) {

        if(b == 0) return a;
        return gcd(b, a%b);

    }

    public double getirandom(int i) {
        double g = gcd(p, q);
        double l = (p-1)*(q-1)/g;

        double exp = 1;
        for(int j = 1; j <= i; ++j) exp = exp*2%l;

        double x0 = seed*seed;
        double r = x0;
        for(double j = 2; j<=exp; ++j ) {
            r = r*x0% bloomNumber;
        }
        return r/ bloomNumber;
    }
    public static boolean check_if_mutually_prime(int a, int b) {
        while (a != b){
            if (a > b) {
                a = a - b;
            }else {
                b = b - a;
            }
        }
        if(a == 1) {return true;}
        else return false;
    }
}
