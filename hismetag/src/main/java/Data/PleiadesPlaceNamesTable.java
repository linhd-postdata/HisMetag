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
public class PleiadesPlaceNamesTable {
    public static Hashtable<String,PleiadesData> pletable=new Hashtable<String,PleiadesData>();
    
public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
    
       
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
         

         // Lectura del fichero
         
         while((line=br.readLine())!=null){
            if (line.contains(";")){
            String[] arr_date=line.split(";");
           
            PleiadesData Plesites=new PleiadesData();
            if (arr_date.length==5){
	            Plesites.description=arr_date[2];
	            
	            Plesites.plename=arr_date[1];
	            Plesites.name=arr_date[0];
	            Plesites.lat=arr_date[3];
	            Plesites.longd=arr_date[4];
	            pletable.put(arr_date[0], Plesites);
            }
            else{
            	//System.out.println("esto al crear la tabla de Place"+line);
            }
         }
         }
         br.close();
      }
      
     
   

public static PleiadesData get(String word){
    if (pletable.contains(word))
    return pletable.get(word);
    else return null;
}

public static PleiadesData contains(String word){
    
    Enumeration<PleiadesData> enumeration = pletable.elements();
while (enumeration.hasMoreElements()) {
  PleiadesData element= enumeration.nextElement();
  if (element.name.contains(word)) return element;
}
return null;
}

public static void putNewPlace(String place, String description, String plename,String lat, String lon){
    pletable.put(place,new PleiadesData(place,description,plename,lat,lon));
    
}
}

