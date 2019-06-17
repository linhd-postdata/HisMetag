/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.*;
import java.io.*;

import IOModule.Input;
import Recognition.InfoFound;
import Recognition.TypesTerms;
/**
 *
 * @author M Luisa Díez Platas
 */
public class MedievalNewPlaceNamesTable {
    public static Hashtable<String,MedievalData> medievaltable=new Hashtable<String,MedievalData>();
    public static String path;
public static void createTable(String p) throws java.io.FileNotFoundException,java.io.IOException{
    	path=p;
    	
        File archivo = new File (path+"dataFiles/new-medieval-data.txt");
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
        
         

         // Lectura del fichero
         
         while((line=br.readLine())!=null){
            String[] arr_date=line.split(";");
           
            MedievalData Medievalsite=new MedievalData();

            Medievalsite.name=arr_date[0];
            Medievalsite.currentname=arr_date[1];
            Medievalsite.pleiadesname=arr_date[2];
            Medievalsite.geoname=arr_date[3];
            
            Medievalsite.type=arr_date[5];
            Medievalsite.document=arr_date[6];
            Medievalsite.description=arr_date[4];
            Medievalsite.provenance=arr_date[7];
            Medievalsite.typeg=arr_date[8];
            
            medievaltable.put(arr_date[0], Medievalsite);
            
         }
         br.close();
      }
      
public static void putNewPlace(String place, InfoFound info, TypesTerms tt, String typeg){
	String infople=" ";
	String infogeo=" ";
	String provenance=" ";
	if (tt==TypesTerms.GENT || tt==TypesTerms.PPT) provenance="Medieval";
	else provenance=info.gazetteer;
	if (info.uri.contains("Geonames")){
			
		infogeo=info.uri;
		}else {
			infople=info.uri;
		}
		MedievalData newData=new MedievalData(place,info.current,infople,infogeo,info.description,tt.toString(),Input.name,provenance,typeg);
		//System.out.println("ESTOY RECIBIENDO LOS DATOS PARA LA TABla"+place);
		medievaltable.put(place, newData);
}
     
   public static boolean contains(String word){
    ArrayList<MedievalData> elementList=new ArrayList();
    Enumeration<MedievalData> enumeration = medievaltable.elements();
    
while (enumeration.hasMoreElements()) {
  MedievalData element= enumeration.nextElement();
  
  if (element.name.contains(word)) {  return true;}
}
return false;
}

public static MedievalData get(String word){
    
    return medievaltable.get(word);
}


public static void putNewPlace(String place, String actual){
    try{
    medievaltable.put(place,new MedievalData(place,actual," "," "," "," "," "," "," "));
                File newfile=new File(path+"dataFiles/new-medieval-data.txt");
            BufferedWriter file = new BufferedWriter(new FileWriter(newfile,true)); 
            file.write("\n");
            file.write(place+';'+actual+"; ; "); 
            file.close();

    }catch(Exception e){;}
    
}
public static void putNewPlace(String place, String actual, String plename, String geoname,String description,String type,String document,String provenance,String typeg){
    try{
        
        
    medievaltable.put(place,new MedievalData(place,actual,plename,geoname,description,type,document,provenance,typeg));
    
      }catch(Exception e){;}
}

public static void updateFile(){
    try{
        File newfile=new File(path+"dataFiles/new-medieval-data.txt");
        BufferedWriter file = new BufferedWriter(new FileWriter(newfile)); 
        Hashtable<String,MedievalData> t=medievaltable;
        Vector v = new Vector(medievaltable.keySet());
        
        Collections.sort(v);
        HashSet<String> hashSet = new HashSet<String>(v);
		v.clear();
		v.addAll(hashSet);
        Collections.sort(v);
        Iterator it = v.iterator();
        while (it.hasNext()) {
            String element =  (String)it.next();
            
            MedievalData md=t.get(element);
            file.write(element+';'+md.currentname+';'+md.pleiadesname+';'+md.geoname+';'+md.description+';'+md.type+';'+md.document+';'+md.provenance+';'+md.typeg);
            file.write("\n");
        }
        
         file.close();
            
            
    }catch(Exception e){;}
}
}

