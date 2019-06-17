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
public class ProperNameBagData extends BagData{
   
 
   
    
   public ProperNameBagData(){
       type=Terms.UN;
   }
  public ProperNameBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      //System.out.println("HE ENTRADO POR PROPER NAME");
      type=Terms.PPN;
      
   
  }
  
  public String getInit(){
	  
      return "<persName type='' subtype='"+subtype+"'>";
  };
  public String getEnd(){
      return "</persName>";
  };
  
  
        
    
}
