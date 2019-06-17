/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordProcessing;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import MedievalTextLexer.Lexer;
import Recognition.ElementsRecognition;

/**
 *
 * @author M Luisa DÃ­ez Platas
 */
public class WordTransformations {
    static public String acumulative;
    
    static public AproximationsList transformedWords=new AproximationsList();
    
    /**
     * Put the first letter of a word in capital letters
     * @param word
     * @return 
     */
    public static String capitalize(String word){
    	
        String[] words;
        String newWord="";
        if (word.contains(" ")){
            words=word.split(" ");
            for (int i=0;i<words.length;i++){
                if (!(Data.AppositionPrepositionsTable.contains(words[i])) && !(Data.AppositionPrepositionsTable.contains(words[i])) && !(Data.ArticleTable.contains(words[i]))){
                	
                    char[] characterWord=words[i].toCharArray();
                    characterWord[0] = Character.toUpperCase(characterWord[0]);
                    words[i]=new String(characterWord);
                }
               newWord+=words[i]+" "; 
            }
                
        }else{
            char[] characterWord=word.toCharArray();
                    characterWord[0] = Character.toUpperCase(characterWord[0]);
                    newWord=new String(characterWord);
                   
        }
        
        if (newWord.charAt(newWord.length()-1)==' ') newWord=newWord.substring(0, newWord.length()-1);
        return newWord;
    }
    
    
    static public String replaceGuion(String string){
        return string.replaceAll("-","");
    }
    
    /**
     * Replaces carriage return and line feed in a string
     * @param string
     * @return 
     */
    
    static public String replaceLineBreak(String string){
        Pattern patron = Pattern.compile("\n");
        
       Matcher encaja = patron.matcher(string);

      string=encaja.replaceAll("");
      
      patron= Pattern.compile("\r");
      
      encaja = patron.matcher(string);
     
      string=encaja.replaceAll(" ");
      return string;
    }
    
 
    
    static public String cleanSpaces(String string){
        Pattern patron = Pattern.compile("\\s+");
        
       Matcher encaja = patron.matcher(string);
        return string=encaja.replaceAll("");
    }
    
    static public String cleanChar(String string){
    	// System.out.println("el patron reconocido "+string);
        Pattern patron = Pattern.compile("\\[|\\]|\\(|\\)");
        //System.out.println("el patron reconocido "+patron.toString());
        
       Matcher encaja = patron.matcher(string);
       //System.out.println("AS");
       
        return string=encaja.replaceAll("");
    }
    
    
    static public String remove(String word){
         String original = "Ã¡Ã Ã¤Ã©Ã¨Ã«Ã­Ã¬Ã¯Ã³Ã²Ã¶ÃºÃ¹uÃ±Ã�Ã€Ã„Ã‰ÃˆÃ‹Ã�ÃŒÃ�Ã“Ã’Ã–ÃšÃ™Ãœ";
    // Cadena de caracteres ASCII que reemplazarÃ¡n los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUU";
    String output = word;
    
    for (int i=0; i<original.length(); i++) {
        // Reemplazamos los caracteres especiales.
        
        output = output.replace(original.charAt(i), ascii.charAt(i));
        
    }//for i
    
    return output;
    }
    
    
    static public String replaceCharacters(String word){
        String [] original={"Ã±","Ã‘","Ã","Ã‰","Ã","Ã“","Ãš","Ã¡","Ã©","Ã­","Ã³","Ãº","Á§","Ã‡"};
        String [] fuente={"ñ","Ñ","Á","É","Í","Ó","Ú","á","é","í","pp","ú","ç","Ç"};
        
        String nueva=word;
       // System.out.println ("ESTOY SUSTITUYENDO LO QUE NOVEO"+nueva);
        
        for (int i=0;i<original.length;i++){
            nueva=nueva.replaceAll(original[i], fuente[i]);
        }
        
        return nueva;
    
    }
    
    static public void reversesubstitution(String word) throws java.io.FileNotFoundException,java.io.IOException{
  
        ArrayList<String> rules=Data.ReverseRulesTable.reverseRules;
       /* File archivo = new File (path+"dataFiles/reverse-rules.txt");
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        line=br.readLine();
        while(!(line=br.readLine()).contains("</groups>")){*/
        for (int i=0;i<rules.size();i++){
            String[] rule=rules.get(i).split(";");
              //System.out.println("REVERSE "+line);
            Pattern patron = Pattern.compile(rule[0]);
            Matcher matcher = patron.matcher(word);
            
            if (matcher.find()){ 
            int n=matcher.groupCount();

            String newS=matcher.replaceAll(rule[1]);

           // System.out.println("ESSSSSSSSSSSSSSS");
           if (!(transformedWords.contains(newS))){
          float  dL=LevenshteinDistance.computeLevenshteinDistance(word, newS);
           float  dB=Bigramms.distanceBi(word, newS);
          float  sumdist=1/dL+dB;
          WordND   wnew=new WordND(newS,dL,dB,sumdist);
              transformedWords.aproxList.add(wnew);
           }
            }
                 
           
        }
       // br.close();

    }
    
