/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.*;

import MedievalTextLexer.Lexer;
import WordProcessing.*;

import java.io.*;
/**
 *
 * @author M Luisa Díez Platas
 */
public class VerbsTable {
    public static Hashtable<String,Verbs> verbtable=new Hashtable<String,Verbs>();
    
public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
    
      
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
        

         // Lectura del fichero
         
         while((line=br.readLine())!=null){
        	
             Verbs verb=new Verbs();
            String[] arr_date=line.split(",");
                verb.verb=arr_date[0];
            	verb.current=arr_date[1];
                verb.complement=arr_date[2];
                verb.mode=arr_date[3];
                verb.type=arr_date[4];
               String[] lematiz=WordTransformations.lemmVerbs(arr_date[0]).split("-");
                verb.raiz=lematiz[0];
               verb.desinencia=lematiz[1];
               //System.out.println("los verbos "+arr_date[0]+" "+verb.raiz+"-"+verb.desinencia);
                verbtable.put(arr_date[0], verb);
                
               //System.out.println("ver los verbos de esto "+verb.verb);
            
         }
         
         //System.out.println("el tamano de la tabla de verbos"+verbtable.size());
         br.close();
      }
      
     


public static Verbs get(String word){
   // System.out.println("estoy buscando el verbo que se representa "+word);
   // System.out.println("tiene la clave"+verbtable.containsKey(word));
    Verbs v=null;
    if (verbtable.containsKey(word)){
    	v=verbtable.get(word);
    	//System.out.println ("estoy en esta parte del verbo "+v.current);
    };
    //System.out.println("estoy buscando el verbo que se representa "+v);
    if (v==null){
    	WordTransformations vtrans=new WordTransformations();
    	vtrans.wordTransformationProcessing(word);
    	for (int i=0;i<vtrans.transformedWords.aproxList.size();i++){
    		v=verbtable.get(vtrans.transformedWords.aproxList.get(i).word);
    		if (v!=null){
    		//System.out.println("estoy buscando el verbo que se representa "+v.verb);
                }
    		if (v!=null) {
               //     System.out.println("HE ENTRADo  POR TRansformaciones "+word+"  "+v.verb);
                    if (WordProcessing.LevenshteinDistance.computeLevenshteinDistance(word,v.verb)<2){
                 
                    return v;
                    }
                }
    		
    	}
    	
    }
    //System.out.println("El verbo encontrado essssss"+v.current);
    return verbtable.get(word);
}


public static Verbs contains(String word){
    
    
    Verbs element=verbtable.get(word);
    
return element;

}

public static void putNewVerb(String verb){
    verbtable.put(verb,new Verbs());
}

public static ArrayList<String> getComplements(String verb){
    String[] verbl=get(verb).complement.split("-");
   // System.out.println("en la funcion complementos "+verb+" "+verbl.length+" "+get(verb).complement);
    ArrayList<String> lVerb=new ArrayList<String>();
    for (int i=0; i<verbl.length;i++)
        lVerb.add(verbl[i]);
        
    return lVerb;
}

public static void escribir(){
    Enumeration e = verbtable.keys();
Object clave;
Object valor;
while( e.hasMoreElements() ){
  clave = e.nextElement();
  valor = verbtable.get( clave ).complement;
  System.out.println( "Clave : " + clave + " - Valor : " + valor );
}
}
}

