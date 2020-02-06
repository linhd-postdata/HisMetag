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
    
    public BuildingsContext(SemanticContext previous, Lexer lexer){
        super(previous, lexer);
    }
   
    public ContextualList getContext(){
        return ContextualList.BUILDINGS;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
      try{
         // System.out.println("entro por minusculas buillding "+word+" "+this.lexer.getLastToken());
          if (this.lexer.getLastToken()!=""){
          //    System.out.println("LA PRIMERA "+this.lexer.getLastToken());
            //  this.lexer.wbag.escribir();
              this.lexer.wbag.put(new BuildingBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
        	 this.lexer.setLastToken("");
          }
          if (ElementsRecognition.isDeterminantPrep(word)){
            //  System.out.println("LA PRIMERA 2 "+this.lexer.getLastToken()+" "+word);
        	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
              //this.lexer.setLastToken("");
              return this.lexer.context;
          }
          
       if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
             //    System.out.println("LA PRIMERA 3 "+this.lexer.getLastToken()+" "+word);
        	  this.lexer.setLastToken("");
        	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
        	  bgd.type=Terms.AFPLN;
        	  this.lexer.wbag.put(bgd);
        	  return this.lexer.context;
          }
          
          if (checkVerb(word)){
              //     System.out.println("LA PRIMERA 4 "+this.lexer.getLastToken()+" "+word);
              this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
            return this.lexer.context;
          }
       
          if (checkSpecialContext(word)){ 
                //  System.out.println("LA PRIMERA 5 "+this.lexer.getLastToken()+" "+word);
                 
                  this.lexer.setLastToken("");
                  this.lexer.wbag.restart();
                  //Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                  this.lexer.clearPreviousContext();
        	  return this.lexer.context.changeContext(ContextualList.INITIAL, new GeneralContext(null), " "," ");
          }
          
         
         //      System.out.println("LA PRIMERA 6 "+this.lexer.getLastToken()+" "+word);
             //  this.lexer.wbag.escribir();
         // System.out.println("LA PRIMERA 6 "+this.lexer.getLastToken()+" "+word);
          BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
    	  this.lexer.wbag.put(bgd);
          if ( !(TermsRecognition.isCommonName(word))){bgd.type=Terms.AFPLN;this.lexer.wbag.put(bgd);
        //  System.out.println("LA PRIMERA 7 "+this.lexer.getLastToken()+" "+word);
         // this.lexer.wbag.escribir();
          
          }
          else {
        //      System.out.println("LA PRIMERA 8 "+this.lexer.getLastToken()+" "+word);
        //  this.lexer.wbag.escribir();
              this.lexer.wbag.restart();
              	this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
              Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
          }
    	   
    	  this.lexer.setLastToken("");
        //  this.lexer.wbag.escribir();
           return this.lexer.context;
      }catch(Exception e){return this.lexer.context;}
    }
    public ContextualList checkCapitalLettersWord(String word){
      try{
          
    	//  this.lexer.wbag.escribir();
          if (ElementsRecognition.isDeterminantPrep(word.toLowerCase())){
        	  if (this.lexer.getLastToken()!="")
        	  this.lexer.wbag.put(new BuildingBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
        	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
              this.lexer.setLastToken(word);
              return this.lexer.context.getContext();
          }
          
          if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
        	 // System.out.println("he entrado por el determinante2"+word);
        	  this.lexer.setLastToken("");
        	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
        	  bgd.type=Terms.AFPLN;
        	  this.lexer.wbag.put(bgd);
        	  return this.lexer.context.getContext();
          }
          
          this.lexer.wbag.put(new BuildingBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
    	  
          BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
    	  bgd.type=Terms.AFPLN;
    	  this.lexer.wbag.put(bgd);
    	  this.lexer.setLastToken("");
           return this.lexer.context.getContext();
      }catch(Exception e){return this.getContext();}
    
    }
    
    public ContextualList wordListProcessing(String string){
       try{
           
    	   //System.out.println("la lista de geog"+string);
    	   if (this.lexer.getLastToken()!="")
      	 this.lexer.wbag.put(new BuildingBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
     	  
           BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
     	  bgd.type=Terms.AFPLN;
     	  this.lexer.wbag.put(bgd);
             return this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
           
       }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    public ContextualList nounPhraseProcessing(String string){
         try{
        	// System.out.println("la lista de geog"+string);
        	 if (this.lexer.getLastToken()!="")
        	 this.lexer.wbag.put(new BuildingBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
       	  
             BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
       	  bgd.type=Terms.AFPLN;
       	  this.lexer.wbag.put(bgd);
               return this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
             
             
         }catch(Exception e){return ContextualList.INITIAL;}
         
    }
    
    
    
     
         public ContextualList prepositionalSyntagmsListProcessing(String string){
          try{
        	//  System.out.println("entro por prepositional buillding "+string+" "+this.lexer.getLastToken());
         //     this.lexer.wbag.escribir();
              
              if (this.lexer.getLastToken()!="")
            	  
              this.lexer.wbag.put(new BuildingBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
        	  
              
              BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
        	  bgd.type=Terms.AFPLN;
        	  this.lexer.wbag.put(bgd);
        	  this.lexer.setLastToken("");
              return this.lexer.context.getContext();
    
    }catch(Exception e){return this.lexer.context.getContext();}
     
             
             
            }

    
}
