package ins;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */

final class SHA1{
    String inputText,hash,binary;  
    String H0 = "67452301";
    String H1 = "EFCDAB89";
    String H2 = "98BADCFE";
    String H3 = "10325476";
    String H4 = "C3D2E1F0";
    String A;
    String B;
    String C;
    String D;
    String E;
    String w[] = new String[80];
    
    String hexToBinary(char c) {
        String t;
        if (c >= 'a' && c <= 'z') {
            t = Integer.toBinaryString(c - 'a' + 10);
        } else if (c >= 'A' && c <= 'Z') {
            t = Integer.toBinaryString(c - 'A' + 10);
        } else if (c >= '0' && c <= '9') {
            t = Integer.toBinaryString(c - '0');
        } else {
            t = "";
        }

        for (int i = t.length(); i < 4; i++) {
            t = "0" + t;
        }
        return t;
    }

    String binaryToHex(String b) {
        String hex = "";
        for (int i = 0; i < b.length(); i += 4) {
            hex += Integer.toHexString(Integer.parseInt(b.substring(i, i + 4), 2));
        }
        return hex;
    }
    
    String XOR(String _1, String _2) {
        int l = _1.length();
        String xor = "";
        for (int i = 0; i < l; i++) {
            char __1, __2;
            __1 = _1.charAt(i);
            __2 = _2.charAt(i);
            if (__1 == __2) {
                xor += "0";
            } else {
                xor += "1";
            }
        }
        return xor;
    }
    
    String AND(String _1, String _2) {
        int l = _1.length();
        String and = "";
        for (int i = 0; i < l; i++) {
            char __1, __2;
            __1 = _1.charAt(i);
            __2 = _2.charAt(i);
            if (__1 == '1' && __2 == '1') {
                and += "1";
            } else {
                and += "0";
            }
        }
        return and;
    }
    
    String OR(String _1, String _2) {
        int l = _1.length();
        String or = "";
        for (int i = 0; i < l; i++) {
            char __1, __2;
            __1 = _1.charAt(i);
            __2 = _2.charAt(i);
            if (__1 == '1' || __2 == '1') {
                or += "1";
            } else {
                or += "0";
            }
        }
        return or;
    }
    
    String NOT(String _1) {
        int l = _1.length();
        String or = "";
        for (int i = 0; i < l; i++) {
            char __1;
            __1 = _1.charAt(i);
            if (__1 == '1') {
                or += "0";
            } else {
                or += "1";
            }
        }
        return or;
    }
    
    SHA1(String inputText){
        this.inputText = inputText;
        A = B = C = D = E = "";
        for (int i = 0; i < H0.length(); i++) {
            A += hexToBinary(H0.charAt(i));
        }
        for (int i = 0; i < H1.length(); i++) {
            B += hexToBinary(H1.charAt(i));
        }
        for (int i = 0; i < H2.length(); i++) {
            C += hexToBinary(H2.charAt(i));
        }
        for (int i = 0; i < H3.length(); i++) {
            D += hexToBinary(H3.charAt(i));
        }
        for (int i = 0; i < H4.length(); i++) {
            E += hexToBinary(H4.charAt(i));
        }
        H0 = new String(A);
        H1 = new String(B);
        H2 = new String(C);
        H3 = new String(D);
        H4 = new String(E);
        binary = generateBinary(inputText);
        binary = appendZeros(binary);
        generateHash();
    }
    
