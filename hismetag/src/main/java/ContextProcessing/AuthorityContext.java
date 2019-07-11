/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import Data.*;
import IOModule.Input;
import IOModule.Output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MedievalTextLexer.Lexer;
import Recognition.ElementsRecognition;
import Recognition.InfoFound;
import Recognition.NewTermsIdentification;
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import StringInProcess.Token;
import StringInProcess.TokenizedString;
import StringNgramms.Ngramms;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;
import DataStructures.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class AuthorityContext extends SemanticContext {
    
      
    public AuthorityContext(){
    	
    }	
    
    
    public AuthorityContext(SemanticContext previous){
        super(previous);
       
        
        }
    public ContextualList getContext(){
        return ContextualList.AUTHORITY;
    }
    
 
    
    public SemanticContext checkLowerCaseWord(String wordProcess){
      try{
          //System.out.println("PROQUE NO VA EL LOWWR"+wordProcess);
         // Lexer.wbag.escribir();
          String word=WordProcessing.WordTransformations.replaceGuion(wordProcess);
           BagData ultimo=new BagData();
          if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
          } 
       // System.out.println("LA BOLSA ANTES DE NETRAR EN MINUSCULAS authority "+Lexer.wbag.tam()+wordProcess);
         // Lexer.wbag.escribir();
          
      
        ContextualList context=determineContext(word,null);
        Verbs elVerb;
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.AUTHORITY) {
            Lexer.isTheFirst=false;
           	Lexer.context.changeContext(context, Lexer.context," ", word);
            return Lexer.context;
        } else return Lexer.context;
       // System.out.println("estoy en NOUUUUUUU"+wordProcess);
        if (Data.AppositionPrepositionsTable.contains(word)){
              if (Lexer.wbag.tam()>0) Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext()));
           return Lexer.context;
        }
     
        if (checkVerb(word)){
            Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
            return Lexer.context;
        }
        
       if (check(word)){
           if (Recognition.ElementsRecognition.isCopulativeConjunction(word.toLowerCase()).size()>0)
               return Lexer.context;
           Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
            
           return Lexer.context;}
        
        
        
        
       InfoFound info=TermsRecognition.existMedievalPlaceName(WordProcessing.WordTransformations.capitalize(word));
       
        boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
        String resultado="";
        int res=0;
       
        if (info!=null){resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        
        
        if (Data.NickNamesTable.contains(word)){
           
            if (ultimo.type==Terms.PSS){
               
                ultimo.string+=" "+word;
                ultimo.type=Terms.NPN;
                
                }else{
            Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
            for (int i=0;i<Lexer.wbag.tam();i++){
                  }
            }
        } 
        else {
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+resultado+word);
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word);
                
                if (infoAprox!=null){
                    
                    if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else {
                   String ultimoTerm=null;
                    if (Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.DE){
                          Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                          ultimoTerm=Lexer.wbag.get(Lexer.wbag.tam()-1).string;
                    }
                        Lexer.wbag.restart();
                       if (ultimoTerm!=null) Output.write(new RoleTreeNode(ultimoTerm));
                      
                	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));}
                } else {
                    
                    if (Lexer.wbag.tam()>0 && Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.DE){
                        Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
            
                    }else{
                    ContextProcessing.SemanticContext previous=Lexer.previousContextStack.pop();
                    
                          Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    	Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
                    return Lexer.context;
                    }
                 
                }
            }/* buscar una variante */;
            break;
            case 1: {
               if (resultado.charAt(0)=='1') Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
               
                if (resultado.charAt(1)=='1') Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                    Lexer.context.changeContext(ContextualList.INITIAL, this, "", word);
                     String ultimoTerm=null;
                    if (Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.DE){
                        ultimoTerm=Lexer.wbag.get(Lexer.wbag.tam()-1).string;
                          Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                          
                    }
                        Lexer.wbag.restart();
                       if (ultimoTerm!=null) Output.write(new RoleTreeNode(ultimoTerm));
                      
                	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                 
                    
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames"))  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
               
                }
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                     String ultimoTerm=null;
                    if (Lexer.wbag.get(Lexer.wbag.tam()-1).type==Terms.DE){
                        ultimoTerm=Lexer.wbag.get(Lexer.wbag.tam()-1).string;  
                        Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                          
                    }
                        Lexer.wbag.restart();
                       if (ultimoTerm!=null) Output.write(new RoleTreeNode(ultimoTerm));
                      
                	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));}
               break;
            }
            case 3:{
                
                     Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    
            break;
            }
        }
        }
        ContextProcessing.SemanticContext previous=Lexer.previousContextStack.pop();
       Lexer.context.changeContext(previous.getContext(),previous," ", " ");
        
     
           return Lexer.context;
      }catch(Exception e){return Lexer.context;}
    }
    
    public ContextualList checkCapitalLettersWord(String word){
      try{
          
        //System.out.println("LA BOLSA ANTES DE NETRAR EN MAYUSCULAS authority"+word+"aaa");
         BagData ultimo=new BagData();
          if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
          }
           boolean IsTheFirst=false;
           word=word.replaceAll("\\s+", " ");
        ContextualList context=determineContext(word,this);
     
           if (context!=ContextualList.SAME)
          if (context!=ContextualList.AUTHORITY) {
        	  Lexer.isTheFirst=false;
             	Lexer.context.changeContext(context, Lexer.context," ", word);
              return context;
        } else {
              Lexer.wbag.put(new AuthorityBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
              return Lexer.context.getContext();}
          if (checkVerb(word)){
            return Lexer.context.getContext();
            }
          if (check(word)){
        	  Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), Lexer.context," ", word);
        	  return context;
          }
         
          Verbs elVerb;
          
       //System.out.println("NOUN DCHA "+word);
       InfoFound info=TermsRecognition.findPlaceName(word);
       
        boolean isProperName=TermsRecognition.isProperName(word);
        boolean isCommonName=TermsRecognition.isCommonName(word);
        String resultado="";
        int res=0;
        
        if (info!=null){resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        //System.out.println("los resultados encontrados son "+res+resultado);
        if (Data.NickNamesTable.contains(word.toLowerCase())){
            if (ultimo.type==Terms.PSS){
                ultimo.string+=" "+word;
                ultimo.type=Terms.NPN;
                
             }else{
            Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.wbag.tam(),Lexer.numCh,Lexer.numWord,new InfoFound(),Lexer.context.getContext()));
            for (int i=0;i<Lexer.wbag.tam();i++){
             }
            }
        } else if (Data.DeityTable.contains(word.toLowerCase())){
         Lexer.wbag.put(new DeityBagData(word,TypesTerms.FT,Lexer.wbag.tam(),Lexer.numCh,Lexer.numWord,new InfoFound(),Lexer.context.getContext()));
 
        }
        else {
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
                
                if (infoAprox!=null){
                    if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else{
                	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                }
               
                } else {
                    
                    Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    Data.NewProperNamesTable.newName(word, Input.name);
                }
            }/* buscar una variante */;
            case 1: {
                 if (resultado.charAt(0)=='1')
                    Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                
               
                if (resultado.charAt(1)=='1') Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                   	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                    Output.write(word);
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames")) {
                        Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                     }
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
               
                    
                    
                }
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    Lexer.wbag.restart();
                    Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord)); 
                	
                }
                break;
            }
            case 3:{
                
                    Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    
            break;
            }
        }
        }
                
       
                return context;
      }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    
    
    public ContextualList wordListProcessing(String string){
      try{
           BagData ultimo=new BagData();
          if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
          }
        
          
           String newString=string.replaceAll("\\s+", " ");
         readCleanString=new String(newString);
       
         
         //tokenize the string
         
         TokenizedString token=new TokenizedString(newString);
         //token.countWordChar();
        
         Lexer.currentString=token;
        
         String firstWord=new String(Lexer.currentString.tokenList.get(0).word);
         String stringInProcess=newString;
         ContextualList recognizedContext=ContextualList.INITIAL;
           
         
        ContextualList newContext=this.determineContext(firstWord, this);
         if (newContext!=ContextualList.SAME)
        if (newContext!=ContextualList.AUTHORITY){
            stringInProcess=newString.substring(firstWord.length()+1);
             Lexer.context=changeContext(newContext,Lexer.context,stringInProcess,firstWord);
             Lexer.currentString.tokenList.remove(0);
             if (token.tokenList.size()>1)
             return Lexer.context.wordListProcessing(stringInProcess);
             else return Lexer.context.checkCapitalLettersWord(stringInProcess);
       
        } else return Lexer.context.getContext();
        if (checkVerb(firstWord)){
            stringInProcess=newString.substring(firstWord.length()+1);
            Lexer.currentString.tokenList.remove(0);
        	 if (Lexer.currentString.tokenList.size()>1) return Lexer.context.wordListProcessing(stringInProcess);
                else return Lexer.context.checkCapitalLettersWord(stringInProcess);
        
        }
        if (check(firstWord)){
        	stringInProcess=newString.substring(firstWord.length()+1);
        	Lexer.currentString.tokenList.remove(0);
        	 if (Lexer.currentString.tokenList.size()>1) return Lexer.context.wordListProcessing(stringInProcess);
                else return Lexer.context.checkCapitalLettersWord(stringInProcess);
        }
        
        
        	 
             boolean hasAuTr=false;
             
             
         stringInProcess=Lexer.currentString.processingArticles(stringInProcess);
         
           //check if the string has copulative conjunctions and separates it in the parts according to the conjunction
         String[] subs=Lexer.currentString.processingCopulativeConjunctions(stringInProcess);
        
      if (subs.length>1){
         
            for (int i=0;i<subs.length;i++){
            // divide the substrings according to the spaces
            String [] newS=subs[i].split(" ");
       
            Lexer.currentString.buildNgramms(newS);
            }
            
         for (int i=0; i<Lexer.currentString.tokenList.size(); i++){
             if (TermsRecognition.isAuthorityOrTreatement(Lexer.currentString.tokenList.get(i).word)){
                 hasAuTr=true;
                Lexer.currentString.ngramms.removeNgrammWithWord(Lexer.currentString.tokenList.get(i).word);
             }
         }
  
         }else{
          
          for (int i=0;i<subs.length;i++){
            // divide the substrings according to the spaces
            String [] newS=subs[i].split(" ");
            Lexer.currentString.buildNgramms(newS);
            
         }
         }
         Ngramms ngramms=Lexer.currentString.ngramms.getNgramms();
         
         for (int i=ngramms.tam()-1;i>=0;i--){
             
             
        

             
             
             
             
             
             
             NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
          InfoFound info=TermsRecognition.findPlaceName(NI.ngramms);
             
        boolean isProperName=TermsRecognition.isProperName(NI.ngramms.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(NI.ngramms.toLowerCase());
        String resultado="";
        int res=0;
         if (info!=null){
            resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
         if (Data.NickNamesTable.tID.containsKey(NI.ngramms.toLowerCase())){
            if (ultimo.type==Terms.PSS){
                ultimo.string+=" "+NI.ngramms;
                ultimo.type=Terms.NPN;
                BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                Lexer.numCh=Lexer.numCh+ultimo.string.length();
                Lexer.numWord+=2;
                ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
                int index=Lexer.currentString.getIndex(NI.ngramms);
                Lexer.currentString.tokenList.get(index).word=ultimo.string;
                Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                  }else{
               
               BagData bgd=new NickNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
               
               Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                //Lexer.numWord++;
                
                ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                }
        }else if (Data.AuthorityNamesTable.tID.containsKey(NI.ngramms.toLowerCase())){
            BagData bgd=new AuthorityBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext());
           Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                //Lexer.numWord++;
                
                ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
        }else if (Data.PosessiveTable.contains(NI.ngramms)){
        	
            BagData bgd=new PossesiveBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
            
            Lexer.numCh=Lexer.numCh+NI.ngramms.length();
             //Lexer.numWord++;
             
             ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                 }else {
       
        switch (res){
            case 0: {
                ngramms.ngramms.get(i).bgdata=new BagData();
                break;
               
            }
                   
            case 1: {
                if (resultado.charAt(0)=='1') {
                  if (!(info.gazetteer.contains("Geonames"))){
                    BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext(),false);
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                     }else{
                      ngramms.ngramms.get(i).bgdata=new BagData();
                  }
                }
           
                if (resultado.charAt(1)=='1'){
                    BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                if (resultado.charAt(2)=='1') {
                    BagData bgd=new BagData(NI.ngramms,TypesTerms.AT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                    bgd.type=Terms.UN;
                    
                    
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    
                   if (info.gazetteer.contains("Geonames")){
                    	
                BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                     
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                        
                    }
                     else {
                       BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    if (info.gazetteer.contains("Geonames"))  {
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                        
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                        //Lexer.numWord++;
                    }
                    else {
                       BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext(),true);
                       Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                       //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames")){
                        BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
            else {
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
            break;
            }
        }
        } 
          }
         
         
        for (int i=0; i<ngramms.tam();i++){
           // System.out.println ("las token gram "+ngramms.ngramms.get(i).ngramms+" "+ngramms.ngramms.get(i).bgdata.type);
        }
        //  System.out.println ("ESTOY EN MEDIO DE TODO ---------------------------------------------");  
           
            /******************************************************/
        
             
             
         
         Lexer.currentString.updateTokenList();
                 
        Lexer.currentString.ngramms.updateNgrammsList();
         
             for (int j=0;j<Lexer.currentString.tokenList.size();j++){
          
             }
             if (Lexer.isTheFirst){
            	if (Lexer.currentString.tokenList.get(0).term==null) {
             		String stringRE=Lexer.currentString.tokenList.get(0).word;
             		Lexer.currentString.ngramms.removeNString(stringRE);
             		Lexer.currentString.tokenList.remove(0);
             		Output.write(new RoleTreeNode(stringRE));
             	}
              } 
             boolean encontrado=false;
         for (int  i=ngramms.tam()-1;i>=0;i--){
             
             NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
             if (NI.bgdata.type==Terms.VACIO){
                 
                 encontrado=true;
                 InfoFound infoAprox=NewTermsIdentification.getAproximation(NI.ngramms.toLowerCase());
                 if (infoAprox!=null){
                    
                    if (infoAprox.uri=="proper"){
                        BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext());
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                        //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                    }
                else if (infoAprox.uri=="nick"){
                    BagData bgd=new NickNameBagData(NI.ngramms,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext());
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                }
                else{
                    BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),infoAprox,Lexer.context.getContext(),false);
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                }
             
                } else{
                     BagData bgd=new BagData(NI.ngramms,TypesTerms.UNI,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext());
                     bgd.type=Terms.UN;
                     Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                
                 }
             }
             }
         
         
         if (encontrado){
          Lexer.currentString.updateTokenList();
        
        
                 
        Lexer.currentString.ngramms.updateNgrammsList();
         }
           
         
          for (int  i=ngramms.tam()-1;i>=0;i--){
             NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
             if (NI.bgdata.type==Terms.VACIO || NI.bgdata.type==Terms.UN){
                  BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext());
                 Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++; 
                 ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
             
                } 
             
         }
          
            
             for (int j=0;j<ngramms.tam();j++){
              }
                
             
         
        Lexer.currentString.sendOutInfo();
       
       
         Lexer.currentString.tokenList.clear();
         Lexer.currentString.ngramms.ngramms.clear();
        
         //}
         return ContextualList.INITIAL;
     }
       catch(Exception e){return ContextualList.INITIAL;}
    }
    public ContextualList prepositionalSyntagmsListProcessing(String string){
         try{
             
        	// System.out.println("AUTHORITY: Procesamiento con aposici�n ->"+string+Lexer.numCh);
             
             BagData ultimo=new BagData();
             if (Lexer.wbag.tam()>0){
                 ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
             }   
            String newString=string.replaceAll("\\s+", " ");
          
            //tokenize the stirng
            Lexer.currentString=new TokenizedString(newString);
            
            for (int i=0; i<Lexer.currentString.tokenList.size();i++){
                Token token=Lexer.currentString.tokenList.get(i);
                if (Recognition.ElementsRecognition.isDeterminantPrep(token.word)){
                	token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
                }else
                if (Recognition.ElementsRecognition.isCopulativeConjunction(token.word).size()>0){
                   
                    
                    token.bdata=new CopulativeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
               
                }
            }
        
            
            newString=Lexer.currentString.processingArticles(newString);
            
           
            newString=Lexer.currentString.processingAppositionsPrepositions(newString);
                   

            String[] newStrings=Lexer.currentString.processingCopulativeConjunctions(newString);
            String firstWord=Lexer.currentString.tokenList.get(0).word;
             
          
             
            for (int i=0; i<newStrings.length;i++){
                String[] aux=newStrings[i].split(" ");
                for (int j=0; j<aux.length;j++){
                    BagData bgd=new BagData(aux[j],TypesTerms.UNI,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux[j].length(),new InfoFound(),Lexer.context.getContext());
                    //Lexer.numWord++;
                    Lexer.numCh+=aux[j].length();                 
                   Lexer.currentString.ngramms.ngramms.add(new NgrammsInfo(aux[j],VerificationInfo.UNIDENTIFIED,false,bgd));
            }
            } 
            
            
            Lexer.currentString.ngramms.write();
            for (int i=0;i<Lexer.currentString.ngramms.ngramms.size();i++){
                String aux=Lexer.currentString.ngramms.ngramms.get(i).ngramms;
                aux=aux.replaceAll("_", " ");
                aux=WordTransformations.removeCleanPattern(Data.AppositionPrepositionsTable.regEx, aux);
                
                
             InfoFound info=TermsRecognition.existMedievalPlaceName(aux);
          
           boolean isProperName=TermsRecognition.isProperName(aux.toLowerCase());
           boolean isCommonName=TermsRecognition.isCommonName(aux.toLowerCase());
           String resultado="";
           int res=0;
           if (info!=null){
                resultado+="1";res++;} else resultado+="0";
           if (isProperName){resultado+="1";res++;} else resultado+="0";
           if (isCommonName){resultado+="1";res++;} else resultado+="0";
           if (Data.NickNamesTable.tID.containsKey(aux.toLowerCase())){
               if (ultimo.type==Terms.PSS){
                    ultimo.string+=" "+aux;
                   ultimo.type=Terms.NPN;
                   BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                   Lexer.numCh=Lexer.numCh+ultimo.string.length();
                   Lexer.numWord+=2;
                   Lexer.currentString.ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
                   int index=Lexer.currentString.getIndex(aux);
                   Lexer.currentString.tokenList.get(index).word=ultimo.string;
                   Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                    }else{
                  BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                  Lexer.numCh=Lexer.numCh+ultimo.string.length();
                   //Lexer.numWord++;
                   Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
              
               }
           }else if (Recognition.ElementsRecognition.isDeterminantPrep(string)){
            //   System.out.println("DETER");
           }
           else {
         // System.out.println("Hemos en contrado las isguientes propuestas "+res+" "+resultado);
           switch (res){
               case 0: {
                  InfoFound infoAprox=NewTermsIdentification.getAproximation(aux.toLowerCase());
                    if (infoAprox!=null){
                       
                       if (infoAprox.uri=="proper"){
                           BagData bgd=new ProperNameBagData(aux,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(),new InfoFound(),Lexer.context.getContext());
                           Lexer.numCh=Lexer.numCh+aux.length();
                           //Lexer.numWord++;
                           Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                       }
                   else if (infoAprox.uri=="nick"){
                       BagData bgd=new NickNameBagData(aux,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(),new InfoFound(),Lexer.context.getContext());
                       Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                       Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                   }
                   else{
                       BagData bgd=new PlaceNameBagData(aux,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(),infoAprox,Lexer.context.getContext(),false);
                       Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                       Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                   }
                
                   } else{
                  
                        BagData bgd=new PlaceNameBagData(aux,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext(),false);
                        
                        Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                       Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                   
                    }
                   
                   
                   break;
                  
               }
                      
               case 1: {
                 //  System.out.println("El caracter = es "+resultado.charAt(1));
                   if (resultado.charAt(0)=='1') {
                      BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                       Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                       Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                   }
              
                   if (resultado.charAt(1)=='1'){
                       BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                       Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                       Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                   }
              
                   if (resultado.charAt(2)=='1') {
                       BagData bgd=new PlaceNameBagData(aux,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext(),false);
                       Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                       Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                  
                   }
               break;
               }
               case 2:{
                   if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                       
                      if (info.gazetteer.contains("Geonames")){
                           BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                           Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                           Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
                        else {
                           BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),true);
                           Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                           Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
              
                   }
                   if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                     
                       if (info.gazetteer.contains("Geonames"))  {
                    	   Output.write(new RoleTreeNode(aux));
                           Output.write(aux);
                           Lexer.numCh=Lexer.numCh+aux.length();
                           //Lexer.numWord++;
                       }
                       else {
                          BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),true);
                          Lexer.numCh=Lexer.numCh+aux.length();
                           
                          //Lexer.numWord++;
                           Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                          
                       }
              
                   }
                   break;
               }
               case 3:{
                   
                       if (info.gazetteer.contains("Geonames")){
                           BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                           Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                           Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
               else {
                           BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),true);
                           Lexer.numCh=Lexer.numCh+aux.length();
                       //Lexer.numWord++;
                           Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
              
               break;
               }
           }
           } 
            
            }
           
            Lexer.currentString.updateTokenList();
          
           Lexer.currentString.sendOutInfo();
      
           
            Lexer.currentString.tokenList.clear();
            Lexer.currentString.ngramms.ngramms.clear();
          
        
         
         Lexer.context=Lexer.previousContextStack.pop();
        
         return Lexer.context.changeContext(ContextualList.INITIAL, this, " "," ").getContext();
     
    
    }catch(Exception e){return ContextualList.INITIAL;}
     }
    
     public ContextualList nounPhraseProcessing(String string){
    	 try{
             //System.out.println("he entrado por NOUN phrase "+string);
             String newString=string.replaceAll("\\s+", " ");
             String stringInProcess="";
         //   System.out.println("ha entrado ppor NOUNPORCES"+Lexer.context.getContext());
            String de=TermsRecognition.findApposition(newString);
            Lexer.currentString=new TokenizedString(newString);
            String firstWord=Lexer.currentString.tokenList.get(0).word;
           
          
           
           int res=0;
           String resultado="";
           
           InfoFound info=TermsRecognition.checkPlaceName(string);
             if (info!=null){
               res++;
               resultado+='1';
           }else resultado+='0';
           
           if (TermsRecognition.isProperName(string)){
              res++;
               resultado+='1';
           }else resultado+='0'; 
           
           if (TermsRecognition.isSaintName(string)){
               res++;
               resultado+='1';
           }else resultado+='0';
           
           switch(res){
               case 0: break;
               case 1:{
                   if (resultado.charAt(0)=='1') {
                       BagData bgd=new PlaceNameBagData(string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),info,Lexer.context.getContext(),false);
                       Lexer.wbag.put(bgd);
                       return Lexer.context.getContext();
                   }
                   if (resultado.charAt(1)=='1') {
                       BagData bgd=new ProperNameBagData(string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
                       Lexer.wbag.put(bgd);
                       return Lexer.context.getContext();
                   }
                   if (resultado.charAt(2)=='1') {
                        BagData bgd=new SaintNameBagData(string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),info,Lexer.context.getContext());
                       Lexer.wbag.put(bgd);
                       return Lexer.context.getContext();
                   }
               }
               case 2:{
                   if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1'){
                       BagData bgd=new ProperNameBagData(string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
                       Lexer.wbag.put(bgd);
                       return Lexer.context.getContext();
                   }
                   if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1'){
                       BagData bgd=new SaintNameBagData(string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),info,Lexer.context.getContext());
                       Lexer.wbag.put(bgd);
                       return Lexer.context.getContext();
                   }
               }
           }
                       
           String tosplitedString=newString.replaceAll(" "+de+" ", "_"+de+"_");
                 ContextualList context=null;
               String[] stringArray=tosplitedString.split("_"+de+"_");
               
               String[] izda=stringArray[0].split(" ");
               String[] dcha=stringArray[1].split(" ");
               String recognized="";
               int encontrada=-1;
              // System.out.println("NOUN NOUN NOUN "+izda[0]+Lexer.context);  
               //System.out.println("NOUN NOUN NOUN "+dcha[0]);  
              /* for (int i=0; i<izda.length; i++){
                   context=determineContext(izda[i],this);
                   //Lexer.wbag.clean();
                   recognized=izda[i]+" ";
             if (context!=ContextualList.AUTHORITY) {
            	 Lexer.isTheFirst=false;
                	Lexer.context.changeContext(context, Lexer.context," ", " ");
                
               encontrada=i;
               break;
               }
              }*/
               //System.out.println("NOUN NOUN NOUN "+izda[0]+Lexer.context);  
            if (encontrada!=-1){
                   if (izda.length==1) {
                       stringInProcess=newString.substring(stringArray[0].length());
                       
                      //System.out.println("el contexto es NOUN"+Lexer.context+"  "+stringInProcess);
                       return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                   }
                   if (izda.length==2){
                       if (encontrada==1){
                           stringInProcess=newString.substring(stringArray[0].length());
                          Lexer.context.checkCapitalLettersWord(izda[0]);
                           
                           Lexer.context.changeContext(context,Lexer.context,stringInProcess,izda[1]);
                          return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                       }else {
                           stringInProcess=newString.substring(izda[0].length());
                           
                           Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[0]);
                          
                           return Lexer.context.nounPhraseProcessing(stringInProcess);
                       }
                   }
                   if (izda.length>2){
                       if (encontrada==izda.length-1){
                          // System.out.println("he entrado por la leng 3 ");
                           stringInProcess=newString.substring(stringArray[0].length());
                           String inicio=izda[0];
                           for (int j=1; j<izda.length-1; j++){
                               inicio+=" "+izda[j];
                           }
                          // System.out.println("he entrado por la leng 4 "+inicio+" "+stringInProcess+" "+Lexer.context.getContext());
                           Lexer.context.wordListProcessing(inicio);
                          // System.out.println ("El tamaño de la bolsa es "+Lexer.wbag.tam());
                           Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[izda.length-1]);
                           for (int k=0;k<Lexer.wbag.tam();k++){
                          // System.out.println ("la vida es "+Lexer.wbag.get(k).string);
                           }
                           return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                       
                           
                       }else{
                          // System.out.println("he entrado por la leng 5 "+encontrada);
                           
                           String inicio=izda[0];
                           for (int j=1; j<encontrada+1; j++){
                               inicio+=" "+izda[j];
                           }
                           stringInProcess=newString.substring(inicio.length()+1);
                           Lexer.context.wordListProcessing(inicio);
                          // System.out.println("he entrado por la leng 7"+inicio+"TTTT"+stringInProcess+" "+Lexer.context.getContext());
                           
                          // System.out.println ("El tamaño de la bolsa es "+Lexer.wbag.tam());
                           Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[izda.length-1]);
                           for (int k=0;k<Lexer.wbag.tam();k++){
                           //System.out.println ("la vida es "+Lexer.wbag.get(k).string);
                           }
                           //System.out.println("la verdad"+izda.length);
                           return Lexer.context.nounPhraseProcessing(stringInProcess);
                       
                           
                           
                       }
                   }
                   
               }
               
               
              
              if (izda.length>1){
               Lexer.context.wordListProcessing(stringArray[0]); }
              else {
                  //System.out.println("NUOOOOOOOOOOOO "+Lexer.context+stringArray[0]);
                  Lexer.context.checkCapitalLettersWord(izda[0]);
                  
              }
              //System.out.println("despues de NOUUUUUUU izda"+Lexer.context);
             // Lexer.wbag.escribir();
             
              // System.out.println("LA BOSLA DE "+Lexer.wbag.tam());
               Lexer.wbag.put(new DeBagData(de,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext()));
               Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context, stringInProcess,dcha[0]);

               if (dcha.length>1)
               Lexer.context.wordListProcessing(dcha[0]);
               else{
                   
               Lexer.context.checkCapitalLettersWord(dcha[0]);
               
               }
               
                //System.out.println("despues de NOUUUUUUU dcha"+dcha[0]+" "+Lexer.context);
             // Lexer.wbag.escribir();
            return ContextualList.INITIAL;         
            
           
           
            }catch (Exception e){return ContextualList.INITIAL;}
        
     }
    
}