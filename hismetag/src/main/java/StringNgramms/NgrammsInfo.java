/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringNgramms;

import Recognition.InfoFound;
import DataStructures.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class NgrammsInfo {
    public String ngramms;
    
    public BagData bgdata;
    public boolean ambiguous;
   
    
    public Recognition.VerificationInfo verif;
    public NgrammsInfo(String ng, Recognition.VerificationInfo ver){
        ngramms=ng;
        //info=in;
        //type=t;
        //term=ter;
        verif=ver;
    }
    
     public NgrammsInfo(String ng, Recognition.VerificationInfo ver, boolean amb,BagData bgd){
        ngramms=ng;
        
        verif=ver;
        ambiguous=amb;
        bgdata=bgd;
    }
    
    public String getFirst(){
        
        String [] array=ngramms.split(" ");
       
        return array[0];
    }
}
