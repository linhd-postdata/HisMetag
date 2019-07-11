/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import Data.MedievalNewPlaceNamesTable;
import Data.NewProperNamesTable;
import Data.Verbs;
import DataStructures.*;
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
public class TreatmentContext extends SemanticContext {
    public TreatmentContext(){
    	
   }
    public TreatmentContext(SemanticContext previous){super(previous);}
   
    public ContextualList getContext(){
        return ContextualList.TREATMENT;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
      try{
    	 //System.out.println("LA BOLSA ANTES DE NETRAR EN MINUSCULAS TREATMENT"+Lexer.wbag.tam()+word+" "+Lexer.numCh+" "+Lexer.numWord);
          BagData ultimo=new BagData();
           
        // Lexer.wbag.escribir();
      //System.out.println("edtoy leyendo esta palabra "+word+"   "+ Lexer.context.getContext()+ Lexer.numCh+Lexer.numWord);
          
        ContextualList context=determineContext(word,this);
       
       // System.out.println("la familia "+context);
        Verbs elVerb;
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.TREATMENT){
        	if (context==ContextualList.FAMILY){
                  //  System.out.println("estanos entrando por la opcion de cambio ");
             //       Lexer.wbag.escribir();
        		Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
        		Lexer.wbag.get(Lexer.wbag.tam()-1).type=Terms.NPD;
        	//	System.out.println("el tipo de la ultima family"+Lexer.wbag.get(Lexer.wbag.tam()-1).type);
        	// Lexer.wbag.escribir();
                 Lexer.lastToken="";
                }else{
        		//System.out.println("he entrado por este contexto");
                Lexer.context.changeContext(context, Lexer.context," ", word);
             //   Lexer.wbag.escribir();
        	}
        	
            //System.out.println("hes salido despues del ambio");
            return Lexer.context;
        }else return Lexer.context;
      //  System.out.println("HE COMPROBADO" +word);
        if (Data.FamilyNamesTable.contains(word)){
        	Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
    		Lexer.wbag.get(Lexer.wbag.tam()-1).type=Terms.NPD;
    		//System.out.println("el tipo de la ultima family"+Lexer.wbag.get(Lexer.wbag.tam()-1).type);
    		return Lexer.context;
        }
        if (checkVerb(word)){  Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
            return Lexer.context;}
        
        if (check(word)){ 
            Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
        return Lexer.context;}
        //System.out.println ("LA BOLSAAAAAAA "+Output.dataOut);
      
        
       
       
 // System.out.println("estamos entrando por minuscula  tras check XXXXXXXXXXXXXX"+word);
       InfoFound info=TermsRecognition.existMedievalPlaceName(WordProcessing.WordTransformations.capitalize(word));
       
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
           /* if (ultimo.type==Terms.PSS){
               System.out.println("EL ULTIMO ES "+ultimo.type);
                ultimo.string+=" "+word;
                ultimo.type=Terms.NPN;
                
              //  System.out.println("PINTAME EL ULTIMO "+Lexer.wbag.get(Lexer.wbag.tam()-1).string);
            }else{*/
            Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
          
           // }
        } 
        else {
      //  System.out.println("Hemos en contrado las isguientes propuestas "+res+resultado);
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word);
                
