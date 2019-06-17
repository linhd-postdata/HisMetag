/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;
import java.util.*;
import ContextProcessing.*;
import Recognition.*;

/**
 *
 * @author mluisadiez
 */
public class AddBagData extends BagData{
   
   
   
    
   public AddBagData(){
       type=Terms.UN;
   }
  public AddBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info, ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.ADD;
   
  }
  
  public String getInit(){
      return "";
  };
  public String getEnd(){
      return "";
  };
  
  
        
    
}
