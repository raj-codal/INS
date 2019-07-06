//monoalphabetic substitution cipher

package ins;

import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */
class MonoAlphabetic {

//a-97
//z-122
//A-65
//Z-90
    String plainText = "";
    String cipherText = "";
    String key = "";

    public MonoAlphabetic() {

    }

    public MonoAlphabetic(String key) {
        this.key = key;
    }

    public MonoAlphabetic(String plainText, String key) {
        this.key = key.toLowerCase();
        this.plainText = plainText;
        encrypt();
    }

    void encrypt(String plainText) {
        this.plainText = plainText;
        encrypt();
    }

    void encrypt() {
        for (int i = 0; i < plainText.length(); i++) {
            char p = plainText.charAt(i);
            if (p >= 'A' && p <= 'Z') {
                cipherText += Character.toUpperCase(key.charAt((int) p - 65));
            } else if (p >= 'a' && p <= 'z') {
                cipherText += Character.toLowerCase(key.charAt((int) p - 97));
            } else {
                cipherText += p;
            }
        }
    }

    String decrypt(String cipherText) {
        String pT = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = cipherText.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                pT += Character.toUpperCase((char) (key.indexOf(Character.toLowerCase(c)) + 65));
            } else if (c >= 'a' && c <= 'z') {
                pT += Character.toLowerCase((char) (key.indexOf(c) + 97));
            } else {
                pT += c;
            }
        }
        return pT;
    }
}

public class INS_P3 {

    public static void main(String[] args) {
        /*
            example: 
            key = qwertyuiopasdfghjklzxcvbnm
            plain text = Hello World
            cipher text = Itssg Vgksr
         */

        Scanner in = new Scanner(System.in);
        System.out.println("ENTER KEY");
        String key = in.nextLine();
        System.out.println("ENTER PLAIN TEXT");
        String plainText = in.nextLine();
        MonoAlphabetic m = new MonoAlphabetic(plainText, key);
        System.out.println("CIPHER:" + m.cipherText);
        System.out.println("DECIPHER:" + m.decrypt(m.cipherText));
    }
}
