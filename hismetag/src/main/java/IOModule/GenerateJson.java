/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;

import clases.Numeros;
import com.google.gson.Gson;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileWriter;


import java.io.IOException;







/**
 *
 * @author mluisadiez
 */
public class GenerateJson {
    
    public static String generateJsonList(ArrayList<JsonClass> Jsonlist){
        String representacionJSON="[";
      for (int i =0; i<Jsonlist.size();i++){
      
       representacionJSON += generateJson(Jsonlist.get(i))+",";
       }
      representacionJSON=representacionJSON .substring(0, representacionJSON.length()-1)+"]";
    
      return representacionJSON;
    }
    
    public static String generateJson(JsonClass jsonObj){
        Gson gson = new Gson();
        
        return gson.toJson(jsonObj);
       
    }
    
    public static String finalJSON(ArrayList<JsonClass> jsonlist,String xml)throws Exception{
        
        String data=generateJsonList(jsonlist);
        
       JsonXMLClass jsonobject=new JsonXMLClass(jsonlist,xml);
        Gson gson = new Gson();
        
        String jsonFinal= gson.toJson(jsonobject);
      
 
       
    
        
 
      
        return jsonFinal;
    }
        
    
}
       

