package ins;

import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */

class Caeser{
    
//a-97
//z-122
//A-65
//Z-90
    
    String plainText="";
    String cipherText="";
    int key;
    Caeser(){
        
    }
    Caeser(String plainText,int key){
        this.plainText = plainText;
        this.key = key;
        encrypt();
    }
    void encrypt(){
        for(int i = 0; i < plainText.length() ; i++){
            char p = plainText.charAt(i);
            if(p >= 'A' && p <= 'Z'){
                 cipherText += (char)(((p - 64) + key) % 27 + 64);
            }
            else if (p >= 'a' && p <= 'z'){
                 cipherText += (char)(((p - 96) + key) % 27 + 96);
            }
            else{
                 cipherText += p;
            } 
        }
    }
    
}


public class INS_P2 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter String:");
        String input = in.nextLine();
        System.out.println("Enter key:");
        int key = in.nextInt();
        Caeser c = new Caeser(input,key);
        System.out.println("Cipher:"+c.cipherText);

    }
    
}
