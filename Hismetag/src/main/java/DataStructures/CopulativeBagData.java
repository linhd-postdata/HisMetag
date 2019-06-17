/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;
import java.util.*;
import ContextProcessing.ContextualList;
import Recognition.*;
/**
 *
 * @author mluisadiez
 */
public class CopulativeBagData extends BagData{
   
   
   
    
   public CopulativeBagData(){
       type=Terms.UN;
   }
  public CopulativeBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.Y;
   
  }
  
  public String getInit(){
      return "";
  };
  public String getEnd(){
      return "";
  };
  
  
        
    
}
