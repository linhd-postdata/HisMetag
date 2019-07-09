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
    
  /*  public static String xmlStringToJSONString(String xmlString) {
        
	try
        {
            // Create a new XmlMapper to read XML tags
            XmlMapper xmlMapper = new XmlMapper();
            
            //Reading the XML
            JsonNode jsonNode = xmlMapper.readTree(xmlString.getBytes());
            
            //Create a new ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            
            //Get JSON as a string
            String value = objectMapper.writeValueAsString(jsonNode);
            
            System.out.println("*** Converting XML to JSON ***");
            System.out.println(value);
            return value;

        } 
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
        
    }*/
    
    public static String finalJSON(ArrayList<JsonClass> jsonlist,String xml){
        
        String data=generateJsonList(jsonlist);
        
        
        String finalj="{"+"\"entities\": "+data+","+"\"XML\":"+xml.toString()+"}";
        System.out.println("el json final es "+finalj);
        return finalj;
    }
        
    
}
       

