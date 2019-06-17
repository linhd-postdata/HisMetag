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
public class PlaceNameMythologicalBagData extends BagData{
   
    
   
    
   public PlaceNameMythologicalBagData(){
       type=Terms.UN;
   }
  public PlaceNameMythologicalBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context,boolean amb){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
     if (amb) 
    	 type=Terms.AMPLN;
     else
      type=Terms.MPLN;
      
  
  }
  
  public String getInit(){
	  String geo="0 0";
      
      //System.out.println("he entrado por un place de "+infoPlace.gazetteer);
     
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
      return "<placeName type='' subtype='_mythological'>";
  };
  public String getEnd(){
      return "</placeName>";
  };
        
    
}
