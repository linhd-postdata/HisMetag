/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringInProcess;

import ContextProcessing.ContextualList;
import StringNgramms.Ngramms;
import StringNgramms.NgrammsInfo;
import Recognition.InfoFound;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import IOModule.*;
import MedievalTextLexer.Lexer;
import Recognition.ElementsRecognition;
import Recognition.Terms;
import Recognition.TermsRecognition;
import Recognition.TypesTerms;
import Recognition.VerificationInfo;
import DataStructures.*;
/**
 *
 * @author M Luisa Díez Platas
 */
public class TokenizedString {
    public ArrayList<Token> tokenList=new ArrayList<Token>();
    public String stringInProcess;
    private Lexer lexer;
    
    public Ngramms ngramms=new Ngramms();
    
    public TokenizedString(Lexer lexer){
    	 stringInProcess="";
    	 this.lexer=lexer;
    }
   /* public TokenizedString(String p){
        stringInProcess="";
       
    }*/
    
    public TokenizedString(String string, Lexer lexer){
        
        this.lexer=lexer;
        String[] array=string.split(" ");
        int position=this.lexer.numCh;
        int nw=this.lexer.numWord;
        
        for (int i=0; i<array.length; i++){
          //  System.out.println("la palara que estoy tokenizando es"+array[i]+"num"+this.lexer.numCh);
            tokenList.add(new Token(array[i],null,null,null,false,this.lexer.numCh,this.lexer.numWord,VerificationInfo.UNIDENTIFIED));
           
           this.lexer.numCh+=array[i].length()+1;
           //this.lexer.numWord++;
          
            
        }
        stringInProcess=string;
        
      
        tokenList.trimToSize();
        
        
        
    }
    
    public Token getToken(String s){
        for (int i=0;i<tokenList.size();i++){
    		if (tokenList.get(i).word.equals(s)) return tokenList.get(i);
    	}
        return null;
    }
    
    
    public int getPos(String s){
    	for (int i=0;i<tokenList.size();i++){
    		if (tokenList.get(i).word.equals(s)) return tokenList.get(i).position;
    	}
    	return -1;
    }
    
    public int getWord(String s){
    	for (int i=0;i<tokenList.size();i++){
    		if (tokenList.get(i).word.equals(s)) return tokenList.get(i).nWord;
    	}
    	return -1;
    }
    public void cut(int n){
    for (int i=0;i<n;i++)
    	tokenList.remove(0);
    }
    
    public int getIndex(String word){
        int i=0;
        for (i=0; i<tokenList.size();i++){
            if (tokenList.get(i).word.equals(word)) break;
        }
        
        return i;
    }
    
    public String getString(){
    	String s=tokenList.get(0).word;
    	for (int i=1; i<tokenList.size();i++)
    		s+=" "+tokenList.get(i).word;
    	return s;
    }
    public void replaceFirstForAll(String string,Recognition.Terms term,InfoFound info,Recognition.TypesTerms type, boolean amb, Recognition.VerificationInfo verif){
      
      tokenList.set(0,new Token (string,term,info,type,amb,tokenList.get(0).position,verif));
      
      for (int i=tokenList.size()-1;i>=1; i--){
          tokenList.remove(i);
      }
      
      
      
  }
     
        
   public void replaceInfo(int position,Recognition.Terms term,InfoFound info,Recognition.TypesTerms type,boolean amb,Recognition.VerificationInfo verif,BagData bgd){
     //System.out.println("LA QUE QARTOYYY "+bgd.string+" "+bgd.type);
       Token t=tokenList.get(position);
       tokenList.set(position,new Token(t.word,term,info,type,amb,t.position,verif));
       tokenList.get(position).putBagData(bgd);
       
   }
   
