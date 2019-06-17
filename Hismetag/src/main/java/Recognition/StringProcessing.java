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

import IOModule.Input;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author M Luisa Díez Platas
 */
public class StringProcessing {
      
   /**
    * Process a string with the preposition "de" or "del", in order to decide
    * if there is one or more words that they could be a place name
    * 
    * @param string
    * @param modo 
    */ 
    
    
  static public String processingGuion(String string){
      return string.replaceAll("-", "");
  }
  
  static public void processingDe(String string,String modo){
       try{
            WordProcessing.WordTransformations.transformedWords.aproxList.clear();

          
      
        InfoFound info;
        String finaloutput;
        String de="( de )";
        Pattern patron = Pattern.compile("\\s+");
        
       Matcher encaja = patron.matcher(string);

       
      //String nueva = encaja.replaceAll(" ");
      string=encaja.replaceAll(" ");
        
         patron = Pattern.compile(de);
         String nueva=string;
        Matcher matcher = patron.matcher(nueva);
        nueva=matcher.replaceAll("_de_");
        
        String[] especial=nueva.split("_de_");
       
       
        if ((especial.length)==2){
            if (ElementsRecognition.hasCapitalleter(especial[0]) && ElementsRecognition.hasCapitalleter(especial[1])){
                
                if ((info=TermsRecognition.isCandidateWord(string))!=null){
                
                    
                }
                else{
                  //  if (PreviousNamesTable.contains(WordTransformations.capitalletersClean(especial[0]))){
                        if ((info=TermsRecognition.isCandidateWord(especial[1]))==null)
                                        Data.MedievalPlaceNamesTable.putNewPlace(especial[1]," "," ", " ", " ", " ", Input.name, " ","PL");

           
     
                }
            }
        
        }
        
       }catch(Exception e){;}
    }
    
    /**
     * Process a list of capitalize word in order to decide
    * if there is one or more words that they could be a place name
     * @param string
     * @param conjunction 
     */
    static public void processingList(String string,String conjunction){
       try{/*
        WordProcessing.WordTransformations.transformedWords.aproxList.clear();

           
           String info=null;
           Pattern patron = Pattern.compile("\\s+");
        
       Matcher encaja = patron.matcher(string);
// invocamos el metodo replaceAll
       
      //String nueva = encaja.replaceAll(" ");
      string=encaja.replaceAll(" ");
       
           if (string.contains(conjunction) ){
               
               patron= Pattern.compile(" "+conjunction+" ");
          
                encaja = patron.matcher(string);

                string=encaja.replaceAll("_"+conjunction+"_");
                 String[] stringList=string.split("_"+conjunction+"_");
                 
                 
                 
                 
                 if ((info=Recognition.findPlaceName(stringList[0]))!=null){
                     
                     Output.write(info);
                 }else{
                    String[] list=stringList[0].split(" ");
                    for (int i=0;i<list.length;i++){
                        if ((info=Recognition.findPlaceName(list[i]))!=null){
                            
                            Output.write(info+" ");
                        }else{
                            Output.write(Recognition.getAproximation(info));
                        }
                    }
   
                   }
                 Output.write(" et ");
                 if ((info=Recognition.findPlaceName(stringList[1]))!=null){
                    
                        Output.write(info) ;
                       
                 }else {
                     Output.write(Recognition.getAproximation(info));
                 }
                 }else {
                    String[] list=string.split(" ");
                    if (list.length==2){
                        if ((info=Recognition.findPlaceName(string))!=null){
                            Output.write(info);
                        }else{
                            for (int i=0; i<2; i++){
                                if ((info=Recognition.findPlaceName(list[i]))!=null){
                                    Output.write(info+" ");
                                }else Output.write(Recognition.getAproximation(list[i]));
                            }
                        }
                    } else {
                            for (int i=0; i<list.length; i++){
                                if ((info=Recognition.findPlaceName(list[i]))!=null){
                                    Output.write(info+" ");
                                }else Output.write(Recognition.getAproximation(list[i]));
                            }
                        }
                }
                 */
           
           }
                 
       
       catch(Exception e){;}
    }
    
    
    public boolean isStopWord(String word){
        
        if (Data.StopWordsTable.contains(word.toLowerCase())) return true;
        else return false;
    }
    
    
}
