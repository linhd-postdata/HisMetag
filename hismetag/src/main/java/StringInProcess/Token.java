/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringInProcess;

import Recognition.InfoFound;
import DataStructures.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class Token {
    public String word;
   
    public InfoFound info;
    public Recognition.Terms term;
    public Recognition.TypesTerms type;
    public boolean ambiguous=false;
    public int position;
    public int nWord;
    public Recognition.VerificationInfo verif;
    public BagData bdata;
    
    
    
    
    
    public Token(String w,Recognition.Terms _term, InfoFound i, Recognition.TypesTerms t,int pos,Recognition.VerificationInfo ver){
        word=w;
        
        info=i;
        type=t;
        position=pos;
        term=_term;
        ambiguous=false;
        verif=ver;
        
     
        
    }
    
     public Token(String w, Recognition.Terms _term,InfoFound i, Recognition.TypesTerms t,boolean amb, int pos,Recognition.VerificationInfo ver){
        word=w;
        term=_term;
        info=i;
        type=t;
        ambiguous=amb;
        position=pos;
        verif=ver;
     
        
    }
     
         public Token(String w, Recognition.Terms _term,InfoFound i, Recognition.TypesTerms t,boolean amb, int pos,int nw,Recognition.VerificationInfo ver){
        word=w;
        term=_term;
        info=i;
        type=t;
        ambiguous=amb;
        position=pos;
        verif=ver;
        nWord=nw;
     
        
    }
     
     public void putBagData(BagData bgd){
         bdata=bgd;
     }
     
     public void write(){
         
         System.out.println("el token es "+word+" "+term.PPN);
        // System.out.println("he entrado por escritura del token");
     }
    
}
