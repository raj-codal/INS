
//simple naive cryptanalysis of monoalphabetic substitution cipher...
//based on probability of the occurence of alphabets in English language...

package monoalphabeticcracker;

import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */

class alphabetStats{
    static char alphabets[] = new char[]{
    'e','t','a','o','i','n','s','h','r','d','l','c','u','m','w','f','g','y','p','b','v','k','j','x','q','z'
    };
}


class cryptAnalysisMonoAlpha{
    int probability[] = new int[26];
    char alphabets[] = new char[26];
    String cipherText="";
    String plainText="";

    public cryptAnalysisMonoAlpha(String cipherText) {
        this.cipherText = cipherText;
        for(int i = 0 ; i < 26 ; i++){
            probability[i] = 0;
        }
        String temp[] = "abcdefghijklmnopqrstuvwxyz".split("");
        for(int j = 0 ; j < 26 ; j++){
            alphabets[j] = temp[j].charAt(0);
        }
        calculateProbability();
        sortAlphabets();
        decrypt();
    }
    
    void calculateProbability(){
        char temp[] = cipherText.toCharArray();
        for(char x : temp){
            if(x != ' ') probability[(int)x - 97]++;
        }
    }
    
    void sortAlphabets(){
        for(int i = 0 ; i < 25 ; i++){
            for(int j = 0 ; j < 25 - i ; j++){
                if(probability[j] < probability[j+1]){
                    int temp = probability[j];
                    probability[j] = probability[j+1];
                    probability[j+1] = temp;
                    char temp1 = alphabets[j];
                    alphabets[j] = alphabets[j+1];
                    alphabets[j+1] = temp1;
                }
            }
        }
        
    }
    
    int indexOf(char x){
        for(int i = 0 ; i < 26 ; i++){
            if(alphabets[i] == x){
                return i;
            }
        }
        return -1;
    }
    
    void decrypt(){
        char c,p;
        for(int i = 0 ; i < cipherText.length() ; i++){
            c = cipherText.charAt(i);
            if(c != ' ') {
                p = alphabetStats.alphabets[indexOf(c)];
                plainText += p;
            }
            else{
                plainText += c;
            }
        }
        
        for(int i = 0 ; i < 26 ; i++){
            System.out.println(alphabets[i] + "->" +probability[i] + "->" + alphabetStats.alphabets[indexOf(alphabets[i])]);
        }
        
    }
    
}


public class driver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in); 
        System.out.println("ENTER CIPHER TEXT:");
        String test = in.nextLine();
        test = test.toLowerCase();
        cryptAnalysisMonoAlpha x = new cryptAnalysisMonoAlpha(test);
        System.out.println(x.cipherText+">>"+x.plainText);
    }
    
}