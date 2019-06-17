/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;
import StringInProcess.*;

/**
 *
 * @author mluisadiez
 */
public class AuthorityTree {
    public String term;
    public String name;
    public TokenizedString aditions;
    public int orden;
    
  public AuthorityTree(){
      term="";
      name="";
      aditions=null;
      orden=1;
              
  }
  
  public void writeTree(){
      if (orden==1){
         // System.out.println ("<persName><roleName>"+term+"</roleName> "+name+" "); 
          aditions.writeTokenList();
        //  System.out.println("</persName>");
      }
  }
    
}
