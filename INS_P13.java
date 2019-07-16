package ins;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */
class numbers {

    public static boolean isPrime(int x) {
        boolean flag = true;
        for (int i = 2; i < x; i++) {
            if (x % i == 0) {
                flag = false;
            }
        }
        return flag;
    }

    public static int nthPrime(int n) {
        int j = 0, prev = 2;
        for (int count = 1; count <= n; count++) {
            while (true) {
                if (isPrime(prev)) {
                    j = prev;
                    prev++;
                    break;
                }
                prev++;
            }
        }
        return j;
    }

    public static int GCD(int a, int b) {
        int min = (a < b) ? a : b;
        for (int c = min; c > 1; c--) {
            if (a % c == 0 && b % c == 0) {
                return c;
            }
        }
        return 1;
    }

}

class RSA {

    int p, q;
    BigInteger e, d, m, c;
    BigInteger n, ETF;

    RSA(int p, int q) throws Exception {
        if (!numbers.isPrime(q) && !numbers.isPrime(q)) {
            System.out.println("p and q should be prime!!");
            throw new Exception("p and q should be prime!!");
        }
        this.p = p;
        this.q = q;
        n = new BigInteger(Integer.toString(p));
        n = n.multiply(new BigInteger(Integer.toString(q)));
        ETF = new BigInteger(Integer.toString(p - 1));
        ETF = ETF.multiply(new BigInteger(Integer.toString(q - 1)));
        generateE();
        generateD();
    }

    void generateE() {
        int etf = ETF.intValue();
        for (int i = 2; i < etf; i++) {
            if (numbers.GCD(i, etf) == 1) {
                e = new BigInteger(Integer.toString(i));
                return;
            }
        }
        e = new BigInteger("1");
    }

    void generateD() {
        d = e.modInverse(ETF);
    }

    int encrypt(int x) {
        m = new BigInteger(Integer.toString(x));
        c = (m.pow(e.intValue())).mod(n);
        return c.intValue();
    }

    int decrypt(int x) {
        m = new BigInteger(Integer.toString(x));
        c = (m.pow(d.intValue())).mod(n);
        return c.intValue();
    }
}

public class INS_P13 {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("ENTER 2 PRIMES:");
        int p = in.nextInt(), q = in.nextInt();
        RSA r = new RSA(p, q);
        System.out.println("ENTER PLAIN TEXT INT:");
        int x = in.nextInt();
        int y = r.encrypt(x);
        System.out.println("CIPHER TEXT INT:" + y);
        int d = r.decrypt(y);
        System.out.println("DECIPHERED TEXT INT:" + d);
    }
    
}
