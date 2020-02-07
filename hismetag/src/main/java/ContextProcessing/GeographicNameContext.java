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
import StringInProcess.Token;
import StringInProcess.TokenizedString;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;

/**
 *
 * @author M Luisa Díez Platas
 */
public class GeographicNameContext extends SemanticContext {
    
    public GeographicNameContext(SemanticContext previous, Lexer lexer, Output output){
        super(previous, lexer, output);
    }
   
    public ContextualList getContext(){
        return ContextualList.GEOGRAPHIC;
    }
    
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
           // System.out.println("entro por minusculas sierra "+word+" "+this.lexer.getLastToken());
            /*if (this.lexer.getLastToken()!="")
            this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
            */
          //  this.lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word)){
          	 // System.out.println("he entrado por el determinante");
          	  if (this.lexer.getLastToken()!="")
          	  this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
          	 // System.out.println(this.lexer.wbag.get(0).type);
          	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
               
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
            
            if (this.lexer.getLastToken()!="")
          	  this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
          	
            if (checkSpecialContext(word)){ 
          	// System.out.println("he entrado por check");
          	  return this.lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
            }
             
            BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
      	  bgd.type=Terms.PLN;
      	  //System.out.println("HE ENTRADO POR LA SALIDA "+word);
      	  this.lexer.wbag.put(bgd);
      	  this.lexer.setLastToken("");
             return this.lexer.context;
        }catch(Exception e){return this.lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
            
      	 // System.out.println("entro por mayusculas buillding "+word+" "+this.lexer.getLastToken());
          //  this.lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word.toLowerCase())){
          	//  System.out.println("he entrado por el determinante");
          	  if (this.lexer.getLastToken()!="")
          	  this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
          	 // System.out.println(this.lexer.wbag.get(0).type);
          	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                this.lexer.setLastToken(word);
                return this.lexer.context.getContext();
            }
            
            if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
          	 // System.out.println("he entrado por el determinante2"+word);
          	  this.lexer.setLastToken("");
          	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
          	  bgd.type=Terms.AFPLN;
          	  this.lexer.wbag.put(bgd);
          	  return this.lexer.context.getContext();
            }
            
            this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
      	  
            BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
      	  bgd.type=Terms.AFPLN;
      	  this.lexer.wbag.put(bgd);
      	  this.lexer.setLastToken("");
             return this.lexer.context.getContext();
        }catch(Exception e){return this.getContext();}
      
      }
      
      public ContextualList wordListProcessing(String string){
         try{
             
      	  // System.out.println("la lista de geog"+string);
      	   if (this.lexer.getLastToken()!="")
        	 this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
       	  
             BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
       	  bgd.type=Terms.AFPLN;
       	  this.lexer.wbag.put(bgd);
               return this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
             
         }catch(Exception e){return ContextualList.INITIAL;}
      }
      
      public ContextualList nounPhraseProcessing(String string){
           try{
          	// System.out.println("la lista de geog"+string);
          	 if (this.lexer.getLastToken()!="")
          	 this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
         	  
               BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
         	  bgd.type=Terms.AFPLN;
         	  this.lexer.wbag.put(bgd);
                 return this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
               
               
           }catch(Exception e){return ContextualList.INITIAL;}
           
      }
      
      
      
       
           public ContextualList prepositionalSyntagmsListProcessing(String string){
            try{
          	// System.out.println("entro por prepositionalgeographic "+string+" "+this.lexer.getLastToken());
               // this.lexer.wbag.escribir();
                
         
               BagData ultimo=new BagData();
        	//System.out.println("estoy entramdopor family sproooeoosuny"+string);
        if (this.lexer.getLastToken()!=""){
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
            	this.lexer.setLastToken("");
              }
            else{
            this.lexer.wbag.put(new GeographicBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
            	this.lexer.setLastToken("");
              }  
        }
        }
        
      //  System.out.println("esto es lo ultimo que he leido antes de la aposicion");
       //      this.lexer.wbag.escribir();
           
        	 
         
         String newString=string.replaceAll("\\s+", " ");
       
         //tokenize the stirng
         this.lexer.currentString=new TokenizedString(newString, this.lexer);
         
         for (int i=0; i<this.lexer.currentString.tokenList.size();i++){
             Token token=this.lexer.currentString.tokenList.get(i);
             if (Recognition.ElementsRecognition.isDeterminantPrep(token.word)){
              //   System.out.println("he entrado por de baga datadeterminante "+token.word);
                 token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext());
                 
        //         this.lexer.wbag.escribir();
             }else if (Recognition.ElementsRecognition.isCopulativeConjunction(token.word).size()>0){
               token.bdata=new CopulativeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext());
              
             }
         }
     
         
         newString=this.lexer.currentString.processingArticles(newString);
         
        
         newString=this.lexer.currentString.processingAppositionsPrepositions(newString);
                
         

         String[] newStrings=this.lexer.currentString.processingCopulativeConjunctions(newString);
         String firstWord=this.lexer.currentString.tokenList.get(0).word;
          
        // System.out.println ("la cadena nueva es "+newString+newStrings.length+firstWord);
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
         
      //   System.out.println("estanos en este punto que no entiendo nada ");
      //   this.lexer.wbag.escribir();
      //   System.out.println("estamos teniendo otras cosas de ");
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
             
             if (elemento.bdata==null){
           //  System.out.println("muestrame el termino "+aux);
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
      // System.out.println("Hemos en contrado las isguientes en preposicion propuestas "+res+" "+resultado+" "+aux);
        switch (res){
            case 0: {
             
              ContextualList context=determineContext(aux,this);
             
              if (context!=ContextualList.SAME) break;   
          //     System.out.println("HE SALIDO POR AQUI CUANDO no he reconocido "+aux+" "+context);
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
                 //    System.out.println("HE SALIDO POR AquI ELSE");
                     BagData bgd=new PlaceNameBagData(aux,TypesTerms.PPT,elemento.position,elemento.nWord,elemento.position+aux.length(),new InfoFound(),this.lexer.context.getContext(),false);
                     elemento.bdata=bgd;
                    // Data.MedievalPlaceNamesTable.putNewPlace(aux.toUpperCase(), "", "", "", "", "", Input.name, "Hismetag", "PL");
                 //   System.out.println("HE SALIDO POR AquI ELSE");
                   
                 }
             //   System.out.println("HE SALIDO POR AquI ELSE FIN");
                
                break;
               
            }
                   
            case 1: {
              //  System.out.println("El caracter = es "+resultado.charAt(1));
                if (resultado.charAt(0)=='1') {
                   // System.out.println("ENTRO POR EL PLACE 1");
                    BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),false);
                    this.lexer.numCh=this.lexer.numCh+aux.length();
                    //this.lexer.numWord++;
                    this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                }
           
                if (resultado.charAt(1)=='1'){
                   // System.out.println("ENTRO POR EL PROPER 1");
                    BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), new InfoFound(),this.lexer.context.getContext());
                    this.lexer.numCh=this.lexer.numCh+aux.length();
                    //this.lexer.numWord++;
                    this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                }
           
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                   // System.out.println("ENTRO POR EL ELSE 1");
                    this.output.write(aux);
                    this.output.write(new RoleTreeNode(aux));
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
               // System.out.println("LOS RESULTADOS DE LAS COSAS otra "+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                  
                    if (info.gazetteer.contains("Geonames"))  {
                        this.output.write(aux);
                        this.output.write(new RoleTreeNode(aux));
                        this.lexer.numCh=this.lexer.numCh+aux.length();
                        //this.lexer.numWord++;
                    }
                    else {
                   //     System.out.println("LOS RESULTADOS 4 "+aux);
                       BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+aux.length(), info,this.lexer.context.getContext(),true);
                   //    System.out.println("LOS RESULTADOS 4 "+aux);
                       this.lexer.numCh=this.lexer.numCh+aux.length();
                        
                       //this.lexer.numWord++;
                        this.lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       
                    }
           
                }
               // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
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
         }
       
        
     //   this.lexer.wbag.escribir();
         
        
         //this.lexer.currentString.updateTokenList();
       
        this.lexer.currentString.sendOutInfo();
    //System.out.println ("HE SALIDO DE LA VUELTA"+this.lexer.wbag.tam());
   // this.lexer.wbag.escribir();
        
         this.lexer.currentString.tokenList.clear();
         this.lexer.currentString.ngramms.ngramms.clear();
      //   System.out.println ("HE SALIDO DE LA VUELTA"+this.lexer.wbag.tam());
  //  this.lexer.wbag.escribir();
         return this.lexer.context.getContext();
      
      }catch(Exception e){return this.lexer.context.getContext();}
       
               
               
              }

      
  }