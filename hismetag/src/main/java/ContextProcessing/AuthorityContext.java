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

    public AuthorityContext(Lexer lexer, Output output){
        super(lexer, output);
    }	

    public AuthorityContext(SemanticContext previous, Lexer lexer, Output output){
        super(previous, lexer, output);
    }

    public ContextualList getContext(){
        return ContextualList.AUTHORITY;
    }

    public SemanticContext checkLowerCaseWord(String wordProcess){
      try{
          //System.out.println("PROQUE NO VA EL LOWWR"+wordProcess);
         // this.lexer.wbag.escribir();
          String word=WordProcessing.WordTransformations.replaceGuion(wordProcess);
           BagData ultimo=new BagData();
          if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
          } 
       // System.out.println("LA BOLSA ANTES DE NETRAR EN MINUSCULAS authority "+this.lexer.wbag.tam()+wordProcess);
         // this.lexer.wbag.escribir();
          
      
        ContextualList context=determineContext(word,null);
        Verbs elVerb;
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.AUTHORITY) {
            this.lexer.setTheFirst(false);
           	this.lexer.context.changeContext(context, this.lexer.context," ", word);
            return this.lexer.context;
        } else return this.lexer.context;
       // System.out.println("estoy en NOUUUUUUU"+wordProcess);
        if (Data.AppositionPrepositionsTable.contains(word)){
              if (this.lexer.wbag.tam()>0) this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh,new InfoFound(),this.lexer.context.getContext()));
           return this.lexer.context;
        }
     
        if (checkVerb(word)){
            this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
            return this.lexer.context;
        }
        
        if (check(word)){
            if (Recognition.ElementsRecognition.isCopulativeConjunction(word.toLowerCase()).size()>0)
                return this.lexer.context;
            this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
            
            return this.lexer.context;}

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
            this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
            for (int i=0;i<this.lexer.wbag.tam();i++){
                  }
            }
        } 
        else {
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+resultado+word);
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word);
                
                if (infoAprox!=null){
                    
                    if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else {
                   String ultimoTerm=null;
                    if (this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.DE){
                          this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                          ultimoTerm=this.lexer.wbag.get(this.lexer.wbag.tam()-1).string;
                    }
                        this.lexer.wbag.restart();
                       if (ultimoTerm!=null) this.output.write(new RoleTreeNode(ultimoTerm));
                      
                	this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));}
                } else {
                    
                    if (this.lexer.wbag.tam()>0 && this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.DE){
                        this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
            
                    }else{
                    ContextProcessing.SemanticContext previous=this.lexer.getPreviousContext();
                    
                          this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    	this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
                    return this.lexer.context;
                    }
                 
                }
            }/* buscar una variante */;
            break;
            case 1: {
               if (resultado.charAt(0)=='1') this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
               
                if (resultado.charAt(1)=='1') this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                    this.lexer.context.changeContext(ContextualList.INITIAL, this, "", word);
                     String ultimoTerm=null;
                    if (this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.DE){
                        ultimoTerm=this.lexer.wbag.get(this.lexer.wbag.tam()-1).string;
                          this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                          
                    }
                        this.lexer.wbag.restart();
                       if (ultimoTerm!=null) this.output.write(new RoleTreeNode(ultimoTerm));
                      
                	this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                 
                    
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames"))  this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
               
                }
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                     String ultimoTerm=null;
                    if (this.lexer.wbag.get(this.lexer.wbag.tam()-1).type==Terms.DE){
                        ultimoTerm=this.lexer.wbag.get(this.lexer.wbag.tam()-1).string;  
                        this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                          
                    }
                        this.lexer.wbag.restart();
                       if (ultimoTerm!=null) this.output.write(new RoleTreeNode(ultimoTerm));
                      
                	this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));}
               break;
            }
            case 3:{
                
                     this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    
            break;
            }
        }
        }
        ContextProcessing.SemanticContext previous=this.lexer.getPreviousContext();
       this.lexer.context.changeContext(previous.getContext(),previous," ", " ");
        
     
           return this.lexer.context;
      }catch(Exception e){return this.lexer.context;}
    }
    
    public ContextualList checkCapitalLettersWord(String word){
      try{
          
        //System.out.println("LA BOLSA ANTES DE NETRAR EN MAYUSCULAS authority"+word+"aaa");
         BagData ultimo=new BagData();
          if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
          }
           boolean IsTheFirst=false;
           word=word.replaceAll("\\s+", " ");
        ContextualList context=determineContext(word,this);
     
           if (context!=ContextualList.SAME)
          if (context!=ContextualList.AUTHORITY) {
        	  this.lexer.setTheFirst(false);
             	this.lexer.context.changeContext(context, this.lexer.context," ", word);
              return context;
        } else {
              this.lexer.wbag.put(new AuthorityBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
              return this.lexer.context.getContext();}
          if (checkVerb(word)){
            return this.lexer.context.getContext();
            }
          if (check(word)){
        	  this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this.lexer.context," ", word);
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
            this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.wbag.tam(),this.lexer.numCh,this.lexer.numWord,new InfoFound(),this.lexer.context.getContext()));
            for (int i=0;i<this.lexer.wbag.tam();i++){
             }
            }
        } else if (Data.DeityTable.contains(word.toLowerCase())){
         this.lexer.wbag.put(new DeityBagData(word,TypesTerms.FT,this.lexer.wbag.tam(),this.lexer.numCh,this.lexer.numWord,new InfoFound(),this.lexer.context.getContext()));
 
        }
        else {
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
                
                if (infoAprox!=null){
                    if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else{
                	this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                }
               
                } else {
                    
                    this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    Data.NewProperNamesTable.newName(word, Input.name);
                }
            }/* buscar una variante */;
            case 1: {
                 if (resultado.charAt(0)=='1')
                    this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                
               
                if (resultado.charAt(1)=='1') this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                   	this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                    this.output.write(word);
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames")) {
                        this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                     }
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
               
                    
                    
                }
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    this.lexer.wbag.restart();
                    this.output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord)); 
                	
                }
                break;
            }
            case 3:{
                
                    this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    
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
          if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
          }
        
          
           String newString=string.replaceAll("\\s+", " ");
         readCleanString=new String(newString);
       
         
         //tokenize the string
         
         TokenizedString token=new TokenizedString(newString, this.lexer);
         //token.countWordChar();
        
         this.lexer.currentString=token;
        
         String firstWord=new String(this.lexer.currentString.tokenList.get(0).word);
         String stringInProcess=newString;
         ContextualList recognizedContext=ContextualList.INITIAL;
           
         
        ContextualList newContext=this.determineContext(firstWord, this);
         if (newContext!=ContextualList.SAME)
        if (newContext!=ContextualList.AUTHORITY){
            stringInProcess=newString.substring(firstWord.length()+1);
             this.lexer.context=changeContext(newContext,this.lexer.context,stringInProcess,firstWord);
             this.lexer.currentString.tokenList.remove(0);
             if (token.tokenList.size()>1)
             return this.lexer.context.wordListProcessing(stringInProcess);
             else return this.lexer.context.checkCapitalLettersWord(stringInProcess);
       
        } else return this.lexer.context.getContext();
        if (checkVerb(firstWord)){
            stringInProcess=newString.substring(firstWord.length()+1);
            this.lexer.currentString.tokenList.remove(0);
        	 if (this.lexer.currentString.tokenList.size()>1) return this.lexer.context.wordListProcessing(stringInProcess);
                else return this.lexer.context.checkCapitalLettersWord(stringInProcess);
        
        }
        if (check(firstWord)){
        	stringInProcess=newString.substring(firstWord.length()+1);
        	this.lexer.currentString.tokenList.remove(0);
        	 if (this.lexer.currentString.tokenList.size()>1) return this.lexer.context.wordListProcessing(stringInProcess);
                else return this.lexer.context.checkCapitalLettersWord(stringInProcess);
        }
        
        
        	 
             boolean hasAuTr=false;
             
             
         stringInProcess=this.lexer.currentString.processingArticles(stringInProcess);
         
           //check if the string has copulative conjunctions and separates it in the parts according to the conjunction
         String[] subs=this.lexer.currentString.processingCopulativeConjunctions(stringInProcess);
        
      if (subs.length>1){
         
            for (int i=0;i<subs.length;i++){
            // divide the substrings according to the spaces
            String [] newS=subs[i].split(" ");
       
            this.lexer.currentString.buildNgramms(newS);
            }
            
         for (int i=0; i<this.lexer.currentString.tokenList.size(); i++){
             if (TermsRecognition.isAuthorityOrTreatement(this.lexer.currentString.tokenList.get(i).word)){
                 hasAuTr=true;
                this.lexer.currentString.ngramms.removeNgrammWithWord(this.lexer.currentString.tokenList.get(i).word);
             }
         }
  
         }else{
          
          for (int i=0;i<subs.length;i++){
            // divide the substrings according to the spaces
            String [] newS=subs[i].split(" ");
            this.lexer.currentString.buildNgramms(newS);
            
         }
         }
         Ngramms ngramms=this.lexer.currentString.ngramms.getNgramms();
         
         for (int i=ngramms.tam()-1;i>=0;i--){
             
             
        

             
             
             
             
             
             
             NgrammsInfo NI=this.lexer.currentString.ngramms.ngramms.get(i);
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
                BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
                this.lexer.numCh=this.lexer.numCh+ultimo.string.length();
                this.lexer.numWord+=2;
                ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
                int index=this.lexer.currentString.getIndex(NI.ngramms);
                this.lexer.currentString.tokenList.get(index).word=ultimo.string;
                this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                  }else{
               
               BagData bgd=new NickNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
               
               this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                //this.lexer.numWord++;
                
                ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                }
        }else if (Data.AuthorityNamesTable.tID.containsKey(NI.ngramms.toLowerCase())){
            BagData bgd=new AuthorityBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),new InfoFound(),this.lexer.context.getContext());
           this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                //this.lexer.numWord++;
                
                ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
        }else if (Data.PosessiveTable.contains(NI.ngramms)){
        	
            BagData bgd=new PossesiveBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
            
            this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
             //this.lexer.numWord++;
             
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
                    BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), info,this.lexer.context.getContext(),false);
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                     }else{
                      ngramms.ngramms.get(i).bgdata=new BagData();
                  }
                }
           
                if (resultado.charAt(1)=='1'){
                    BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                if (resultado.charAt(2)=='1') {
                    BagData bgd=new BagData(NI.ngramms,TypesTerms.AT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
                    bgd.type=Terms.UN;
                    
                    
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    
                   if (info.gazetteer.contains("Geonames")){
                    	
                BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
                     
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                        
                    }
                     else {
                       BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), info,this.lexer.context.getContext(),true);
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    if (info.gazetteer.contains("Geonames"))  {
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), info,this.lexer.context.getContext(),true);
                        
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                        //this.lexer.numWord++;
                    }
                    else {
                       BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), info,this.lexer.context.getContext(),true);
                       this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                       //this.lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames")){
                        BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
            else {
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), info,this.lexer.context.getContext(),true);
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
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
        
             
             
         
         this.lexer.currentString.updateTokenList();
                 
        this.lexer.currentString.ngramms.updateNgrammsList();
         
             for (int j=0;j<this.lexer.currentString.tokenList.size();j++){
          
             }
             if (this.lexer.isTheFirst()){
            	if (this.lexer.currentString.tokenList.get(0).term==null) {
             		String stringRE=this.lexer.currentString.tokenList.get(0).word;
             		this.lexer.currentString.ngramms.removeNString(stringRE);
             		this.lexer.currentString.tokenList.remove(0);
             		this.output.write(new RoleTreeNode(stringRE));
             	}
              } 
             boolean encontrado=false;
         for (int  i=ngramms.tam()-1;i>=0;i--){
             
             NgrammsInfo NI=this.lexer.currentString.ngramms.ngramms.get(i);
             if (NI.bgdata.type==Terms.VACIO){
                 
                 encontrado=true;
                 InfoFound infoAprox=NewTermsIdentification.getAproximation(NI.ngramms.toLowerCase());
                 if (infoAprox!=null){
                    
                    if (infoAprox.uri=="proper"){
                        BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),new InfoFound(),this.lexer.context.getContext());
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                        //this.lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                    }
                else if (infoAprox.uri=="nick"){
                    BagData bgd=new NickNameBagData(NI.ngramms,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),new InfoFound(),this.lexer.context.getContext());
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                }
                else{
                    BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),infoAprox,this.lexer.context.getContext(),false);
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                }
             
                } else{
                     BagData bgd=new BagData(NI.ngramms,TypesTerms.UNI,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh,new InfoFound(),this.lexer.context.getContext());
                     bgd.type=Terms.UN;
                     this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                
                 }
             }
             }
         
         
         if (encontrado){
          this.lexer.currentString.updateTokenList();
        
        
                 
        this.lexer.currentString.ngramms.updateNgrammsList();
         }
           
         
          for (int  i=ngramms.tam()-1;i>=0;i--){
             NgrammsInfo NI=this.lexer.currentString.ngramms.ngramms.get(i);
             if (NI.bgdata.type==Terms.VACIO || NI.bgdata.type==Terms.UN){
                  BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),new InfoFound(),this.lexer.context.getContext());
                 this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++; 
                 ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
             
                } 
             
         }
          
            
             for (int j=0;j<ngramms.tam();j++){
              }
                
             
         
        this.lexer.currentString.sendOutInfo();
       
       
         this.lexer.currentString.tokenList.clear();
         this.lexer.currentString.ngramms.ngramms.clear();
        
         //}
         return ContextualList.INITIAL;
     }
       catch(Exception e){return ContextualList.INITIAL;}
    }
    public ContextualList prepositionalSyntagmsListProcessing(String string){
         try{
             
        	// System.out.println("AUTHORITY: Procesamiento con aposici�n ->"+string+this.lexer.numCh);
             
             BagData ultimo=new BagData();
             if (this.lexer.wbag.tam()>0){
                 ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
             }   
            String newString=string.replaceAll("\\s+", " ");
          
            //tokenize the stirng
            this.lexer.currentString=new TokenizedString(newString, this.lexer);
            
            for (int i=0; i<this.lexer.currentString.tokenList.size();i++){
                Token token=this.lexer.currentString.tokenList.get(i);
                if (Recognition.ElementsRecognition.isDeterminantPrep(token.word)){
                	token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext());
                }else
                if (Recognition.ElementsRecognition.isCopulativeConjunction(token.word).size()>0){
                   
                    
                    token.bdata=new CopulativeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext());
               
                }
            }
        
            
            newString=this.lexer.currentString.processingArticles(newString);
            
           
            newString=this.lexer.currentString.processingAppositionsPrepositions(newString);
                   

            String[] newStrings=this.lexer.currentString.processingCopulativeConjunctions(newString);
            String firstWord=this.lexer.currentString.tokenList.get(0).word;
             
          
             
            for (int i=0; i<newStrings.length;i++){
                String[] aux=newStrings[i].split(" ");
                for (int j=0; j<aux.length;j++){
                    BagData bgd=new BagData(aux[j],TypesTerms.UNI,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux[j].length(),new InfoFound(),this.lexer.context.getContext());
                    //this.lexer.numWord++;
                    this.lexer.numCh+=aux[j].length();                 
                   this.lexer.currentString.ngramms.ngramms.add(new NgrammsInfo(aux[j],VerificationInfo.UNIDENTIFIED,false,bgd));
            }
            } 
            
            
            this.lexer.currentString.ngramms.write();
            for (int i=0;i<this.lexer.currentString.ngramms.ngramms.size();i++){
                String aux=this.lexer.currentString.ngramms.ngramms.get(i).ngramms;
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
                   BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
                   this.lexer.numCh=this.lexer.numCh+ultimo.string.length();
                   this.lexer.numWord+=2;
                   this.lexer.currentString.ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
                   int index=this.lexer.currentString.getIndex(aux);
                   this.lexer.currentString.tokenList.get(index).word=ultimo.string;
                   this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                    }else{
                  BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
                  this.lexer.numCh=this.lexer.numCh+ultimo.string.length();
                   //this.lexer.numWord++;
                   this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
              
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
                           BagData bgd=new ProperNameBagData(aux,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(),new InfoFound(),this.lexer.context.getContext());
                           this.lexer.numCh=this.lexer.numCh+aux.length();
                           //this.lexer.numWord++;
                           this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                       }
                   else if (infoAprox.uri=="nick"){
                       BagData bgd=new NickNameBagData(aux,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(),new InfoFound(),this.lexer.context.getContext());
                       this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                       this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                   }
                   else{
                       BagData bgd=new PlaceNameBagData(aux,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(),infoAprox,this.lexer.context.getContext(),false);
                       this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                       this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                   }
                
                   } else{
                  
                        BagData bgd=new PlaceNameBagData(aux,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh,new InfoFound(),this.lexer.context.getContext(),false);
                        
                        this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                       this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                   
                    }
                   
                   
                   break;
                  
               }
                      
               case 1: {
                 //  System.out.println("El caracter = es "+resultado.charAt(1));
                   if (resultado.charAt(0)=='1') {
                      BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                       this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                       this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                   }
              
                   if (resultado.charAt(1)=='1'){
                       BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                       this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                       this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                   }
              
                   if (resultado.charAt(2)=='1') {
                       BagData bgd=new PlaceNameBagData(aux,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext(),false);
                       this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                       this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.VALIDATE,false,bgd));
                  
                   }
               break;
               }
               case 2:{
                   if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                       
                      if (info.gazetteer.contains("Geonames")){
                           BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                           this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                           this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
                        else {
                           BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),true);
                           this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                           this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
              
                   }
                   if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                     
                       if (info.gazetteer.contains("Geonames"))  {
                    	   this.output.write(new RoleTreeNode(aux));
                           this.output.write(aux);
                           this.lexer.numCh=this.lexer.numCh+aux.length();
                           //this.lexer.numWord++;
                       }
                       else {
                          BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),true);
                          this.lexer.numCh=this.lexer.numCh+aux.length();
                           
                          //this.lexer.numWord++;
                           this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                          
                       }
              
                   }
                   break;
               }
               case 3:{
                   
                       if (info.gazetteer.contains("Geonames")){
                           BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                           this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                           this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
               else {
                           BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),true);
                           this.lexer.numCh=this.lexer.numCh+aux.length();
                       //this.lexer.numWord++;
                           this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       }
              
               break;
               }
           }
           } 
            
            }
           
            this.lexer.currentString.updateTokenList();
          
           this.lexer.currentString.sendOutInfo();
      
           
            this.lexer.currentString.tokenList.clear();
            this.lexer.currentString.ngramms.ngramms.clear();
          
        
         
         this.lexer.context=this.lexer.getPreviousContext();
        
         return this.lexer.context.changeContext(ContextualList.INITIAL, this, " "," ").getContext();
     
    
    }catch(Exception e){return ContextualList.INITIAL;}
     }
    
     public ContextualList nounPhraseProcessing(String string){
    	 try{
             //System.out.println("he entrado por NOUN phrase "+string);
             String newString=string.replaceAll("\\s+", " ");
             String stringInProcess="";
         //   System.out.println("ha entrado ppor NOUNPORCES"+this.lexer.context.getContext());
            String de=TermsRecognition.findApposition(newString);
            this.lexer.currentString=new TokenizedString(newString, this.lexer);
            String firstWord=this.lexer.currentString.tokenList.get(0).word;
           
          
           
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
                       BagData bgd=new PlaceNameBagData(string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),info,this.lexer.context.getContext(),false);
                       this.lexer.wbag.put(bgd);
                       return this.lexer.context.getContext();
                   }
                   if (resultado.charAt(1)=='1') {
                       BagData bgd=new ProperNameBagData(string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
                       this.lexer.wbag.put(bgd);
                       return this.lexer.context.getContext();
                   }
                   if (resultado.charAt(2)=='1') {
                        BagData bgd=new SaintNameBagData(string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),info,this.lexer.context.getContext());
                       this.lexer.wbag.put(bgd);
                       return this.lexer.context.getContext();
                   }
               }
               case 2:{
                   if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1'){
                       BagData bgd=new ProperNameBagData(string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
                       this.lexer.wbag.put(bgd);
                       return this.lexer.context.getContext();
                   }
                   if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1'){
                       BagData bgd=new SaintNameBagData(string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),info,this.lexer.context.getContext());
                       this.lexer.wbag.put(bgd);
                       return this.lexer.context.getContext();
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
              // System.out.println("NOUN NOUN NOUN "+izda[0]+this.lexer.context);  
               //System.out.println("NOUN NOUN NOUN "+dcha[0]);  
              /* for (int i=0; i<izda.length; i++){
                   context=determineContext(izda[i],this);
                   //this.lexer.wbag.clean();
                   recognized=izda[i]+" ";
             if (context!=ContextualList.AUTHORITY) {
            	 this.lexer.setTheFirst(false);
                	this.lexer.context.changeContext(context, this.lexer.context," ", " ");
                
               encontrada=i;
               break;
               }
              }*/
               //System.out.println("NOUN NOUN NOUN "+izda[0]+this.lexer.context);  
            if (encontrada!=-1){
                   if (izda.length==1) {
                       stringInProcess=newString.substring(stringArray[0].length());
                       
                      //System.out.println("el contexto es NOUN"+this.lexer.context+"  "+stringInProcess);
                       return this.lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                   }
                   if (izda.length==2){
                       if (encontrada==1){
                           stringInProcess=newString.substring(stringArray[0].length());
                          this.lexer.context.checkCapitalLettersWord(izda[0]);
                           
                           this.lexer.context.changeContext(context,this.lexer.context,stringInProcess,izda[1]);
                          return this.lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                       }else {
                           stringInProcess=newString.substring(izda[0].length());
                           
                           this.lexer.context.changeContext(context, this.lexer.context, stringInProcess,izda[0]);
                          
                           return this.lexer.context.nounPhraseProcessing(stringInProcess);
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
                          // System.out.println("he entrado por la leng 4 "+inicio+" "+stringInProcess+" "+this.lexer.context.getContext());
                           this.lexer.context.wordListProcessing(inicio);
                          // System.out.println ("El tamaño de la bolsa es "+this.lexer.wbag.tam());
                           this.lexer.context.changeContext(context, this.lexer.context, stringInProcess,izda[izda.length-1]);
                           for (int k=0;k<this.lexer.wbag.tam();k++){
                          // System.out.println ("la vida es "+this.lexer.wbag.get(k).string);
                           }
                           return this.lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                       
                           
                       }else{
                          // System.out.println("he entrado por la leng 5 "+encontrada);
                           
                           String inicio=izda[0];
                           for (int j=1; j<encontrada+1; j++){
                               inicio+=" "+izda[j];
                           }
                           stringInProcess=newString.substring(inicio.length()+1);
                           this.lexer.context.wordListProcessing(inicio);
                          // System.out.println("he entrado por la leng 7"+inicio+"TTTT"+stringInProcess+" "+this.lexer.context.getContext());
                           
                          // System.out.println ("El tamaño de la bolsa es "+this.lexer.wbag.tam());
                           this.lexer.context.changeContext(context, this.lexer.context, stringInProcess,izda[izda.length-1]);
                           for (int k=0;k<this.lexer.wbag.tam();k++){
                           //System.out.println ("la vida es "+this.lexer.wbag.get(k).string);
                           }
                           //System.out.println("la verdad"+izda.length);
                           return this.lexer.context.nounPhraseProcessing(stringInProcess);
                       
                           
                           
                       }
                   }
                   
               }
               
               
              
              if (izda.length>1){
               this.lexer.context.wordListProcessing(stringArray[0]); }
              else {
                  //System.out.println("NUOOOOOOOOOOOO "+this.lexer.context+stringArray[0]);
                  this.lexer.context.checkCapitalLettersWord(izda[0]);
                  
              }
              //System.out.println("despues de NOUUUUUUU izda"+this.lexer.context);
             // this.lexer.wbag.escribir();
             
              // System.out.println("LA BOSLA DE "+this.lexer.wbag.tam());
               this.lexer.wbag.put(new DeBagData(de,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh,new InfoFound(),this.lexer.context.getContext()));
               this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context, stringInProcess,dcha[0]);

               if (dcha.length>1)
               this.lexer.context.wordListProcessing(dcha[0]);
               else{
                   
               this.lexer.context.checkCapitalLettersWord(dcha[0]);
               
               }
               
                //System.out.println("despues de NOUUUUUUU dcha"+dcha[0]+" "+this.lexer.context);
             // this.lexer.wbag.escribir();
            return ContextualList.INITIAL;         
            
           
           
            }catch (Exception e){return ContextualList.INITIAL;}
        
     }
    
}