        static public void substitution(String word) throws java.io.FileNotFoundException,java.io.IOException{
       
             ArrayList<String> rules=Data.RulesTable.rules;
          //  System.out.println ("estoy buscando las substituciones de la palabra "+word);
       /* File archivo = new File (path+"dataFiles/rules.txt");
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        acumulative=word;
        String line;
        line=br.readLine();
        

        while(!(line.contains("</groups>"))){*/
           //   System.out.println("SUSTITUCION "+line);
             for (int i=0;i<rules.size();i++){
            String[] rule=rules.get(i).split(";");
            
            Pattern patron = Pattern.compile(rule[0]);
            Matcher matcher = patron.matcher(word);
          //  Matcher matcheracum=patron.matcher(acumulative);
            
            
           if (matcher.find()){ 
               
            int n=matcher.groupCount();
            
       
            String newS=matcher.replaceFirst(rule[1]);
            
           if (!(transformedWords.contains(newS))){
            float dL=LevenshteinDistance.computeLevenshteinDistance(word, newS);
            float dB=Bigramms.distanceBi(word, newS);
            float sumdist=1/dL+dB;
              WordND wnew=new WordND(newS,dL,dB,sumdist);
              transformedWords.aproxList.add(wnew);
           }
            }
         
           
       
           
        // line=br.readLine();
        }
         
        
//    br.close();
   
    
    }
    
    public static void rulesApplication(String word) throws java.io.FileNotFoundException,java.io.IOException{
         ArrayList<String> rules=Data.RulesTable.rules;
         
       /* File archivo = new File (path+"dataFiles/rules.txt");
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String line;
        line=br.readLine();
        while(!(line=br.readLine()).contains("<rules>")){
            
        }
        while (!(line=br.readLine()).contains("</rules>")){*/
        for (int j=0;j<rules.size();j++){
            String[] rule=rules.get(j).split(";");
            
        //    System.out.println("ELTAMANO DE RULES ES "+ rules.length);
          String[] sustitution;
            if (rule.length>1){
            sustitution=rule[1].split(" ");
            }else {
                
                sustitution=new String[1];
                sustitution[0]="";
                //System.out.println("estamos aqui");
            //sustitution[0]=rules[1];
            }
            // System.out.println("final final");
            Pattern patron = Pattern.compile(rule[0]);
            Matcher matcher = patron.matcher(word);
            //System.out.println ("estoy buscando los patrones "+patron.toString()+"sustitucion "+sustitution.length+"wwww"+sustitution[0]+"sss");
            
                

            if (matcher.find()){ 

            String newS;
            
           
           
            
            if (sustitution.length==0){
                
               newS =matcher.replaceAll("");
               
            //   System.out.println("DEBERIA DE REEMPLAZAR LA COMILLA PIR PLANCO "+newS);
           
           if (!(transformedWords.contains(newS))){
               
            float dL=LevenshteinDistance.computeLevenshteinDistance(word, newS);
            float dB=Bigramms.distanceBi(word, newS);
            float sumdist=1/dL+dB;
             WordND   wnew=new WordND(newS,dL,dB,sumdist);
              transformedWords.aproxList.add(wnew);
                
            }
           
            }
           
            
            for (int i=0;i<sustitution.length;i++){
            newS=matcher.replaceAll(sustitution[i]);
           
           if (!(transformedWords.contains(newS))){
            float dL=LevenshteinDistance.computeLevenshteinDistance(word, newS);
            float dB=Bigramms.distanceBi(word, newS);
            float sumdist=1/dL+dB;
              WordND wnew=new WordND(newS,dL,dB,sumdist);
              transformedWords.aproxList.add(wnew);
           }
           
            }
            }
            
            // System.out.println("por aqui");
            // WordTransformations.transformedWords.escribir();
            Matcher matcheracum=patron.matcher(acumulative);
            if (matcheracum.find()){ 
            int n=matcher.groupCount(); 
            //System.out.println("por aqui");
            if (sustitution.length==0){
                
               acumulative =matcheracum.replaceAll("");
           
           if (!(transformedWords.contains(acumulative))){
            float dL=LevenshteinDistance.computeLevenshteinDistance(word, acumulative);
            float dB=Bigramms.distanceBi(word, acumulative);
            float sumdist=1/dL+dB;
              WordND wnew=new WordND(acumulative,dL,dB,sumdist);
              transformedWords.aproxList.add(wnew);
                
            }
            }
            // System.out.println("por aqui");
           
            for (int i=0;i<sustitution.length;i++){
            acumulative=matcheracum.replaceAll(sustitution[i]);
           
           if (!(transformedWords.contains(acumulative))){
            float dL=LevenshteinDistance.computeLevenshteinDistance(word, acumulative);
            float dB=Bigramms.distanceBi(word, acumulative);
            float sumdist=1/dL+dB;
              WordND wnew=new WordND(acumulative,dL,dB,sumdist);
              transformedWords.aproxList.add(wnew);
           }
            }
            }
            
             
            
    } //System.out.println("la lista ");
   // System.out.println("-------------------------AAAA");
        float dL=LevenshteinDistance.computeLevenshteinDistance("Llebrixa", "Lebrija");
       // System.out.println(dL);
        float dB=Bigramms.distanceBi("Llebrixa", "Lebrija");
         float sumdist;
        if (dL==0) sumdist=0+dB;
        else sumdist=1/dL+dB;
              WordND wnew=new WordND("Llebrixa",dL,dB,sumdist);
             // System.out.println("-------------------------");
       
        //br.close();

      
   }
        
  

    
   public static boolean dominated(WordND word){
       for (int i=0; i<transformedWords.aproxList.size();i++){
           WordND wselect=transformedWords.aproxList.get(i);
           if (word.distanceLeven>wselect.distanceLeven && word.distanceBgramm<wselect.distanceBgramm )return true;
       }
       return false;
    }
    
