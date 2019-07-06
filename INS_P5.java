package ins;

import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */
class MatrixIndex {

    int x, y;

    public MatrixIndex(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

class PlayFair {

    String plainText;
    String cipherText;
    String key;
    char matrix[][] = new char[5][5];

    public PlayFair(String plainText, String key) {
        this.plainText = plainText;
        this.key = removeSpaces(key).toLowerCase();
        String a2z = "abcdefghiklmnopqrstuvwxyz";
        String temp = eleminateRedundancy(this.key);
        temp = eleminateRedundancy(temp + a2z);
        initMatrix(temp);
        encrypt();
    }

    String removeSpaces(String s) {
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                temp += s.charAt(i);
            }
        }
        return temp;
    }

    String eleminateRedundancy(String s) {
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);
            if (temp.indexOf(x) == -1) {
                temp += x;
            }
        }
        return temp;
    }

    void initMatrix(String s) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = s.charAt(i * 5 + j);
            }
        }
    }

    String addJunk(String plainText) {
        String temp = plainText;
        for (int i = 0; i < temp.length(); i += 2) {
            char x1, x2;
            x1 = temp.charAt(i);
            if (i + 1 == temp.length()) {
                if (temp.length() % 2 != 0) {
                    temp = temp + "" + (x1 == 'x' ? 'y' : 'x');
                }
            } else {
                x2 = temp.charAt(i + 1);
                if (x1 == x2) {
                    temp = temp.substring(0, i + 1) + (x1 == 'x' ? 'y' : 'x') + temp.substring(i + 1);
                }
            }
        }
        return temp;
    }

    MatrixIndex indexOf(char x) {
        if (x == 'j') {
            x = 'i';
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == x) {
                    return new MatrixIndex(i, j);
                }
            }
        }
        return null;
    }

    void encrypt() {
        String temp = addJunk(removeSpaces(plainText)).toLowerCase();
        MatrixIndex a, b;
        char p1, p2, c1, c2;
        cipherText = "";
        for (int i = 0; i < temp.length(); i += 2) {
            p1 = temp.charAt(i);//same row cange column
            p2 = temp.charAt(i + 1);
            a = indexOf(p1);
            b = indexOf(p2);
            if (a.x == b.x) {
                c1 = matrix[a.x][(a.y + 1) % 5];
                c2 = matrix[b.x][(b.y + 1) % 5];
            } else if (a.y == b.y) {
                c1 = matrix[(a.x + 1) % 5][a.y];
                c2 = matrix[(b.x + 1) % 5][b.y];
            } else {
                c1 = matrix[a.x][b.y];
                c2 = matrix[b.x][a.y];
            }
            cipherText += c1 + "" + c2;
        }
    }

}

public class INS_PLAYFAIRCIPHER {

    public static void main(String[] args) {
        /*
            Example:
                ENTER PLAIN TEXT AND KEY (LINE SEPERATED):
                committee
                textile
                CIPHER:aqkirdeiexxi
         */
        System.out.println("ENTER PLAIN TEXT AND KEY (LINE SEPERATED):");
        Scanner in = new Scanner(System.in);
        PlayFair p = new PlayFair(in.nextLine(), in.nextLine());
        System.out.println("CIPHER:" + p.cipherText);
    }
}
