package ins;

import java.util.ArrayList;
import java.util.Collections;


class RowTranspose{
    String plainText, cipherText, key;
    String temp[];
    ArrayList<String> temp1;
    int keySize = 0;
    public RowTranspose(String key, String plainText){
        this.plainText = plainText;
        this.key = key;
        this.keySize = key.length();
        temp = new String[keySize];
        temp1 = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Character.toString(key.charAt(i));
        }
        encrypt();
        
    }
    void encrypt(){
        cipherText = "";
        int i,m = 0;
        int l = (int)Math.ceil(plainText.length() /(float) keySize) * keySize;
        for (int j = plainText.length(); j < (int)l; j++) {
            plainText += "x";
        }
        for (i = 0; i < plainText.length(); i++) {
            temp[i % keySize] += Character.toString(plainText.charAt(i));
        }
        for(String x : temp){
            temp1.add(x);
        }
        
        Collections.sort(temp1);
        
        temp1.forEach((x) -> {
            cipherText += x.substring(1);
        });
    }
    
}

public class INS_P10 {
    public static void main(String[] args) {
        String key = "4312567";
        String pT = "attackpostponeduntiltwoam";
        RowTranspose r1 = new RowTranspose(key,pT);
        RowTranspose r2 = new RowTranspose(key, r1.cipherText);
        System.out.println("Round 2 cipher:"+r2.cipherText);
    }
}
