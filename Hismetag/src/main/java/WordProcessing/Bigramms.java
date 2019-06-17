/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordProcessing;
import java.util.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class Bigramms {
    static public ArrayList<String> getBigramm(String word){
        ArrayList<String> bigramm= new ArrayList<String>();
        for (int i=1;i<word.length();i++){
            StringBuilder bi = new StringBuilder();
            bi.append(word.charAt(i-1));
            bi.append(word.charAt(i));
            String binew=bi.toString();

                bigramm.add(binew);
                
        }
        
        return bigramm;
    }
    
    static public int getUnique(ArrayList<String> bg){
        int numUniques=0;
        
        for (int i=0; i<bg.size(); i++){
            String s=bg.get(i);
           
            
            for (int j=0; j<bg.size(); j++){
                
                 if (j!=i && bg.get(j).equals(s)){bg.remove(j);}
            }
               
            
         numUniques++;
         
        }
      return numUniques;      
    }
    
    static public boolean contain(ArrayList<String> big, String bi){
        for (int i=0; i<big.size();i++){
            if (big.get(i).equals(bi)) return true;
        }
        return false;
    }
    
    static public int commonsBi(ArrayList<String> bi1, ArrayList<String> bi2){
        int nCommon=0;
        for (int i=0; i<bi1.size();i++){
            if (contain(bi2,bi1.get(i))) nCommon++;
        }
        return nCommon;
    }
    
    static public float distanceBi(String word1, String word2){
        ArrayList<String> bg1,bg2=new ArrayList<String>();
        bg1=getBigramm(word1);
        bg2=getBigramm(word2);
        int nbg1=getUnique(bg1);
        
        
        int nbg2=getUnique(bg2);
        
        
        int ncommon=commonsBi(bg1,bg2);
       
        float distance=(2*ncommon)/(float)(nbg1+nbg2);
        
        return distance;
    }
    
}
