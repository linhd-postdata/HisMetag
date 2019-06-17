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
public class OrgCollBagData extends BagData{
   
    
   
    
   public OrgCollBagData(){
       type=Terms.ON;
   }
  public OrgCollBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context,boolean amb){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
     type=Terms.ON;
      
  
  }
  
  public String getInit(){
	  
      return "<orgName>";
  };
  public String getEnd(){
      return "</orgName>";
  };
        
    
}
