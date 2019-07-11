/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.*;
import java.io.*;
/**
 *
 * @author M Luisa Díez Platas
 */
public class NewSaintNamesTable {
    static public Hashtable<String,String> tPR=new Hashtable<String,String>();
    private static String path;
public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
    	
        tPR=new Hashtable<String,String>();
       // File archivo = new File ("dataFiles/proper-names.txt");
       
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine())!=null){
           // System.out.println(line);
            //String[] arr=line.split(";");
            tPR.put(line, line);
        }
        br.close();    
    }
      
      
     
   static public boolean contains(String word){
    return tPR.containsKey(word);
}

static public void newName(String word,String document){
    ProperName newn=new ProperName(word,document,"validar");
    //tPR.put(word.toLowerCase(),newn);
}
static void updateFile(){
    try{
    File newfile=new File(path+"dataFiles/saint-names.txt");
        BufferedWriter file = new BufferedWriter(new FileWriter(newfile)); 
        Hashtable<String,String> t=tPR;
        Vector v = new Vector(tPR.keySet());
        
        Collections.sort(v);
        Iterator it = v.iterator();
        while (it.hasNext()) {
            String element =  (String)it.next();
            
            
           
            file.write(element);
            file.write("\n");
        }
        
         file.close();
            
        
        
    }catch (Exception e){;}
}
}

