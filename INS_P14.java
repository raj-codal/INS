package ins;

import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author Raj Dhanani
 */

class Diffie{
    numbers globalPublicData;
    int x,y,k;
    Diffie(int n, int x){
        globalPublicData = new numbers(n);
        this.x = x;
    }
    void generatePublicKey(){
        BigInteger temp = new BigInteger(Integer.toString(globalPublicData.a));
        temp = temp.pow(x).mod(new BigInteger(Integer.toString(globalPublicData.n)));
        y = temp.intValue();
    }
    void generateKey(int publicKey){
        BigInteger temp = new BigInteger(Integer.toString(publicKey));
        temp = temp.pow(x).mod(new BigInteger(Integer.toString(globalPublicData.n)));
        k = temp.intValue();
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

public class INS_P14 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("ENTER Q:");
        int q = in.nextInt();
        int xa,xb;
        in.nextLine();
        System.out.println("ENTER PRIVATE KEY FOR USER A");
        xa = in.nextInt();
        in.nextLine();
        System.out.println("ENTER PRIVATE KEY FOR USER B");
        xb = in.nextInt();
        in.nextLine();
        Diffie a = new Diffie(q, xa);
        Diffie b = new Diffie(q, xb);
        a.generatePublicKey();
        b.generatePublicKey();
        a.generateKey(b.y);
        b.generateKey(a.y);
        System.out.println("USER-A KEY:"+a.k);
        System.out.println("USER-B KEY:"+b.k);
    }
}
