/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import Data.MedievalNewPlaceNamesTable;
import DataStructures.*;
import Data.NewProperNamesTable;
import Data.Verbs;
import IOModule.Input;
import IOModule.Output;
import MedievalTextLexer.Lexer;
import Recognition.InfoFound;
import Recognition.NewTermsIdentification;
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import StringInProcess.TokenizedString;
import StringNgramms.Ngramms;
import StringNgramms.NgrammsInfo;

/**
 *
 * @author M Luisa Díez Platas
 */
public class PosessiveContext extends SemanticContext {
    public PosessiveContext(Lexer lexer){
    	super(lexer);
    }
   
    public PosessiveContext(SemanticContext previous, Lexer lexer){
        super(previous, lexer);
    }
   
    public ContextualList getContext(){
        return ContextualList.POSESSIVE;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
      try{
    	// System.out.println("minusculas>>>>posesivo "+word);
         
    //     this.lexer.wbag.escribir();
         ContextualList context=determineContext(word,this);
       
      //  System.out.println("estoy en esta entrada de minusculas "+context);
       
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.INITIAL){
        	   this.lexer.context.changeContext(context, this.lexer.context," ", word);
            this.lexer.setTheFirst(false);
           return this.lexer.context;
        }else return this.lexer.context;
        
         if (Data.NickNamesTable.contains(word)){ 
        	//System.out.println("los nicks"+this.lexer.context.getContext());
        	 int position=this.lexer.numCh-word.length()-1;
        	 
        	 	this.lexer.wbag.put(new NickNameBagData(this.lexer.getWordBag()+" "+word,TypesTerms.FT,position,this.lexer.numWord-1,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
        return this.lexer.context.changeContext(ContextualList.INITIAL, this," ", " ");
         }
        if (Data.AuthorityNamesTable.contains(word)){
           
        	{ 
        	 	this.lexer.wbag.put(new NickNameBagData(this.lexer.getWordBag()+" "+word,TypesTerms.FT,1,1,1,new InfoFound(),this.lexer.context.getContext()));
        
        	 	return this.lexer.context.changeContext(ContextualList.INITIAL, this," ", " ");
         }
        }
        
        if (checkVerb(word)){  this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
            return this.lexer.context;}
        
        if (check(word)){ this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
        return this.lexer.context;}
       //  System.out.println("entro por el posesivo de autoridad"+this.lexer.peekPreviousContext().getContext());
         this.lexer.clearPreviousContext();
      //   System.out.println("por que no escribe mi mujer");
         this.lexer.context.changeContext(ContextualList.INITIAL,this.lexer.context," ", " ");
       this.lexer.wbag.restart();
         return this.lexer.context.checkLowerCaseWord(word);
         
       
        
      }catch(Exception e){return this.lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");}
    }
    
    public ContextualList checkCapitalLettersWord(String word){
    	try{
      // 	 System.out.println("minusculas>>>>posesivo "+word);
            if (Data.NickNamesTable.contains(word.toLowerCase())){ 
           	// System.out.println("los nicks");
           	 	this.lexer.wbag.put(new NickNameBagData(this.lexer.getWordBag()+" "+word,TypesTerms.FT,1,1,1,new InfoFound(),this.lexer.context.getContext()));
           return this.lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
            }
           if (Data.AuthorityNamesTable.contains(word.toLowerCase())){
           	{ 
           	 	this.lexer.wbag.put(new NickNameBagData(this.lexer.getWordBag()+" "+word,TypesTerms.FT,1,1,1,new InfoFound(),this.lexer.context.getContext()));
           
           	 	return this.lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
            }
           }
           this.lexer.context.changeContext(ContextualList.INITIAL,this," "," ");
           Output.write(new RoleTreeNode(this.lexer.getWordBag()));
           return this.lexer.context.checkLowerCaseWord(word).getContext();
      }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    public ContextualList nounPhraseProcessing(String word){
    	try{
    		this.lexer.currentString=new TokenizedString(word);
            String firstWord=this.lexer.currentString.tokenList.get(0).word;
          //	 System.out.println("minusculas>>>>posesivo "+word);
               if (Data.NickNamesTable.contains(firstWord.toLowerCase())){ 
              	// System.out.println("los nicks");
              	 	this.lexer.wbag.put(new NickNameBagData(this.lexer.getWordBag()+" "+firstWord,TypesTerms.FT,1,1,1,new InfoFound(),this.lexer.context.getContext()));
              
              	 	this.lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
              	 	this.lexer.currentString.cut(1);
              	 	//System.out.println("la que han quedado tras el corte "+this.lexer.currentString.tokenList.size());
              	 	String stringInProcess=this.lexer.currentString.getString();
              	 	//System.out.println("EL NICK NAME QUE HE ENCONTRADO "+stringInProcess);
              	 	if (Recognition.ElementsRecognition.isDeterminantPrep(this.lexer.currentString.tokenList.get(0).word)){
              	 		
              	 		return this.lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
              	 	}
              	 	else{
              	 		return this.lexer.context.nounPhraseProcessing(stringInProcess);
              	 	}
               }
              if (Data.AuthorityNamesTable.contains(firstWord.toLowerCase())){
              	{ 
              	 	this.lexer.wbag.put(new NickNameBagData(this.lexer.getWordBag()+" "+firstWord,TypesTerms.FT,1,1,1,new InfoFound(),this.lexer.context.getContext()));
              
              	 	return this.lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
               }
              }
              this.lexer.context.changeContext(ContextualList.INITIAL,this," "," ");
              Output.write(new RoleTreeNode(this.lexer.getWordBag()));
              return this.lexer.context.checkLowerCaseWord(word).getContext();
         }catch(Exception e){return ContextualList.INITIAL;}
    }
    public ContextualList wordListProcessing(String string){
       try{
           
         return  this.lexer.context.getContext();

       }
       catch(Exception e){return ContextualList.INITIAL;}
    }
    
    
     public ContextualList prepositionalSyntagmsListProcessing(String string){
         //System.out.println("estoy ennes esteeeee"+string);
         
         
         return null;}
     
}
