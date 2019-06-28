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
        this.key = key;
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
    }
}
