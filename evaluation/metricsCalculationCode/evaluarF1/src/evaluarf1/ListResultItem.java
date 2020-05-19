/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluarf1;

import java.util.HashMap;

/**
 *
 * @author Mª Luisa Díez Platas
 */
public class ListResultItem {
    HashMap<String,ResultItem> result=new HashMap<String,ResultItem>();
    
    ListResultItem(){
              result.put("placeName", new ResultItem());
         result.put("persName", new ResultItem());
          result.put("orgName", new ResultItem());
           result.put("roleName", new ResultItem());
           result.put("geogName", new ResultItem());
    }
    
   public void putPrecision(String tag, double value){
       result.get(tag).precision=value;
   }
   public void putRecall(String tag, double value){
       result.get(tag).recall=value;
   }
   public void putF1(String tag, double value){
       result.get(tag).F1=value;
   }
   public void putTotal(String tag, double value){
       result.get(tag).total+=value;
   }
  
   public void putTotalAutomatic(String tag, double value){
       result.get(tag).totalAutomatic+=value;
   }
   public void putTotalChecked(String tag, double value){
       result.get(tag).totalChecked+=value;
   }
   public double getPrecision(String tag){
       return result.get(tag).precision;
   }
    public double getRecall(String tag){
       return result.get(tag).recall;
   }
    public double getF1(String tag){
       return result.get(tag).F1;
   }
       public double getTotal(String tag){
       return result.get(tag).total;
   }
    public double getTotalAutomatic(String tag){
       return result.get(tag).totalAutomatic;
   }
       public double getTotalChecked(String tag){
       return result.get(tag).totalChecked;
   }
  
    public void write(){
        System.out.println("placeName:  "); result.get("placeName").write();
        System.out.println("persName:  "); result.get("persName").write();
        System.out.println("orgName:  "); result.get("orgName").write();
        System.out.println("roleName:  "); result.get("roleName").write();
        System.out.println("geogName:  "); result.get("geogName").write();
        
    }
    
}
