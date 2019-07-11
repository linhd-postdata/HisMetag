/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Hashtable;
import static WordProcessing.WordTransformations.transformedWords;

/**
 *
 * @author M Luisa Díez Platas
 */
public class ContextPlaceNames {
    static public Hashtable<String,String> tPR=new Hashtable<String,String>();
    public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
        tPR=new Hashtable<String,String>();
        
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine())!=null){
            
           
            tPR.put(line, line);
        }
        br.close();
}
static public boolean contains(String word){
    
   //System.out.println("+**************+"+word);
   
    return tPR.containsKey(word);
}

static public void escribir(){
    
    Enumeration e = tPR.keys();
          Object clave;
          Object valor;
            while( e.hasMoreElements() ){
                clave = e.nextElement();
           
                String word=(String)clave;
               
              
            }
}
}