   public static void addWord(WordND word){
      
       for (int i=0; i<transformedWords.aproxList.size();i++){
           WordND wselect=transformedWords.aproxList.get(i);
           if (word.distanceLeven<wselect.distanceLeven && word.distanceBgramm>wselect.distanceBgramm)
               transformedWords.aproxList.remove(wselect);
          
           
       }
       transformedWords.aproxList.add(word);
   }
   
   public static boolean isVocal(char c){
       return (c=='a' || c=='e' || c=='i'||c=='o' || c=='u');
   }
   public static String pluralWord(String word){
       if (isVocal(word.charAt(word.length()-1))) return word+'s'; else return word+"es";
   }
   
  public static String removePattern(String pattern, String string){
      String pt=pattern.replace("|"," ");
      String[] subPattern=pt.split(" ");
      String newString=string;
      
      for (int i=0;i<subPattern.length;i++){
          
          newString=newString.replaceAll(subPattern[i]+"_", "");
      }
        return newString;
  }
  
    public static String removeCleanPattern(String pattern, String string){
      String pt=pattern.replace("|"," ");
      String[] subPattern=pt.split(" ");
      String newString=new String(string);
      
      for (int i=0;i<subPattern.length;i++){
          
          newString=newString.replaceAll(subPattern[i]+" ", "");
      }
      
      
        return newString;
  }
   
     /**
     * Make some word transformations by the application of morphological rules
     * 
     * @param word 
     */
     static public void wordTransformationProcessing(String word){
      try{  
         // System.out.println ("estoy buscando las variante s en transformcaciones de la palabra "+word);
       // transformedWords=new AproximationsList();
          WordProcessing.WordTransformations.substitution(word);
          
        WordProcessing.WordTransformations.rulesApplication(word);
       
        WordTransformations.reversesubstitution(word);
        Pattern patron = Pattern.compile("’");
            Matcher matcher = patron.matcher(word);
            if (matcher.find()){
       String nueva=matcher.replaceAll("");
       //System.out.println("NO PUEDO VER ALFOSSO");
      //  System.out.println("la lalalalalalalal palalalalallala "+nueva);
        
       
            }
       // System.out.println("las palabras transformadas de "+transformedWords.aproxList.size()+"la palabra es "+word);
        
        for (int i =0; i<transformedWords.aproxList.size(); i++){
            
           // System.out.println("las siguinetes "+transformedWords.aproxList.get(i).word+" "+transformedWords.aproxList.get(i).distance+" "+transformedWords.aproxList.get(i).distanceLeven+" "+transformedWords.aproxList.get(i).distanceBgramm);
        }
    }catch(Exception e){;}
      
     
    }
     
     static public String  lemmVerbs(String verb){
    	 
    	 ArrayList<String> desinencias=Data.DesiTable.desinencias;
    	 String res=" - ";
    	 int index=0;
    	 
    	 //System.out.println("he pasado el verbo "+verb+desinencias.size());
    	 for (int i=0; i<desinencias.size();i++){
    		 String desinencia=desinencias.get(i);
    		
    		 index=verb.lastIndexOf(desinencias.get(i));
    		 
    		 if (index>-1){
    			 //res=verb.substring(0,index)+"-"+desinencia;
    			 int total=index+desinencia.length();
    			 if (total==verb.length()){
    			 String raiz=verb.substring(0,index);
    			 
    			 return raiz+"-"+desinencia;
    			 }
    			
    		 }
    		 
    	 }
    	 return res;
    	 
     }
}
