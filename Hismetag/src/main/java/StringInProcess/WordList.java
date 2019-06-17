/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringInProcess;

import java.util.ArrayList;
import IOModule.JsonClass;
/**
 *
 * @author mluisadiez
 */
public class WordList {
    public ArrayList<Word> wordList;
    int position=1;
    int word=1;
    
    public WordList(String text){
        wordList=new ArrayList<Word>();
        position=1;
        word=1;
        createWordList(text);
    }
    
    public  void createWordList(String text){
        
       String lines[]=text.split("\n");
       for (int i=0;i<lines.length;i++){
           addLine(lines[i]);
       }
       
       
        
    }
    
    public void addLine(String l){
        String words[]=l.split(" ");
        for (int i=0; i<words.length;i++){
            wordList.add(new Word(words[i],position,word));
            position+=words[i].length()+1;
            word++;
            
      }
        position++;
    }
    
    
    public void updateJsonList(ArrayList<JsonClass> jList){
        
        int j=0;
        for (int i=0;i<wordList.size();i++){
            Word wordObject=wordList.get(i);
            System.out.println(wordObject.word);
            if (j<jList.size()){
                JsonClass jObject=jList.get(j);
                System.out.println(jObject.getTerm());
                if (jObject.getTerm().contains(wordObject.word)){
                    jObject.setPosition(String.valueOf(wordObject.nChar));
                    jObject.setWord(String.valueOf(wordObject.nWord));
                    j++;
                }
            }
           
        }
    }
    
}
