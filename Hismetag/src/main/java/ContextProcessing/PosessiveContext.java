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
    public PosessiveContext(){
    	
   }
    public PosessiveContext(SemanticContext previous){super(previous);}
   
    public ContextualList getContext(){
        return ContextualList.POSESSIVE;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
      try{
    	// System.out.println("minusculas>>>>posesivo "+word);
         
    //     Lexer.wbag.escribir();
         ContextualList context=determineContext(word,this);
       
      //  System.out.println("estoy en esta entrada de minusculas "+context);
       
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.INITIAL){
        	   Lexer.context.changeContext(context, Lexer.context," ", word);
            Lexer.isTheFirst=false;
           return Lexer.context;
        }else return Lexer.context;
        
         if (Data.NickNamesTable.contains(word)){ 
        	//System.out.println("los nicks"+Lexer.context.getContext());
        	 int position=Lexer.numCh-word.length()-1;
        	 
        	 	Lexer.wbag.put(new NickNameBagData(Lexer.wordBag+" "+word,TypesTerms.FT,position,Lexer.numWord-1,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
        return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ");
         }
        if (Data.AuthorityNamesTable.contains(word)){
           
        	{ 
        	 	Lexer.wbag.put(new NickNameBagData(Lexer.wordBag+" "+word,TypesTerms.FT,1,1,1,new InfoFound(),Lexer.context.getContext()));
        
        	 	return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ");
         }
        }
        
        if (checkVerb(word)){  Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
            return Lexer.context;}
        
        if (check(word)){ Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
        return Lexer.context;}
       //  System.out.println("entro por el posesivo de autoridad"+Lexer.previousContextStack.peek().getContext());
         Lexer.previousContextStack.clear();
      //   System.out.println("por que no escribe mi mujer");
         Lexer.context.changeContext(ContextualList.INITIAL,Lexer.context," ", " ");
       Lexer.wbag.restart();
         return Lexer.context.checkLowerCaseWord(word);
         
       
        
      }catch(Exception e){return Lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");}
    }
    
    public ContextualList checkCapitalLettersWord(String word){
    	try{
      // 	 System.out.println("minusculas>>>>posesivo "+word);
            if (Data.NickNamesTable.contains(word.toLowerCase())){ 
           	// System.out.println("los nicks");
           	 	Lexer.wbag.put(new NickNameBagData(Lexer.wordBag+" "+word,TypesTerms.FT,1,1,1,new InfoFound(),Lexer.context.getContext()));
           return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
            }
           if (Data.AuthorityNamesTable.contains(word.toLowerCase())){
           	{ 
           	 	Lexer.wbag.put(new NickNameBagData(Lexer.wordBag+" "+word,TypesTerms.FT,1,1,1,new InfoFound(),Lexer.context.getContext()));
           
           	 	return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
            }
           }
           Lexer.context.changeContext(ContextualList.INITIAL,this," "," ");
           Output.write(new RoleTreeNode(Lexer.wordBag));
           return Lexer.context.checkLowerCaseWord(word).getContext();
      }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    public ContextualList nounPhraseProcessing(String word){
    	try{
    		Lexer.currentString=new TokenizedString(word);
            String firstWord=Lexer.currentString.tokenList.get(0).word;
          //	 System.out.println("minusculas>>>>posesivo "+word);
               if (Data.NickNamesTable.contains(firstWord.toLowerCase())){ 
              	// System.out.println("los nicks");
              	 	Lexer.wbag.put(new NickNameBagData(Lexer.wordBag+" "+firstWord,TypesTerms.FT,1,1,1,new InfoFound(),Lexer.context.getContext()));
              
              	 	Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
              	 	Lexer.currentString.cut(1);
              	 	//System.out.println("la que han quedado tras el corte "+Lexer.currentString.tokenList.size());
              	 	String stringInProcess=Lexer.currentString.getString();
              	 	//System.out.println("EL NICK NAME QUE HE ENCONTRADO "+stringInProcess);
              	 	if (Recognition.ElementsRecognition.isDeterminantPrep(Lexer.currentString.tokenList.get(0).word)){
              	 		
              	 		return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
              	 	}
              	 	else{
              	 		return Lexer.context.nounPhraseProcessing(stringInProcess);
              	 	}
               }
              if (Data.AuthorityNamesTable.contains(firstWord.toLowerCase())){
              	{ 
              	 	Lexer.wbag.put(new NickNameBagData(Lexer.wordBag+" "+firstWord,TypesTerms.FT,1,1,1,new InfoFound(),Lexer.context.getContext()));
              
              	 	return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ").getContext();
               }
              }
              Lexer.context.changeContext(ContextualList.INITIAL,this," "," ");
              Output.write(new RoleTreeNode(Lexer.wordBag));
              return Lexer.context.checkLowerCaseWord(word).getContext();
         }catch(Exception e){return ContextualList.INITIAL;}
    }
    public ContextualList wordListProcessing(String string){
       try{
           
         return  Lexer.context.getContext();

       }
       catch(Exception e){return ContextualList.INITIAL;}
    }
    
    
     public ContextualList prepositionalSyntagmsListProcessing(String string){
         //System.out.println("estoy ennes esteeeee"+string);
         
         
         return null;}
     
}
