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
public class  BagData {
    public String string;
  
   public TypesTerms subtype;
   // public ArrayList<Integer> arcleft;
  // public ArrayList<Integer> arcright;
   // public int num;
   public Terms type;
   public String atribute;
   
    public int position;
    public int nword;
    public int endposition;
   public Recognition.InfoFound infoPlace;
   public ContextProcessing.ContextualList context=null;
    
   public BagData(){
       type=Terms.VACIO;
   }
   
   public BagData(BagData bgd){
       subtype=bgd.subtype;
       type=bgd.type;
       atribute=bgd.atribute;
       position=bgd.position;
       nword=bgd.nword;
       endposition=bgd.endposition;
       infoPlace=new InfoFound(bgd.infoPlace);
       context=bgd.context;
   }
  public BagData(String _string, TypesTerms _subtype, int _pos, int _nword,int _endpos, Recognition.InfoFound _info, ContextualList _context){
      string=_string;
     // System.out.println("meto en el bag"+_string+"wwww");
      //type=_type;
   //   typet=_typet;
      subtype=_subtype;
      //arcleft.add(_arcleft);
      //arcright.add(_arcright);
     // num=_num;
      
      position=_pos;
      nword=_nword;
      endposition=_endpos;
      infoPlace=_info;
     context=_context;
  }
  
  public String getInit(){
      
      if (context==ContextualList.INITIAL && type==Terms.UN)
      return "<name>";
      else return "";
              
  
  }
  public String getEnd(){if (context==ContextualList.INITIAL && type==Terms.UN)
      return "</name>";
      else return "";}
 /* public boolean depend(BagData bd){
      
      System.out.println("ESTOY EN LE OTRR"+this.string+this.type+"el otro "+bd.string+bd.type);
      
      if ((type=="PPN" && bd.type=="RNF") && position>bd.position)return true;
      if ((type=="PPN" && bd.type=="RNH") && position>bd.position)return true;
      if (type=="PPN" && bd.type=="Y") return true;
      if ((type=="PPN" && bd.type=="DE") && position>bd.position) return true;
      if (type=="RNA" && bd.type=="PPN") return true;
      if ((type=="RNH" && bd.type=="PPN") && position<bd.position) return true;
      if (type=="RNF" && bd.type=="PPN") return true;
      if (type=="PLN" && bd.type=="RNA") return true;
      if (type=="PLN" && bd.type=="RNF") return true;
      if (type=="PLN" && bd.type=="DE")  return true;
      if (type=="PLN" && bd.type=="Y") return true;
      if (type=="DE" && bd.type=="PPN") return true;
      if (type=="DE" && bd.type=="RNA") return true;
      if (type=="DE" && bd.type=="RNF") return true;
      
      return false;
  }
        */
    
}
