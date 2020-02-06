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
    public TreatmentContext(Lexer lexer){
        super(lexer);
    }

    public TreatmentContext(SemanticContext previous, Lexer lexer){
        super(previous, lexer);
    }
   
    public ContextualList getContext(){
        return ContextualList.TREATMENT;
    }
    
    public SemanticContext checkLowerCaseWord(String word){
      try{
    	 //System.out.println("LA BOLSA ANTES DE NETRAR EN MINUSCULAS TREATMENT"+this.lexer.wbag.tam()+word+" "+this.lexer.numCh+" "+this.lexer.numWord);
          BagData ultimo=new BagData();
           
        // this.lexer.wbag.escribir();
      //System.out.println("edtoy leyendo esta palabra "+word+"   "+ this.lexer.context.getContext()+ this.lexer.numCh+this.lexer.numWord);
          
        ContextualList context=determineContext(word,this);
       
       // System.out.println("la familia "+context);
        Verbs elVerb;
        if (context!=ContextualList.SAME)
        if (context!=ContextualList.TREATMENT){
        	if (context==ContextualList.FAMILY){
                  //  System.out.println("estanos entrando por la opcion de cambio ");
             //       this.lexer.wbag.escribir();
        		this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
        		this.lexer.wbag.get(this.lexer.wbag.tam()-1).type=Terms.NPD;
        	//	System.out.println("el tipo de la ultima family"+this.lexer.wbag.get(this.lexer.wbag.tam()-1).type);
        	// this.lexer.wbag.escribir();
                 this.lexer.setLastToken("");
                }else{
        		//System.out.println("he entrado por este contexto");
                this.lexer.context.changeContext(context, this.lexer.context," ", word);
             //   this.lexer.wbag.escribir();
        	}
        	
            //System.out.println("hes salido despues del ambio");
            return this.lexer.context;
        }else return this.lexer.context;
      //  System.out.println("HE COMPROBADO" +word);
        if (Data.FamilyNamesTable.contains(word)){
        	this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
    		this.lexer.wbag.get(this.lexer.wbag.tam()-1).type=Terms.NPD;
    		//System.out.println("el tipo de la ultima family"+this.lexer.wbag.get(this.lexer.wbag.tam()-1).type);
    		return this.lexer.context;
        }
        if (checkVerb(word)){  this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
            return this.lexer.context;}
        
        if (check(word)){ 
            this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
        return this.lexer.context;}
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
                
              //  System.out.println("PINTAME EL ULTIMO "+this.lexer.wbag.get(this.lexer.wbag.tam()-1).string);
            }else{*/
            this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
          
           // }
        } 
        else {
      //  System.out.println("Hemos en contrado las isguientes propuestas "+res+resultado);
        switch (res){
            case 0: {
                
                InfoFound infoAprox=NewTermsIdentification.getAproximation(word);
                
                if (infoAprox!=null){
                   // System.out.println("estoy entrando por el 0");
                    if (infoAprox.uri=="proper") this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else if (infoAprox.uri=="nick") this.lexer.wbag.put(new NickNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                else {
                    if (this.lexer.articleFlag!=""){this.lexer.wbag.restart();this.lexer.articleFlag="";}
                	Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                        this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, "", "");
                	//Output.write(word);
                }
                } else {
                    if (this.lexer.articleFlag!=""){this.lexer.wbag.restart();this.lexer.articleFlag="";}
                    this.lexer.context.changeContext(ContextualList.INITIAL, this.lexer.context," ", word);
                    this.lexer.wbag.restart();
                	Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                   // Output.write(word);
                }
               
            }/* buscar una variante */;
            case 1: {
               // System.out.println("El caracter = es "+resultado.charAt(2));
                if (resultado.charAt(0)=='1') this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),false));
                if (resultado.charAt(1)=='1') this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                    Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                   // Output.write(word);
                }
            break;
            }
            case 2:{
                if (resultado.charAt(0)=='1' && resultado.charAt(1)=='1') {
                    if (info.gazetteer.contains("Geonames"))  this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
                }
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                	
                    	Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
                      //  Output.write(word);
                     }
              //this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));  
            break;
            }
            case 3:{
                
                    if (info.gazetteer.contains("Geonames"))  this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                    else this.lexer.wbag.put(new PlaceNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),info,this.lexer.context.getContext(),true));
            break;
            }
        }
        }
        
       
     
           return this.lexer.context;
      }catch(Exception e){return this.lexer.context;}
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
              this.lexer.context.changeContext(context, this.lexer.context," ", word);
             // System.out.println("hes salido despues del ambio");
              return this.lexer.context.getContext();
          }else return this.lexer.context.getContext();
          //System.out.println("HE COMPROBADO" +word);
           if (checkVerb(word)){ 
        	  this.lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
        	  return this.lexer.context.getContext();}
         // System.out.println("entro por capital letters "+word);
        if (this.lexer.peekPreviousContext() instanceof ContextProcessing.PlaceContext){
        }
        else if (check(word)){ 
        	  this.lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
        	  return this.lexer.context.getContext();}
         // System.out.println("entro por capital letters "+word);
        if (this.lexer.peekPreviousContext() instanceof ContextProcessing.PlaceContext){
        }else{
        	InfoFound info=TermsRecognition.existMedievalPlaceName(word);
            
            boolean isProperName=TermsRecognition.isProperName(word.toLowerCase());
            boolean isCommonName=TermsRecognition.isCommonName(word.toLowerCase());
            if (isProperName){
            	this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
            //	this.lexer.wbag.escribir();
                return  this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
            }else if (isCommonName){
            	Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
            	return  this.lexer.context.changeContext(this.lexer.context.getContext(), this, " "," ").getContext();
            }else {
            	
            	if (this.lexer.isTheFirst()){
            		Output.write(new RoleTreeNode(word,this.lexer.numCh,this.lexer.numWord));
            		return  this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
                	
            	}else{
            	this.lexer.wbag.put(new ProperNameBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
            	this.lexer.wbag.get(this.lexer.wbag.tam()-1).type=Terms.PPN;
            	return  this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
            	}
            }
        }
        
        return  this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
    	

      }catch(Exception e){return ContextualList.INITIAL;}
    }
    
    public ContextualList wordListProcessing(String string){
    	try{
        //    System.out.println("ESTIY ENTRANDO POR LISTA inicial tratamiento "+string+this.lexer.prepositionFlag+" "+this.lexer.verbsFlag.verb);
             
                if (this.lexer.peekPreviousContext().getContext()==ContextualList.PLACE){
               BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext(),false);
         	  
         	  this.lexer.wbag.put(bgd);
            	 }else if (this.lexer.prepositionFlag.toLowerCase().equals("en") && !(this.lexer.verbsFlag.complement.contains("en"))){
            		 BagData bgd=new PlaceNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext(),false);
                	  
                	  this.lexer.wbag.put(bgd);
            	 }
            	 else{
                     
                //     System.out.println("el nombre de la lista encontrada ABDUL");
            		BagData bgd=new ProperNameBagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
               	  
               	  this.lexer.wbag.put(bgd);
            	 }
            	 this.lexer.setLastToken("");
                return this.lexer.context.getContext();
          
      }

       catch(Exception e){return ContextualList.INITIAL;}
    }
    
     public ContextualList nounPhraseProcessing(String string){
    	 try{
         //    System.out.println("noun frase treatment");
             String newString=string.replaceAll("\\s+", " ");
             String stringInProcess="";
          //  System.out.println("ha entrado ppor NOUNPORCES"+string+this.lexer.context.getContext());
            String de=TermsRecognition.findApposition(newString);
          //  System.out.println("EL DE "+de);
            
            //tokenize the stirng
            this.lexer.currentString=new TokenizedString(newString);
            String firstWord=this.lexer.currentString.tokenList.get(0).word;
           
           for (int i=0; i<this.lexer.currentString.tokenList.size();i++){
             //  System.out.println("las palabaras de token "+this.lexer.currentString.tokenList.get(i).word);
           }
            //if (check(firstWord)){ return this.lexer.context.getContext();}
          
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
           // System.out.println("la bag "+this.lexer.wbag.tam());
           // System.out.println("la bolsa ");this.lexer.wbag.escribir();
                          
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
                   //this.lexer.wbag.clean();
                   recognized=izda[i]+" ";
             if (context!=ContextualList.SAME)
             if (context!=ContextualList.TREATMENT) {
                 //System.out.println("he encontradi "+i+" "+izda[i]);
               encontrada=i;
               break;
               }
              }
           //System.out.println("la palabra encontrada "+this.lexer.context.getContext());
               if (encontrada!=-1){
                   if (izda.length==1) {
                       stringInProcess=newString.substring(stringArray[0].length()+1);
                       
                       this.lexer.context.changeContext(context,this.lexer.context,stringInProcess,izda[0]);
                       return this.lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                   }
                   if (izda.length==2){
                       if (encontrada==1){
                           stringInProcess=newString.substring(stringArray[0].length()+1);
                           //System.out.println("vamos a mandar una substring "+stringInProcess);
                           //System.out.println("vamos a usar "+izda[0]+" "+izda[1]+" "+this.lexer.context);
                           this.lexer.context.checkCapitalLettersWord(izda[0]);
                           
                           this.lexer.context.changeContext(context,this.lexer.context,stringInProcess,izda[1]);
                           //System.out.println("la bag "+this.lexer.wbag.tam()+this.lexer.wbag.get(0).string);
                           return this.lexer.context.prepositionalSyntagmsListProcessing(stringInProcess);
                       }else {
                           stringInProcess=newString.substring(izda[0].length()+1);
                           
                           this.lexer.context.changeContext(context, this.lexer.context, stringInProcess,izda[0]);
                           //System.out.println("vamos a mandar una substring del final"+stringInProcess+this.lexer.wbag.tam());
                          
                           return this.lexer.context.nounPhraseProcessing(stringInProcess);
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
                         //  System.out.println("he entrado por la leng 4 "+inicio+" "+stringInProcess+" "+this.lexer.context.getContext());
                           this.lexer.context.wordListProcessing(inicio);
                         //  System.out.println ("El tamaño de la bolsa es "+this.lexer.wbag.tam());
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
                           //System.out.println("he entrado por la leng 7"+inicio+"TTTT"+stringInProcess+" "+this.lexer.context.getContext());
                           
                           //System.out.println ("El tamaño de la bolsa es "+this.lexer.wbag.tam());
                           this.lexer.context.changeContext(context, this.lexer.context, stringInProcess,izda[izda.length-1]);
                           for (int k=0;k<this.lexer.wbag.tam();k++){
                          // System.out.println ("la vida es "+this.lexer.wbag.get(k).string);
                           }
                          // System.out.println("la verdad"+izda.length);
                           return this.lexer.context.nounPhraseProcessing(stringInProcess);
                       
                           
                           
                       }
                   }
                   
               }
               
               
               //System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        
               //System.out.println("escribeme "+izda.length+" "+dcha.length);
              
              if (izda.length>1)
               this.lexer.context.wordListProcessing(stringArray[0]);
              else{//System.out.println("CHECK CAPIRAL ");
                  this.lexer.context.checkCapitalLettersWord(stringArray[0]);
              }
              // System.out.println("LA BOSLA DE "+this.lexer.wbag.tam());
              // System.out.println("he entrado por de baga data general 2");
               this.lexer.wbag.put(new DeBagData(de,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh,new InfoFound(),this.lexer.context.getContext()));
               if (dcha.length>1)
               this.lexer.context.wordListProcessing(stringArray[1]);
               else
               this.lexer.context.checkCapitalLettersWord(stringArray[1]);
               
               //for (int i=0;i<this.lexer.wbag.tam();i++)
               //System.out.println("LA BOSLA DE FINAL ES"+this.lexer.wbag.get(i).string+" "+this.lexer.wbag.get(i).type);
                    
            return ContextualList.INITIAL;         
            
           
            }catch(Exception e){return null;}
     }
    
     public ContextualList prepositionalSyntagmsListProcessing(String string){
       //  System.out.println("estoy ennes esteeeee"+string);
         
         
         return null;}
     
}
