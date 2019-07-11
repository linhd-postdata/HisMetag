/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordProcessing;
import java.io.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class WordND {
    public String word;
    public float distanceLeven;
    public float distanceBgramm;
    public float distance;
    public WordND(String w,float dL,float dB,float d ){
        word=w;
        distanceLeven=dL;
        distanceBgramm=dB;
        distance=d;
       // System.out.println("se ha metido "+word+"L"+dL+"B"+dB+"d"+d);
    }
    public void print(){
   //    System.out.println(word+" "+distanceLeven+" "+distanceBgramm+" "+distance);
    }
    
}
