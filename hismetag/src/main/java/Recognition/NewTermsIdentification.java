                      
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


import Data.FreelingPlaceNamesTable;
import Data.GeositesPlaceNamesTable;
import Data.MedievalPlaceNamesTable;
import Data.PleiadesPlaceNamesTable;
import Data.UpdateData;
import WordProcessing.AproximationsList;
import WordProcessing.ExactAproximationWord;
import WordProcessing.WordND;
import WordProcessing.WordTransformations;
import java.util.ArrayList;

/**
 *
 * @author M Luisa Díez Platas
 */
public class NewTermsIdentification {
    

    static public InfoFound getAproximation(String word){
       try{
   
           WordTransformations.transformedWords.aproxList.clear();
           // System.out.println ("estoy buscando las variante s de la palabra "+word);
         
         InfoFound info=null;
         WordProcessing.WordTransformations.wordTransformationProcessing(word);
         
         ArrayList<ExactAproximationWord> aproximationw=getBetterAproximation();
         //System.out.println("Las aproximaciones exavtas de la palabra "+word+"son las sigueintes "+aproximationw.size()+" "+aproximationw.get(0).word);
       
        

           if (aproximationw.size()==1){
               info=Data.UpdateData.updateTable(word,aproximationw.get(0));
             //  System.out.println("la info ");
              if (info.gazetteer!="none"){
               
                 
                  return info;
              }else if (Recognition.TermsRecognition.isProperName(aproximationw.get(0).word.toLowerCase())){
               //   System.out.println("la recoocida "+ aproximationw.get(0).word.toLowerCase());
                  aproximationw.clear();
                 // System.out.println ("ALFONAO "+aproximationw.size());//aproximationw.get(0).word);
                  
                  return new InfoFound("proper","","","proper");
              }else if (aproximationw.get(0).table=="nick") {
                  aproximationw.clear();
                  return new InfoFound("nick","","","nick");
              }
              else if (aproximationw.get(0).table=="common"){
                  aproximationw.clear();
                  return new InfoFound("common","","","common");
              }
               else 
              {
                  aproximationw.clear();
                 
                  return null;
              }
               
           }
           else if (aproximationw.size()>1) {
              
              ExactAproximationWord exact=selectWordAproximation(aproximationw);
              
            // System.out.println("EXACTOOOOO "+exact);

               UpdateData.updateTable(word,exact);
                  if ((info=TermsRecognition.findPlaceName(exact.word))!=null){
               aproximationw.clear();
                  
                  return info;
              }else if (Recognition.TermsRecognition.isProperName(exact.word.toLowerCase())){
                  aproximationw.clear();
                  return new InfoFound("none","","","none");
              }else if (exact.table=="nick"){
                  aproximationw.clear();
                  return new InfoFound("nick","","","nick");
              }
              else if (exact.table=="common"){
                  aproximationw.clear();
                  return new InfoFound("common","","","common");
              }
               else 
              {
                  
                  aproximationw.clear();
                  return null;
              }
               
              }
               else 
              {
                  
             aproximationw.clear();
                  return null;
              }
           
           }
           catch (Exception e){return null;}
        }
    
    
    
     /**
      * Get the best aproximation to the transformed word
      * 
      * @return 
      */
      static public ArrayList<WordProcessing.ExactAproximationWord> getBetterAproximation(){
           
         
        ArrayList<ExactAproximationWord> listWords=new ArrayList<ExactAproximationWord>();
           AproximationsList aproxList=WordProcessing.WordTransformations.transformedWords;
           
          

          for (int i=0; i<aproxList.aproxList.size();i++){
              WordND wordND=aproxList.aproxList.get(i);
              String word=aproxList.aproxList.get(i).word;
              String wordLow=word.toLowerCase();
              //System.out.println("las TRASNFORMACIONES QUE VOY VIENDO SON "+wordLow);
              String wordCap=WordTransformations.capitalize(word);
              if (MedievalPlaceNamesTable.get(wordCap)!=null){ listWords.add(new ExactAproximationWord(word,"medieval"));}
              else if(FreelingPlaceNamesTable.get(wordLow)!=null){ listWords.add(new ExactAproximationWord(word,"freeling"));}
              else if (PleiadesPlaceNamesTable.get(wordCap)!=null) listWords.add(new ExactAproximationWord(word,"pleiades"));
              else if (GeositesPlaceNamesTable.geotable.get(wordCap)!=null){
                  listWords.add(new ExactAproximationWord(word,"geonames"));
              }
              else if (GeositesPlaceNamesTable.contains(wordCap)!=null) listWords.add(new ExactAproximationWord(word,"geonames_alternative"));
              else if (Recognition.TermsRecognition.isProperName(wordLow)) listWords.add(new ExactAproximationWord(word,"none"));
              else if (Data.NickNamesTable.contains(wordLow)) listWords.add(new ExactAproximationWord(word,"nick"));
              else if (Data.CommonNamesTable.contains(wordLow)) listWords.add(new ExactAproximationWord(word,"common"));
              
          }
             
             

        WordProcessing.WordTransformations.transformedWords.aproxList.clear();
      
          return listWords;
        
    }
      
      static public ArrayList<WordProcessing.ExactAproximationWord> getBetterAproximationName(){
           
         
        ArrayList<ExactAproximationWord> listWords=new ArrayList<ExactAproximationWord>();
           AproximationsList aproxList=WordProcessing.WordTransformations.transformedWords;
           
           

          for (int i=0; i<aproxList.aproxList.size();i++){
              WordND wordND=aproxList.aproxList.get(i);
              String word=aproxList.aproxList.get(i).word;
              String wordLow=word.toLowerCase();
              String wordCap=WordTransformations.capitalize(word);
              if (Recognition.TermsRecognition.isProperName(wordLow)) listWords.add(new ExactAproximationWord(word,"none"));
              
          }
             
             

        
      
          return listWords;
        
    }
    
    /**
     * Select the better aproximations to the candidate string
     * 
     * @param exAPW
     * @return 
     */
    static public ExactAproximationWord selectWordAproximation(ArrayList<ExactAproximationWord> exAPW){
        
        ExactAproximationWord selected=null;
        float maxDistance=0;
        for (int i=0;i<exAPW.size();i++){
            String wordi=exAPW.get(i).word;
            
            
            if (WordTransformations.transformedWords.aproxList.get(i).distance>maxDistance){
                maxDistance=WordTransformations.transformedWords.aproxList.get(i).distance;
                selected=exAPW.get(i);
                        }
            
        }
        System.out.println ("MAXIMA   DUSTANCIA "+maxDistance);
        return selected;
    }
    
}
