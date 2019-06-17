/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class AppositionPrepositionsTable {
    static public ArrayList<String> tP=new ArrayList<String>();
    public static String regEx;

    public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
        tP=new ArrayList<String>();
       
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        regEx="";
        while((line=br.readLine())!=null){
            tP.add(line);
            regEx+=line+"|";
        }
        regEx=regEx.substring(0,regEx.length()-1);
        br.close();
    }
    
    public static boolean contains(String word){
        return tP.contains(word);
    }
    
    public static String getRegularExpression(){
        return regEx;
        
       
    }
}
