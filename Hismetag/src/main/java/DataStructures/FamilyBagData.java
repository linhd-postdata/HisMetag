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
public class FamilyBagData extends BagData{
   

   
    
   public FamilyBagData(){
       type=Terms.UN;
   }
  public FamilyBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.RNF;
  
  }
  public String getInit(){
      return "<roleName type='_family' subtype='"+subtype+"'>";
  };
  public String getEnd(){
      return "</roleName>";
  };
  
  
        
    
}
