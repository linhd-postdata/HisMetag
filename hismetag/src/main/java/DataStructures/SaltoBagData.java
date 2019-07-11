/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;
import ContextProcessing.ContextualList;
import Recognition.*;
import java.util.*;

/**
 *
 * @author mluisadiez
 */
public class SaltoBagData extends BagData{
   
    
   public SaltoBagData(){
       type=Terms.UN;
   }
  public SaltoBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.SALTO;
   
  }
  public String getInit(){
      return "";
  };
  public String getEnd(){
      return "";
  };
  
  
        
    
}
