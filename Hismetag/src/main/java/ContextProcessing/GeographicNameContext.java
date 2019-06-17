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
    
    public GeographicNameContext(SemanticContext previous){super(previous);}
   
    public ContextualList getContext(){
        return ContextualList.GEOGRAPHIC;
    }
    
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
           // System.out.println("entro por minusculas sierra "+word+" "+Lexer.lastToken);
            /*if (Lexer.lastToken!="")
            Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
            */
          //  Lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word)){
          	 // System.out.println("he entrado por el determinante");
          	  if (Lexer.lastToken!="")
          	  Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
          	 // System.out.println(Lexer.wbag.get(0).type);
          	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
               
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
            
            if (Lexer.lastToken!="")
          	  Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
          	
            if (checkSpecialContext(word)){ 
          	// System.out.println("he entrado por check");
          	  return Lexer.context.changeContext(ContextualList.INITIAL, this, " "," ");
            }
             
            BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
      	  bgd.type=Terms.PLN;
      	  //System.out.println("HE ENTRADO POR LA SALIDA "+word);
      	  Lexer.wbag.put(bgd);
      	  Lexer.lastToken="";
             return Lexer.context;
        }catch(Exception e){return Lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
            
      	 // System.out.println("entro por mayusculas buillding "+word+" "+Lexer.lastToken);
          //  Lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word.toLowerCase())){
          	//  System.out.println("he entrado por el determinante");
          	  if (Lexer.lastToken!="")
          	  Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
          	 // System.out.println(Lexer.wbag.get(0).type);
          	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                Lexer.lastToken=word;
                return Lexer.context.getContext();
            }
            
            if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
          	 // System.out.println("he entrado por el determinante2"+word);
          	  Lexer.lastToken="";
          	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
          	  bgd.type=Terms.AFPLN;
          	  Lexer.wbag.put(bgd);
          	  return Lexer.context.getContext();
            }
            
            Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
      	  
            BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
      	  bgd.type=Terms.AFPLN;
      	  Lexer.wbag.put(bgd);
      	  Lexer.lastToken="";
             return Lexer.context.getContext();
        }catch(Exception e){return this.getContext();}
      
      }
      
      public ContextualList wordListProcessing(String string){
         try{
             
      	  // System.out.println("la lista de geog"+string);
      	   if (Lexer.lastToken!="")
        	 Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
       	  
             BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
       	  bgd.type=Terms.AFPLN;
       	  Lexer.wbag.put(bgd);
               return Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
             
         }catch(Exception e){return ContextualList.INITIAL;}
      }
      
      public ContextualList nounPhraseProcessing(String string){
           try{
          	// System.out.println("la lista de geog"+string);
          	 if (Lexer.lastToken!="")
          	 Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
         	  
               BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
         	  bgd.type=Terms.AFPLN;
         	  Lexer.wbag.put(bgd);
                 return Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
               
               
           }catch(Exception e){return ContextualList.INITIAL;}
           
      }
      
      
      
       
           public ContextualList prepositionalSyntagmsListProcessing(String string){
            try{
          	// System.out.println("entro por prepositionalgeographic "+string+" "+Lexer.lastToken);
               // Lexer.wbag.escribir();
                
         
               BagData ultimo=new BagData();
        	//System.out.println("estoy entramdopor family sproooeoosuny"+string);
        if (Lexer.lastToken!=""){
            if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  Lexer.wbag.wbag.remove(Lexer.wbag.wbag.size()-1);
          
            Lexer.wbag.put(new GeographicBagData(Lexer.lastToken+ultimo.string,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length()-1,Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
            	Lexer.lastToken="";
              }
            else{
            Lexer.wbag.put(new GeographicBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
            	Lexer.lastToken="";
              }  
        }
        }
        
      //  System.out.println("esto es lo ultimo que he leido antes de la aposicion");
       //      Lexer.wbag.escribir();
           
        	 
         
         String newString=string.replaceAll("\\s+", " ");
       
         //tokenize the stirng
         Lexer.currentString=new TokenizedString(newString);
         
         for (int i=0; i<Lexer.currentString.tokenList.size();i++){
             Token token=Lexer.currentString.tokenList.get(i);
             if (Recognition.ElementsRecognition.isDeterminantPrep(token.word)){
              //   System.out.println("he entrado por de baga datadeterminante "+token.word);
                 token.bdata=new DeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
                 
        //         Lexer.wbag.escribir();
             }else if (Recognition.ElementsRecognition.isCopulativeConjunction(token.word).size()>0){
               token.bdata=new CopulativeBagData(token.word,TypesTerms.FT,token.position,token.nWord,token.position+token.word.length(),token.info,Lexer.context.getContext());
              
             }
         }
     
         
         newString=Lexer.currentString.processingArticles(newString);
         
        
         newString=Lexer.currentString.processingAppositionsPrepositions(newString);
                
         

         String[] newStrings=Lexer.currentString.processingCopulativeConjunctions(newString);
         String firstWord=Lexer.currentString.tokenList.get(0).word;
          
        // System.out.println ("la cadena nueva es "+newString+newStrings.length+firstWord);
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
      // System.out.println("Hemos en contrado las isguientes en preposicion propuestas "+res+" "+resultado+" "+aux);
        switch (res){
            case 0: {
             
              ContextualList context=determineContext(aux,this);
             
              if (context!=ContextualList.SAME) break;   
          //     System.out.println("HE SALIDO POR AQUI CUANDO no he reconocido "+aux+" "+context);
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
                 //    System.out.println("HE SALIDO POR AquI ELSE");
                     BagData bgd=new PlaceNameBagData(aux,TypesTerms.PPT,elemento.position,elemento.nWord,elemento.position+aux.length(),new InfoFound(),Lexer.context.getContext(),false);
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
                    BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),false);
                    Lexer.numCh=Lexer.numCh+aux.length();
                    //Lexer.numWord++;
                    Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                }
           
                if (resultado.charAt(1)=='1'){
                   // System.out.println("ENTRO POR EL PROPER 1");
                    BagData bgd=new ProperNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), new InfoFound(),Lexer.context.getContext());
                    Lexer.numCh=Lexer.numCh+aux.length();
                    //Lexer.numWord++;
                    Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                }
           
                if (resultado.charAt(2)=='1') {
                   // System.out.println("he entrado por aqui en el case 1 "+word);
                   // System.out.println("ENTRO POR EL ELSE 1");
                    Output.write(aux);
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
               // System.out.println("LOS RESULTADOS DE LAS COSAS otra "+resultado);
                if (resultado.charAt(0)=='1' && resultado.charAt(2)=='1') {
                  
                    if (info.gazetteer.contains("Geonames"))  {
                        Output.write(aux);
                        Output.write(new RoleTreeNode(aux));
                        Lexer.numCh=Lexer.numCh+aux.length();
                        //Lexer.numWord++;
                    }
                    else {
                   //     System.out.println("LOS RESULTADOS 4 "+aux);
                       BagData bgd=new PlaceNameBagData(aux,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+aux.length(), info,Lexer.context.getContext(),true);
                   //    System.out.println("LOS RESULTADOS 4 "+aux);
                       Lexer.numCh=Lexer.numCh+aux.length();
                        
                       //Lexer.numWord++;
                        Lexer.currentString.ngramms.ngramms.set(i, new NgrammsInfo(aux,VerificationInfo.FOUND,false,bgd));
                       
                    }
           
                }
               // System.out.println("LOS RESULTADOS 3 "+NI.ngramms);
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
         }
       
        
     //   Lexer.wbag.escribir();
         
        
         //Lexer.currentString.updateTokenList();
       
        Lexer.currentString.sendOutInfo();
    //System.out.println ("HE SALIDO DE LA VUELTA"+Lexer.wbag.tam());
   // Lexer.wbag.escribir();
        
         Lexer.currentString.tokenList.clear();
         Lexer.currentString.ngramms.ngramms.clear();
      //   System.out.println ("HE SALIDO DE LA VUELTA"+Lexer.wbag.tam());
  //  Lexer.wbag.escribir();
         return Lexer.context.getContext();
      
      }catch(Exception e){return Lexer.context.getContext();}
       
               
               
              }

      
  }