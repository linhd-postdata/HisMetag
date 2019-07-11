/*
 * Copyright (C) 2017 mluisadiez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Recognition;


import Recognition.*;

import Data.AmbiguousTermsFoundTable;
import IOModule.Input;
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class TermsRecognition {
     /**
     * Check if the string recognized is a place name
     * @param word 
     */
    static public InfoFound checkPlaceName(String word){
     try{
         
         
    
     InfoFound info=null;   
        if ((info=isCandidateWord(word))!=null){
            
            
            
            WordProcessing.WordTransformations.transformedWords.aproxList.clear();
           return info;
        }
        else {
            
            WordProcessing.WordTransformations.transformedWords.aproxList.clear();
            return null;
        }
                   

    }catch(Exception e){return null;}
     
    }
    
     /**
     * Check and process a word to check if it is a place name
     * @param word 
     * @param info //Associated geographic information
     * @return 
     */
    static public String isPlaceName(String word){
        return null;
    }
    
     /**
     * Check and process a word to check if it is a place name
     * @param word 
     * @param info //Associated geographic information
     * @return 
     */
    static public InfoFound isCandidateWord(String word){
      try{
          InfoFound info;
          if (ElementsRecognition.hasCapitalleter(word)){
              if ((info=findPlaceName(word))!=null) return info;
              else return null;
              /*else if (Recognition.isProperName(word.toLowerCase())) 
                {
                  info="ProperName";
                  return null;
              }
              else {
               return getAproximation(word);   
                 
          }*/
          }
          else{
              if ((info=findPlaceName(word))!=null){ 
                  
              
               return info ;  
              }
              else if (Data.PrepositionsTable.contains(word)) return null;
              
              else if (Data.StopWordsTable.contains(word)) return null;
              else return null; 
          }     
      }
      catch (Exception e){return null;}
    }

     static public boolean isAmbiguous(String word){
          
          
                  if (Data.CommonNamesTable.contains(word)) {
             if (AmbiguousTermsFoundTable.contains(word))AmbiguousTermsFoundTable.modifyFrequency(word);
             else AmbiguousTermsFoundTable.ambiguoustable.put(word,new Data.AmbiguousFoundData(word,'1',"place-name"));
         return true;
                  }
         return false;
         
      }
      static public boolean isAmbiguousProperName(String word){
          
          
                  if (Data.ProperNamesTable.contains(word)) {
             if (Data.AmbiguousProperNamesFoundTable.contains(word))Data.AmbiguousProperNamesFoundTable.modifyFrequency(word);
             else Data.AmbiguousProperNamesFoundTable.ambiguoustable.put(word,new Data.AmbiguousFoundData(word,'1',"place-name"));
         return true;
                  }
         return false;
         
      }
      
      static public boolean isAuthorityOrTreatement(String word){
          return Data.TreatmentsTable.contains(word) || Data.AuthorityNamesTable.contains(word.toLowerCase());
      }
      
       static public boolean isAuthority(String word){
          return  Data.AuthorityNamesTable.contains(word.toLowerCase());
      }
       
         static public boolean isTreatment(String word){
          return  Data.TreatmentsTable.contains(word.toLowerCase());
      }
      
      
      
      static public String findApposition(String words){
          String[] listw=words.split(" ");
          for (int i=0;i<listw.length;i++){
              if (Data.AppositionPrepositionsTable.tP.contains(listw[i])) return listw[i];
          }
          return " ";
      }
      
     /**
     * Find a string in one of the gazeeter
     * @param place
     * @return 
     */
    static public InfoFound findPlaceName(String place){
        try{
        	 
            String pLower=place.toLowerCase();
            //System.out.println("estoy mirando los gaz para "+place+"www");
            String pCapit=WordTransformations.capitalize(place);
            //System.out.println("estoy mirando los gaz para "+pLower+"www");
            
            InfoFound output;
           output=existMedievalPlaceName(pCapit);
            if (output!=null){
            	//System.out.println("estoy mirando los gaz para "+place);
               return output;         
            }
           
            output=existFreelingPlaceName(pLower);
            	if (output!=null){
           
               return output; 
            	}
            	 
            	output=existPleiadesPlaceName(pCapit);
            	if (output!=null){
               return output;   
            }
            	
            //	System.out.println("estoy mirando los gaz para geonames "+pCapit+"www");
            	output=existGeonamesPlaceName(pCapit);
            	
            	if (output!=null){
            		
                return output;
                   
            }
            	
            	
        return null;
        
        }catch(Exception e){return null;} 
    }
    
    
    /**
     * Check if the word is in Medieval Gazeeter
     * @param word
     * @return 
     */
    public static InfoFound existMedievalPlaceName(String string) {
        
        Data.MedievalData MData=Data.MedievalPlaceNamesTable.get(string);
      
        if (MData!=null){
            
                if (MData.pleiadesname.equals(" ")){

                    
                    return new InfoFound(MData.geoname,MData.currentname,MData.description,"Medieval");
                }
                else {
                    
                    return new InfoFound(MData.pleiadesname,MData.currentname,MData.description,"Medieval");
                }
           
        }else {
            return null;
               
                
        }
        
    }
    
    /**
     * Check if the place is in Freeling Dictionary
     * 
     * @param place
     * @return 
     */
    public static InfoFound existFreelingPlaceName(String string) {
        
        Data.FreelingData FData=Data.FreelingPlaceNamesTable.get(string);
        if (FData!=null){
            //Data.MedievalPlaceNamesTable.putNewPlace(WordTransformations.capitalize(string), FData.currentname, FData.pleiadesname,FData.geoname," "," ",Input.name,"Freeling");
            
                if (FData.pleiadesname.equals(" ")){

                    
                    return new InfoFound(FData.geoname,FData.currentname,null,"Freeling");
                }
                else {
                    
                    return new InfoFound(FData.pleiadesname,FData.currentname,null,"Freeling");
                }
           
        }
       
        return null;
        
        
    }
    
    /**
     * Check if the place is in Pleiades Gazeeter
     * @param place
     * @return 
     */
    public static InfoFound existPleiadesPlaceName(String string) {
         Data.PleiadesData PData=Data.PleiadesPlaceNamesTable.get(string);
        if (PData!=null){
            
            
          //Data.MedievalPlaceNamesTable.putNewPlace(string, " ",PData.plename," ",PData.description," ",Input.name,"Pleiades");

                    
          return new InfoFound(PData.plename,null,PData.description,"Pleiades");
  
        }
       
        return null;
    }
    
    
    /**
     * Check if the place is Geonames Gazeeter
     * @param place
     * @return 
     */
     public static InfoFound existGeonamesPlaceName(String string) {
        Data.GeositesData GData=Data.GeositesPlaceNamesTable.get(string);
          
       
        if (GData!=null){
            //Data.MedievalPlaceNamesTable.putNewPlace(string, GData.name," ", GData.geoname," ", " ",Input.name,"Geonames");
            
                    return new InfoFound(GData.geoname,GData.alternativename,null,"Geonames");
        }else {
            Data.GeositesData alternativenames=Data.GeositesPlaceNamesTable.contains(string);
           if (alternativenames!=null){
               
           //Data.MedievalPlaceNamesTable.putNewPlace(string, alternativenames.name," ", alternativenames.geoname," ", " ",Input.name,"Geonames");

               return new InfoFound(alternativenames.geoname,GData.alternativename,null,"Geonames_alternate");
                       
          }
        }
        
        return null;
    }
     
     
       /**
     * Check if the word is a proper name
     * 
     * @param word
     * @return 
     */    
    static public boolean isProperName(String string){
    	
       String newString=string.toLowerCase();
       if (Data.ProperNamesTable.tPR.containsKey(newString)){ 
           
          
           return true;}
       
       //newString=WordProcessing.WordTransformations.remove(newString);
      // if (Data.ProperNamesTable.contains(newString)) return true;
       
       return false;
       
    }
    
    
        static public boolean isCommonName(String string){
    	
       String newString=string.toLowerCase();
     //  System.out.println("estoy comprobando los comunes"+newString);
       if (Data.CommonNamesTable.tPR.containsKey(newString)){ //System.out.println("estoy comprobando los comunes"+string);
       return true;}
       
       //newString=WordProcessing.WordTransformations.remove(newString);
       //if (Data.ProperNamesTable.contains(newString)) return true;
       
       return false;
       
    }
    
    static public boolean isSaintName(String string){
       String newString=string.toLowerCase();
       if (Data.NewSaintNamesTable.contains(newString)){ return true;}
       //newString=WordProcessing.WordTransformations.remove(newString);
       if (Data.NewSaintNamesTable.contains(newString)) return true;
       return false;
       
    }
    
    
}