   public void replaceInfo(String word,Recognition.Terms term,InfoFound info,Recognition.TypesTerms type,boolean amb,Recognition.VerificationInfo verif,BagData bgd){
       int index=getIndex(word);
       
       Token t=tokenList.get(index);
       t.info=info;
       t.term=term;
       t.type=type;
       t.ambiguous=amb;
       t.verif=verif;
       t.bdata=bgd;
      // System.out.println("LA QUE QARTOYYY "+bgd.string+" "+bgd.type);
              
   }
   public void replaceFor(int position,NgrammsInfo ng){
      // System.out.println("voy a reemplazar "+ng.ngramms+" "+position);
       Token t=tokenList.get(position);
       //System.out.println("lallla"+t.word);
       
       t.bdata=ng.bgdata;
      tokenList.set(position,new Token(ng.ngramms,ng.bgdata.type,ng.bgdata.infoPlace,ng.bgdata.subtype,t.position,ng.verif));
      tokenList.get(position).bdata=ng.bgdata;
      t=tokenList.get(position);
     // System.out.println("lallla"+tokenList.get(0).bdata.string+" "+t.bdata.string);
       
   }
   
    public boolean updateTokenList(){
      
        boolean found=false;
        
     // System.out.println("CAAAAAAAAAA"+ngramms);
       
        for (int i=ngramms.ngramms.size()-1; i>=0; i--){
            NgrammsInfo ng=this.lexer.currentString.ngramms.ngramms.get(i);
           
        //   System.out.println("DAAAAAAAAAA"+i);
          
            if (ng.bgdata!=null){
                
        // System.out.println("HEMOS ENTRADO pro un lado no vacio "+ng.bgdata.string);
               
                
               //System.out.println("RRRRRRRRRRA "+ng.bgdata.infoPlace.gazetteer+"SSS"+ng.ngramms+" "+i+"dddd"+tokenList.get(0).word);
                int j=0;
                //System.out.println("no se loq ue vemos"+ng.ngramms+tokenList.get(3).word);
                /*while (j<tokenList.size()){
                    System.out.println("ellle"+this.lexer.currentString.tokenList.get(j).info+ " "+tokenList.get(j).word);
                    if ( ((this.lexer.currentString.tokenList.get(j).info)!=null) || !(ng.ngramms.equals(tokenList.get(j).word))) {
                        System.out.println("SALI DE AQUI")
;                        break;
                    }
                    System.out.println("estoy aqui "+j);
                    j++;
                }*/
               
               // System.out.println(tokenList.get(0).word+" "+tokenList.get(0).bdata);
                while (j<tokenList.size() && !(ng.getFirst().equals(tokenList.get(j).word))) j++;
               // System.out.println ("LA J ES "+j+" "+ng.ngramms);
               //for (int l=0;l<tokenList.size();l++) System.out.println("loas tokenlist "+tokenList.get(l).word);
                if (j<tokenList.size()){
                         //System.out.println("HEMOS ENTRADO por la condicion de que hay que borrar las que tiene igual"+j+ngramms.ngramms.get(i).ngramms);
                   //if (ng.bgdata.type!=Terms.NPN){
                    if (ng.ngramms.contains(" ")){
                   
                            
           
                        if (checkNext(ng.ngramms.split(" "),j)){
                           // System.out.println("hemos entrado por el blanco JJJJJJJJJJJJJJJJJJJJJJJJ");
                        replaceFor(j,ng);
                         
                        }  
                    }else{
                          //System.out.println("Reemplazamos informacion de "+j);
                        replaceInfo(j,ng.bgdata.type,ng.bgdata.infoPlace,ng.bgdata.subtype,ng.ambiguous,ng.verif,ng.bgdata);
                                   

                            
                        }
                  /* }else{
                       System.out.println("HEMOS ENTRADO por algo ditinto de nickname RPALCE"+j+ ng.bgdata.type);
                       for (int k=0;k<tokenList.size();k++)
                       System.out.println("esto es lo que tiene la token list "+tokenList.get(k).bdata.string);
                        replaceInfo(j,ng.bgdata.type,ng.bgdata.infoPlace,ng.bgdata.subtype,ng.ambiguous,ng.verif,ng.bgdata);
                                   
                   }*/
                 // ngramms.ngramms.remove(i);
                    
                        //  System.out.println("HEMOS ENTRADO POS RPALCE otra"+j);
                    found=true;
                     
                    
            }
                      
            } 
       //    System.out.println("HEMOS ENTRADO POS RPALCE hhh");
            }
        
      //  System.out.println("AL ALLL ALLLALALALALALALAL ");
        
                       
          
        return found;
        }
    
    
    public boolean hasUnidentified (){
        
        if (ngramms.ngramms.size()>0)
         for (int i=ngramms.ngramms.size()-1; i>=0; i--){
            NgrammsInfo ng=ngramms.ngramms.get(i);
            if (ng.bgdata.infoPlace==null){
                
                return true;
            }
            
    }
        return false;
    }
    
    
     private boolean checkNext(String[] list, int position){
        int tam=list.length;
        boolean checked=false;
       //System.out.println("la lista es "+list.length+" "+position+" ");
        for (int i=1;i<list.length;i++){
           if (list[i].equals(tokenList.get(position+i).word)){
              // System.out.println("la lista es de palbras"+tokenList.get(position+i).word) ;
               checked=true;
           }
           else {checked=false; break;}
        }
        if (checked)
            for (int i=position+list.length-1; i>position;i--){
                //System.out.println("las remove "+i+" "+tokenList.get(i).word);
                tokenList.remove(i);
            }
                
        return checked;
    }


 

    
    public void write(){
       // System.out.println("el tamaño de tokenlist"+tokenList.size());
        for (int i=0; i<tokenList.size();i++)System.out.println(tokenList.get(i).word+" "+tokenList.get(i).info+" "+tokenList.get(i).term);
        
    }
      public void writeNgramms(){
        for (int i=0; i<ngramms.tam();i++){
            String info;
           
            if (ngramms.ngramms.get(i).bgdata.infoPlace==null) info="no tiene"; else info=ngramms.ngramms.get(i).bgdata.type.toString(); 
            
        }
    }
    
