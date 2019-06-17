/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import Data.MedievalPlaceNamesTable;
import DataStructures.*;
import IOModule.Input;
import IOModule.Output;
import MedievalTextLexer.Lexer;
import Recognition.ElementsRecognition;
import Recognition.InfoFound;
import Recognition.NewTermsIdentification;
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import StringInProcess.TokenizedString;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class BuildingsContext extends SemanticContext {
    
    public BuildingsContext(SemanticContext previous){super(previous);}
   
    public ContextualList getContext(){
        return ContextualList.BUILDINGS;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
      try{
         // System.out.println("entro por minusculas buillding "+word+" "+Lexer.lastToken);
          if (Lexer.lastToken!=""){
          //    System.out.println("LA PRIMERA "+Lexer.lastToken);
            //  Lexer.wbag.escribir();
              Lexer.wbag.put(new BuildingBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
        	 Lexer.lastToken="";
          }
          if (ElementsRecognition.isDeterminantPrep(word)){
            //  System.out.println("LA PRIMERA 2 "+Lexer.lastToken+" "+word);
        	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
              //Lexer.lastToken="";
              return Lexer.context;
          }
          
       if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
             //    System.out.println("LA PRIMERA 3 "+Lexer.lastToken+" "+word);
        	  Lexer.lastToken="";
        	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
        	  bgd.type=Terms.AFPLN;
        	  Lexer.wbag.put(bgd);
        	  return Lexer.context;
          }
          
          if (checkVerb(word)){
              //     System.out.println("LA PRIMERA 4 "+Lexer.lastToken+" "+word);
              Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
            return Lexer.context;
          }
       
          if (checkSpecialContext(word)){ 
                //  System.out.println("LA PRIMERA 5 "+Lexer.lastToken+" "+word);
                 
                  Lexer.lastToken="";
                  Lexer.wbag.restart();
                  //Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                  Lexer.previousContextStack.clear();
        	  return Lexer.context.changeContext(ContextualList.INITIAL, new GeneralContext(null), " "," ");
          }
          
         
         //      System.out.println("LA PRIMERA 6 "+Lexer.lastToken+" "+word);
             //  Lexer.wbag.escribir();
         // System.out.println("LA PRIMERA 6 "+Lexer.lastToken+" "+word);
          BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
    	  Lexer.wbag.put(bgd);
          if ( !(TermsRecognition.isCommonName(word))){bgd.type=Terms.AFPLN;Lexer.wbag.put(bgd);
        //  System.out.println("LA PRIMERA 7 "+Lexer.lastToken+" "+word);
         // Lexer.wbag.escribir();
          
          }
          else {
        //      System.out.println("LA PRIMERA 8 "+Lexer.lastToken+" "+word);
        //  Lexer.wbag.escribir();
              Lexer.wbag.restart();
              	Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
              Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
          }
    	   
    	  Lexer.lastToken="";
        //  Lexer.wbag.escribir();
           return Lexer.context;
      }catch(Exception e){return Lexer.context;}
    }
    public ContextualList checkCapitalLettersWord(String word){
      try{
          
    	//  Lexer.wbag.escribir();
          if (ElementsRecognition.isDeterminantPrep(word.toLowerCase())){
        	  if (Lexer.lastToken!="")
        	  Lexer.wbag.put(new BuildingBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
        	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
              Lexer.lastToken=word;
              return Lexer.context.getContext();
          }
          
          if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
        	 // System.out.println("he entrado por el determinante2"+word);
        	  Lexer.lastToken="";
        	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
        	  bgd.type=Terms.AFPLN;
        	  Lexer.wbag.put(bgd);
        	  return Lexer.context.getContext();
          }
          
          Lexer.wbag.put(new BuildingBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
    	  
          BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
    	  bgd.type=Terms.AFPLN;
    	  Lexer.wbag.put(bgd);
    	  Lexer.lastToken="";
           return Lexer.context.getContext();
      }catch(Exception e){return this.getContext();}
    
    }
    
    public ContextualList wordListProcessing(String string){
       try{
           
    	   //System.out.println("la lista de geog"+string);
    	   if (Lexer.lastToken!="")
      	 Lexer.wbag.put(new BuildingBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
     	  
           BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
     	  bgd.type=Terms.AFPLN;
     	  Lexer.wbag.put(bgd);
             return Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
           
       }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    public ContextualList nounPhraseProcessing(String string){
         try{
        	// System.out.println("la lista de geog"+string);
        	 if (Lexer.lastToken!="")
        	 Lexer.wbag.put(new BuildingBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
       	  
             BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
       	  bgd.type=Terms.AFPLN;
       	  Lexer.wbag.put(bgd);
               return Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
             
             
         }catch(Exception e){return ContextualList.INITIAL;}
         
    }
    
    
    
     
         public ContextualList prepositionalSyntagmsListProcessing(String string){
          try{
        	//  System.out.println("entro por prepositional buillding "+string+" "+Lexer.lastToken);
         //     Lexer.wbag.escribir();
              
              if (Lexer.lastToken!="")
            	  
              Lexer.wbag.put(new BuildingBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
        	  
              
              BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
        	  bgd.type=Terms.AFPLN;
        	  Lexer.wbag.put(bgd);
        	  Lexer.lastToken="";
              return Lexer.context.getContext();
    
    }catch(Exception e){return Lexer.context.getContext();}
     
             
             
            }

    
}
