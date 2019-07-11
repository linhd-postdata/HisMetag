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
public class HonorificBagData extends BagData{
   

   
    
   public HonorificBagData(){
       type=Terms.UN;
   }
  public HonorificBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.RNH;
   
  }
  
  public String getInit(){
      return "<roleName type='honorific' subtype='"+subtype+"'>";
  };
  public String getEnd(){
      return "</roleName>";
  };
  
  
        
    
}
