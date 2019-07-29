package ins;

import java.util.Scanner;

class DES {

    String plainText, cipherText, key;
    String plainBinary, keyBinary;
    int sbox[][][];

    public DES(String plainText, String key) {
        this.sbox = new int[][][]{{
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        }, {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        }, {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
        }, {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        }, {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
        }, {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
        }, {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
        }, {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
        }};
        this.plainText = plainText;
        this.key = key;
        generateBinaries();
        encrypt();
    }

    String hexToBinary(char c) {
        String t;
        if (c >= 'a' && c <= 'z') {
            t = Integer.toBinaryString(c - 'a' + 10);
        } else if (c >= 'A' && c <= 'Z') {
            t = Integer.toBinaryString(c - 'A' + 10);
        } else if (c >= '0' && c <= '9') {
            t = Integer.toBinaryString(c - '0');
        } else {
            t = null;
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

    void generateBinaries() {
        plainBinary = "";
        keyBinary = "";
        for (char c : plainText.toCharArray()) {
            plainBinary += hexToBinary(c);
        }
        for (char c : key.toCharArray()) {
            keyBinary += hexToBinary(c);
        }
    }

    void encrypt() {
        System.out.println("plain text:" + plainText);
        System.out.println("key:" + key);
        String p = InitialPermutation(plainBinary);
        System.out.println("initial permutation:" + binaryToHex(p));
        String k = PC1(keyBinary);
        System.out.println("after PC-1 key:" + binaryToHex(k));
        String _56bitskey = k;
        System.out.println("PLAIN TEXT\t\t48 BITS KEY\t56 BITS KEY(LCS)");
        for (int i = 0; i < 16; i++) { // round function
            _56bitskey = leftCircularShift(_56bitskey, i);
            String _48bitskey = PC2(_56bitskey);
            p = roundFunction(p, _48bitskey, i);
            System.out.println(binaryToHex(p) + "\t" + binaryToHex(_48bitskey) + "\t" + binaryToHex(_56bitskey));
        }
        cipherText = binaryToHex(FinalPermutation(p));
    }

    String InitialPermutation(String binary) {
        String p = "";
        int table[] = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
        for (int i = 0; i < table.length; i++) {
            p += binary.charAt(table[i] - 1);
        }
        return p;
    }

    String PC1(String binary) {
        String p = "";
        int table[] = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
        for (int i = 0; i < table.length; i++) {
            p += binary.charAt(table[i] - 1);
        }
        return p;
    }

    String leftCircularShift(String binary, int round) {
        int table[] = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        String l, r;
        l = binary.substring(0, binary.length() / 2);
        r = binary.substring(binary.length() / 2);
        return lcs(l, table[round]) + lcs(r, table[round]);
    }

    String lcs(String binary, int i) {
        String t = binary.substring(i) + binary.substring(0, i);
        return t;
    }

    String PC2(String binary) {
        String p = "";
        int table[] = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
        for (int i = 0; i < table.length; i++) {
            p += binary.charAt(table[i] - 1);
        }
        return p;
    }

    String roundFunction(String string, String _48bitsKey, int round) {
        String l, r;
        l = string.substring(0, string.length() / 2);
        r = string.substring(string.length() / 2);
        String expandedR = ExpansionPermutation(r);
        String _48bit = XOR(_48bitsKey, expandedR);
        String _32bitSbox = "";
        for (int i = 0; i < 8; i++) {
            int row = Integer.parseInt((_48bit.charAt(i * 6) + "" + _48bit.charAt(i * 6 + 5)), 2);
            int col = Integer.parseInt(_48bit.substring(i * 6 + 1, i * 6 + 5), 2);
            int n = sbox[i][row][col];
            _32bitSbox += hexToBinary(Integer.toHexString(n).charAt(0));
        }
        String pbox = Pbox(_32bitSbox);
        l = XOR(pbox, l);
        if (round == 15) {
            return l + r;
        } else {
            return r + l;
        }
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

    String ExpansionPermutation(String binary) {
        String p = "";
        int table[] = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};
        for (int i = 0; i < table.length; i++) {
            p += binary.charAt(table[i] - 1);
        }
        return p;
    }

    String Pbox(String binary) {
        String p = "";
        int table[] = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};
        for (int i = 0; i < table.length; i++) {
            p += binary.charAt(table[i] - 1);
        }
        return p;
    }

    String FinalPermutation(String binary) {
        String p = "";
        int table[] = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};
        for (int i = 0; i < table.length; i++) {
            p += binary.charAt(table[i] - 1);
        }
        return p;
    }
}

public class INS_P11 {

    /*
        ENTER PLAIN TEXT AND KEY (LINE SEPERATED 64 bits HEX!)
        ab2221abcd132536
        0000160420116013
        plain text:ab2221abcd132536
        key:0000160420116013
        initial permutation:10a0d07d19cf19ab
        after PC-1 key:004050a840c004
        PLAIN TEXT		48 BITS KEY	56 BITS KEY(LCS)
        19cf19abfd624784	500480812140	0080a140818009
        fd624784e94c594f	000860450201	01014281030012
        e94c594f01707d8b	806820120048	04050a040c0048
        01707d8b8584931f	802700009104	10142800300121
        8584931f735e9c82	4016010024a0	4050a000c00484
        735e9c8289e95c30	419040680801	01428013001210
        89e95c30f6cbf23f	00c0c202401a	050a004c004840
        f6cbf23fe52ab5b4	304102051100	14280100012103
        e52ab5b497849ca1	061a000c0210	28500200024206
        97849ca1bedd4f9e	0a3040514040	a1400800090818
        bedd4f9e293414ad	084448008008	85002020242060
        293414adac960169	404108803404	140080a0908180
        ac960169a03d275e	0089012802a0	50020282420600
        a03d275ee6b2d338	012803104803	40080a19081800
        e6b2d338951fc447	212480060010	00202854206002
        9f3e2a2c951fc447	00100c800092	004050a840c004
        CIPHER:e276fb75f0150ac8
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("ENTER PLAIN TEXT AND KEY (LINE SEPERATED 64 bits HEX!)");
        DES d = new DES(in.nextLine(), in.nextLine());
        System.out.println("CIPHER:" + d.cipherText);
    }
}
