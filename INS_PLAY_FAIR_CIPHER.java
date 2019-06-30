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
        //adding junk x or y to make length of plain text even and also unpair two same chars which are together.
        String t = plainText;
        int junkCount = 0;
        for (int i = 0; i < plainText.length() - 1; i++) {
            if (plainText.charAt(i) == plainText.charAt(i + 1)) {
                if (plainText.charAt(i) == 'x') {
                    t = t.substring(0, i + junkCount + 1) + "y" + t.substring(i + 1 + junkCount);
                } else {
                    t = t.substring(0, i + junkCount + 1) + "x" + t.substring(i + 1 + junkCount);
                }
                junkCount++;
            }
        }
        if (t.length() % 2 == 0) {
            return t;
        } else {
            if (t.charAt(t.length() - 1) == 'x') {
                return t + "y";
            } else {
                return t + "x";
            }
        }
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
                c1 = matrix[(b.x + 1) % 5][b.y];
                c2 = matrix[(a.x + 1) % 5][a.y];
            } else {
                c1 = matrix[a.x][b.y];
                c2 = matrix[b.x][a.y];
            }
            cipherText += c1 + "" + c2;
        }
    }

}

public class INS_PLAY_FAIR_CIPHER {

    public static void main(String[] args) {
        /*
            Example:
                ENTER PLAIN TEXT AND KEY (LINE SEPERATED):
                raspberry
                apple
                CIPHER:qprlabswtw
         */
        System.out.println("ENTER PLAIN TEXT AND KEY (LINE SEPERATED):");
        Scanner in = new Scanner(System.in);
        PlayFair p = new PlayFair(in.nextLine(), in.nextLine());
        System.out.println("CIPHER:" + p.cipherText);
    }
}
