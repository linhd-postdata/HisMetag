/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordProcessing;
import java.util.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class AproximationsList {
    public ArrayList<WordND> aproxList=new ArrayList<WordND>();
    
    public boolean contains(String word){
        for (int i=0; i<aproxList.size();i++)
            if (aproxList.get(i).word.equals(word)) return true;
        return false;
    }
    
    public void escribir (){
       //System.out.println ("la lista de aprox "+aproxList.size());
        for (int i=0; i<aproxList.size();i++)
            System.out.println(aproxList.get(i).word);
    }
}
