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
package Data;

import Recognition.InfoFound;
import IOModule.Input;
import WordProcessing.ExactAproximationWord;

/**
 *
 * @author M Luisa Díez Platas
 */
public class UpdateData {
      
    /**
     * Create a new place in mediavel table with information from a gazeeter that
     * contains the candidate word
     * 
     * @param word
     * @param exAW 
     */
    static public InfoFound updateTable(String word,ExactAproximationWord exAW){
       try{
        if (exAW.table.equals("freeling")){
            FreelingData data=FreelingPlaceNamesTable.get(exAW.word);
          //   Data.MedievalPlaceNamesTable.putNewPlace(word, data.currentname, data.pleiadesname,data.geoname," "," ",Input.name,"Freeling");   
             return new InfoFound(data.geoname,data.currentname,null,"Freeling");
        }else if (exAW.table.equals("pleiades")){
            PleiadesData data=PleiadesPlaceNamesTable.get(exAW.word);
          //   Data.MedievalPlaceNamesTable.putNewPlace(word, " ",data.plename," ",data.description," ",Input.name,"Pleiades");  
             return new InfoFound(data.plename,null,data.description,"Pleiades");
        }else if (exAW.table.equals("geonames")){
            GeositesData data=GeositesPlaceNamesTable.get(exAW.word);
          //  Data.MedievalPlaceNamesTable.putNewPlace(word, data.name," ", data.geoname," ", " ",Input.name,"Geonames"); 
            return new InfoFound(data.geoname,data.alternativename,null,"Geonames");
        }else if (exAW.table.equals("geonames_alternative")){
            GeositesData data=GeositesPlaceNamesTable.contains(exAW.word);
          //  Data.MedievalPlaceNamesTable.putNewPlace(word, data.name," ", data.geoname," ", " ",Input.name,"Geonames"); 
            return new InfoFound(data.geoname,data.alternativename,null,"Geonames");
        }else if (exAW.table.equals("none")){
            Data.NewProperNamesTable.putNewName(word, Input.name, "");
            return new InfoFound(exAW.word,"","","none");
        }else{
            
            
            MedievalData data=MedievalPlaceNamesTable.get(exAW.word);
         //   Data.MedievalPlaceNamesTable.putNewPlace(word, data.currentname, data.pleiadesname, data.geoname, data.description, data.type, Input.name, "medieval");
            return new InfoFound(data.geoname,data.currentname,data.description,"Medieval");
        }
        
       
       }catch(Exception e){return null;}       
    }
    
    /**
     * Get an aproximation to the candidate word through the application of several 
     * transformations
     * 
     * @param word
     * @return 
     */
    
    
    
   
    
    
    /**
     * Insert a new term to validate in the validation medieval gazeeter
     * 
     * @param word
     * @return 
     */
 static public String putNewTerm(String word){
        
//    Data.MedievalPlaceNamesTable.putNewPlace(word," "," "+" "," "," "," ",Input.name,"VALIDATE"); 
                        return "\n<placeName ref=' "+word+"'></placeName></span></a>\n";
                    
    }
}
