package ins;

import java.math.BigInteger;
import java.security.*;

/**
 *
 * @author Raj Dhanani
 */
class DSA{
    
    int x,y,r,s,k;
    String m;
    DSA(int x,String m) throws NoSuchAlgorithmException{
        this.m = m;
        this.x = x;
        BigInteger temp = new BigInteger(Integer.toString(GlobalData.g));
        temp = temp.pow(x).mod(new BigInteger(Integer.toString(GlobalData.p)));
        y = temp.intValue();
        k = (int) ((Math.random() * GlobalData.q * 10) % (GlobalData.q - 1));
//        k = 15;
        sign();
    }
    
    void sign() throws NoSuchAlgorithmException{
        BigInteger temp = new BigInteger(Integer.toString(GlobalData.g));
        temp = temp.pow(k)
                .mod(new BigInteger(Integer.toString(GlobalData.p)))
                .mod(new BigInteger(Integer.toString(GlobalData.q)));
        r = temp.intValue();
        temp = new BigInteger(Integer.toString(k));
        temp = temp.modInverse(new BigInteger(Integer.toString(GlobalData.q)));
        BigInteger t = new BigInteger(Integer.toString(x))
                        .multiply(new BigInteger(Integer.toString(r)))
                        .add(new BigInteger(hash(m),16));
        
        temp = temp.multiply(t)
                .mod(new BigInteger(Integer.toString(GlobalData.q)));
        s = temp.intValue();
    }
    
    boolean verify(int r_,int s_,String m_,int y) throws NoSuchAlgorithmException{
        boolean f = false;
        int u1,u2,v;
        BigInteger w = new BigInteger(Integer.toString(s_));
        w = w.modInverse(new BigInteger(Integer.toString(GlobalData.q)));
        BigInteger t = new BigInteger(hash(m_),16).multiply(w).mod(new BigInteger(Integer.toString(GlobalData.q)));
        u1 = t.intValue();
        t = new BigInteger(Integer.toString(r_)).multiply(w).mod(new BigInteger(Integer.toString(GlobalData.q)));
        u2 = t.intValue();
        t = new BigInteger(Integer.toString(GlobalData.g)).pow(u1);
        BigInteger t1 = new BigInteger(Integer.toString(y)).pow(u2);
        t = t.multiply(t1).mod(new BigInteger(Integer.toString(GlobalData.p))).mod(new BigInteger(Integer.toString(GlobalData.q)));
        v = t.intValue();
        if(v == r_){
            f = true;
        }
        return f;
    }
    
    String hash(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-1"); 
            byte[] messageDigest = md.digest(input.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
//            return "41";
            return hashtext;
    }
}

class GlobalData{
    static int p,q,h,g;
    static void configure(int p,int q,int h){
        GlobalData.p = p;
        GlobalData.q = q;
        GlobalData.h = h;
        BigInteger H = new BigInteger(Integer.toString(h));
        H = H.pow((p-1)/q).mod(new BigInteger(Integer.toString(p)));
        g = H.intValue();
    }
}

public class INS_P16_1 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        GlobalData.configure(283, 47, 0);
        DSA d = new DSA(24, "hello");
        System.out.println(d.verify(d.r, d.s, d.m, d.y));
    }
}