    public String processingArticles(String string){
    	
        ArrayList<String> articlesList=ElementsRecognition.hasArticle(string);
        
        String newString=string;

        if (articlesList.size()>0){
            for (int i=0; i<articlesList.size();i++){
                newString=newString.replaceAll(articlesList.get(i)+" ", articlesList.get(i)+"_");
                
            }
            
        }
        
        return newString;
    }
    
      public String processingTreatment(String string){
        ArrayList<String> articlesList=ElementsRecognition.hasTreatment(string);
        String newString=string;
        
        if (articlesList.size()>0){
            for (int i=0; i<articlesList.size();i++){
                newString=newString.replaceAll(articlesList.get(i)+" ", articlesList.get(i)+"_");
                
        }
            
    }
        return newString;
    }
    
     public String[] processingCopulativeConjunctions(String string){
        ArrayList<String> conjunctionsList=ElementsRecognition.hasCopulativeConjunction(string);
        
        String conjList="";
        String[] subStrings=null;
        if (conjunctionsList.size()>0){
        for (int i=0;i<conjunctionsList.size();i++) conjList+="_"+conjunctionsList.get(i)+"_|";
        
        String newString=string;
        
       conjList=conjList.substring(0,conjList.length()-1);
     
      
      
       
        if (conjunctionsList.size()>0){
            for (int i=0; i<conjunctionsList.size();i++){
               
                newString=newString.replaceAll(" "+conjunctionsList.get(i)+" ", "_"+conjunctionsList.get(i)+"_");
                 
        }
             
            subStrings=newString.split(conjList);
            
            
    }
        return subStrings;
    }else {
            subStrings=new String[1];
            subStrings[0]=string;
            
            return subStrings;
        }
        
    }
     
     public String processingAppositionsPrepositions(String string){
         ArrayList<String> prepositionsList=ElementsRecognition.hasAppositionPrepositions(string);
         String prepList="";
        String[] subStrings=null;
        for (int i=0;i<prepositionsList.size();i++) prepList+=" "+prepositionsList.get(i)+"_|";
        String newString=string;
        
        prepList=prepList.substring(0,prepList.length()-1);
     
      
      
       
        if (prepositionsList.size()>0){
            for (int i=0; i<prepositionsList.size();i++){
               
                newString=newString.replaceAll(prepositionsList.get(i)+" ", prepositionsList.get(i)+"_");
                 
        }
             
            
            
            
    }
        return newString;
     }
     
