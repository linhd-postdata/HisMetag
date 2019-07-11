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
public class GeositesPlaceNamesTable {
    public static Hashtable<String,GeositesData> geotable=new Hashtable<String,GeositesData>();
    private String filePlaces;
public static void createTable(File archivo) throws java.io.FileNotFoundException,java.io.IOException{
    
        
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
         

         // Lectura del fichero
         int i=0;
         while((line=br.readLine())!=null){
            String[] arr_date=line.split(";");
            
            GeositesData Geosites=new GeositesData();
            if (arr_date.length==5){
            	Geosites.alternativename=arr_date[2];
                Geosites.geoname="http://sws.geonames.org/"+arr_date[0];
                Geosites.name=arr_date[1];
                if (i<20)
                   // System.out.println("la info de geonames "+arr_date[1]);
                    i++;
                Geosites.lat=arr_date[3];
                Geosites.lon=arr_date[4];
                geotable.put(arr_date[1], Geosites);
            }
            else{
            	//System.out.println("la linea que se imprime"+line);
            	
            }
         }
         br.close();
      }
      
     
   

public static GeositesData get(String word){
    return geotable.get(word);
}

public static GeositesData contains(String word){
    ArrayList<GeositesData> elementList=new ArrayList();
    Enumeration<GeositesData> enumeration = geotable.elements();
while (enumeration.hasMoreElements()) {
  GeositesData element= enumeration.nextElement();
  String[] alternatives=element.alternativename.split(",");
  for (int i=0; i<alternatives.length;i++)
      if (alternatives[i].equals(word)) return element;
}
return null;
}

public static void putNewPlace(String place, String alternativePlace, String geoname,String lat, String lon){
    geotable.put(place,new GeositesData(place,alternativePlace,geoname,lat,lon));
    
}


}