    void generateHash(){
        String f="",k="",temp="",chunks[];
        int count = binary.length() / 512;
//        System.out.println(count);
        chunks = new String[count];

        System.out.println(
                new BigInteger(binaryToHex(H0),16)+" "
                + new BigInteger(binaryToHex(H1),16)+" "
                + new BigInteger(binaryToHex(H2),16)+" "
                + new BigInteger(binaryToHex(H3),16)+" "
                + new BigInteger(binaryToHex(H4),16)+" "
        );
        
        for (int i = 0; i < count; i++) {
            chunks[i] = binary.substring(i*512 , i*512 + 512);
        }
        
        for(String chunk: chunks){
            
            A = H0;
            B = H1;
            C = H2;
            D = H3;
            E = H4;
            
            for (int i = 0; i < 16; i++) {
                w[i] = chunk.substring(i*32 , i*32 + 32);
            }

            for (int i = 16; i <= 79; i++) {
                w[i] = leftRotate(XOR(XOR(XOR(w[i-3],w[i-8]),w[i-14]),w[i-16]),1);
            }

            for (int i = 0; i < 80; i++) {
               if(0 <= i && i <= 19){
                    f = OR(AND(B,C),AND(NOT(B),D));
                    k = "5A827999";
                    String v = new String(k);
                    k = "";
                    for(char c : v.toCharArray()){
                        k += hexToBinary(c);
                    }
               } 
               else if(20 <= i && i <= 39){
                    f = XOR(XOR(B,C),D);
                    k = "6ED9EBA1";
                    String v = new String(k);
                    k = "";
                    for(char c : v.toCharArray()){
                        k += hexToBinary(c);
                    }
               } 
               else if(40 <= i && i <= 59){
                    f = OR(OR(AND(B,C),AND(B,D)),AND(C,D));
                    k = "8F1BBCDC";
                    String v = new String(k);
                    k = "";
                    for(char c : v.toCharArray()){
                        k += hexToBinary(c);
                    }
               } 
               else if(60 <= i && i <= 79){
                    f = XOR(XOR(B, C),D);
                    k = "CA62C1D6";
                    String v = new String(k);
                    k = "";
                    for(char c : v.toCharArray()){
                        k += hexToBinary(c);
                    }
               } 
                temp = modAdd32(modAdd32(modAdd32(modAdd32(leftRotate(A, 5), f),E),k),w[i]);
                E = D;
                D = C;
                C = leftRotate(B, 30);
                B = A;
                A = temp;

                System.out.println(i + ") "
                    + binaryToHex(A)+"\t"
                    + binaryToHex(B)+"\t"
                    + binaryToHex(C)+"\t"
                    + binaryToHex(D)+"\t"
                    + binaryToHex(E)+" "
                );

            }

            H0 = modAdd32(H0, A);
            H1 = modAdd32(H1, B);
            H2 = modAdd32(H2, C);
            H3 = modAdd32(H3, D);
            H4 = modAdd32(H4, E);
        }
        
        
          hash = binaryToHex(H0 + H1 + H2 + H3 +H4);
    }
    
    String modAdd32(String a, String b){
        BigInteger t1 = new BigInteger(a,2);
        BigInteger t2 = new BigInteger(b,2);
        BigInteger t3 = t1.add(t2);
        t3 = t3.mod(new BigInteger("4294967296"));
        String t =  t3.toString(2);
        int zeros = 32 - t.length();
        for (int i = 0; i < zeros; i++) {
            t = "0" + t;
        }
        return t;
    }
    
    String leftRotate(String s, int i){
        for (int j = 0; j < i; j++) {
            s = s.substring(1) + s.substring(0, 1);
        }
        return s;
    }
    
    String generateBinary(String input){
        String binary = "";
        for (int i = 0; i < input.length(); i++) {
            String temp = Integer.toBinaryString(input.charAt(i));
            int size = 8 - temp.length();
            for (int j = 0; j < size; j++) {
                temp = "0" + temp;
            }
            binary += temp;
        }
        return binary;
    }
    
    String appendZeros(String input){
        int l = input.length();
        int s = (l+64)%512;
        if(s != 0){
            s = 448 - (l%512)%448;
            input += "1";
            for (int i = 0; i < s - 1; i++) {
                input += "0";
            }
            String temp = Integer.toBinaryString(l);
            int size = 64 - temp.length();
            for (int j = 0; j < size; j++) {
                temp = "0" + temp;
            }
            input += temp;
        }
        return input;
    }
}

public class INS_P15 {
    public static void main(String[] args) {
        System.out.println("ENTER PLAIN TEXT:");
        Scanner in = new Scanner(System.in);
        SHA1 s1 = new SHA1(in.nextLine());        
        System.out.println("HASH:"+s1.hash);
    }
}
