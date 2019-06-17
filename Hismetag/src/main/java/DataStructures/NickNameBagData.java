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
public class NickNameBagData extends BagData{
   

   
    
   public NickNameBagData(){
       type=Terms.NPN;
   }
  public NickNameBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.NPN;
   
  }
  
  public String getInit(){
      String nueva=string.replace("'", "");
      return "<persName type='nickName' subtype='"+subtype+"'>";
  };
  public String getEnd(){
      return "</persName>";
  };
  
  
        
    
}
