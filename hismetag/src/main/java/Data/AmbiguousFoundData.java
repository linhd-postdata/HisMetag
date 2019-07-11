/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author M Luisa Díez Platas
 */
public class AmbiguousFoundData {
    public String word;
    public int frequency;
    public String type;
    
    public AmbiguousFoundData(){;}
    
    public AmbiguousFoundData(String w,int f, String t){
        word=w;
        frequency=f;
        type=t;
    }
    
}
