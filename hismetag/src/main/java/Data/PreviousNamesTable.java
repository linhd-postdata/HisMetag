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
public class PreviousNamesTable {
    static public Hashtable<String,String> tID=new Hashtable<String,String>();
    public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
        tID=new Hashtable<String,String>();
       
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine())!=null){
            tID.put(line, line);
        }
        br.close();
    }
    
    public static boolean contains(String word){
        return tID.containsKey(word);
    }
}