      public String processingOneAppositionsPrepositions(String string){
         ArrayList<String> prepositionsList=ElementsRecognition.hasAppositionPrepositions(string);
         String prepList="";
        String[] subStrings=null;
        for (int i=0;i<prepositionsList.size();i++) prepList+="_"+prepositionsList.get(i)+"_|";
        String newString=string;
        
        prepList=prepList.substring(0,prepList.length()-1);
     
      
      
       
        if (prepositionsList.size()>0){
            for (int i=0; i<prepositionsList.size();i++){
               
                newString=newString.replaceAll(" "+prepositionsList.get(i)+" ", "_"+prepositionsList.get(i)+"_");
                 
        }
             
            
            
            
    }
        return newString;
     }
     
      private boolean isName(Recognition.Terms term){
          
          return (term==Recognition.Terms.ACN ||term==Recognition.Terms.CN||term==Recognition.Terms.APN||term==Recognition.Terms.PN ||term==Recognition.Terms.STN || term==Recognition.Terms.PPN ||term==Recognition.Terms.PSTN);
      }
     public void sendOutInfo() throws java.io.FileNotFoundException,java.io.IOException{
         
      
       // System.out.println("LALLLALLLOTRA VEZ"+this.tokenList.get(0).word+this.tokenList.size());
          //System.out.println("la bolsa tiene los "+this.lexer.wbag.tam());
         for (int i=0;i<this.tokenList.size();i++){
         //  System.out.println("EL BAGADTA"+this.tokenList.get(i).word);
            BagData bgd=this.tokenList.get(i).bdata;
            
         //System.out.println("POR DONDE VOY");
            if (this.lexer.wbag.tam()>0){
                
            if (bgd.type==Terms.DE && this.lexer.wbag.getLast().type==Terms.NPLN){
                this.lexer.wbag.getLast().string+=" "+bgd.string;
            }else{
           //     System.out.println("entro por este caso 2"+tokenList.get(i));
             this.lexer.wbag.put(tokenList.get(i).bdata);
            }
           //  System.out.println("cuale es la bolsa ");
           //  this.lexer.wbag.escribir();
            } else{
                
           //     System.out.println("entro por este caso "+tokenList.get(i));
                this.lexer.wbag.put(tokenList.get(i).bdata);
            }
             
         }
                   //System.out.println("la bolsa tiene los "+this.lexer.wbag.tam()+this.lexer.wbag.get(4).string);

        /* if (this.lexer.context.getContext()==ContextualList.INITIAL){
             if (Data.ContextPlaceNames.contains(this.lexer.lastToken.toLowerCase())){
                 Output.write("<placeName>"+this.lexer.lastToken+" ");
                 //Output.writeData(this.lexer.lastToken, String.valueOf(this.lexer.numCh), String.valueOf(this.lexer.numWord),Terms.PLN.toString(),TypesTerms.FT.toString(), " ", " ", "no", " ", " "," "," ",VerificationInfo.FOUND.toString());

             }
             
         }*/
         
           
        /* for (int i=0;i<tokenList.size();i++){
             
             Token token=tokenList.get(i);
             token.write();
             System.out.println("he entrado por send "+tokenList.size());
            // if (token.type==Terms.PPN)this.lexer.wbag.put(new ProperNameBagData(token.word,token.type.toString(),token.position,token.nWord,token.position+token.word.length(),token.info,this.lexer.context.getContext()));
           //  System.out.println("los tokens se encunetran "+ token.word);
           //  System.out.println("en la cadena actual hay"+tokenList.size());
            if (token.ambiguous ){
                if (isName(token.term)){
               //     System.out.println("Voy a escribir "+token.term);
               String type=Data.ProperNamesTable.nameType(token.word.toLowerCase());

                    Output.writeName(token.word, token.term, token.type,type);
               
                Output.writeData(token.word, String.valueOf(token.position),String.valueOf(this.lexer.numWord),token.term.toString(),token.type.toString(), "", "", "yes", Terms.APLN.toString(), "", "", "", token.verif.toString());
                }else{
                Output.writePlaceName(token.word, token.info.uri, token.term);
               
                Output.writeData(token.word, String.valueOf(token.position), String.valueOf(this.lexer.numWord),token.term.toString(),token.type.toString(), token.info.uri, token.info.current, "yes", Terms.PN.toString(), " ",token.info.description,token.info.gazetteer, token.verif.toString());
                
                }
                
            }else{
             if (token.info==null){ 
                 
                 Output.write(token.word); 
                }
             
             else {
                 
                 
                 
                 if (isName(token.term)){
                                    String type=Data.ProperNamesTable.nameType(token.word.toLowerCase());

                    
                        Output.writeName(token.word, token.term, token.type,type);
                        if (token.type==TypesTerms.GENT){
                        Output.writeData(token.word, String.valueOf(token.position), String.valueOf(this.lexer.numWord),token.term.toString(),token.type.toString(), token.info.current, "", "", "", "", "", "", token.verif.toString());
                 
                        }else{
                           Output.writeData(token.word, String.valueOf(token.position), String.valueOf(this.lexer.numWord),token.term.toString(),token.type.toString(), "", "", "", "", "", "", "", token.verif.toString());
                  
                        }
                 }else if (token.term==Terms.UN){
                     Output.writePlaceName(token.word,"", token.term);
                 Output.write(" ");
                 Output.writeData(token.word, String.valueOf(token.position),String.valueOf(this.lexer.numWord),token.term.toString(),token.type.toString(), "", "", "not", "", "", "", "", token.verif.toString());
                   
                 }else{
                     //System.out.println("Voy a escribir place "+token.word);
                   Output.writePlaceName(token.word,token.info.uri, token.term);
                 Output.write(" ");
              //   System.out.println ("FLAG"+this.lexer.numWord);
                 Output.writeData(token.word, String.valueOf(token.position),String.valueOf(this.lexer.numWord),token.term.toString(),token.type.toString(), token.info.uri, token.info.current, "not", "", "", token.info.description, token.info.gazetteer, token.verif.toString());
              //     System.out.println("Salgo de aqui por el final");
                 }   
                     
                 
             }
         }
            if (tokenList.size()>1 || i!=tokenList.size()-1) Output.write(" ");
         }*/
         
        /* if (this.lexer.context.getContext()==ContextualList.PLACE){
             if (Data.ContextPlaceNames.contains(this.lexer.lastToken.toLowerCase())){
                 Output.write("</placeName>");
             }
             
         }*/
                   
                       }
    
