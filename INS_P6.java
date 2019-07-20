package ins;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */
class Matrix {

    double[][] data = null;
    int rows = 0, cols = 0;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void set(double[][] data) {
        this.data = data;
    }

    void display() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println("");
        }
    }

    Matrix multiply(Matrix x) {
        double sum = 0;
        int m = rows, n = cols, p = x.rows, q = x.cols;
        Matrix temp = new Matrix(m, q);
        for (int c = 0; c < m; c++) {
            for (int d = 0; d < q; d++) {
                for (int k = 0; k < p; k++) {
                    sum += data[c][k] * x.data[k][d];
                }
                temp.data[c][d] = sum;
                sum = 0;
            }
        }
        return temp;
    }

    public static Matrix subMatrix(Matrix matrix, int exclude_row, int exclude_col) {
        Matrix result = new Matrix(matrix.rows - 1, matrix.cols - 1);

        for (int row = 0, p = 0; row < matrix.rows; ++row) {
            if (row != exclude_row - 1) {
                for (int col = 0, q = 0; col < matrix.cols; ++col) {
                    if (col != exclude_col - 1) {
                        result.data[p][q] = matrix.data[row][col];

                        ++q;
                    }
                }

                ++p;
            }
        }

        return result;
    }

    public double determinant() {
        if (rows != cols) {
            return Double.NaN;
        } else {
            return subDet(this);
        }
    }

    private double subDet(Matrix matrix) {
        switch (matrix.cols) {
            case 1:
                return matrix.data[0][0];
            case 2:
                return (matrix.data[0][0] * matrix.data[1][1]
                        - matrix.data[0][1] * matrix.data[1][0]);
            default:
                double result = 0.0;

                for (int col = 0; col < matrix.cols; ++col) {
                    Matrix sub = subMatrix(matrix, 1, col + 1);

                    result += (Math.pow(-1, 1 + col + 1)
                            * matrix.data[0][col] * subDet(sub));
                }
                return result;
        }
    }

    public Matrix inverse() {
        // not an ordinary matrix inverse, but by using Extended Euclidean algorithm...
        int f = -1;
        double de = determinant();
        BigInteger det = new BigInteger(Integer.toString((int) de));
        BigInteger d = det.mod(BigInteger.valueOf((long) 26));
        try {
            BigInteger BigF = d.modInverse(new BigInteger("26"));
            f = BigF.intValue();
        } catch (ArithmeticException e) {
            System.out.println("KEY NOT VALID!");
        }
        if (rows != cols || det.intValue() == 0.0 || f == -1) {
            System.out.println("MATRIX NOT INVERTIBLE");
            return null;
        } else {
            Matrix result = new Matrix(rows, cols);

            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    Matrix sub = subMatrix(this, row + 1, col + 1);

                    result.data[col][row] = (((1.0 * f
                            * Math.pow(-1, row + col)
                            * subDet(sub)) % 26) + 26) % 26;
                }
            }

            return result;
        }
    }
}

class Hill {

    String plainText, keyStr, cipherText = "";
    int dimension = 3;
    double data[][];
    Matrix key;

    Hill(String plainText, String keyStr, int dimension) throws Exception {
        this.plainText = plainText;
        this.keyStr = keyStr;
        this.dimension = dimension;
        key = new Matrix(dimension, dimension);
        data = new double[dimension][dimension];
        generateData();
    }

    Hill(String keyStr, int dimension, String cipherText) throws Exception {
        this.cipherText = cipherText;
        this.keyStr = keyStr;
        this.dimension = dimension;
        key = new Matrix(dimension, dimension);
        data = new double[dimension][dimension];
        generateData();
    }

    void generateData() throws Exception {
        keyStr = keyStr.toUpperCase();
        for (int i = 0; i < keyStr.length();) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    data[j][k] = (int) (keyStr.charAt(i++) - 65);
                }
            }
        }
        key.set(data);

        if (key.inverse() == null) {
            throw new Exception("THIS KEY CAN'T BE USED!! TEXT CAN'T BE DECRYPTED BY THIS KEY");
        }

    }

    void preprocessor() {
        char c;
        String temp = "";
        for (int i = 0; i < plainText.length(); i++) {
            c = plainText.charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                temp += c;
            }
        }
        plainText = temp;
    }

    void encrypt() {
        preprocessor();
        int r = plainText.length() % dimension;
        for (int i = 0; i < (dimension - r) % dimension; i++) {
            plainText += "x";
        }
        Matrix pT = new Matrix(dimension, 1);
        double temp[][] = new double[dimension][1];
        Matrix cT;
        for (int i = 0; i < plainText.length(); i += dimension) {
            for (int j = 0; j < dimension; j++) {
                char p = plainText.charAt(i + j);
                if (p >= 'A' && p < 'Z') {
                    temp[j][0] = (int) (p - 65);
                } else if (p >= 'a' && p <= 'z') {
                    temp[j][0] = (int) (p - 97);
                }
            }
            pT.set(temp);
            cT = key.multiply(pT);
            for (int j = 0; j < dimension; j++) {
                cipherText += (char) (cT.data[j][0] % 26 + 65);
            }
        }
    }

    void decrypt() {
        plainText = "";
        Matrix cT = new Matrix(dimension, 1);
        double temp[][] = new double[dimension][1];
        Matrix pT;
        Matrix keyInverse = key.inverse();

        for (int i = 0; i < cipherText.length(); i += dimension) {
            for (int j = 0; j < dimension; j++) {
                char c = cipherText.charAt(i + j);
                if (c >= 'A' && c < 'Z') {
                    temp[j][0] = (int) (c - 65);
                } else if (c >= 'a' && c <= 'z') {
                    temp[j][0] = (int) (c - 97);
                }
            }
            cT.set(temp);
            pT = keyInverse.multiply(cT);
            for (int j = 0; j < dimension; j++) {
                plainText += (char) (((int) pT.data[j][0]) % 26 + 65);
            }
        }
    }
}

public class INS_P6 {

    /*
        EXAMPLE:
        ENTER KEY DIMENSION:
        3
        ENTER KEY OF LENGTH 9:
        GYBNQKURP
        ENTER PLAIN TEXT:
        HELLO WORLD
        ENC:TFJIPIJSGVNQ
        DEC:HELLOWORLDXX
        I AM ADDING 'X' as a junk character... 
     */
    public static void main(String[] args) throws Exception {
        int n = 3;
        String key;
        String plainText;
        Scanner in = new Scanner(System.in);
        System.out.println("ENTER KEY DIMENSION:");
        n = in.nextInt();
        in.nextLine();
        System.out.println("ENTER KEY OF LENGTH " + (n * n) + ":");
        key = in.nextLine();
        System.out.println("ENTER PLAIN TEXT:");
        plainText = in.nextLine();
        Hill h = new Hill(plainText, key, n);
        h.encrypt();
        Hill h1 = new Hill(key, n, h.cipherText);
        System.out.println("ENC:" + h.cipherText);
        h1.decrypt();
        System.out.println("DEC:" + h1.plainText);
    }
}
