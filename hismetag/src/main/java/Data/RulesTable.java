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
import java.util.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class RulesTable {
    
    static public ArrayList<String> rules=new ArrayList<String>();
    public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
       
       
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine())!=null){
        	
        
            rules.add(line);
        }
        
       
      
        
        br.close();
}

}