 public boolean endsWithAuthority(){
     return Recognition.TermsRecognition.isAuthority(tokenList.get(tokenList.size()-1).word.toLowerCase());
 }
 
     
 public boolean hasAuthority(){
     for (int i=0; i<tokenList.size();i++){
         Token token=tokenList.get(i);
         if (TermsRecognition.isAuthority(token.word.toLowerCase())) return true;
         
     }
     return false;
 }
   
    
   public void buildNgramms(String[] words){
       int num=0;
       
     //  System.out.println("Lalista de ne gramas es de tamaño "+words.length+"se"+words[0]+"to"+words[1]);
   
       num=(words.length>3)? 3:words.length;
       for (int i=1;i<=num;i++){
       ngramms.buildNgramms(words, i);
       }
   
   
  }
   
   public void writeTokenList(){
       for (int i=0; i<tokenList.size();i++){
             Token tk=tokenList.get(i);
            // System.out.println(tk.word+" "+tk.info.uri+" "+tk.type);
                System.out.println(tk.word);
         }
   }
       
  public void countWordChar(){
      for (int i=0; i<tokenList.size();i++){
          Token token=tokenList.get(i);
          token.position=this.lexer.numCh;
          token.nWord=this.lexer.numWord;
      }
  }
  
   
   

}
