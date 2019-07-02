package ins;

import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */
class Caesar {

//a-97
//z-122
//A-65
//Z-90
    String plainText = "";
    String cipherText = "";
    int key;

    Caesar() {

    }

    Caesar(String plainText, int key) {
        this.plainText = plainText;
        this.key = key;
        encrypt();
    }

    void encrypt() {
        for (int i = 0; i < plainText.length(); i++) {
            char p = plainText.charAt(i);
            if (p >= 'A' && p <= 'Z') {
                cipherText += (char) (((p - 65) + key) % 26 + 65);
            } else if (p >= 'a' && p <= 'z') {
                cipherText += (char) (((p - 97) + key) % 26 + 97);
            } else {
                cipherText += p;
            }
        }
    }

    String decrypt(String cipherText, int key) {
        String pT = "";
        char e;
        for (int i = 0; i < cipherText.length(); i++) {
            char p = cipherText.charAt(i);
            if (p >= 'A' && p <= 'Z') {
                int temp = (int) (((p - 65) - key) % 26 + 65);
                if(temp < 65){
                    e = (char) ((int)'Z' - (65 - temp));
                    System.out.println((int)e);
                }
                else{
                    e = (char)temp;
                }
            } else if (p >= 'a' && p <= 'z') {
                int temp = (char) (((p - 97) - key) % 26 + 97);
                if(temp < 97){
                    e = (char) ((int)'z' - (97 - temp));
                    System.out.println((int)e);
                }
                else{
                    e = (char)temp;
                }
            } else {
                e = p;
            }
            pT += e;
        }
        return pT;
    }

}

public class INS_P2 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter String:");
        String input = in.nextLine();
        System.out.println("Enter key:");
        int key = in.nextInt();
        Caesar c = new Caesar(input, key);
        System.out.println("Cipher:" + c.cipherText);
        System.out.println("Decripted:" + c.decrypt(c.cipherText,c.key));

    }

}
