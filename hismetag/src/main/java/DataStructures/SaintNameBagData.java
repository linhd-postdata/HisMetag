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
public class SaintNameBagData extends BagData{
   

   
    
   public SaintNameBagData(){
       type=Terms.UN;
   }
  public SaintNameBagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info,ContextualList _context){
      super(_string,_subtype,_pos,_nword,_endpos,_info,_context);
      
      type=Terms.PSTN;
   
  }
  
  public String getInit(){
      String[] list=string.split(" ");
      string=string.substring(list[0].length());
      
      return "<persName type='_deity' subtype='"+subtype+"'><roleName>"+list[0]+"</roleName>";
  };
  public String getEnd(){
      return "</persName>";
  };
  
  
        
    
}
