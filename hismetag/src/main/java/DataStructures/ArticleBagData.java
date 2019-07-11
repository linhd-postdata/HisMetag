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
public class ArticleBagData extends BagData{
   
   
   
    
   public ArticleBagData(){
       type=Terms.ART;
   }
  public ArticleBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info, ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.ART;
   
  }
  
  public String getInit(){
	 
      return "";
  };
  public String getEnd(){
      return "";
  };
  
  
        
    
}
