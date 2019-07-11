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
public class FreelingPlaceNamesTable {
    public static Hashtable<String,FreelingData> freetable=new Hashtable<String,FreelingData>();
    public static String path;
public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
    	//path=p;
       
         
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
         

         // Lectura del fichero
         
         while((line=br.readLine())!=null){
            String[] arr_date=line.split(";");
           
            FreelingData Freesite=new FreelingData();
            
            Freesite.name=arr_date[0];
            Freesite.currentname=arr_date[1];
            Freesite.pleiadesname=arr_date[2];
            
            Freesite.geoname=arr_date[3];
            freetable.put(arr_date[0], Freesite);
            
         }
         br.close();
      }
      
     
   

public static FreelingData get(String word){
    return freetable.get(word);
}

public ArrayList<FreelingData> contains(String word){
    ArrayList<FreelingData> elementList=new ArrayList();
    Enumeration<FreelingData> enumeration = freetable.elements();
while (enumeration.hasMoreElements()) {
  FreelingData element= enumeration.nextElement();
  if (element.name.contains(word)) elementList.add(element);
}
return elementList;
}
public static void putNewPlace(String place, String description){
    try{
    freetable.put(place,new FreelingData(place,description," "," "));
                File newfile=new File(path+"dataFiles/datos_free.txt");
            BufferedWriter file = new BufferedWriter(new FileWriter(newfile,true)); 
            file.write("\n");
            file.write(place+';'+description+"; ; "); 
            file.close();

    }catch(Exception e){;}
    
}
public static void putNewPlace(String place, String description, String plename, String geoname){
    try{
    freetable.put(place,new FreelingData(place,description,plename,geoname));
    File newfile=new File(path+"dataFiles/datos_free.txt");
            BufferedWriter file = new BufferedWriter(new FileWriter(newfile,true)); 
            file.write("\n");
            file.write(place+';'+description+';'+plename+';'+geoname);
            file.close();
      }catch(Exception e){;}
}

public static void updateFile(){
    
}
}

