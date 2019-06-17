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
public class DesiTable {
    static public Hashtable<String,String> tPR=new Hashtable<String,String>();
    static public ArrayList<String> desinencias=new ArrayList<String>();
    public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
        tPR=new Hashtable<String,String>();
       
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine())!=null){
        	
        	String[] arr=line.split(",");
            tPR.put(arr[0], arr[1]);
            desinencias.add(arr[0]);
        }
        
       
      
        
        br.close();
}
static public boolean contains(String word){
    return tPR.containsKey(word);
}

static public String getMode(String word){
	//System.out.println("lema "+tPR.get(word));
	
	return tPR.get(word);
}
}
