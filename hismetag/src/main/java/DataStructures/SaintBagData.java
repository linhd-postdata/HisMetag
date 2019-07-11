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
public class SaintBagData extends BagData{
   

   
    
   public SaintBagData(){
       type=Terms.UN;
   }
  public SaintBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.RNS;
   
  }
  
  public String getInit(){
      
      return "<roleName>";
  };
  public String getEnd(){
      return "</roleName>";
  };
  
  
        
    
}
