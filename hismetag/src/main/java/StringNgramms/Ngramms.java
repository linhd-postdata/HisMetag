/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringNgramms;

import MedievalTextLexer.Lexer;
import Recognition.InfoFound;
import Recognition.NewTermsIdentification;
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import java.util.*;
import java.util.function.Predicate;
import DataStructures.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class Ngramms {
    public ArrayList<NgrammsInfo> ngramms=new ArrayList<NgrammsInfo>();
    
    public Ngramms(){;}
    public Ngramms(ArrayList<String> list, int n){
        ;
        
    }
    
    public void buildNgramms(String[] list,int n){
      // System.out.println("ESTOY CONSTRUYENDO LOS BUILD");
        for (int i = 0; i < list.length-n+1; i++)
            ngramms.add(new NgrammsInfo(concat(list, i, i+n),null,false,new BagData()));
       
       
    }
    public int tam(){
        return ngramms.size();
    }
    
    public Ngramms getNgramms(){
        cleanNgramms();
        return this;
    }
    
    public void recognizeNgramms(int i){
       
         NgrammsInfo NI=ngramms.get(i);
      
         InfoFound info=TermsRecognition.checkPlaceName(NI.ngramms);
         
         
         if (info!=null) {
            
            if (TermsRecognition.isProperName(NI.ngramms.toLowerCase()) ){
               
                if (info.gazetteer.equals("Geonames")){
                   
                    NI.bgdata.infoPlace=new InfoFound("","","","");
                    NI.bgdata.type=Terms.PN;
                    NI.bgdata.subtype=TypesTerms.FT;
                    NI.verif=VerificationInfo.FOUND;
                }else{
                    NI.bgdata.infoPlace=info;
                   NI.bgdata.type=Terms.APN;
                    NI.bgdata.subtype=TypesTerms.AT;
                    NI.verif=VerificationInfo.VALIDATE;
                }
                }else{
                    NI.bgdata.infoPlace=info;
                    NI.bgdata.type=Terms.PLN;
                    NI.bgdata.subtype=TypesTerms.FT;
                    NI.verif=VerificationInfo.FOUND;
                
                  }  
             }else {
                 if (TermsRecognition.isProperName(NI.ngramms)){
                    NI.bgdata.infoPlace=new InfoFound("","","","");
                    NI.bgdata.type=Terms.PN;
                    NI.bgdata.subtype=TypesTerms.FT;
                    NI.verif=VerificationInfo.FOUND;
                     }else {
                     
                 InfoFound infoAprox=NewTermsIdentification.getAproximation(NI.ngramms);
                 if (infoAprox==null){
                    NI.bgdata.infoPlace=new InfoFound("","","","");
                    NI.bgdata.type=Terms.UN;
                    NI.bgdata.subtype=TypesTerms.UNI;
                    NI.verif=VerificationInfo.UNIDENTIFIED;
                   
                }
                else{
                    NI.bgdata.infoPlace=infoAprox;
                    NI.bgdata.type=Terms.PPLN;
                    NI.bgdata.subtype=TypesTerms.GENT;
                    NI.verif=VerificationInfo.VALIDATE;
                   
                } 
                 }
             }
             
    }
    private void cleanNgramms(){
        for (int i=0;i<ngramms.size();i++) ngramms.set(i, new NgrammsInfo(ngramms.get(i).ngramms.replaceAll("_"," "),null,false,null));
    }
    
    public void updateNgrammsList(){
        
       System.out.println ("estoy actualizando ngramas "+this.ngramms.size());
       
      ArrayList<String> remove=new ArrayList<String>();
      for (int i=0; i<ngramms.size();i++){
          NgrammsInfo element=ngramms.get(i);
          if (element.bgdata!=null && element.bgdata.subtype!=TypesTerms.UNI){
              String[] els=element.ngramms.split(" ");
              for (int j=0;j<els.length;j++){
              System.out.println("Las palabars que voy a borrar "+els[j]);
                  remove.add(els[j]);}
              }
          }
      
       System.out.println ("estoy actualizando ngramasdos ");     
      
      for (int i=0;i<remove.size();i++){
          String wors=remove.get(i);
         
          for (int j=ngramms.size()-1; j>=0;j--){
              if ( (ngramms.get(j).ngramms.contains(wors))){
               //   System.out.println("lo ngramas que estoy borranfdo "+j);
                  ngramms.remove(j);
              }
          }
      }
    }
  
        
   public void  addInfo(int i, String word,  Recognition.VerificationInfo ver,boolean am,BagData bgd){
       ngramms.set(i, new NgrammsInfo(word,ver,am,bgd));
   }   
   
   public void addDefaultInfo( ){
       for (int i=0; i<ngramms.size();i++){
           ngramms.set(i,new NgrammsInfo(ngramms.get(i).ngramms,null,false,new BagData()));
       }
   }
   
   public void addDefaultInfo(Recognition.Terms type){
       for (int i=0; i<ngramms.size();i++){
          // ngramms.set(i,new NgrammsInfo(ngramms.get(i).ngramms,new InfoFound(null,null,null,null),type,null,null));
       }
   }
   
   public void removeNString(String word){
	   for (int i=ngramms.size()-1; i>=0; i--){
		   if (ngramms.get(i).ngramms.contains(word)) ngramms.remove(i);
	   }
   }
           
    
           

    private static String concat(String[] words, int start, int end) {
        
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
    
   
    
    public String firstWord(int i){
        String[] words=ngramms.get(i).ngramms.split(" ");
        return words[0];
    }
    
    public void write(){
        
        for (int i=0;i<ngramms.size();i++){
            
            String gazetter;
            if (ngramms.get(i).bgdata.infoPlace!=null)gazetter=ngramms.get(i).bgdata.infoPlace.gazetteer; else gazetter="no tiene";
            System.out.println(ngramms.get(i).ngramms);

            
        }
    }
    
    public void removeNgrammWithWord(String word){
        for (int i=ngramms.size()-1; i>=0; i--){
            if (ngramms.get(i).ngramms.contains(word)) ngramms.remove(i);
        }
    }
    
    
}
