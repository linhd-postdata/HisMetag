/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;
import ContextProcessing.ContextualList;
import java.util.*;
import Recognition.*;

/**
 *
 * @author mluisadiez
 */
public class PossesiveBagData extends BagData{
   
    
   
    
   public PossesiveBagData(){
       type=Terms.UN;
   }
  public PossesiveBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.PSS;
   
  }
  
  public String getInit(){
      return "";
  };
  public String getEnd(){
      return "";
  };
  
  
        
    
}
