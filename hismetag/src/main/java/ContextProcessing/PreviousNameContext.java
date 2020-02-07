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
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import StringNgramms.NgrammsInfo;
import WordProcessing.WordTransformations;


/**
 *
 * @author M Luisa Díez Platas
 */
public class PreviousNameContext extends SemanticContext {
    public PreviousNameContext(Lexer lexer, Output output){
        super(lexer, output);
    }

    public PreviousNameContext(SemanticContext previous, Lexer lexer, Output output){
        super(previous, lexer, output);
    }
  
    
    public ContextualList getContext(){
        return ContextualList.PREVIOUS;
    }
    
    
    public SemanticContext checkLowerCaseWord(String word){
        try{
           // System.out.println("entro por minusculas orden "+word+" "+this.lexer.getLastToken());
            //this.lexer.wbag.escribir();
            
            if (ElementsRecognition.isDeterminantPrep(word)){
          	  //System.out.println("he entrado por el determinante");
                BagData ultimo=new BagData();
        	//System.out.println("estoy entramdopor family minusculas"+word);
        if (this.lexer.getLastToken()!=""){
         
            if (this.lexer.wbag.tam()>0){
              ultimo=this.lexer.wbag.get(this.lexer.wbag.tam()-1);
              if (ultimo.type==Terms.SALTO){
                  this.lexer.wbag.wbag.remove(this.lexer.wbag.wbag.size()-1);
          
            this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken()+ultimo.string,TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length()-1,this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
            	this.lexer.setLastToken("");
              }
        else{
            this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
            	this.lexer.setLastToken("");
            }
        }else {
              this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
            	this.lexer.setLastToken("");  
            }
          	  // System.out.println(this.lexer.wbag.get(0).type);
          	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                this.lexer.setLastToken(word);
                return this.lexer.context;
            }
            }
            
            if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
          	 // System.out.println("he entrado por el determinante2");
          	  this.lexer.setLastToken("");
          	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
          	  bgd.type=Terms.AON;
          	  this.lexer.wbag.put(bgd);
          	  return this.lexer.context;
            }
            
            if (this.lexer.getLastToken()!=""){ this.output.write(new DataStructures.RoleTreeNode(this.lexer.getLastToken()));
            
            this.lexer.setLastToken("");
            }
            if (checkSpecialContext(word)){ 
          	 this.lexer.clearPreviousContext();
          	  return this.lexer.context.changeContext(ContextualList.INITIAL, null, " "," ");
            }
            
            this.output.write(new DataStructures.RoleTreeNode(word));
             return this.lexer.context.changeContext(ContextualList.INITIAL, null, " "," ");
        }catch(Exception e){return this.lexer.context;}
      }
      public ContextualList checkCapitalLettersWord(String word){
        try{
            
      	//  System.out.println("entro por mayusculas buillding "+word+" "+this.lexer.getLastToken());
           // this.lexer.wbag.escribir();
            if (ElementsRecognition.isDeterminantPrep(word.toLowerCase())){
          	//  System.out.println("he entrado por el determinante");
          	  if (this.lexer.getLastToken()!="")
          	  this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
          	 // System.out.println(this.lexer.wbag.get(0).type);
          	  this.lexer.wbag.put(new DeBagData(word,TypesTerms.FT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext()));
                this.lexer.setLastToken(word);
                return this.lexer.context.getContext();
            }
            
            if (ElementsRecognition.isDeterminantPrep(this.lexer.getLastToken())){
          	 // System.out.println("he entrado por el determinante2"+word);
          	  this.lexer.setLastToken("");
          	  BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
          	  bgd.type=Terms.AON;
          	  this.lexer.wbag.put(bgd);
          	  return this.lexer.context.getContext();
            }
            
            this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
      	  
            BagData bgd=new BagData(word,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+word.length(),new InfoFound(),this.lexer.context.getContext());
      	  bgd.type=Terms.AON;
      	  this.lexer.wbag.put(bgd);
      	  this.lexer.setLastToken("");
             return this.lexer.context.getContext();
        }catch(Exception e){return this.getContext();}
      
      }
      
      public ContextualList wordListProcessing(String string){
         try{
             
      	  // System.out.println("la lista de geog"+string);
      	   if (this.lexer.getLastToken()!="")
        	 this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
       	  
             BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
       	  bgd.type=Terms.AON;
       	  this.lexer.wbag.put(bgd);
               return this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
             
         }catch(Exception e){return ContextualList.INITIAL;}
      }
      
      public ContextualList nounPhraseProcessing(String string){
           try{
          	// System.out.println("la noun de geog"+string);
          	 if (this.lexer.getLastToken()!="")
          	 this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
         	  
               BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
         	  bgd.type=Terms.AON;
         	  this.lexer.wbag.put(bgd);
                 return this.lexer.context.changeContext(this.lexer.getPreviousContext().getContext(), this, " "," ").getContext();
               
               
           }catch(Exception e){return ContextualList.INITIAL;}
           
      }
      
      
      
       
           public ContextualList prepositionalSyntagmsListProcessing(String string){
            try{
          	//  System.out.println("entro por prepositional buillding "+string+" "+this.lexer.getLastToken());
           //     this.lexer.wbag.escribir();
                
                if (this.lexer.getLastToken()!="")
              	  
                this.lexer.wbag.put(new OrgBagData(this.lexer.getLastToken(),TypesTerms.FT,this.lexer.numCh-this.lexer.getLastToken().length(),this.lexer.numWord-1,this.lexer.numCh-1,new InfoFound(),this.lexer.context.getContext(),false));
          	  
                
                BagData bgd=new BagData(string,TypesTerms.PPT,this.lexer.numCh,this.lexer.numWord,this.lexer.numCh+string.length(),new InfoFound(),this.lexer.context.getContext());
          	  bgd.type=Terms.AON;
          	  this.lexer.wbag.put(bgd);
          	  this.lexer.setLastToken("");
                return this.lexer.context.getContext();
      
      }catch(Exception e){return this.lexer.context.getContext();}
       
               
               
              }

      
  }
