/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

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
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class SaintContext extends SemanticContext {
    
    public SaintContext(SemanticContext previous, Lexer lexer, Output output){
        super(previous, lexer, output);
    }
  
    public ContextualList getContext(){
        return ContextualList.SAINT;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
            InfoFound info=TermsRecognition.existMedievalPlaceName(WordProcessing.WordTransformations.capitalize(word));
    
                if (Data.ProperNamesTable.contains(word)){
                    if (this.lexer.peekPreviousContext().getContext()==ContextualList.PLACE){
                        BagData bgd=new SaintBagData(this.lexer.getLastToken(),TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
                        this.lexer.wbag.put(bgd);
                         bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
        	  this.lexer.setLastToken("");
        	  this.lexer.wbag.put(bgd);
                  return this.lexer.context;
                    }else {
                        BagData bgd=new SaintBagData(this.lexer.getLastToken(),TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
                        this.lexer.wbag.put(bgd);
                         bgd=new ProperNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
        	  this.lexer.setLastToken("");
        	  this.lexer.wbag.put(bgd);
                  return this.lexer.context;
                    }
                    
                }else if(ElementsRecognition.isDeterminantPrep(word)){
                   BagData bgd=new DeBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
                   this.lexer.wbag.put(bgd);
                   return this.lexer.context;
                }else if (info!=null ){
                   BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
        	  this.lexer.setLastToken("");
        	  this.lexer.wbag.put(bgd);
                  return this.lexer.context;
                }
            ContextualList context=determineContext(word,this);
           if (context!=ContextualList.SAME)
        if (context!=ContextualList.SAINT){
        //System.out.println("he entrado por este cambio contexto");
            this.lexer.context.changeContext(context, this.lexer.context," ", word);
            this.lexer.setTheFirst(false);
           // System.out.println("hes salido despues del ambio");
          
            return this.lexer.context;
        }else return this.lexer.context;
           
          if (checkVerb(word)){ return new GeneralContext(null, this.lexer, this.output);}
           
           if (check(word)){ return new GeneralContext(null, this.lexer, this.output);}
            
        	//  System.out.println("entro por minusculas saint "+word+" "+this.lexer.getLastToken());
            //  this.lexer.wbag.escribir();
              if (ElementsRecognition.isDeterminantPrep(word)){
            	// System.out.println("he entrado por el determinante");
            	  if (this.lexer.getLastToken()!="")
            	  this.lexer.wbag.put(new SaintBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	//  System.out.println(this.lexer.wbag.get(0).type);
            	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                  this.lexer.setLastToken("");
                  return this.lexer.context;
              }
              
              if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
            	//  System.out.println("he entrado por el determinante2");
            	  this.lexer.setLastToken("");
            	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
            	  bgd.type=Terms.AFPLN;
            	  this.lexer.wbag.put(bgd);
            	  return this.lexer.context;
              }
              
             
            
              
             // System.out.println("el verbo anterior es "+this.lexer.verbsFlag.verb+" "+this.lexer.prepositionFlag+" "+this.lexer.verbsFlag.complement);
             // if (this.lexer.prepositionFlag.toLowerCase().equals("en")) System.out.println("es verdad");
              if (this.lexer.getLastToken()!=""){
              this.lexer.wbag.put(new SaintBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
              this.lexer.setLastToken("");
              }
             // System.out.println("YA ESTAMOS AQUI CON LOS SANTOS");
              if (ElementsRecognition.isCopulativeConjunction(word).size()>0){
                //     System.out.println("he entrado por aqui en copulativa");
                     BagData bgd=new CopulativeBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
        	this.lexer.wbag.put(bgd);
                this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, "", "");
                 }else
              if (this.lexer.peekPreviousContext().getContext()==ContextualList.PLACE){
                  
                  if (Data.ProperNamesTable.contains(word) || Data.MedievalPlaceNamesTable.contains(WordTransformations.capitalize(word)).size()>0){
                      BagData bgd=new SaintBagData(this.lexer.getLastToken(),TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
                        this.lexer.wbag.put(bgd);
                       bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
        	  this.lexer.setLastToken("");
        	  this.lexer.wbag.put(bgd);
                  }else if (checkVerb(word)){
           		//System.out.println("el contexto anterior");
                            this.lexer.clearPreviousContext();
                            return new GeneralContext(null, this.lexer, this.output);
                  }else if (check(word)){
           		//System.out.println("el contexto anterior");
                            this.lexer.clearPreviousContext();
                            return new GeneralContext(null, this.lexer, this.output);
                  }else{
                   BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
        	  this.lexer.setLastToken("");
        	  this.lexer.wbag.put(bgd);   
                  }
                 // return this.lexer.context;
           	 } else if (this.lexer.prepositionFlag.toLowerCase().equals("en") && !(this.lexer.verbsFlag.type.equals("NPL"))){
                  //   System.out.println("el contexto de santo es un desastre");
           		 BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
               	  this.lexer.setLastToken("");
               	  this.lexer.wbag.put(bgd);
                     
                  //return this.lexer.context;
           	 } 
                 
           	 else{//System.out.println("estoy por el proper");
           		BagData bgd=new ProperNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
              	  this.lexer.setLastToken("");
              	  this.lexer.wbag.put(bgd);
                 // return this.lexer.context;
           	 }
               
              
               return this.lexer.context;
        }catch(Exception e){return this.lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
            
      	  //System.out.println("entro por mayusculas saint "+word+" "+this.lexer.getLastToken());
         //   this.lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word)){
          	//  System.out.println("he entrado por el determinante");
          	  this.lexer.wbag.put(new SaintBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
          	//  System.out.println(this.lexer.wbag.get(0).type);
          	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                this.lexer.setLastToken("");
                return this.lexer.context.getContext();
            }
            
            if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
          	//  System.out.println("he entrado por el determinante2");
          	  this.lexer.setLastToken("");
          	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
          	  bgd.type=Terms.AFPLN;
          	  this.lexer.wbag.put(bgd);
          	  return this.lexer.context.getContext();
            }
            
           // System.out.println("el verbo anterior es "+this.lexer.verbsFlag.verb+" "+this.lexer.prepositionFlag+" "+this.lexer.verbsFlag.complement);
            //if (this.lexer.prepositionFlag.toLowerCase().equals("en")) System.out.println("es verdad");
            if (this.lexer.getLastToken()!=""){
            this.lexer.wbag.put(new SaintBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
         	this.lexer.setLastToken(""); 
            }
            
            if (ElementsRecognition.isCopulativeConjunction(word).size()>0){
                 //    System.out.println("he entrado por aqui en copulativa");
                     BagData bgd=new CopulativeBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
        	this.lexer.wbag.put(bgd);
                this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, "", "");
                 }else
            if (this.lexer.peekPreviousContext().getContext()==ContextualList.PLACE){
            BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
      	  
      	  this.lexer.wbag.put(bgd);
         	 }else if (this.lexer.prepositionFlag.toLowerCase().equals("en") && !(this.lexer.verbsFlag.complement.contains("en"))){
         		 BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext(),false);
             	  
             	  this.lexer.wbag.put(bgd);
         	 }
         	 else{
         		BagData bgd=new ProperNameBagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
            	  
            	  this.lexer.wbag.put(bgd);
         	 }
             return this.lexer.context.getContext();
        }catch(Exception e){return this.getContext();}
      
      }
      
    
    public ContextualList wordListProcessing(String string){
       try{
    	   if (this.lexer.getLastToken()!="")
               this.lexer.wbag.put(new SaintBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
            	 if (this.lexer.peekPreviousContext().getContext()==ContextualList.PLACE){
               BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext(),false);
         	  
         	  this.lexer.wbag.put(bgd);
            	 }else if (this.lexer.prepositionFlag.toLowerCase().equals("en") && !(this.lexer.verbsFlag.complement.contains("en"))){
            		 BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext(),false);
                	  
                	  this.lexer.wbag.put(bgd);
            	 }
            	 else{
            		BagData bgd=new ProperNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
               	  
               	  this.lexer.wbag.put(bgd);
            	 }
            	 this.lexer.setLastToken("");
                return this.lexer.context.getContext();
       }catch(Exception e){return ContextualList.INITIAL;}
       
    }
    
     public ContextualList nounPhraseProcessing(String string){
        // System.out.println("he entrado por el contexto de nombre de santo"+string);
         
         if (this.lexer.getLastToken()!="")
             this.lexer.wbag.put(new SaintBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext()));
          	 if (this.lexer.peekPreviousContext().getContext()==ContextualList.PLACE){
          		// System.out.println("el contexto anterior");
             BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext(),false);
       	  this.lexer.setLastToken("");
       	  this.lexer.wbag.put(bgd);
       //	  this.lexer.wbag.escribir();
       }else if (this.lexer.prepositionFlag.toLowerCase().equals("en") && !(this.lexer.verbsFlag.complement.contains("en"))){
          		 BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext(),false);
              	  this.lexer.setLastToken("");
              	  this.lexer.wbag.put(bgd);
          	 }
          	 else{
          		 
          		 
          		String newString=string.replaceAll("\\s+", " ");
          		
          		String de=TermsRecognition.findApposition(newString);
          		
          		
          		String tosplitedString=newString.replaceAll(" "+de+" ","_"+de+"_");
          		
          		
              String[] stringArray=tosplitedString.split("_"+de+"_");
              
              String[] izda=stringArray[0].split(" ");
              String[] dcha=stringArray[1].split(" ");
             // System.out.println("vamos a ver que psa"+tosplitedString);
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
                
              //  System.out.println("Hemos en contrado las isguientes propuestas  dddd"+res+resultado);
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
                        	
                           // this.output.write(word);
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
                            
                           // this.output.write(word);
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
                            	
                              //  this.output.write(word);
                             
                        }
                        if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),this.lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	this.lexer.wbag.put(bgd);
                            	
                              //  this.output.write(word);
                             
                        }
                      //this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));  
                    break;
                    }
                    case 3:{
                    	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,this.lexer.context.getContext(),true);
                    	this.lexer.wbag.put(bgd);break;
                    }
                }
                }
          		this.lexer.setLastToken("");
             	  
          	 
      
        return  this.lexer.context.getContext();
     }
     
     public ContextualList prepositionalSyntagmsListProcessing(String string){return null;}
       
       
    
}
