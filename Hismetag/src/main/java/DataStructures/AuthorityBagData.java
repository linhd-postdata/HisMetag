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
public class AuthorityBagData extends BagData{
   
   
   
    
   public AuthorityBagData(){
       type=Terms.UN;
   }
  public AuthorityBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info, ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.RNA;
   
  }
  
  public String getInit(){
      return "<roleName  subtype='"+subtype+"'>";
  };
  public String getEnd(){
      return "</roleName>";
  };
  
  
        
    
}
