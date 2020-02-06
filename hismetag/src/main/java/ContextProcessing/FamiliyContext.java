/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import Data.MedievalNewPlaceNamesTable;
import Data.NewProperNamesTable;
import DataStructures.*;
import DataStructures.ProperNameBagData;
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
import StringInProcess.Token;
import StringInProcess.TokenizedString;
import StringNgramms.Ngramms;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class FamiliyContext extends SemanticContext {
    public FamiliyContext(Lexer lexer){
    	super(lexer);
    }
   
    public FamiliyContext(SemanticContext previous, Lexer lexer){
        super(previous, lexer);
    }
   
    public ContextualList getContext(){
        return ContextualList.FAMILY;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
            //System.out.println("HIJO HIJO "+word);
            BagData ultimo=new BagData();
        	//System.out.println("estoy entramdopor family minusculas"+word);
        if (this.lexer.getLastToken()!=""){
         
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }else{
                  this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
        }   else{
                  this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
        }
          
          
         
         
         
           word=word.replaceAll("\\s+", " ");
        ContextualList context=determineContext(word,this);
        //System.out.println("no encuentro la palabra");
        if (context!=ContextualList.SAME)
          if (context!=ContextualList.FAMILY) {
        	  this.lexer.setTheFirst(false);
               	this.lexer.context.changeContext(context, this.lexer.context," ", word);
                return this.lexer.context;
        }else return this.lexer.context;
        if (Recognition.ElementsRecognition.isDeterminantPrep(word))  {
            this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
            	
            return this.lexer.context;
        }
        
        
        if (checkVerb(word)){
            this.lexer.setTheFirst(false);
            this.lexer.context.changeContext(context, this.lexer.context," ", word);
            this.lexer.setTheFirst(false);
           return this.lexer.context;
        }
           
          if (check(word)) {this.lexer.context.changeContext(context, this.lexer.context," ", word);
            this.lexer.setTheFirst(false);
           return this.lexer.context;}
        //  System.out.println("estoy por aqui tratando de ver por family que "+word);
       

        //if (this.lexer.firstToken==""){this.lexer.setTheFirst(true);}
       
       
       InfoFound info=TermsRecognition.existMedievalPlaceName(word);
       
        boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
        String resultado="";
        int res=0;
        
        if (info!=null){resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        
        
        if (Data.NickNamesTable.contains(word.toLowerCase())){
          
            this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.wbag.tam(),this.lexer.numCh,this.lexer.numWord,new InfoFound(),this.lexer.context.getContext()));
            
            
        } else if (Data.DeityTable.contains(word.toLowerCase())){
         this.lexer.wbag.put(new DeityBagData(word,TypesTerms.FT,this.lexer.wbag.tam(),this.lexer.numCh,this.lexer.numWord,new InfoFound(),this.lexer.context.getContext()));
 
        }
        else {
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+" "+resultado);
        switch (res){
            case 0: {
               // System.out.println ("entro por aqui"+this.lexer.isTheFirst());
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
               
                if (infoAprox!=null){
                   // System.out.println("estoy entrando por el 0");
                if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                break;
                } else {
                	if (this.lexer.isTheFirst()) {
                	if (this.lexer.wbag.tam()>0  ){
                            this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                                Data.ProperNamesTable.putNewName(word.toLowerCase(), Input.name, "Hismetag", "person");
                        }else{
                    	Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                    	this.lexer.setTheFirst(false);
                        }
                    	break;
                    }else{
                    BagData bgd=new BagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext());
                    bgd.type=Terms.UN;
                    this.lexer.wbag.put(bgd);
                    break;
                    }
                }
            }
                
            /* buscar una variante */
            case 1: {
               // System.out.println("El caracter = es "+word);
                if (resultado.charAt(0)=='1') this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                if (resultado.charAt(1)=='1') this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                //   System.out.println("he entrado por aqui en el case 1 "+word);
                    
                    InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
                
                if (infoAprox!=null){
                // System.out.println("estoy entrando por el 0");
                if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                
                }else {
                    Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                }
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames")) {
                        this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    //System.out.println("estoy entrando por el info gazeeet");
                    }
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
                
                    
                    
                }
               // System.out.println("lalalllallllallla");
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                    this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.AT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                
                }
               break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames"))  this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
                
            break;
            }
        }
        }
        this.lexer.setTheFirst(false);
       
                return this.lexer.context;
        }catch(Exception e){return this.lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
                 BagData ultimo=new BagData();
        //	System.out.println("estoy entramdopor family minusculas"+word);
        if (this.lexer.getLastToken()!=""){
         
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }else{
                  this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
        }   else{
                  this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
        }
         
         
           word=word.replaceAll("\\s+", " ");
        ContextualList context=determineContext(word,this);
        //System.out.println("no encuentro la palabra"+context);
      
          if (context!=ContextualList.FAMILY) {
             // System.out.println("la letra mayuscula "+word+this.lexer.getLastToken()+"WWW"+context);
        	  this.lexer.setTheFirst(false);
               	this.lexer.context.changeContext(context, this.lexer.context," ", word);
                return context;
        }
          
          if (checkVerb(word)){
              this.lexer.setTheFirst(false);return context;
          }
           
          if (check(word)) {this.lexer.setTheFirst(false);return context;}
         // System.out.println("estoy por aqui tratando de ver por que "+word);
       

        //if (this.lexer.firstToken==""){this.lexer.setTheFirst(true);}
      // System.out.println("cas"+word+"ss");
       
       
       InfoFound info=TermsRecognition.findPlaceName(word);
       
        boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
        String resultado="";
        int res=0;
        
        if (info!=null){resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        
        
        if (Data.NickNamesTable.contains(word.toLowerCase())){
         
            this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.wbag.tam(),this.lexer.numCh,this.lexer.numWord,new InfoFound(),this.lexer.context.getContext()));
            
           
        } else if (Data.DeityTable.contains(word.toLowerCase())){
         this.lexer.wbag.put(new DeityBagData(word,TypesTerms.FT,this.lexer.wbag.tam(),this.lexer.numCh,this.lexer.numWord,new InfoFound(),this.lexer.context.getContext()));
 
        }
        else {
            
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+" "+resultado);
        
        switch (res){
            case 0: {
             //   System.out.println ("entro por aqui"+this.lexer.isTheFirst());
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
               
                if (infoAprox!=null){
                   // System.out.println("estoy entrando por el 0");
                if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                break;
                } else {
                	if (this.lexer.isTheFirst()) {
                		//System.out.println("el grado es este "+word);
                    	Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                    	this.lexer.setTheFirst(false);
                    	break;
                    }else{
                    BagData bgd=new BagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext());
                    bgd.type=Terms.UN;
                    this.lexer.wbag.put(bgd);
                    break;
                    }
                }
            }
                
            /* buscar una variante */
            case 1: {
               // System.out.println("El caracter = es "+word);
                if (resultado.charAt(0)=='1') this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                if (resultado.charAt(1)=='1') this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                  // System.out.println("he entrado por aqui en el case 1 "+word);
                    
                    InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
                
                if (infoAprox!=null){
                // System.out.println("estoy entrando por el 0");
                if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                
                }else {
                    Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                }
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames")) {
                        this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    //System.out.println("estoy entrando por el info gazeeet");
                    }
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
                
                    
                    
                }
                //System.out.println("lalalllallllallla");
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                    this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.AT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                
                }
               break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames"))  this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
                
            break;
            }
        }
        }
        this.lexer.setTheFirst(false);
       
                return context;
        }catch(Exception e){return this.getContext();}
      
      }
      
    
    public ContextualList wordListProcessing(String string){
       try{
           
                BagData ultimo=new BagData();
        //	System.out.println("estoy entramdopor family wordlist"+string);
        if (this.lexer.getLastToken()!=""){
         
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
        }else{
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
            }
        }
          
         String newString=string.replaceAll("\\s+", " ");
         readCleanString=new String(newString);
       
         
         
         
         TokenizedString token=new TokenizedString(newString);
         
        
         this.lexer.currentString=token;
        
         String firstWord=new String(this.lexer.currentString.tokenList.get(0).word);
         String stringInProcess=newString;
         ContextualList recognizedContext=ContextualList.INITIAL;
           
         
        ContextualList newContext=this.determineContext(firstWord, this);
        
        if (newContext!=ContextualList.FAMILY){
           stringInProcess=newString.substring(firstWord.length()+1);
             this.lexer.context=changeContext(newContext,this.lexer.context,stringInProcess,firstWord);
             this.lexer.currentString.tokenList.remove(0);
             if (token.tokenList.size()>1)
             return this.lexer.context.wordListProcessing(stringInProcess);
             else return this.lexer.context.checkCapitalLettersWord(stringInProcess);
       
        }
       if (checkVerb(firstWord)){
        	stringInProcess=newString.substring(firstWord.length()+1);
        	this.lexer.currentString.tokenList.remove(0);
        	 if (this.lexer.currentString.tokenList.size()>1) return this.lexer.context.wordListProcessing(stringInProcess);
                else return this.lexer.context.checkCapitalLettersWord(stringInProcess);
        }
        if (check(firstWord)){
        	stringInProcess=newString.substring(firstWord.length()+1);
        	this.lexer.currentString.tokenList.remove(0);
        	//System.out.println("he entrado por check con"+firstWord);
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
       //System.out.println("LOS NEGRAMASS CONSTRUIDOS ");
            this.lexer.currentString.buildNgramms(newS);
           // System.out.println("LOS NEGRAMAS CONSTRUIDOS "+this.lexer.currentString.ngramms.tam());
            
         }
         // System.out.println("las cosas de las ngramas1 "+this.lexer.currentString.ngramms.ngramms.get(5).bgdata.type);
            // return this.lexer.context.checkCapitalLettersWord(subs[0]);
         }
         Ngramms ngramms=this.lexer.currentString.ngramms.getNgramms();
       //System.out.println("LAS COSAS QUE RECONOZCO FUERA de qaqui"+ngramms.tam());
         
         for (int i=ngramms.tam()-1;i>=0;i--){
             
             
        

             
             
             
             
             
             
             NgrammsInfo NI=this.lexer.currentString.ngramms.ngramms.get(i);
          //   System.out.println("LOS NGRAMAS QUESE CONSULTAN "+NI.ngramms);
             
             
             //InfoFound info=TermsRecognition..existMedievalPlaceName(NI.ngramms);
        InfoFound info=TermsRecognition.findPlaceName(NI.ngramms);
             
        boolean isProperName=TermsRecognition.isProperName(NI.ngramms.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(NI.ngramms.toLowerCase());
        String resultado="";
        int res=0;
        //System.out.println("he salido de la minuscula"+resultado+" "+res);
        if (info!=null){
            //System.out.println("he enrtado or planame ");
            resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        //System.out.println("LOS RESULATODS QUE HE TRAIDO HASTA AQUI "+NI.ngramms);
        if (Data.NickNamesTable.tID.containsKey(NI.ngramms.toLowerCase())){
           // System.out.println("he entrado por el mio cid ");
            if (ultimo.type==Terms.PSS){
               // System.out.println("EL ULTIMO ES "+ultimo.type);
                ultimo.string+=" "+NI.ngramms;
                ultimo.type=Terms.NPN;
                BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
                this.lexer.numCh=this.lexer.numCh+ultimo.string.length();
                this.lexer.numWord+=2;
                ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
               // System.out.println("la propuesta"+ngramms.ngramms.get(i).bgdata.nword);
                int index=this.lexer.currentString.getIndex(NI.ngramms);
                this.lexer.currentString.tokenList.get(index).word=ultimo.string;
                this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                //System.out.println("PINTAME EL ULTIMO "+this.lexer.wbag.tam()+" "+ngramms.ngramms.get(i).ngramms);
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
              //  System.out.println("EL AUTORIDA "+NI.ngramms);
                
                ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
        }else if (Data.PosessiveTable.contains(NI.ngramms)){
        		//System.out.println("es mio");
            BagData bgd=new PossesiveBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
            
            this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
             //this.lexer.numWord++;
             
             ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                 // System.out.println("estoy poniendo el mio cid "+NI.ngramms);
        }else {
       
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+NI.ngramms+" "+resultado);
        switch (res){
            case 0: {
            //   System.out.println("HE SALIDO POR AQUI CUANDO "+ngramms.ngramms.get(i).ngramms);
                ngramms.ngramms.get(i).bgdata=new BagData();
                break;
               
            }
                   
            case 1: {
              //  System.out.println("El caracter = es "+resultado.charAt(1));
                if (resultado.charAt(0)=='1') {
                 //   System.out.println("EL GAZETTER "+info.gazetteer);
                  if (!(info.gazetteer.contains("Geonames"))){
                 //  System.out.println("ENTRO POR EL PLACE 1");
                    BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), info,this.lexer.context.getContext(),false);
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                  //  System.out.println("ENTRO POR EL PLACE 1");
                }else{
                      ngramms.ngramms.get(i).bgdata=new BagData();
                  }
                }
           
                if (resultado.charAt(1)=='1'){
                 //  System.out.println("ENTRO POR EL PROPER 1");
                    BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                 //   System.out.println("ENTRO POR EL PROPER 1");
                }
           
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                   // System.out.println("ENTRO POR EL ELSE 1");
                    BagData bgd=new BagData(NI.ngramms,TypesTerms.AT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
                    bgd.type=Terms.UN;
                    
                    
                    this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                }
            break;
            }
            case 2:{
              // System.out.println("LOS RESULTADOS DE LAS COSAS POR EL DOS"+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    
                //   System.out.println("LOS RESULTADOS 2 "+info.gazetteer);
                    if (info.gazetteer.contains("Geonames")){
                    	
                BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), new InfoFound(),this.lexer.context.getContext());
                     
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                    
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                        
                    }
                     else {
                    	// System.out.println("sigo por aqui en 2");
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), info,this.lexer.context.getContext(),true);
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                  // System.out.println("LOS RESULTADOS 4 "+NI.ngramms);
                    if (info.gazetteer.contains("Geonames"))  {
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), info,this.lexer.context.getContext(),true);
                        
                        this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                        //this.lexer.numWord++;
                    }
                    else {
                       // System.out.println("LA NUEVA LUGAR ES CASTILLA ");
                       BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(), info,this.lexer.context.getContext(),true);
                       this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                       //this.lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
               // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
            //  this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));  
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
         //System.out.println("hemos salido por esta salida nueva");
         }
         
         
        //System.out.println ("ESTOY EN MEDIO DE TODO ");
       
         // System.out.println ("ESTOY EN MEDIO DE TODO ---------------------------------------------");  
           
            /******************************************************/
        
             
             
         
         this.lexer.currentString.updateTokenList();
        //System.out.println ("EL CENTRO DE ARTE "+ngramms.tam());
         
                 
        this.lexer.currentString.ngramms.updateNgrammsList();
         //System.out.println("HE ENTRADO POR LA PRINTLNvmedio"+this.lexer.currentString.ngramms.tam());
        // System.out.println("el tamano de ngramas la lista final es "+ngramms.tam()+ngramms.ngramms.get(0).ngramms+" "+this.lexer.isTheFirst());
         
             for (int j=0;j<this.lexer.currentString.tokenList.size();j++){
          //   System.out.println("LA PALIZA DE TODO ESTO ES UN ROLLO MEDIO "+this.lexer.currentString.tokenList.get(j).bdata.type);
         
             }
             if (this.lexer.isTheFirst()){
            	// System.out.println("Estoy en la primeraXXXXXXXXXXXXXXX"+this.lexer.currentString.tokenList.get(0).word+" "+this.lexer.currentString.tokenList.get(2).term);
             	if (this.lexer.currentString.tokenList.get(0).term==null) {
             		String stringRE=this.lexer.currentString.tokenList.get(0).word;
             		this.lexer.currentString.ngramms.removeNString(stringRE);
             		this.lexer.currentString.tokenList.remove(0);
             		Output.write(new RoleTreeNode(stringRE));
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
            // System.out.println("el recorrido medioooooooooooooooooooo");
         }
         
         
         if (encontrado){
          this.lexer.currentString.updateTokenList();
        // System.out.println ("EL CENTRO DE ARTE fuinal "+ngramms.tam());
          
        
                 
        this.lexer.currentString.ngramms.updateNgrammsList();
         }
           
         //System.out.println("HE ENTRADO POR LA PRINTLNv fuinal"+this.lexer.currentString.tokenList.size());
         
          for (int  i=ngramms.tam()-1;i>=0;i--){
             NgrammsInfo NI=this.lexer.currentString.ngramms.ngramms.get(i);
             //System.out.println("EL VACIO "+NI.ngramms);
             if (NI.bgdata.type==Terms.VACIO || NI.bgdata.type==Terms.UN){
                 //System.out.println("ENTRANDO POR EL VACIO FINAL");
                 BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),new InfoFound(),this.lexer.context.getContext());
                 this.lexer.numCh=this.lexer.numCh+NI.ngramms.length();
                    //this.lexer.numWord++; 
                 ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
             
                } 
             
         }
          
            
          
                
         //  System.out.println("CUANTAS HAY EN BAG "+this.lexer.wbag.tam());
             
        
        this.lexer.currentString.sendOutInfo();
       
       
         this.lexer.currentString.tokenList.clear();
         this.lexer.currentString.ngramms.ngramms.clear();
        
         //}
         return ContextualList.INITIAL;
       }catch(Exception e){return ContextualList.INITIAL;}
       
    }
    
     public ContextualList nounPhraseProcessing(String string){
        // System.out.println("he entrado por el contexto de nombre de santo"+string);
         
               BagData ultimo=new BagData();
        //	System.out.println("estoy entramdopor family nounphrases"+string);
       if (this.lexer.getLastToken()!=""){
         
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
        }else{
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
            }
        }
          		String newString=string.replaceAll("\\s+", " ");
          		
          		String de=TermsRecognition.findApposition(newString);
          		
          		
          		String tosplitedString=newString.replaceAll(" "+de+" ","_"+de+"_");
          		
          		
              String[] stringArray=tosplitedString.split("_"+de+"_");
              
              String[] izda=stringArray[0].split(" ");
              String[] dcha=stringArray[1].split(" ");
           //   System.out.println("vamos a ver que psa"+tosplitedString);
          		BagData bgd=new ProperNameBagData(stringArray[0],TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+stringArray[0].length(),new InfoFound(),this.lexer.context.getContext());
             	this.lexer.wbag.put(bgd);
             	this.lexer.wbag.put(new DeBagData(de,TypesTerms.FT,this.lexer.numCh+1+stringArray[0].length(), this.lexer.numWord++,this.lexer.numCh+1+stringArray[0].length()+2+de.length(), new InfoFound(),this.lexer.context.getContext()));
          		InfoFound info=TermsRecognition.findPlaceName(stringArray[1]);
             	boolean isProperName=TermsRecognition.isProperName(stringArray[1].toLowerCase());
                boolean isCommonName=TermsRecognition.isCommonName(stringArray[1].toLowerCase());
                String resultado="";
                int res=0;
               // System.out.println("he salido de la minuscula");
                if (info!=null){resultado+="1";res++;} else resultado+="0";
                if (isProperName){resultado+="1";res++;} else resultado+="0";
                if (isCommonName){resultado+="1";res++;} else resultado+="0";
                
            //    System.out.println("Hemos en contrado las isguientes propuestas  dddd"+res+resultado);
                int numCh=this.lexer.numCh+stringArray[0].length()+1+de.length()+1;
            	int numW=this.lexer.numWord+2;
            	int end=numCh+stringArray[1].length();
            	BagData bgdiz;
                switch (res){
                    case 0: {
                     //   System.out.println("el resultado es "+stringArray[1]);
                        InfoFound infoAprox=NewTermsIdentification.getAproximation(stringArray[1]);
                        
                        if (infoAprox!=null){
                           // System.out.println("estoy entrando por el 0");
                        if (info.gazetteer!="proper" && info.gazetteer!="nick"){
                        	bgdiz=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,this.lexer.context.getContext(),true);
                        	this.lexer.wbag.put(bgd);
                        }else{
                        	bgdiz=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),this.lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	this.lexer.wbag.put(bgd);
                        }
                        
                        } else {
                        	bgdiz=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),this.lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	this.lexer.wbag.put(bgd);
                        	
                           // Output.write(word);
                        }
                    }/* buscar una variante */;
                    case 1: {
                       // System.out.println("El caracter = es "+resultado.charAt(2));
                        if (resultado.charAt(0)=='1') {
                        	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,this.lexer.context.getContext(),true);
                        	this.lexer.wbag.put(bgd);
                        	}
                        if (resultado.charAt(1)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),this.lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	this.lexer.wbag.put(bgd);
                        }
                        if (resultado.charAt(2)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),this.lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	this.lexer.wbag.put(bgd);
                            
                           // Output.write(word);
                        }
                    break;
                    }
                    case 2:{
                        if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                        	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,this.lexer.context.getContext(),true);
                        	this.lexer.wbag.put(bgd);
                        }
                        if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                        	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,this.lexer.context.getContext(),true);
                        	this.lexer.wbag.put(bgd);
                            	
                              //  Output.write(word);
                             
                        }
                        if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),this.lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	this.lexer.wbag.put(bgd);
                            	
                              //  Output.write(word);
                             
                        }
                       break;
                    }
                    case 3:{
                    	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,this.lexer.context.getContext(),true);
                    	this.lexer.wbag.put(bgd);break;
                    }
                }
                
          		this.lexer.setLastToken("");
             	  
          	 
      
        return  this.lexer.context.getContext();
     }
     
     public ContextualList prepositionalSyntagmsListProcessing(String string){
         try{
         System.out.println("he enratod por PREOP FAMILY "+string);
           //  this.lexer.wbag.escribir();
         
               BagData ultimo=new BagData();
        //	System.out.println("estoy entramdopor family sproooeoosuny"+string);
        if (this.lexer.getLastToken()!=""){
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }
            else{
            this.lexer.wbag.put(new FamilyBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.setLastToken("");
              }  
        }
        }
        
       // System.out.println("esto es lo ultimo que he leido antes de la aposicion");
          //   this.lexer.wbag.escribir();
           
        	 
         
       String newString=string.replaceAll("\\s+", " ");
         newString=WordProcessing.WordTransformations.replaceGuion(newString);
       
         //tokenize the stirng
         this.lexer.currentString=new TokenizedString(newString);
         
         for (int i=0; i<this.lexer.currentString.tokenList.size();i++){
             Token token=this.lexer.currentString.tokenList.get(i);
             if (Recognition.ElementsRecognition.isDeterminantPrep(token.word)){
              //   System.out.println("he entrado por de baga datadeterminante "+token.word);
                 
                 token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext());
                 
                 this.lexer.setLastToken("");
                
             }else if (Recognition.ElementsRecognition.isCopulativeConjunction(token.word).size()>0){
               token.bdata=new CopulativeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext());
              
             }
         }
     
         
         newString=this.lexer.currentString.processingArticles(newString);
         
        
         newString=this.lexer.currentString.processingAppositionsPrepositions(newString);
                
         

         String[] newStrings=this.lexer.currentString.processingCopulativeConjunctions(newString);
         String firstWord=this.lexer.currentString.tokenList.get(0).word;
          
      //   System.out.println ("la cadena nueva es "+newString+newStrings.length+firstWord);
       //  System.out.println("La cadena tokenizada es "+this.lexer.currentString.tokenList.size());
         
       /*  for (int i=0; i<newStrings.length;i++){
             String[] aux=newStrings[i].split(" ");
             System.out.println("que hago aqui "+aux.length+aux[0]);
             for (int j=0; j<aux.length;j++){
                 BagData bgd=new BagData(aux[j],TypesTerms.UNI,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux[j].length(),new InfoFound(),this.lexer.context.getContext());
                 //this.lexer.numWord++;
                 this.lexer.numCh+=aux[j].length();                 
                this.lexer.currentString.ngramms.ngramms.add(new NgrammsInfo(aux[j],VerificationInfo.UNIDENTIFIED,false,bgd));
         }
         } */
         this.lexer.currentString.ngramms.write();
         
       //  System.out.println("estanos en este punto que no entiendo nada ");
       //  this.lexer.wbag.escribir();
       //  System.out.println("estamos teniendo otras cosas de "+this.lexer.currentString.ngramms.tam());
         this.lexer.currentString.ngramms.write();
        // System.out.println("La cadena tokenizada es "+this.lexer.currentString.ngramms.tam());
         /*for (int i=0;i<this.lexer.currentString.ngramms.ngramms.size();i++){
             String aux=this.lexer.currentString.ngramms.ngramms.get(i).ngramms;
             aux=aux.replaceAll("_", " ");
                
             aux=WordTransformations.removeCleanPattern(Data.AppositionPrepositionsTable.regEx, aux);
             */
             
         //   System.out.println(aux+"pitnntnnnt");
         
         for (int i=0; i<this.lexer.currentString.tokenList.size();i++){
             Token elemento=this.lexer.currentString.tokenList.get(i);
             String aux=this.lexer.currentString.tokenList.get(i).word;
       //      this.lexer.wbag.escribir();
             if (elemento.bdata==null){
          //   System.out.println("muestrame el termino "+aux+i);
             
             BagData bgdc=typeContext(aux,this);
    //  this.lexer.wbag.escribir();
        if (bgdc!=null){
          //  System.out.println("Estoy aqui dentro"+i);
            elemento.bdata=bgdc;
        //    this.lexer.wbag.escribir();
         //   System.out.println(elemento.bdata.type+"tipo");this.lexer.wbag.escribir();
            continue;
        }
       
        
        
         InfoFound info=TermsRecognition.existMedievalPlaceName(aux);
       
        boolean isProperName=TermsRecognition.isProperName(aux.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(aux.toLowerCase());
        String resultado="";
        int res=0;
        //System.out.println("he salido de la listaminuscula"+aux+" "+res);
        if (info!=null){
            //System.out.println("he enrtado or planame ");
            resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        //System.out.println("LOS RESULATODS QUE HE TRAIDO HASTA AQUI "+NI.ngramms);
        if (Data.NickNamesTable.tID.containsKey(aux.toLowerCase())){
            //System.out.println("he entrado por el mio cid ");
            if (ultimo.type==Terms.PSS){
                //System.out.println("EL ULTIMO ES "+ultimo.type);
                ultimo.string+=" "+aux;
                ultimo.type=Terms.NPN;
                BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
                this.lexer.numCh=this.lexer.numCh+ultimo.string.length();
                this.lexer.numWord+=2;
                this.lexer.currentString.ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
               // System.out.println("la propuesta"+ngramms.ngramms.get(i).bgdata.nword);
                int index=this.lexer.currentString.getIndex(aux);
                this.lexer.currentString.tokenList.get(index).word=ultimo.string;
                this.lexer.wbag.wbag.remove(this.lexer.wbag.tam()-1);
                //System.out.println("PINTAME EL ULTIMO "+this.lexer.wbag.tam()+" "+ngramms.ngramms.get(i).ngramms);
            }else{
               BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+ultimo.string.length(), new InfoFound(),this.lexer.context.getContext());
               this.lexer.numCh=this.lexer.numCh+ultimo.string.length();
                //this.lexer.numWord++;
                this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
            //this.lexer.wbag.put(new NickNameBagData(NI.ngramms,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+NI.ngramms.length(),new InfoFound(),this.lexer.context.getContext()));
            for (int j=0;j<this.lexer.wbag.tam();j++){
              //  System.out.println("las boslas nick "+this.lexer.wbag.get(i).string);
            }
            }
        }else if (Recognition.ElementsRecognition.isDeterminantPrep(string)){
         //   System.out.println("DETER");
        }else if (Data.AuthorityNamesTable.tID.containsKey(aux.toLowerCase())){
            BagData bgd=new AuthorityBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(),new InfoFound(),this.lexer.context.getContext());
            this.lexer.numCh=this.lexer.numCh+aux.length();
                 //this.lexer.numWord++;
                // System.out.println("EL AUTORIDA "+aux);
                 
                 this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
         }
        else {
            
            
     //System.out.println("Hemos en contrado las isguientes en preposicion propuestas "+res+" "+resultado+" "+aux);
     //this.lexer.wbag.escribir();
     
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
                    if (this.lexer.wbag.tam()>0){
                     BagData last=this.lexer.wbag.getLast();
                     if (last.type==Terms.NPLN){
                         if (((PlaceNameBagData)last).tipoNombre) last.string+=" "+aux;
                     this.lexer.currentString.tokenList.remove(i);
                     
                     }else{
                         BagData bgd=new PlaceNameBagData(aux,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                    this.lexer.numCh=this.lexer.numCh+aux.length();
                    //this.lexer.numWord++;
                    this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
               
                     }
                 }else{
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.GENT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                    this.lexer.numCh=this.lexer.numCh+aux.length();
                    //this.lexer.numWord++;
                    this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                }
                 this.lexer.setLastToken("");
                    
                    }
             
                } else{
                //     System.out.println("HE SALIDO POR AquI ELSE");
                     BagData bgd=new ProperNameBagData(aux,TypesTerms.PPT,elemento.position,elemento.nWord,elemento.position+aux.length(),new InfoFound(),this.lexer.context.getContext());
                     elemento.bdata=bgd;
                     Data.ProperNamesTable.putNewName(aux.toLowerCase(), Input.name, "Hismetag", "person");
               //     System.out.println("HE SALIDO POR AquI ELSE");
                   
                 }
             //   System.out.println("HE SALIDO POR AquI ELSE FIN");
                
                break;
               
            }
                   
            case 1: {
              //  System.out.println("El caracter = es "+resultado.charAt(1));
                if (resultado.charAt(0)=='1') {
               //   System.out.println("ENTRO POR EL PLACE 1");
                  
                 
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                    elemento.bdata=bgd;
                 this.lexer.numCh=this.lexer.numCh+aux.length();
                    //this.lexer.numWord++;
                 //   System.out.println("NOOOOOOOOOOOOOOOOOOOOO"+this.lexer.currentString.ngramms.ngramms.size());
                    
                        
                
                        
                        
                        
                        
                     }
           
                if (resultado.charAt(1)=='1'){
                   // System.out.println("ENTRO POR EL PROPER 1");
                    BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                    elemento.bdata=bgd;
                }
           
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                   // System.out.println("ENTRO POR EL ELSE 1");
                    this.lexer.wbag.restart();
                    Output.write(new RoleTreeNode(aux));
                    this.lexer.numCh=this.lexer.numCh+aux.length();
                    //this.lexer.numWord++;
                }
                
            break;
            }
            case 2:{
              // System.out.println("LOS RESULTADOS DE LAS COSAS "+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    
               //    System.out.println("LOS RESULTADOS 2 "+aux);
                    if (info.gazetteer.contains("Geonames")){
                        BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                        elemento.bdata=bgd;
                    }
                     else {
                       
                         BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                    elemento.bdata=bgd;
                   
                 this.lexer.setLastToken("");
                    }
           
                }
               // System.out.println("LOS RESULTADOS DE LAS COSAS otra "+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                  
                    if (info.gazetteer.contains("Geonames"))  {
                       this.lexer.wbag.restart();
                        Output.write(new RoleTreeNode(aux));
                        this.lexer.numCh=this.lexer.numCh+aux.length();
                        //this.lexer.numWord++;
                    }
                    else {
                   //     System.out.println("LOS RESULTADOS 4 "+aux);
                       
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                    elemento.bdata=bgd; }
                 this.lexer.setLastToken("");
                   
           
                }
               // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
            break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames")){
                        BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                        elemento.bdata=bgd;   }
            else {
                       
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                   elemento.bdata=bgd;
                 this.lexer.setLastToken("");
                    }
           
            break;
            }
        }
        } 
         
         }
             
          //   System.out.println("ESTIY EN ESTE CASO"+i);
         }
       
      //  System.out.println("NO SALGO POR AQUI");
     
         
        
         //this.lexer.currentString.updateTokenList();
       
        this.lexer.currentString.sendOutInfo();
 //   System.out.println ("HE SALIDO DE LA VUELTA"+this.lexer.wbag.tam());
 //   this.lexer.wbag.escribir();
        
         this.lexer.currentString.tokenList.clear();
         this.lexer.currentString.ngramms.ngramms.clear();
    //     System.out.println ("HE SALIDO DE LA VUELTA"+this.lexer.wbag.tam());
   // this.lexer.wbag.escribir();
         return this.lexer.context.getContext();
     }catch(Exception e){return ContextualList.INITIAL;}
     }
     
    
}
