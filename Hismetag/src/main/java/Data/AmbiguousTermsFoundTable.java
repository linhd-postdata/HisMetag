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
public class AmbiguousTermsFoundTable {
    public static Hashtable<String,AmbiguousFoundData> ambiguoustable=new Hashtable<String,AmbiguousFoundData>();
    public static String path;
public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
    	
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
         

         // Lectura del fichero
        while((line=br.readLine())!=null){
            String[] arr_date=line.split(";");
           
            AmbiguousFoundData ambiguosdata=new AmbiguousFoundData();
            
            
            
         }
        br.close();
        
            
         }
      
      
     
   


public static boolean contains(String word){
    if (ambiguoustable.containsKey(word)) return true;
    
return false;
}

public static void modifyFrequency(String word){
    
    int frequency=ambiguoustable.get(word).frequency;
    
    AmbiguousFoundData nuevo=new AmbiguousFoundData(word, frequency++,ambiguoustable.get(word).type);
    ambiguoustable.replace(word, ambiguoustable.get(word), nuevo);
}


public static void updateFile(){
    try{
        File newfile=new File(path+"dataFiles/new-ambiguous-terms-found.txt");
        BufferedWriter file = new BufferedWriter(new FileWriter(newfile)); 
        Hashtable<String,AmbiguousFoundData> t=ambiguoustable;
        Vector v = new Vector(ambiguoustable.keySet());
        
        Collections.sort(v);
        HashSet<String> hashSet = new HashSet<String>(v);
		v.clear();
		v.addAll(hashSet);
        Collections.sort(v);
        Iterator it = v.iterator();
        while (it.hasNext()) {
            String element =  (String)it.next();
            
            AmbiguousFoundData md=t.get(element);
            file.write(element+';'+md.frequency+';'+md.type);
            file.write("\n");
            
            file.write("\n");
        }
        
         file.close();
            
            
    }catch(Exception e){;}
}
}

