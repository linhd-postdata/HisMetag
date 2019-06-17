/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;
import ContextProcessing.ContextualList;
import Data.GeositesData;
import Data.PleiadesData;

import java.util.*;

import Recognition.*;
import WordProcessing.WordTransformations;
/**
 *
 * @author mluisadiez
 */
public class GeographicBagData extends BagData{
   
    
   
    
   public GeographicBagData(){
       type=Terms.GPLN;
   }
  public GeographicBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context,boolean amb){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
     type=Terms.GPLN;
      
  
  }
  
  public String getInit(){
	  String geo="0 0";
	  
	  
   
   	if(infoPlace.uri.contains("pleiades")){
           
          
           
          //System.out.println("Contiene en pleiades "+info);
   		PleiadesData d=Data.PleiadesPlaceNamesTable.get(WordTransformations.capitalize(string));
               
               if (d==null) geo="0 0";
               else geo=d.lat+" "+d.longd;
               
               
               
   	}
       else if (infoPlace.uri.contains("geonames")){
           
           
   		GeositesData d=Data.GeositesPlaceNamesTable.get(WordTransformations.capitalize(string));
               
   			if (d==null) geo="0 0";
   			else geo=d.lat+" "+d.lon;
            
               
   	}else{
           
           //System.out.println("este es "+word);
           
       }
      return "<geogName type='' subtype='"+subtype+"'>"+"<ptr target="+'"'+infoPlace.uri+'"'+"></ptr><geo style='display:none'>"+geo+"</geo>";
  };
  public String getEnd(){
      return "</geogName>";
  };
        
    
}
