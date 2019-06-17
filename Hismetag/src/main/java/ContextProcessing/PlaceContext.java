/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import Data.AuthorityNamesTable;
import Data.BuildingsTable;
import Data.GeographicNamesTable;
import Data.MedievalNewPlaceNamesTable;
import Data.SaintsTable;
import Data.TreatmentsTable;
import Data.Verbs;
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
import StringInProcess.Token;
import StringInProcess.TokenizedString;
import StringNgramms.Ngramms;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class PlaceContext extends SemanticContext {
    
    public PlaceContext(){
    	
    }
    public PlaceContext(SemanticContext previous){
        super(previous);}
  
    public ContextualList getContext(){
        return ContextualList.PLACE;
    }
    
    
    public SemanticContext checkLowerCaseWord(String wordProcess){
      try{
          
         String word=WordProcessing.WordTransformations.replaceGuion(wordProcess);
        //  System.out.println("estoy entrando por minusculas de place "+word+" "+Lexer.verbsFlag);
        //  if (Lexer.verbsFlag!=null)System.out.println("el flag del verbo es "+Lexer.verbsFlag.verb);
      
          BagData ultimo=new BagData();
          if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
          } 
         if (Recognition.ElementsRecognition.isDeterminantPrep(word) && Lexer.wbag.tam()>0){
             BagData bgd=Lexer.wbag.getLast();
             if (bgd.type==Terms.NPLN){
                 bgd.string+=" "+word;
             }else{
             Lexer.wbag.put(new DeBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
              
             }
             return Lexer.context;
         }
         
       //  Lexer.wbag.escribir();
      //System.out.println("edtoy leyendo esta palabra "+word+"   "+ Lexer.context.getContext()+ Lexer.numCh+Lexer.numWord);
          
        ContextualList context=determineContext(word,this);
        
 // System.out.println("el contexto  en place de "+context+" "+word);
        Verbs elVerb;
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.PLACE){
        	Lexer.context.changeContext(context, Lexer.context," ", word);
        	return Lexer.context;
        } else return Lexer.context;
        if (check(word)){ Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
        	return Lexer.context;}
         if (checkVerb(word)){ 
           //  System.out.println ("el verbo que he encontrado "+word);
             Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
        	return Lexer.context;}
       
       
        
 //System.out.println("estamos entrando por minuscula  tras check XXXXXXXXXXXXXX"+word);
       InfoFound info=TermsRecognition.findPlaceName(word);
       
        boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
        String resultado="";
        int res=0;
       // System.out.println("he salido de la minuscula");
        if (info!=null){resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        
        //System.out.println ("LAS SALIDA DE IO CID"+word);
        if (Data.NickNamesTable.contains(word)){
           // System.out.println("he entrado por el mio cid ");
            if (ultimo.type==Terms.PSS){
               // System.out.println("EL ULTIMO ES "+ultimo.type);
                ultimo.string+=" "+word;
                ultimo.type=Terms.NPN;
                
              //  System.out.println("PINTAME EL ULTIMO "+Lexer.wbag.get(Lexer.wbag.tam()-1).string);
            }else{
            Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
            for (int i=0;i<Lexer.wbag.tam();i++){
                //System.out.println("las boslas nick "+Lexer.wbag.get(i).string);
            }
            }
        } 
        else {
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+" "+resultado);
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word);
                
                if (infoAprox!=null){
                   // System.out.println("estoy entrando por el 0");
                    if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else {
                    
                    Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false));
                      // Data.MedievalPlaceNamesTable.putNewPlace(word,"", "", "", "", "", Input.name, "Hismetag", "PL");
                }

                
                
                } else {
            //        System.out.println ("estoy por el place pero nos se si cero");
                  if (Lexer.verbsFlag!=null) {//System.out.println("entro por aqui en el verbo de place");
                    Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false));
                  //  Data.MedievalPlaceNamesTable.putNewPlace(word,"", "", "", "", "", Input.name, "Hismetag", "PL");
                return Lexer.context;
                  }else{
                     // System.out.println("entro por aqui en el verbo de place no verb"+Lexer.context+Lexer.verbsFlag);
                      Lexer.wbag.restart();
                      Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                      return Lexer.context;
                  }
                }
            }/* buscar una variante */;
            case 1: {
               // System.out.println("El caracter = es "+resultado.charAt(2));
                if (resultado.charAt(0)=='1') {
                    
                    if (info.gazetteer.contains("Medieval")){
                        String typeg=Data.MedievalPlaceNamesTable.contains(WordTransformations.capitalize(word)).get(0).typeg;
                        
                        if (typeg.equals("GE")) Lexer.wbag.put(new GeographicBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
                        else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
                    
                    } else{
                    Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));}
                    return Lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
                }
                if (resultado.charAt(1)=='1') Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                   
                    Lexer.wbag.restart();
                  //  System.out.println("he entrado por aqui en el case 1 "+word);
                    Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                    return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ");
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames"))  Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
              
                }
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    if (info.gazetteer.contains("Geonames") || info.gazetteer.contains("Pleiades"))  {
                       Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                    }
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
              
                }
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                   
                    if (Lexer.wbag.containsTypeTerms(Terms.PPN)) Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
              
                    else {
                        Lexer.wbag.restart();
                        
                        Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                    }
              
                }
                return Lexer.context.changeContext(ContextualList.INITIAL, this," ", " ");
              
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames"))  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    else Output.writePlaceName(word, info.gazetteer, Terms.CN);
            break;
            }
        }
        }
        
        
           return Lexer.context;
      }catch(Exception e){return Lexer.context;}
    }
   
    
    public ContextualList checkCapitalLettersWord(String word){
      try{
       // System.out.println("He entrado por mayusculas de place "+word);
          boolean IsTheFirst=false;
          word=word.replaceAll("\\s+", " ");
           
        ContextualList context=determineContext(word.toLowerCase(),this);
        if (context!=ContextualList.SAME)
          if (context!=ContextualList.PLACE) {
        	  Lexer.context.changeContext(context, Lexer.context," ", word);
                return context;
        }else return Lexer.context.getContext();
         if (checkVerb(word)){
      
        Lexer.isTheFirst=false;
           return Lexer.context.getContext();
       }
       
       //  System.out.println ("el articulo que no sale");
      
      if (check(word)){
           
       
          Lexer.isTheFirst=false;
          return Lexer.context.getContext();
      }
        InfoFound info=TermsRecognition.checkPlaceName(word);

      //  System.out.println("no veo si salgo por aqui en place");
          
        String resultado="";
        int res=0;
        
        
        boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
        
        
        if (info!=null){resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        
      
        if (Data.NickNamesTable.contains(word.toLowerCase())){
          
            Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.wbag.tam(),Lexer.numCh,Lexer.numWord,new InfoFound(),Lexer.context.getContext()));
            
           
        } else if (Data.DeityTable.contains(word.toLowerCase())){
         Lexer.wbag.put(new DeityBagData(word,TypesTerms.FT,Lexer.wbag.tam(),Lexer.numCh,Lexer.numWord,new InfoFound(),Lexer.context.getContext()));
 
        }
        else {
     //   System.out.println("Hemos en contrado las isguientes propuestas "+res+" "+resultado);
        switch (res){
            case 0: {
               // System.out.println ("entro por aqui"+Lexer.isTheFirst);
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
               
                if (infoAprox!=null){
                   // System.out.println("estoy entrando por el 0");
                if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
                break;
                } else {
                	
                    BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false);
                    
                    Lexer.wbag.put(bgd);
                    break;
                    
                }
            }
                
            /* buscar una variante */
            case 1: {
              
                if (resultado.charAt(0)=='1'){
                    if (Lexer.isTheFirst && info.gazetteer.contains("Geonames")){
                        Lexer.wbag.restart();
                        Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                    } else
                    Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
                }
                if (resultado.charAt(1)=='1') Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                   //System.out.println("he entrado por aqui en el case 1 "+word);
                    
                    InfoFound infoAprox=NewTermsIdentification.getAproximation(word.toLowerCase());
                
                if (infoAprox!=null){
                 //System.out.println("estoy entrando por el 0");
                if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
                
                }else {
                    if (Lexer.articleFlag!="")Lexer.wbag.restart();
                    Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                }
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames")) {
                        Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    //System.out.println("estoy entrando por el info gazeeet");
                    }
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
                
                    
                    
                }
                //System.out.println("lalalllallllallla");
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                    Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.AT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                
                }
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.AT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
                
                }
               break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames"))  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
                
            break;
            }
        }
        }
        Lexer.isTheFirst=false;
       
                return context;
      }catch(Exception e){return ContextualList.INITIAL;}
    }
    
  /*   public ContextualList wordListProcessing(String string){
    	 try{
             System.out.println("ESTIY ENTRANDO POR LISTA PLACE "+string);
             BagData ultimo=new BagData();
            if (Lexer.wbag.tam()>0){
                ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
            }
          
           
           String newString=string.replaceAll("\\s+", " ");
           readCleanString=new String(newString);
         
           
           //tokenize the string
           
           TokenizedString token=new TokenizedString(newString,this.path);
           //token.countWordChar();
          
           Lexer.currentString=token;
           System.out.println("LAS COSAS DE LA LISTA DE TOKENES");
           for (int i=0; i<Lexer.currentString.tokenList.size(); i++){
               System.out.println("las cosas de la lista "+Lexer.currentString.tokenList.get(i).word);
           }
          
           String firstWord=new String(Lexer.currentString.tokenList.get(0).word);
           String stringInProcess=newString;
          System.out.println("EL PRIMERO DE LA LISTA ES "+firstWord);
           ContextualList recognizedContext=ContextualList.INITIAL;
             
           
          ContextualList newContext=this.determineContext(firstWord, this);
          System.out.println("El CONTEXTO NUEVO ES "+newContext.name());
          
          if (newContext!=ContextualList.SAME)
      
          if (newContext!=ContextualList.PLACE){
           // System.out.println ("el nuevos contexto no incial"+token.tokenList.size()); 
              stringInProcess=newString.substring(firstWord.length()+1);
               Lexer.context=changeContext(newContext,Lexer.context,stringInProcess,firstWord);
               Lexer.currentString.tokenList.remove(0);
            //   System.out.println ("el nuevos contexto no incial"+token.tokenList.size()+Lexer.context);
               if (token.tokenList.size()>1)
               return Lexer.context.wordListProcessing(stringInProcess);
               else return Lexer.context.checkCapitalLettersWord(stringInProcess);
            }else return Lexer.context.getContext();
          
          
           System.out.println("El CONTEXTO NUEVO ES "+newContext.name());
          
          
           if (checkVerb(firstWord)){
          	stringInProcess=newString.substring(firstWord.length()+1);
          	Lexer.currentString.tokenList.remove(0);
          	//System.out.println("he entrado por check con"+firstWord);
          }
          if (check(firstWord)){
          	stringInProcess=newString.substring(firstWord.length()+1);
          	Lexer.currentString.tokenList.remove(0);
          	//System.out.println("he entrado por check con"+firstWord);
          }
          
        
          System.out.println("sriiiii"+stringInProcess); 
             //  System.out.println("ESTAMOS AQUI EN LA CASA DE TODOS");
           //check if the string has articles and links them to their nouns
          	 System.out.println("LAS COSAS DE LA LISTA DE TOKENES");
           for (int i=0; i<Lexer.currentString.tokenList.size(); i++){
               System.out.println("las cosas de la lista "+Lexer.currentString.tokenList.get(i).word);
           }
               boolean hasAuTr=false;
               
               
           stringInProcess=Lexer.currentString.processingArticles(stringInProcess);
           
             //check if the string has copulative conjunctions and separates it in the parts according to the conjunction
           String[] subs=Lexer.currentString.processingCopulativeConjunctions(stringInProcess);
         
        if (subs.length>1){
          System.out.println("las cosas de las ngramas"+subs.length);
            
              for (int i=0;i<subs.length;i++){
              // divide the substrings according to the spaces
              String [] newS=subs[i].split(" ");
         
              Lexer.currentString.buildNgramms(newS);
           //   System.out.println("LOS NEGRA,AS CONSTRUIDOS "+Lexer.currentString.ngramms.tam());
           }
              
           for (int i=0; i<Lexer.currentString.tokenList.size(); i++){
               if (TermsRecognition.isAuthorityOrTreatement(Lexer.currentString.tokenList.get(i).word)){
                   hasAuTr=true;
                  Lexer.currentString.ngramms.removeNgrammWithWord(Lexer.currentString.tokenList.get(i).word);
               }
           }
    
           }else{
            System.out.println("las cosasasasasasasassasassasasasasa");
            
            
            
            for (int i=0;i<subs.length;i++){
              //divide the substrings according to the spaces
              String [] newS=subs[i].split(" ");
         //System.out.println("LOS NEGRAMASS CONSTRUIDOS ");
              Lexer.currentString.buildNgramms(newS);
             // System.out.println("LOS NEGRAMAS CONSTRUIDOS "+Lexer.currentString.ngramms.tam());
              
           }
           // System.out.println("las cosas de las ngramas1 "+Lexer.currentString.ngramms.ngramms.get(5).bgdata.type);
              // return Lexer.context.checkCapitalLettersWord(subs[0]);
           }
           Ngramms ngramms=Lexer.currentString.ngramms.getNgramms();
         //System.out.println("LAS COSAS QUE RECONOZCO FUERA de qaqui"+ngramms.tam());
            	 System.out.println("LAS COSAS DE LA LISTA DE TOKENES");
           for (int i=0; i<Lexer.currentString.tokenList.size(); i++){
               System.out.println("las cosas de la lista "+Lexer.currentString.tokenList.get(i).word);
           }
           for (int i=ngramms.tam()-1;i>=0;i--){
               
               
          

               
               
               
               
               
               
               NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
              // System.out.println("LOS NGRAMAS QUESE CONSULTAN "+NI.ngramms);
               
               
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
              //System.out.println("he entrado por el mio cid ");
              if (ultimo.type==Terms.PSS){
                 // System.out.println("EL ULTIMO ES "+ultimo.type);
                  ultimo.string+=" "+NI.ngramms;
                  ultimo.type=Terms.NPN;
                  BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                  Lexer.numCh=Lexer.numCh+ultimo.string.length();
                  Lexer.numWord+=2;
                  ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
                 // System.out.println("la propuesta"+ngramms.ngramms.get(i).bgdata.nword);
                  int index=Lexer.currentString.getIndex(NI.ngramms);
                  Lexer.currentString.tokenList.get(index).word=ultimo.string;
                  Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                  //System.out.println("PINTAME EL ULTIMO "+Lexer.wbag.tam()+" "+ngramms.ngramms.get(i).ngramms);
              }else{
                 
                 BagData bgd=new NickNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                 
                 Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                  //Lexer.numWord++;
                  
                  ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                   //    System.out.println("estoy poniendo el mio cid "+NI.ngramms);
  //Lexer.wbag.put(new NickNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext()));
             

  //System.out.println("las nick name no se puede"); 
              }
          }else if (Data.AuthorityNamesTable.tID.containsKey(NI.ngramms.toLowerCase())){
              BagData bgd=new AuthorityBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext());
             Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                  //Lexer.numWord++;
               //   System.out.println("EL AUTORIDA "+NI.ngramms);
                  
                  ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
          }else if (Data.PosessiveTable.contains(NI.ngramms)){
          		//System.out.println("es mio");
              BagData bgd=new PossesiveBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
              
              Lexer.numCh=Lexer.numCh+NI.ngramms.length();
               //Lexer.numWord++;
               
               ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    //System.out.println("estoy poniendo el mio cid "+NI.ngramms);
          }else {
         
         System.out.println("ANTES DE LA ACTUALIZACION final ");
             for (int j=0; j<Lexer.currentString.tokenList.size(); j++){
               System.out.println("las cosas de la lista "+Lexer.currentString.tokenList.get(j).word);
           }  
               switch (res){
              case 0: {
                 //System.out.println("HE SALIDO POR AQUI CUANDO "+ngramms.ngramms.get(i).ngramms);
                  ngramms.ngramms.get(i).bgdata=new BagData();
                  break;
                 /* InfoFound infoAprox=NewTermsIdentification.getAproximation(word,this.path);
                  
                  if (infoAprox!=null){
                      System.out.println("estoy entrando por el 0");
                      if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                  else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                  else Output.write(word);
                  } else {
                      
                      Output.write(word);
                  }*/
          /*    }
                   
              case 1: {
                //  System.out.println("El caracter = es "+resultado.charAt(1));
                  if (resultado.charAt(0)=='1') {
                    //  System.out.println("EL GAZETTER "+info.gazetteer);
                    if (!(info.gazetteer.contains("Geonames"))){
                    // System.out.println("ENTRO POR EL PLACE 1");
                      BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext(),false);
                      Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                      //Lexer.numWord++;
                      ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    //  System.out.println("ENTRO POR EL PLACE 1");
                  }else{
                        ngramms.ngramms.get(i).bgdata=new BagData();
                    }
                  }
             
                  if (resultado.charAt(1)=='1'){
                   //  System.out.println("ENTRO POR EL PROPER 1");
                      BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                      Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                      //Lexer.numWord++;
                      ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                     // System.out.println("ENTRO POR EL PROPER 1");
                  }
             
                  if (resultado.charAt(2)=='1') {
                     // System.out.println("he entrado por aqui en el case 1 "+word);
                     // System.out.println("ENTRO POR EL ELSE 1");
                      BagData bgd=new BagData(NI.ngramms,TypesTerms.AT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                      bgd.type=Terms.UN;
                      
                      
                      Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                      //Lexer.numWord++;
                  }
              break;
              }
              case 2:{
                 //System.out.println("LOS RESULTADOS DE LAS COSAS POR EL DOS"+resultado);
                  if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                      
                    // System.out.println("LOS RESULTADOS 2 "+info.gazetteer);
                      if (info.gazetteer.contains("Geonames")){
                      	
                  BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                       
                          Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                      //Lexer.numWord++;
                      
                          ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                          
                      }
                       else {
                      //	 System.out.println("sigo por aqui en 2");
                          BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                          Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                      //Lexer.numWord++;
                          ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                      }
             
                  }
                  
                  if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                    // System.out.println("LOS RESULTADOS 4 "+NI.ngramms);
                      if (info.gazetteer.contains("Geonames"))  {
                          BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                          
                          Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                          //Lexer.numWord++;
                      }
                      else {
                        //  System.out.println("LA NUEVA LUGAR ES CASTILLA ");
                         BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext(),true);
                         Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                         //Lexer.numWord++;
                          ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                      }
             
                  }
                 // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
              //  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));  
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
           //System.out.println("hemos salido por esta salida nueva");
           }
           
             System.out.println("ANTES DE LA ACTUALIZACION final despues ");
             for (int j=0; j<Lexer.currentString.tokenList.size(); j++){
               System.out.println("las cosas de la lista "+Lexer.currentString.tokenList.get(j).word);
               if (Lexer.currentString.tokenList.get(j).bdata!=null) System.out.println(Lexer.currentString.tokenList.get(j).bdata.type);
             } 
         // System.out.println ("ESTOY EN MEDIO DE TODO ");
          for (int i=0; i<ngramms.tam();i++){
             System.out.println ("las token gram "+ngramms.ngramms.get(i).ngramms+" "+ngramms.ngramms.get(i).bgdata.type);
          }
          
              /******************************************************/
           
           
         /*  Lexer.currentString.updateTokenList();
           
           for (int i=0; i<Lexer.currentString.tokenList.size(); i++){
               System.out.println("las cosas de la lista despues de actualizar"+Lexer.currentString.tokenList.get(i).word);
               
           
           }
           
           System.out.println(Lexer.currentString.tokenList.size());
             for (int i=0; i<ngramms.tam();i++){
             System.out.println ("las token gram "+ngramms.ngramms.get(i).ngramms+" "+ngramms.ngramms.get(i).bgdata.type);
          }
          System.out.println ("EL CENTRO DE ARTE "+ngramms.tam());
           
                   
          Lexer.currentString.ngramms.updateNgrammsList();
           System.out.println("HE ENTRADO POR LA PRINTLNvmedio"+Lexer.currentString.ngramms.tam());
          // System.out.println("el tamano de ngramas la lista final es "+ngramms.tam()+ngramms.ngramms.get(0).ngramms+" "+Lexer.isTheFirst);
           
               for (int j=0;j<Lexer.currentString.tokenList.size();j++){
            //   System.out.println("LA PALIZA DE TODO ESTO ES UN ROLLO MEDIO "+Lexer.currentString.tokenList.get(j).bdata.type);
           
               }
               if (Lexer.isTheFirst){
              //	 System.out.println("Estoy en la primeraXXXXXXXXXXXXXXX"+Lexer.currentString.tokenList.get(0).word+" "+Lexer.currentString.tokenList.get(2).term);
               	if (Lexer.currentString.tokenList.get(0).term==null) {
               		String stringRE=Lexer.currentString.tokenList.get(0).word;
               		Lexer.currentString.ngramms.removeNString(stringRE);
               		Lexer.currentString.tokenList.remove(0);
               		Output.write(new RoleTreeNode(stringRE));
               	}
                } 
               boolean encontrado=false;
               
               System.out.println("los ngramas que quedan"+ngramms.tam());
               
                 System.out.println("ANTES DE LA ACTUALIZACION los negramasfinales ");
             for (int j=0; j<Lexer.currentString.tokenList.size(); j++){
               System.out.println("las cosas de la lista "+Lexer.currentString.tokenList.get(j).word);
           } 
           for (int  i=ngramms.tam()-1;i>=0;i--){
               
               NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
               if (NI.bgdata.type==Terms.VACIO){
                   
                   encontrado=true;
                   InfoFound infoAprox=NewTermsIdentification.getAproximation(NI.ngramms.toLowerCase(),this.path);
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
              // System.out.println("el recorrido medioooooooooooooooooooo");
           }
           
           
           if (encontrado){
            Lexer.currentString.updateTokenList();
          // System.out.println ("EL CENTRO DE ARTE fuinal "+ngramms.tam());
            for (int i=0; i<Lexer.currentString.tokenList.size();i++){
          	// System.out.println( Lexer.currentString.tokenList.get(i).type+" "+Lexer.currentString.tokenList.get(i).word);
            }
          
                   
          Lexer.currentString.ngramms.updateNgrammsList();
           }
             
          // System.out.println("HE ENTRADO POR LA PRINTLNv fuinal"+Lexer.currentString.tokenList.size());
           
            for (int  i=ngramms.tam()-1;i>=0;i--){
               NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
               //System.out.println("EL VACIO "+NI.ngramms);
               if (NI.bgdata.type==Terms.VACIO || NI.bgdata.type==Terms.UN){
                   //System.out.println("ENTRANDO POR EL VACIO FINAL");
                   BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext());
                   Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                      //Lexer.numWord++; 
                   ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
               
                  } 
               
           }
            
              
               for (int j=0;j<ngramms.tam();j++){
               //System.out.println("LA PALIZA DE TODO ESTO ES UN ROLLO FINAL"+ngramms.ngramms.get(j).ngramms);
           }
                  
           //  System.out.println("CUANTAS HAY EN BAG "+Lexer.wbag.tam());
               
           for (int l=0; l<Lexer.currentString.tokenList.size(); l++){
              // System.out.println("las tokenizadas "+Lexer.currentString.tokenList.get(l).bdata.type);
           }
          Lexer.currentString.sendOutInfo();
         
         
           Lexer.currentString.tokenList.clear();
           Lexer.currentString.ngramms.ngramms.clear();
          
           //}
           return Lexer.context.getContext();
       }catch(Exception e){return ContextualList.INITIAL;}
     }*/
     
     
     public ContextualList nounPhraseProcessing(String string){
         
         try{
             
          String newString=string.replaceAll("\\s+", " ");
          String stringInProcess="";
         //System.out.println("ha entrado ppor NOUNPORCES"+Lexer.context.getContext());
         String de=TermsRecognition.findApposition(newString);
        // System.out.println("EL DE "+de);
         
         //tokenize the stirng
         Lexer.currentString=new TokenizedString(newString);
         String firstWord=Lexer.currentString.tokenList.get(0).word;
         
        
        
        for (int i=0; i<Lexer.currentString.tokenList.size();i++){
          //  System.out.println("las palabaras de token "+Lexer.currentString.tokenList.get(i).word);
        }
         //if (check(firstWord)){ return Lexer.context.getContext();}
       
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
           // System.out.println("la cadena del noun es "+string);
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
        // System.out.println("la bag "+Lexer.wbag.tam());
        // System.out.println("la bolsa ");Lexer.wbag.escribir();
                       
        String tosplitedString=newString.replaceAll(" "+de+" ", "_"+de+"_");
              ContextualList context=null;
            String[] stringArray=tosplitedString.split("_"+de+"_");
            
            String[] izda=stringArray[0].split(" ");
            String[] dcha=stringArray[1].split(" ");
            String recognized="";
            int encontrada=-1;
            
            for (int i=0; i<izda.length; i++){
                context=determineContext(izda[i],this);
                //Lexer.wbag.clean();
                recognized=izda[i]+" ";
          if (context!=ContextualList.PLACE) {
              //System.out.println("he encontradi "+i+" "+izda[i]);
            encontrada=i;
            break;
            }
           }
        //System.out.println("la palabra encontrada "+Lexer.context.getContext());
            if (encontrada!=-1){
                if (izda.length==1) {
                    stringInProcess=newString.substring(stringArray[0].length()+1);
                   // System.out.println("la palabra enviada"+stringInProcess);
                    Lexer.context.changeContext(context,Lexer.context,stringInProcess,izda[0]);
                    return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                }
                if (izda.length==2){
                    if (encontrada==1){
                        stringInProcess=newString.substring(stringArray[0].length()+1);
                      //  System.out.println("vamos a mandar una substring "+stringInProcess);
                       // System.out.println("vamos a usar "+izda[0]+" "+izda[1]+" "+Lexer.context);
                        Lexer.context.checkCapitalLettersWord(izda[0]);
                        
                        Lexer.context.changeContext(context,Lexer.context,stringInProcess,izda[1]);
                        //System.out.println("la bag "+Lexer.wbag.tam()+Lexer.wbag.get(0).string);
                        return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                    }else {
                        stringInProcess=newString.substring(izda[0].length()+1);
                        
                        Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[0]);
                       // System.out.println("vamos a mandar una substring del final"+stringInProcess+Lexer.wbag.tam());
                       
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
                        //System.out.println ("El tamaño de la bolsa es "+Lexer.wbag.tam());
                        Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[izda.length-1]);
                        for (int k=0;k<Lexer.wbag.tam();k++){
                        //System.out.println ("la vida es "+Lexer.wbag.get(k).string);
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
                       // System.out.println ("la vida es "+Lexer.wbag.get(k).string);
                        }
                       // System.out.println("la verdad"+izda.length);
                        return Lexer.context.nounPhraseProcessing(stringInProcess);
                    
                        
                        
                    }
                }
                
            }
            
            
           // System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
     
           // System.out.println("escribeme "+izda.length+" "+dcha.length);
           
           if (izda.length>1)
            Lexer.context.wordListProcessing(stringArray[0]);
           else Lexer.context.checkCapitalLettersWord(stringArray[0]);
            //System.out.println("LA BOSLA DE "+Lexer.wbag.tam());
            //System.out.println("he entrado por de baga data general 2");
            Lexer.wbag.put(new DeBagData(de,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext()));
            if (dcha.length>1)
            Lexer.context.wordListProcessing(stringArray[1]);
            else
            Lexer.context.checkCapitalLettersWord(stringArray[1]);
            
            //for (int i=0;i<Lexer.wbag.tam();i++)
            //System.out.println("LA BOSLA DE FINAL ES"+Lexer.wbag.get(i).string+" "+Lexer.wbag.get(i).type);
                 
         return ContextualList.INITIAL;         
         
        
         }catch(Exception e){return ContextualList.INITIAL;}
     }
    
    
    
    
    public ContextualList wordListProcessing(String string){
       try{
      // System.out.println("ESTIY ENTRANDO POR LISTA inicial "+string);
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
        
       //   System.out.println("no veo si salgo por aqui"+newContext);
         if (newContext!=ContextualList.SAME)
        if (newContext!=ContextualList.PLACE){
          //System.out.println ("el nuevos contexto no incial"+token.tokenList.size()); 
            stringInProcess=newString.substring(firstWord.length()+1);
             Lexer.context=changeContext(newContext,Lexer.context,stringInProcess,firstWord);
             Lexer.currentString.tokenList.remove(0);
            // System.out.println ("el nuevos contexto no incial"+token.tokenList.size());
             if (token.tokenList.size()>1)
             return Lexer.context.wordListProcessing(stringInProcess);
             else return Lexer.context.checkCapitalLettersWord(stringInProcess);
       
        } else return Lexer.context.getContext();
         
     //  System.out.println ("estoy entrando por esta 00 ");
        if (checkVerb(firstWord)){
            
          //  System.out.println ("estoy entrando por esta 1 ");
        	stringInProcess=newString.substring(firstWord.length()+1);
        	Lexer.currentString.tokenList.remove(0);
                Lexer.isTheFirst=false;
        	//System.out.println("he entrado por check con"+firstWord);
                if (Lexer.currentString.tokenList.size()>1) return Lexer.context.wordListProcessing(stringInProcess);
                else return Lexer.context.checkCapitalLettersWord(stringInProcess);
        }
        
       
        if (check(firstWord)){
             Lexer.isTheFirst=false;
          //  System.out.println ("estoy entrando por esta 2 ");
        	stringInProcess=newString.substring(firstWord.length()+1);
        	Lexer.currentString.tokenList.remove(0);
        	//System.out.println("he entrado por check con"+firstWord);
                if (Lexer.currentString.tokenList.size()>1) return Lexer.context.wordListProcessing(stringInProcess);
                else return Lexer.context.checkCapitalLettersWord(stringInProcess);
        }
        
      	 //System.out.println("despues de los verbos encontrados o de otra plabaras "+stringInProcess);
             boolean hasAuTr=false;
             
             
         stringInProcess=Lexer.currentString.processingArticles(stringInProcess);
         
           //check if the string has copulative conjunctions and separates it in the parts according to the conjunction
         String[] subs=Lexer.currentString.processingCopulativeConjunctions(stringInProcess);
        for (int l=0;l<subs.length;l++){
        	//System.out.println("los len "+subs[l]);
        }
      if (subs.length>1){
       //System.out.println("las cosas de las ngramas"+subs.length);
          
            for (int i=0;i<subs.length;i++){
            //divide the substrings according to the spaces
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
       //System.out.println("LOS NEGRAMASS CONSTRUIDOS ");
            Lexer.currentString.buildNgramms(newS);
           // System.out.println("LOS NEGRAMAS CONSTRUIDOS "+Lexer.currentString.ngramms.tam());
            
         }
         // System.out.println("las cosas de las ngramas1 "+Lexer.currentString.ngramms.ngramms.get(5).bgdata.type);
            // return Lexer.context.checkCapitalLettersWord(subs[0]);
         }
         Ngramms ngramms=Lexer.currentString.ngramms.getNgramms();
       //System.out.println("LAS COSAS QUE RECONOZCO FUERA de qaqui"+ngramms.tam());
         
         for (int i=ngramms.tam()-1;i>=0;i--){
             
             
        

             
             
             
             
             
             
             NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
           //System.out.println("LOS NGRAMAS QUESE CONSULTAN "+NI.ngramms);
             
             
             //InfoFound info=TermsRecognition..existMedievalPlaceName(NI.ngramms);
        InfoFound info=TermsRecognition.findPlaceName(NI.ngramms);
             
        boolean isProperName=TermsRecognition.isProperName(NI.ngramms.toLowerCase());
        boolean isCommonName=TermsRecognition.isCommonName(NI.ngramms.toLowerCase());
        String resultado="";
        int res=0;
       // System.out.println("he salido de la minuscula"+resultado+" "+res);
        if (info!=null){
            //System.out.println("he enrtado or planame ");
            resultado+="1";res++;} else resultado+="0";
        if (isProperName){resultado+="1";res++;} else resultado+="0";
        if (isCommonName){resultado+="1";res++;} else resultado+="0";
        //System.out.println("LOS RESULATODS QUE HE TRAIDO HASTA AQUI "+NI.ngramms+resultado+" "+res);
        if (Data.NickNamesTable.tID.containsKey(NI.ngramms.toLowerCase())){
           // System.out.println("he entrado por el mio cid ");
            if (ultimo.type==Terms.PSS){
                //System.out.println("EL ULTIMO ES "+ultimo.type);
                ultimo.string+=" "+NI.ngramms;
                ultimo.type=Terms.NPN;
                Token tokennew;
                 
                Lexer.numCh=Lexer.numCh+ultimo.string.length();
                Lexer.numWord+=2;
                BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                
                ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
               // System.out.println("la propuesta"+ngramms.ngramms.get(i).bgdata.nword);
                int index=Lexer.currentString.getIndex(NI.ngramms);
                Lexer.currentString.tokenList.get(index).word=ultimo.string;
                Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                //System.out.println("PINTAME EL ULTIMO "+Lexer.wbag.tam()+" "+ngramms.ngramms.get(i).ngramms);
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
                //System.out.println("EL AUTORIDA "+NI.ngramms);
                
                ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
        }else if (Data.PosessiveTable.contains(NI.ngramms)){
        		//System.out.println("es mio");
            BagData bgd=new PossesiveBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
            
            Lexer.numCh=Lexer.numCh+NI.ngramms.length();
             //Lexer.numWord++;
             
             ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                  //System.out.println("estoy poniendo el mio cid "+NI.ngramms);
        }else {
       
       // System.out.println("Hemos en contrado las isguientes propuestas "+res+NI.ngramms+" "+resultado);
        switch (res){
            case 0: {
               // System.out.println ("entro por aqui"+Lexer.isTheFirst);
                if (NI.ngramms.contains(" ")){
                    
                }else{
                BagData bgd=new BagData(NI.ngramms,TypesTerms.UNI,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
               ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.UNIDENTIFIED,false,bgd));
                }
                break;
            }
                   
            case 1: {
           //   System.out.println("El caracter = es "+resultado.charAt(1));
                if (resultado.charAt(0)=='1') {
                    //System.out.println("EL GAZETTER "+info.gazetteer);
                  if (!(info.gazetteer.contains("Geonames"))){
                  // System.out.println("ENTRO POR EL PLACE 1");
                    BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext(),false);
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                   // System.out.println("ENTRO POR EL PLACE 1");
                }else{
                      ngramms.ngramms.get(i).bgdata=new BagData();
                  }
                }
           
                if (resultado.charAt(1)=='1'){
                   //System.out.println("ENTRO POR EL PROPER 1");
                    BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    //System.out.println("ENTRO POR EL PROPER 1");
                }
           
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                   // System.out.println("ENTRO POR EL ELSE 1");
                    BagData bgd=new BagData(NI.ngramms,TypesTerms.AT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                    bgd.type=Terms.UN;
                    
                    
                    Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                }
            break;
            }
            case 2:{
               //System.out.println("LOS RESULTADOS DE LAS COSAS POR EL DOS"+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    
                  // System.out.println("LOS RESULTADOS 2 "+info.gazetteer);
                    if (info.gazetteer.contains("Geonames")){
                    	
                BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                    // System.out.println("meto nuevamemte propername");
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                        
                    }
                     else {
                    	// System.out.println("sigo por aqui en 2");
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                   //System.out.println("LOS RESULTADOS 4 "+NI.ngramms);
                    if (info.gazetteer.contains("Geonames"))  {
                        BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), info,Lexer.context.getContext(),true);
                        
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                        //Lexer.numWord++;
                    }
                    else {
                       // System.out.println("LA NUEVA LUGAR ES CASTILLA ");
                       BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext(),true);
                       Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                       //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    }
           
                }
                
                if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
               //   System.out.println("LOS RESULTADOS 4 ");
                  
                       // System.out.println("LA NUEVA LUGAR ES CASTILLA ");
                       BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), info,Lexer.context.getContext());
                       Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                       //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                    
           
                }
               // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
            //  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));  
            break;
            }
            case 3:{
      
             if (info.gazetteer.contains("Geonames")){
                       //    System.out.println("estoy por la opcion 3 y es mejor buscar "+info.gazetteer+" "+Lexer.numCh+NI.ngramms.length());
                        BagData bgd=new ProperNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(), new InfoFound(),Lexer.context.getContext());
                              //    System.out.println("estoy por la opcion 3 y es mejor buscar kkkkk");
                        Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                        //Lexer.numWord++;
                        ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.FOUND,false,bgd));
                        //System.out.println("he metido proper names");
                       //     Lexer.wbag.escribir();
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
         
       // System.out.println ("ESTOY EN MEDIO DE TODO "+ngramms.tam());
      //  for (int i=0; i<ngramms.tam();i++){
         //  System.out.println ("las token gram "+ngramms.ngramms.get(i).ngramms+" "+ngramms.ngramms.get(i).bgdata);
      //  }
         // System.out.println ("ESTOY EN MEDIO DE TODO ---------------------------------------------");  
            
            /******************************************************/
        
             
             
         
         Lexer.currentString.updateTokenList();
         //System.out.println("ACTUALIZAR LA LISTA");
         Lexer.currentString.ngramms.updateNgrammsList();
        // System.out.println("ACTUALIZAR LA LISTA"+Lexer.currentString.ngramms.tam());
         
        for (int i=0;i<Lexer.currentString.tokenList.size();i++){
             Token elemento=Lexer.currentString.tokenList.get(i);
            // System.out.println("los token"+elemento.word+" "+elemento.bdata);
             
         }
       
      //  System.out.println("lista de tokenes "+Lexer.currentString.tokenList.size());
                 
        //Lexer.currentString.ngramms.updateNgrammsList();
      // System.out.println ("EL CENTRO DE ARTE ");
             if (Lexer.isTheFirst){
       // System.out.println("Estoy en la primeraXXXXXXXXXXXXXXX"+Lexer.currentString.tokenList.get(0).word);
             	if (Lexer.currentString.tokenList.get(0).term==null) {
             		String stringRE=Lexer.currentString.tokenList.get(0).word;
             		
                        if (Lexer.wbag.tam()>0) {
                            BagData bgd=new BagData(stringRE,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(),new InfoFound(),Lexer.context.getContext());
                       
                        ngramms.ngramms.set(0, new NgrammsInfo(stringRE,VerificationInfo.FOUND,false,bgd));
                    
                        }else{
                            Lexer.currentString.ngramms.removeNString(stringRE);
             		Lexer.currentString.tokenList.remove(0);
             		Output.write(new RoleTreeNode(stringRE));
                        }
             	}
              } 
             
             
         //System.out.println("estoy en la lista "+ngramms.tam());
             boolean encontrado=false;
         for (int  i=ngramms.tam()-1;i>=0;i--){
          //  System.out.println("cuantas quedan "+ngramms.tam());
             NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
           //   System.out.println("vemos las variantes "+NI.bgdata.string+" "+NI.bgdata.type+" "+NI.ngramms);
             if (NI.bgdata.type==null){
              //   System.out.println("estoy por aqui en el final de la lista");
                 encontrado=true;
                 InfoFound infoAprox=NewTermsIdentification.getAproximation(NI.ngramms.toLowerCase());
             //    System.out.println("las varantes en encontradas son "+NI.ngramms+" "+infoAprox);
                 
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
                     
                     BagData bgd=new BagData(NI.ngramms,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext());
                     bgd.type=Terms.PPN;
                     Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++;
                    ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
                
                 }
             }
            // System.out.println("el recorrido medioooooooooooooooooooo");
         }
         
         
         if (encontrado){
          Lexer.currentString.updateTokenList();
        
        
                 
        Lexer.currentString.ngramms.updateNgrammsList();
         }
           
      //  System.out.println("HE ENTRADO POR LA PRINTLNv fuinal"+Lexer.currentString.tokenList.size());
         
          for (int  i=ngramms.tam()-1;i>=0;i--){
             NgrammsInfo NI=Lexer.currentString.ngramms.ngramms.get(i);
             //System.out.println("EL VACIO "+NI.ngramms);
             if (NI.bgdata.type==Terms.VACIO || NI.bgdata.type==Terms.UN){
                 //System.out.println("ENTRANDO POR EL VACIO FINAL");
                 BagData bgd=new PlaceNameBagData(NI.ngramms,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext(),false);
                 Lexer.numCh=Lexer.numCh+NI.ngramms.length();
                    //Lexer.numWord++; 
                 ngramms.ngramms.set(i, new NgrammsInfo(NI.ngramms,VerificationInfo.VALIDATE,false,bgd));
             
                } 
             
         }
          
       
                
         //System.out.println("CUANTAS HAY EN BAG "+Lexer.wbag.tam());
             
        
        Lexer.currentString.sendOutInfo();
       
       
         Lexer.currentString.tokenList.clear();
         Lexer.currentString.ngramms.ngramms.clear();
        
         //}
         return ContextualList.INITIAL;
     }
        
         catch(Exception e){ return ContextualList.INITIAL;}
    
   
}
     
     
     public ContextualList prepositionalSyntagmsListProcessing(String string){
         try{
           //  System.out.println("he enratod por preop family");
         //    Lexer.wbag.escribir();
         
               BagData ultimo=new BagData();
        //	System.out.println("estoy entramdopor family sproooeoosuny"+string);
        if (Lexer.lastToken!=""){
            if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  Lexer.wbag.wbag.remove(Lexer.wbag.wbag.size()-1);
          
            Lexer.wbag.put(new FamilyBagData(Lexer.lastToken+ultimo.string,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length()-1,Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
            	Lexer.lastToken="";
              }
            else{
            Lexer.wbag.put(new FamilyBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
            	Lexer.lastToken="";
              }  
        }
        }
        
      //  System.out.println("esto es lo ultimo que he leido antes de la aposicion");
           //  Lexer.wbag.escribir();
           
        	 
         
         String newString=string.replaceAll("\\s+", " ");
       
         //tokenize the stirng
         Lexer.currentString=new TokenizedString(newString);
         
         for (int i=0; i<Lexer.currentString.tokenList.size();i++){
             Token token=Lexer.currentString.tokenList.get(i);
             if (Recognition.ElementsRecognition.isDeterminantPrep(token.word)){
               //  System.out.println("he entrado por de baga datadeterminante "+token.word);
                 if (Lexer.wbag.tam()>0){
                     BagData last=Lexer.wbag.getLast();
                     if (last.type==Terms.NPLN){
                         if (((PlaceNameBagData)last).tipoNombre) last.string+=" "+token.word;
                     Lexer.currentString.tokenList.remove(i);
                     
                     }else{
                         token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
                 
                     }
                 }else{
                 token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
                 }
                 Lexer.lastToken="";
         //        Lexer.wbag.escribir();
             }else if (Recognition.ElementsRecognition.isCopulativeConjunction(token.word).size()>0){
               token.bdata=new CopulativeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
              
             }
         }
     
         
         newString=Lexer.currentString.processingArticles(newString);
         
        
         newString=Lexer.currentString.processingAppositionsPrepositions(newString);
                
         

         String[] newStrings=Lexer.currentString.processingCopulativeConjunctions(newString);
         String firstWord=Lexer.currentString.tokenList.get(0).word;
          
       //  System.out.println ("la cadena nueva es "+newString+newStrings.length+firstWord);
       //  System.out.println("La cadena tokenizada es "+Lexer.currentString.tokenList.size());
         
       /*  for (int i=0; i<newStrings.length;i++){
             String[] aux=newStrings[i].split(" ");
             System.out.println("que hago aqui "+aux.length+aux[0]);
             for (int j=0; j<aux.length;j++){
                 BagData bgd=new BagData(aux[j],TypesTerms.UNI,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux[j].length(),new InfoFound(),Lexer.context.getContext());
                 //Lexer.numWord++;
                 Lexer.numCh+=aux[j].length();                 
                Lexer.currentString.ngramms.ngramms.add(new NgrammsInfo(aux[j],VerificationInfo.UNIDENTIFIED,false,bgd));
         }
         } */
         Lexer.currentString.ngramms.write();
         
      //   System.out.println("estanos en este punto que no entiendo nada ");
      //   Lexer.wbag.escribir();
      //   System.out.println("estamos teniendo otras cosas de ");
         Lexer.currentString.ngramms.write();
        // System.out.println("La cadena tokenizada es "+Lexer.currentString.ngramms.tam());
         /*for (int i=0;i<Lexer.currentString.ngramms.ngramms.size();i++){
             String aux=Lexer.currentString.ngramms.ngramms.get(i).ngramms;
             aux=aux.replaceAll("_", " ");
                
             aux=WordTransformations.removeCleanPattern(Data.AppositionPrepositionsTable.regEx, aux);
             */
             
         //   System.out.println(aux+"pitnntnnnt");
         
         for (int i=0; i<Lexer.currentString.tokenList.size();i++){
             Token elemento=Lexer.currentString.tokenList.get(i);
             String aux=Lexer.currentString.tokenList.get(i).word;
             
             if (elemento.bdata==null){
         //    System.out.println("muestrame el termino "+aux);
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
                BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
                Lexer.numCh=Lexer.numCh+ultimo.string.length();
                Lexer.numWord+=2;
                Lexer.currentString.ngramms.ngramms.set(i,new NgrammsInfo(ultimo.string,VerificationInfo.FOUND,false,bgd));
               // System.out.println("la propuesta"+ngramms.ngramms.get(i).bgdata.nword);
                int index=Lexer.currentString.getIndex(aux);
                Lexer.currentString.tokenList.get(index).word=ultimo.string;
                Lexer.wbag.wbag.remove(Lexer.wbag.tam()-1);
                //System.out.println("PINTAME EL ULTIMO "+Lexer.wbag.tam()+" "+ngramms.ngramms.get(i).ngramms);
            }else{
               BagData bgd=new NickNameBagData(ultimo.string,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+ultimo.string.length(), new InfoFound(),Lexer.context.getContext());
               Lexer.numCh=Lexer.numCh+ultimo.string.length();
                //Lexer.numWord++;
                Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
            //Lexer.wbag.put(new NickNameBagData(NI.ngramms,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+NI.ngramms.length(),new InfoFound(),Lexer.context.getContext()));
            for (int j=0;j<Lexer.wbag.tam();j++){
              //  System.out.println("las boslas nick "+Lexer.wbag.get(i).string);
            }
            }
        }else if (Recognition.ElementsRecognition.isDeterminantPrep(string)){
         //   System.out.println("DETER");
        }else if (Data.AuthorityNamesTable.tID.containsKey(aux.toLowerCase())){
            BagData bgd=new AuthorityBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(),new InfoFound(),Lexer.context.getContext());
            Lexer.numCh=Lexer.numCh+aux.length();
                 //Lexer.numWord++;
                // System.out.println("EL AUTORIDA "+aux);
                 
                 Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
         }
        else {
       //System.out.println("Hemos en contrado las isguientes en preposicion propuestas "+res+" "+resultado+" "+aux);
        switch (res){
            case 0: {
             
              ContextualList context=determineContext(aux,this);
             
              if (context!=ContextualList.SAME) break;   
           //    System.out.println("HE SALIDO POR AQUI CUANDO no he reconocido "+aux+" "+context);
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
                    if (Lexer.wbag.tam()>0){
                     BagData last=Lexer.wbag.getLast();
                     if (last.type==Terms.NPLN){
                         if (((PlaceNameBagData)last).tipoNombre) last.string+=" "+aux;
                     Lexer.currentString.tokenList.remove(i);
                     
                     }else{
                         BagData bgd=new PlaceNameBagData(aux,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                    Lexer.numCh=Lexer.numCh+aux.length();
                    //Lexer.numWord++;
                    Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
               
                     }
                 }else{
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.GENT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                    Lexer.numCh=Lexer.numCh+aux.length();
                    //Lexer.numWord++;
                    Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                }
                 Lexer.lastToken="";
                    
                    }
             
                } else{
                 //    System.out.println("HE SALIDO POR AquI ELSE");
                     BagData bgd=new ProperNameBagData(aux,TypesTerms.PPT,elemento.position,elemento.nWord,elemento.position+aux.length(),new InfoFound(),Lexer.context.getContext());
                     elemento.bdata=bgd;
                     Data.ProperNamesTable.putNewName(aux.toLowerCase(), Input.name, "Hismetag", "person");
                 //   System.out.println("HE SALIDO POR AquI ELSE");
                   
                 }
               // System.out.println("HE SALIDO POR AquI ELSE FIN");
                
                break;
               
            }
                   
            case 1: {
              //  System.out.println("El caracter = es "+resultado.charAt(1));
                if (resultado.charAt(0)=='1') {
                //  System.out.println("ENTRO POR EL PLACE 1");
                  
                 
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                    elemento.bdata=bgd;
                 Lexer.numCh=Lexer.numCh+aux.length();
                    //Lexer.numWord++;
                //    System.out.println("NOOOOOOOOOOOOOOOOOOOOO"+Lexer.currentString.ngramms.ngramms.size());
                    
                        
                
                        
                        
                        
                        
                     }
           
                if (resultado.charAt(1)=='1'){
                   // System.out.println("ENTRO POR EL PROPER 1");
                    BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                    elemento.bdata=bgd;
                }
           
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                   // System.out.println("ENTRO POR EL ELSE 1");
                    Lexer.wbag.restart();
                    Output.write(new RoleTreeNode(aux));
                    Lexer.numCh=Lexer.numCh+aux.length();
                    //Lexer.numWord++;
                }
                
            break;
            }
            case 2:{
              // System.out.println("LOS RESULTADOS DE LAS COSAS "+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    
               //    System.out.println("LOS RESULTADOS 2 "+aux);
                    if (info.gazetteer.contains("Geonames")){
                        BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                        elemento.bdata=bgd;
                    }
                     else {
                       
                         BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                    elemento.bdata=bgd;
                   
                 Lexer.lastToken="";
                    }
           
                }
               // System.out.println("LOS RESULTADOS DE LAS COSAS otra "+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                  
                    if (info.gazetteer.contains("Geonames"))  {
                       Lexer.wbag.restart();
                        Output.write(new RoleTreeNode(aux));
                        Lexer.numCh=Lexer.numCh+aux.length();
                        //Lexer.numWord++;
                    }
                    else {
                   //     System.out.println("LOS RESULTADOS 4 "+aux);
                       
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                    elemento.bdata=bgd; }
                 Lexer.lastToken="";
                   
           
                }
               // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
            break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames")){
                        BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                        elemento.bdata=bgd;   }
            else {
                       
                 BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                   elemento.bdata=bgd;
                 Lexer.lastToken="";
                    }
           
            break;
            }
        }
        } 
         
         }
         }
       
       // System.out.println("NO SALGO POR AQUI");
       // Lexer.wbag.escribir();
         
        
         //Lexer.currentString.updateTokenList();
       
        Lexer.currentString.sendOutInfo();
   // System.out.println ("HE SALIDO DE LA VUELTA"+Lexer.wbag.tam());
   // Lexer.wbag.escribir();
        
         Lexer.currentString.tokenList.clear();
         Lexer.currentString.ngramms.ngramms.clear();
       //  System.out.println ("HE SALIDO DE LA VUELTA"+Lexer.wbag.tam());
    //Lexer.wbag.escribir();
         return Lexer.context.getContext();
     }catch(Exception e){return ContextualList.INITIAL;}
     }
     
}
    
        
    
    

