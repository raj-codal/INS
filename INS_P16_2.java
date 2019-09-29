package ins;

import java.util.*;
import java.math.*;

/**
 *
 * @author Raj Dhanani
 */

class Elgamal{
 
 numbers GlobalData;
 int p,z,b,k,r,m,s;
 int v1,v2;
 
 public Elgamal(int p, int z){
    this.p = p;
    this.z = z;
    GlobalData = new numbers(p);
    // System.out.println(GlobalData.n+" "+GlobalData.a);
    BigInteger temp = new BigInteger(Integer.toString(GlobalData.a));
    temp = temp.pow(z).mod(new BigInteger(Integer.toString(p)));
    b = temp.intValue(); 
 }
 
 void sign(int m,int k){
    this.k = k;
    this.m = m;
    BigInteger temp = new BigInteger(Integer.toString(GlobalData.a));
    temp = temp.pow(k).mod(new BigInteger(Integer.toString(p)));
    r = temp.intValue();
    //  s ≡ k-1(m – zr) (mod p – 1).
    temp = new BigInteger(Integer.toString(k));
    temp = temp.modInverse(new BigInteger(Integer.toString(p-1)));
    temp = temp.multiply(new BigInteger(Integer.toString(m - z*r)));
    temp = temp.mod(new BigInteger(Integer.toString(p-1)));
    s = temp.intValue();
 }
 
 boolean verify(numbers GlobalData, int b, int m, int r, int s){
    //  v1 ≡ βrrs (mod p) and v2 ≡ αm (mod p).
    BigInteger temp = new BigInteger(Integer.toString(b));
    temp = temp.pow(r);
    BigInteger temp1 = new BigInteger(Integer.toString(r));
    temp1 = temp1.pow(s);
    temp = temp.multiply(temp1).mod(new BigInteger(Integer.toString(GlobalData.n)));
    v1 = temp.intValue();
    
    temp = new BigInteger(Integer.toString(GlobalData.a));
    temp = temp.pow(m).mod(new BigInteger(Integer.toString(GlobalData.n)));
    v2 = temp.intValue();
    return (v1 == v2 % GlobalData.n) ? true : false ;
 }
    
}

class numbers{
    int n,a;
    
    int GCD(int a,int b){
    	int min = (a<b)?a:b;
    	for(int c = min ; c > 1; c--){
    		if(a%c == 0 && b%c == 0){
    			return c;
    		}
    	}
    	return 1;
    }
    
    boolean isPrime(int x){
    	boolean flag = true;
    	for(int i = 2 ;i < x; i++){
    		if(x % i == 0){
    			flag = false;
    		}
    	}
    	return flag;
    }

    public numbers(int prime) {
        try
        {
            if(isPrime(prime)){
                n = prime;
                a = findPrimitive(n);
            }
            else {
                throw new Exception("SELECT PRIME NUMBER ONLY");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    int power(int x, int y, int p) 
    { 
        int res = 1;
        x = x % p; 
        while (y > 0){ 
            if (y % 2 == 1){ 
                res = (res * x) % p; 
            } 
            y = y >> 1; // y = y/2 
            x = (x * x) % p; 
        } 
        return res; 
    }
    
    int findPrimitive(int n)  
    { 
        ArrayList<Integer> s = new ArrayList<Integer>(); 
        if (isPrime(n) == false){ 
            return -1; 
        }  
        int phi = n - 1; 
        int phi1 = phi;
        while (phi1 % 2 == 0){ 
            s.add(2); 
            phi1 = phi1 / 2; 
        }
        for (int i = 3; i <= Math.sqrt(phi1); i = i + 2){ 
            while (phi1 % i == 0)  
            { 
                s.add(i); 
                phi1 = phi1 / i; 
            } 
        } 
        if (phi1 > 2)  
        { 
            s.add(phi1); 
        }
        for (int r = 2; r <= phi; r++)  
        {
            boolean flag = false; 
            for (Integer a : s)  
            {
                if (power(r, phi / (a), n) == 1){ 
                    flag = true; 
                    break; 
                } 
            }
            if (flag == false){ 
                return r; 
            } 
        }  
        return -1; 
    }
}

public class INS_P16_2
{
    /*
    
        ENTER VALUE OF P,Z,M,K:                                                                                                                
        71 16 15 31                                                                                                                            
        r = 11                                                                                                                                 
        s = 49                                                                                                                                 
        ------                                                                                                                                 
        verification:                                                                                                                          
        v1 = 23                                                                                                                                
        v2 = 23                                                                                                                                
        verification : true

    */
    
    
	public static void main(String[] args) {
	    System.out.println("ENTER VALUE OF P,Z,M,K:");
	    Scanner in = new Scanner(System.in);
	    
	    Elgamal e = new Elgamal(in.nextInt(),in.nextInt());
	    e.sign(in.nextInt(),in.nextInt());
	    System.out.println("r = " + e.r);
	    System.out.println("s = " + e.s);
	    System.out.println("------\n"+"verification:");
	    boolean v = e.verify(e.GlobalData, e.b, e.m, e.r, e.s);
	    System.out.println("v1 = " + e.v1);
	    System.out.println("v2 = " + e.v2);
	    System.out.println("verification : " + v);
	}
}
