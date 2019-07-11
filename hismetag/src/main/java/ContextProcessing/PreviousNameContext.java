/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContextProcessing;

import DataStructures.BagData;
import DataStructures.OrgBagData;
import DataStructures.OrgBagData;
import DataStructures.DeBagData;
import DataStructures.FamilyBagData;
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
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;


/**
 *
 * @author M Luisa Díez Platas
 */
public class PreviousNameContext extends SemanticContext {
    public PreviousNameContext(){
    		
    }
    public PreviousNameContext(SemanticContext previous){super(previous);}
  
    
    public ContextualList getContext(){
        return ContextualList.PREVIOUS;
    }
    
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
           // System.out.println("entro por minusculas orden "+word+" "+Lexer.lastToken);
            //Lexer.wbag.escribir();
            
            if (ElementsRecognition.isDeterminantPrep(word)){
          	  //System.out.println("he entrado por el determinante");
                BagData ultimo=new BagData();
        	//System.out.println("estoy entramdopor family minusculas"+word);
        if (Lexer.lastToken!=""){
         
            if (Lexer.wbag.tam()>0){
              ultimo=Lexer.wbag.get(Lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  Lexer.wbag.wbag.remove(Lexer.wbag.wbag.size()-1);
          
            Lexer.wbag.put(new OrgBagData(Lexer.lastToken+ultimo.string,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length()-1,Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
            	Lexer.lastToken="";
              }
        else{
            Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
            	Lexer.lastToken="";
            }
        }else {
              Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
            	Lexer.lastToken="";  
            }
          	  // System.out.println(Lexer.wbag.get(0).type);
          	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                Lexer.lastToken=word;
                return Lexer.context;
            }
            }
            
            if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
          	 // System.out.println("he entrado por el determinante2");
          	  Lexer.lastToken="";
          	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
          	  bgd.type=Terms.AON;
          	  Lexer.wbag.put(bgd);
          	  return Lexer.context;
            }
            
            if (Lexer.lastToken!=""){ Output.write(new DataStructures.RoleTreeNode(Lexer.lastToken));
            
            Lexer.lastToken="";
            }
            if (checkSpecialContext(word)){ 
          	 Lexer.previousContextStack.clear();
          	  return Lexer.context.changeContext(ContextualList.INITIAL, null, " "," ");
            }
            
            Output.write(new DataStructures.RoleTreeNode(word));
             return Lexer.context.changeContext(ContextualList.INITIAL, null, " "," ");
        }catch(Exception e){return Lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
            
      	//  System.out.println("entro por mayusculas buillding "+word+" "+Lexer.lastToken);
           // Lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word.toLowerCase())){
          	//  System.out.println("he entrado por el determinante");
          	  if (Lexer.lastToken!="")
          	  Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
          	 // System.out.println(Lexer.wbag.get(0).type);
          	  Lexer.wbag.put(new DeBagData(word,TypesTerms.FT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext()));
                Lexer.lastToken=word;
                return Lexer.context.getContext();
            }
            
            if (ElementsRecognition.isDeterminantPrep(Lexer.lastToken)){
          	 // System.out.println("he entrado por el determinante2"+word);
          	  Lexer.lastToken="";
          	  BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
          	  bgd.type=Terms.AON;
          	  Lexer.wbag.put(bgd);
          	  return Lexer.context.getContext();
            }
            
            Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
      	  
            BagData bgd=new BagData(word,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+word.length(),new InfoFound(),Lexer.context.getContext());
      	  bgd.type=Terms.AON;
      	  Lexer.wbag.put(bgd);
      	  Lexer.lastToken="";
             return Lexer.context.getContext();
        }catch(Exception e){return this.getContext();}
      
      }
      
      public ContextualList wordListProcessing(String string){
         try{
             
      	  // System.out.println("la lista de geog"+string);
      	   if (Lexer.lastToken!="")
        	 Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
       	  
             BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
       	  bgd.type=Terms.AON;
       	  Lexer.wbag.put(bgd);
               return Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
             
         }catch(Exception e){return ContextualList.INITIAL;}
      }
      
      public ContextualList nounPhraseProcessing(String string){
           try{
          	// System.out.println("la noun de geog"+string);
          	 if (Lexer.lastToken!="")
          	 Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
         	  
               BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
         	  bgd.type=Terms.AON;
         	  Lexer.wbag.put(bgd);
                 return Lexer.context.changeContext(Lexer.previousContextStack.pop().getContext(), this, " "," ").getContext();
               
               
           }catch(Exception e){return ContextualList.INITIAL;}
           
      }
      
      
      
       
           public ContextualList prepositionalSyntagmsListProcessing(String string){
            try{
          	//  System.out.println("entro por prepositional buillding "+string+" "+Lexer.lastToken);
           //     Lexer.wbag.escribir();
                
                if (Lexer.lastToken!="")
              	  
                Lexer.wbag.put(new OrgBagData(Lexer.lastToken,TypesTerms.FT,Lexer.numCh-Lexer.lastToken.length(),Lexer.numWord-1,Lexer.numCh-1,new InfoFound(),Lexer.context.getContext(),false));
          	  
                
                BagData bgd=new BagData(string,TypesTerms.PPT,Lexer.numCh,Lexer.numWord,Lexer.numCh+string.length(),new InfoFound(),Lexer.context.getContext());
          	  bgd.type=Terms.AON;
          	  Lexer.wbag.put(bgd);
          	  Lexer.lastToken="";
                return Lexer.context.getContext();
      
      }catch(Exception e){return Lexer.context.getContext();}
       
               
               
              }

      
  }