                if (infoAprox!=null){
                   // System.out.println("estoy entrando por el 0");
                    if (infoAprox.uri=="proper") Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else if (infoAprox.uri=="nick") Lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                else {
                    if (Lexer.articleFlag!=""){Lexer.wbag.restart();Lexer.articleFlag="";}
                	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                        Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, "", "");
                	//Output.write(word);
                }
                } else {
                    if (Lexer.articleFlag!=""){Lexer.wbag.restart();Lexer.articleFlag="";}
                    Lexer.context.changeContext(ContextualList.INITIAL, Lexer.context," ", word);
                    Lexer.wbag.restart();
                	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                   // Output.write(word);
                }
               
            }/* buscar una variante */;
            case 1: {
               // System.out.println("El caracter = es "+resultado.charAt(2));
                if (resultado.charAt(0)=='1') Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),false));
                if (resultado.charAt(1)=='1') Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                    Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                   // Output.write(word);
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames"))  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
                }
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                	
                    	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
                      //  Output.write(word);
                     }
              //Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));  
            break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames"))  Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                    else Lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),info,Lexer.context.getContext(),true));
            break;
            }
        }
        }
        
       
     
           return Lexer.context;
      }catch(Exception e){return Lexer.context;}
    }
    
    public ContextualList checkCapitalLettersWord(String word){
      try{
    	  
    	 // System.out.println("entro por capital letters "+word);
          word=word.replaceAll("\\s+", " ");
          ContextualList context=determineContext(word,this);
          Verbs elVerb;
          if (context!=ContextualList.SAME)
          if (context!=ContextualList.TREATMENT){
          	//System.out.println("he entrado por este contexto");
              Lexer.context.changeContext(context, Lexer.context," ", word);
             // System.out.println("hes salido despues del ambio");
              return Lexer.context.getContext();
          }else return Lexer.context.getContext();
          //System.out.println("HE COMPROBADO" +word);
           if (checkVerb(word)){ 
        	  Lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
        	  return Lexer.context.getContext();}
         // System.out.println("entro por capital letters "+word);
        if (Lexer.previousContextStack.peek() instanceof ContextProcessing.PlaceContext){
        }
        else if (check(word)){ 
        	  Lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
        	  return Lexer.context.getContext();}
         // System.out.println("entro por capital letters "+word);
        if (Lexer.previousContextStack.peek() instanceof ContextProcessing.PlaceContext){
        }else{
        	InfoFound info=TermsRecognition.existMedievalPlaceName(word);
            
            boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
            boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
            if (isProperName){
            	Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
            //	Lexer.wbag.escribir();
                return  Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
            }else if (isCommonName){
            	Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
            	return  Lexer.context.changeContext(Lexer.context.getContext(), this, " "," ").getContext();
            }else {
            	
            	if (Lexer.isTheFirst){
            		Output.write(new RoleTreeNode(word,Lexer.numCh,Lexer.numWord));
            		return  Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
                	
            	}else{
            	Lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
            	Lexer.wbag.get(Lexer.wbag.tam()-1).type=Terms.PPN;
            	return  Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
            	}
            }
        }
        
        return  Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
    	

      }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    public ContextualList wordListProcessing(String string){
    	try{
        //    System.out.println("ESTIY ENTRANDO POR LISTA inicial tratamiento "+string+Lexer.prepositionFlag+" "+Lexer.verbsFlag.verb);
             
                if (Lexer.previousContextStack.peek().getContext()==ContextualList.PLACE){
               BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext(),false);
         	  
         	  Lexer.wbag.put(bgd);
            	 }else if (Lexer.prepositionFlag.toLowerCase().equals("en") && !(Lexer.verbsFlag.complement.contains("en"))){
            		 BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext(),false);
                	  
                	  Lexer.wbag.put(bgd);
            	 }
            	 else{
                     
                //     System.out.println("el nombre de la lista encontrada ABDUL");
            		BagData bgd=new ProperNameBagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
               	  
               	  Lexer.wbag.put(bgd);
            	 }
            	 Lexer.lastToken="";
                return Lexer.context.getContext();
          
      }

       catch(Exception e){return ContextualList.INITIAL;}
    }
    
     public ContextualList nounPhraseProcessing(String string){
    	 try{
         //    System.out.println("noun frase treatment");
             String newString=string.replaceAll("\\s+", " ");
             String stringInProcess="";
          //  System.out.println("ha entrado ppor NOUNPORCES"+string+Lexer.context.getContext());
            String de=TermsRecognition.findApposition(newString);
          //  System.out.println("EL DE "+de);
            
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
               //System.out.println("veo el split"+stringArray[0]+stringArray[1]);
               
               String[] izda=stringArray[0].split(" ");
               String[] dcha=stringArray[1].split(" ");
              
               String recognized="";
               int encontrada=-1;
               
               for (int i=0; i<izda.length; i++){
            	 // System.out.println("llll"+izda[i]);
                   context=determineContext(izda[i],this);
                   //Lexer.wbag.clean();
                   recognized=izda[i]+" ";
             if (context!=ContextualList.SAME)
             if (context!=ContextualList.TREATMENT) {
                 //System.out.println("he encontradi "+i+" "+izda[i]);
               encontrada=i;
               break;
               }
              }
           //System.out.println("la palabra encontrada "+Lexer.context.getContext());
               if (encontrada!=-1){
                   if (izda.length==1) {
                       stringInProcess=newString.substring(stringArray[0].length()+1);
                       
                       Lexer.context.changeContext(context,Lexer.context,stringInProcess,izda[0]);
                       return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                   }
                   if (izda.length==2){
                       if (encontrada==1){
                           stringInProcess=newString.substring(stringArray[0].length()+1);
                           //System.out.println("vamos a mandar una substring "+stringInProcess);
                           //System.out.println("vamos a usar "+izda[0]+" "+izda[1]+" "+Lexer.context);
                           Lexer.context.checkCapitalLettersWord(izda[0]);
                           
                           Lexer.context.changeContext(context,Lexer.context,stringInProcess,izda[1]);
                           //System.out.println("la bag "+Lexer.wbag.tam()+Lexer.wbag.get(0).string);
                           return Lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                       }else {
                           stringInProcess=newString.substring(izda[0].length()+1);
                           
                           Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[0]);
                           //System.out.println("vamos a mandar una substring del final"+stringInProcess+Lexer.wbag.tam());
                          
                           return Lexer.context.nounPhraseProcessing(stringInProcess);
                       }
                   }
                   if (izda.length>2){
                       if (encontrada==izda.length-1){
                           //System.out.println("he entrado por la leng 3 ");
                           stringInProcess=newString.substring(stringArray[0].length());
                           String inicio=izda[0];
                           for (int j=1; j<izda.length-1; j++){
                               inicio+=" "+izda[j];
                           }
                         //  System.out.println("he entrado por la leng 4 "+inicio+" "+stringInProcess+" "+Lexer.context.getContext());
                           Lexer.context.wordListProcessing(inicio);
                         //  System.out.println ("El tamaño de la bolsa es "+Lexer.wbag.tam());
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
                           //System.out.println("he entrado por la leng 7"+inicio+"TTTT"+stringInProcess+" "+Lexer.context.getContext());
                           
                           //System.out.println ("El tamaño de la bolsa es "+Lexer.wbag.tam());
                           Lexer.context.changeContext(context, Lexer.context, stringInProcess,izda[izda.length-1]);
                           for (int k=0;k<Lexer.wbag.tam();k++){
                          // System.out.println ("la vida es "+Lexer.wbag.get(k).string);
                           }
                          // System.out.println("la verdad"+izda.length);
                           return Lexer.context.nounPhraseProcessing(stringInProcess);
                       
                           
                           
                       }
                   }
                   
               }
               
               
               //System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        
               //System.out.println("escribeme "+izda.length+" "+dcha.length);
              
              if (izda.length>1)
               Lexer.context.wordListProcessing(stringArray[0]);
              else{//System.out.println("CHECK CAPIRAL ");
                  Lexer.context.checkCapitalLettersWord(stringArray[0]);
              }
              // System.out.println("LA BOSLA DE "+Lexer.wbag.tam());
              // System.out.println("he entrado por de baga data general 2");
               Lexer.wbag.put(new DeBagData(de,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh,new InfoFound(),Lexer.context.getContext()));
               if (dcha.length>1)
               Lexer.context.wordListProcessing(stringArray[1]);
               else
               Lexer.context.checkCapitalLettersWord(stringArray[1]);
               
               //for (int i=0;i<Lexer.wbag.tam();i++)
               //System.out.println("LA BOSLA DE FINAL ES"+Lexer.wbag.get(i).string+" "+Lexer.wbag.get(i).type);
                    
            return ContextualList.INITIAL;         
            
           
            }catch(Exception e){return null;}
     }
    
     public ContextualList prepositionalSyntagmsListProcessing(String string){
       //  System.out.println("estoy ennes esteeeee"+string);
         
         
         return null;}
     
}
