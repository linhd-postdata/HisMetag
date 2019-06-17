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
import StringInProcess.TokenizedString;
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class SaintContext extends SemanticContext {
    
    public SaintContext(SemanticContext previous){super(previous);}
  
    public ContextualList getContext(){
        return ContextualList.SAINT;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
            InfoFound info=TermsRecognition.existMedievalPlaceName(WordProcessing.WordTransformations.capitalize(word));
    
                if (Data.ProperNamesTable.contains(word)){
                    if (Lexer.previousContextStack.peek().getContext()==ContextualList.PLACE){
                        BagData bgd=new SaintBagData(Lexer.lastToken,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
                        Lexer.wbag.put(bgd);
                         bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
        	  Lexer.lastToken="";
        	  Lexer.wbag.put(bgd);
                  return Lexer.context;
                    }else {
                        BagData bgd=new SaintBagData(Lexer.lastToken,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
                        Lexer.wbag.put(bgd);
                         bgd=new ProperNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
        	  Lexer.lastToken="";
        	  Lexer.wbag.put(bgd);
                  return Lexer.context;
                    }
                    
                }else if(ElementsRecognition.isDeterminantPrep(word)){
                   BagData bgd=new DeBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
                   Lexer.wbag.put(bgd);
                   return Lexer.context;
                }else if (info!=null ){
                   BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
        	  Lexer.lastToken="";
        	  Lexer.wbag.put(bgd);
                  return Lexer.context;
                }
            ContextualList context=determineContext(word,this);
           if (context!=ContextualList.SAME)
        if (context!=ContextualList.SAINT){
        //System.out.println("he entrado por este cambio contexto");
            Lexer.context.changeContext(context, Lexer.context," ", word);
            Lexer.isTheFirst=false;
           // System.out.println("hes salido despues del ambio");
          
            return Lexer.context;
        }else return Lexer.context;
           
          if (checkVerb(word)){ return new GeneralContext(null);}
           
           if (check(word)){ return new GeneralContext(null);}
            
        	//  System.out.println("entro por minusculas saint "+word+" "+Lexer.lastToken);
            //  Lexer.wbag.escribir();
              if (ElementsRecognition.isDeterminantPrep(word)){
            	// System.out.println("he entrado por el determinante");
            	  if (Lexer.lastToken!="")
            	  Lexer.wbag.put(new SaintBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
            	//  System.out.println(Lexer.wbag.get(0).type);
            	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                  Lexer.lastToken="";
                  return Lexer.context;
              }
              
              if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
            	//  System.out.println("he entrado por el determinante2");
            	  Lexer.lastToken="";
            	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
            	  bgd.type=Terms.AFPLN;
            	  Lexer.wbag.put(bgd);
            	  return Lexer.context;
              }
              
             
            
              
             // System.out.println("el verbo anterior es "+Lexer.verbsFlag.verb+" "+Lexer.prepositionFlag+" "+Lexer.verbsFlag.complement);
             // if (Lexer.prepositionFlag.toLowerCase().equals("en")) System.out.println("es verdad");
              if (Lexer.lastToken!=""){
              Lexer.wbag.put(new SaintBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
              Lexer.lastToken="";
              }
             // System.out.println("YA ESTAMOS AQUI CON LOS SANTOS");
              if (ElementsRecognition.isCopulativeConjunction(word).size()>0){
                //     System.out.println("he entrado por aqui en copulativa");
                     BagData bgd=new CopulativeBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
        	Lexer.wbag.put(bgd);
                Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, "", "");
                 }else
              if (Lexer.previousContextStack.peek().getContext()==ContextualList.PLACE){
                  
                  if (Data.ProperNamesTable.contains(word) || Data.MedievalPlaceNamesTable.contains(WordTransformations.capitalize(word)).size()>0){
                      BagData bgd=new SaintBagData(Lexer.lastToken,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
                        Lexer.wbag.put(bgd);
                       bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
        	  Lexer.lastToken="";
        	  Lexer.wbag.put(bgd);
                  }else if (checkVerb(word)){
           		//System.out.println("el contexto anterior");
                            Lexer.previousContextStack.clear();
                            return new GeneralContext(null);
                  }else if (check(word)){
           		//System.out.println("el contexto anterior");
                            Lexer.previousContextStack.clear();
                            return new GeneralContext(null);
                  }else{
                   BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
        	  Lexer.lastToken="";
        	  Lexer.wbag.put(bgd);   
                  }
                 // return Lexer.context;
           	 } else if (Lexer.prepositionFlag.toLowerCase().equals("en") && !(Lexer.verbsFlag.type.equals("NPL"))){
                  //   System.out.println("el contexto de santo es un desastre");
           		 BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
               	  Lexer.lastToken="";
               	  Lexer.wbag.put(bgd);
                     
                  //return Lexer.context;
           	 } 
                 
           	 else{//System.out.println("estoy por el proper");
           		BagData bgd=new ProperNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
              	  Lexer.lastToken="";
              	  Lexer.wbag.put(bgd);
                 // return Lexer.context;
           	 }
               
              
               return Lexer.context;
        }catch(Exception e){return Lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
            
      	  //System.out.println("entro por mayusculas saint "+word+" "+Lexer.lastToken);
         //   Lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word)){
          	//  System.out.println("he entrado por el determinante");
          	  Lexer.wbag.put(new SaintBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
          	//  System.out.println(Lexer.wbag.get(0).type);
          	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                Lexer.lastToken="";
                return Lexer.context.getContext();
            }
            
            if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
          	//  System.out.println("he entrado por el determinante2");
          	  Lexer.lastToken="";
          	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
          	  bgd.type=Terms.AFPLN;
          	  Lexer.wbag.put(bgd);
          	  return Lexer.context.getContext();
            }
            
           // System.out.println("el verbo anterior es "+Lexer.verbsFlag.verb+" "+Lexer.prepositionFlag+" "+Lexer.verbsFlag.complement);
            //if (Lexer.prepositionFlag.toLowerCase().equals("en")) System.out.println("es verdad");
            if (Lexer.lastToken!=""){
            Lexer.wbag.put(new SaintBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
         	Lexer.lastToken=""; 
            }
            
            if (ElementsRecognition.isCopulativeConjunction(word).size()>0){
                 //    System.out.println("he entrado por aqui en copulativa");
                     BagData bgd=new CopulativeBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
        	Lexer.wbag.put(bgd);
                Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, "", "");
                 }else
            if (Lexer.previousContextStack.peek().getContext()==ContextualList.PLACE){
            BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
      	  
      	  Lexer.wbag.put(bgd);
         	 }else if (Lexer.prepositionFlag.toLowerCase().equals("en") && !(Lexer.verbsFlag.complement.contains("en"))){
         		 BagData bgd=new PlaceNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext(),false);
             	  
             	  Lexer.wbag.put(bgd);
         	 }
         	 else{
         		BagData bgd=new ProperNameBagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
            	  
            	  Lexer.wbag.put(bgd);
         	 }
             return Lexer.context.getContext();
        }catch(Exception e){return this.getContext();}
      
      }
      
    
    public ContextualList wordListProcessing(String string){
       try{
    	   if (Lexer.lastToken!="")
               Lexer.wbag.put(new SaintBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
            	 if (Lexer.previousContextStack.peek().getContext()==ContextualList.PLACE){
               BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext(),false);
         	  
         	  Lexer.wbag.put(bgd);
            	 }else if (Lexer.prepositionFlag.toLowerCase().equals("en") && !(Lexer.verbsFlag.complement.contains("en"))){
            		 BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext(),false);
                	  
                	  Lexer.wbag.put(bgd);
            	 }
            	 else{
            		BagData bgd=new ProperNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
               	  
               	  Lexer.wbag.put(bgd);
            	 }
            	 Lexer.lastToken="";
                return Lexer.context.getContext();
       }catch(Exception e){return ContextualList.INITIAL;}
       
    }
    
     public ContextualList nounPhraseProcessing(String string){
        // System.out.println("he entrado por el contexto de nombre de santo"+string);
         
         if (Lexer.lastToken!="")
             Lexer.wbag.put(new SaintBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext()));
          	 if (Lexer.previousContextStack.peek().getContext()==ContextualList.PLACE){
          		// System.out.println("el contexto anterior");
             BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext(),false);
       	  Lexer.lastToken="";
       	  Lexer.wbag.put(bgd);
       //	  Lexer.wbag.escribir();
       }else if (Lexer.prepositionFlag.toLowerCase().equals("en") && !(Lexer.verbsFlag.complement.contains("en"))){
          		 BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext(),false);
              	  Lexer.lastToken="";
              	  Lexer.wbag.put(bgd);
          	 }
          	 else{
          		 
          		 
          		String newString=string.replaceAll("\\s+", " ");
          		
          		String de=TermsRecognition.findApposition(newString);
          		
          		
          		String tosplitedString=newString.replaceAll(" "+de+" ","_"+de+"_");
          		
          		
              String[] stringArray=tosplitedString.split("_"+de+"_");
              
              String[] izda=stringArray[0].split(" ");
              String[] dcha=stringArray[1].split(" ");
             // System.out.println("vamos a ver que psa"+tosplitedString);
          		BagData bgd=new ProperNameBagData(stringArray[0],TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+stringArray[0].length(),new InfoFound(),Lexer.context.getContext());
             	Lexer.wbag.put(bgd);
             	Lexer.wbag.put(new DeBagData(de,TypesTerms.FT,Lexer.numCh+1+stringArray[0].length(), Lexer.numWord++,Lexer.numCh+1+stringArray[0].length()+2+de.length(), new InfoFound(),Lexer.context.getContext()));
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
                int numCh=Lexer.numCh+stringArray[0].length()+1+de.length()+1;
            	int numW=Lexer.numWord+2;
            	int end=numCh+stringArray[1].length();
            	BagData bgdiz;
                switch (res){
                    case 0: {
                     //   System.out.println("el resultado es "+stringArray[1]);
                        InfoFound infoAprox=NewTermsIdentification.getAproximation(stringArray[1]);
                        
                        if (infoAprox!=null){
                           // System.out.println("estoy entrando por el 0");
                        if (info.gazetteer!="proper" && info.gazetteer!="nick"){
                        	bgdiz=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,Lexer.context.getContext(),true);
                        	Lexer.wbag.put(bgd);
                        }else{
                        	bgdiz=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),Lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	Lexer.wbag.put(bgd);
                        }
                        
                        } else {
                        	bgdiz=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),Lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	Lexer.wbag.put(bgd);
                        	
                           // Output.write(word);
                        }
                    }/* buscar una variante */;
                    case 1: {
                       // System.out.println("El caracter = es "+resultado.charAt(2));
                        if (resultado.charAt(0)=='1') {
                        	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,Lexer.context.getContext(),true);
                        	Lexer.wbag.put(bgd);
                        	}
                        if (resultado.charAt(1)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),Lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	Lexer.wbag.put(bgd);
                        }
                        if (resultado.charAt(2)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),Lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	Lexer.wbag.put(bgd);
                            
                           // Output.write(word);
                        }
                    break;
                    }
                    case 2:{
                        if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                        	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,Lexer.context.getContext(),true);
                        	Lexer.wbag.put(bgd);
                        }
                        if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                        	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,Lexer.context.getContext(),true);
                        	Lexer.wbag.put(bgd);
                            	
                              //  Output.write(word);
                             
                        }
                        if (resultado.charAt(1)=='1' && resultado.charAt(2)=='1') {
                        	bgd=new BagData(stringArray[1],TypesTerms.FT,numCh,numW,end,new InfoFound(),Lexer.context.getContext());
                        	bgd.type=Terms.ARNS;
                        	Lexer.wbag.put(bgd);
                            	
                              //  Output.write(word);
                             
                        }
                      //Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));  
                    break;
                    }
                    case 3:{
                    	bgd=new PlaceNameBagData(stringArray[1],TypesTerms.FT,numCh,numW,end,info,Lexer.context.getContext(),true);
                    	Lexer.wbag.put(bgd);break;
                    }
                }
                }
          		Lexer.lastToken="";
             	  
          	 
      
        return  Lexer.context.getContext();
     }
     
     public ContextualList prepositionalSyntagmsListProcessing(String string){return null;}
       
       
    
}
