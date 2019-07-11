/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

/**
 *
 * @author M Luisa Díez Platas
 */
public class CommonNamesTable {
    static public Hashtable<String,String> tPR=new Hashtable<String,String>();
    public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
        tPR=new Hashtable<String,String>();
        
        //System.out.println(path+"dataFiles/common-names.txt");
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine())!=null){
        	
        	//System.out.println("COMUNES "+line);
            tPR.put(line, line);
        }
        br.close();
}
static public boolean contains(String word){
    return tPR.containsKey(word);
}
}
