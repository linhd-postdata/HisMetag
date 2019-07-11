/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;
import java.io.*;
import java.util.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class Input {
    public static String name;
    static public InputStreamReader setInputText(String f, String n) throws java.io.FileNotFoundException,java.io.IOException{
        try{
        	name=n;
            System.out.println ("Filename to processed "+name+":"+f);
       
                    File file=new File(f);
            		System.out.print("Processing...............");
                        FileInputStream entrada=new FileInputStream(file);
                        
                        System.out.println("entrada"+ entrada);
					return new InputStreamReader(entrada,"cp1252");
    }catch (Exception e){System.out.println("Error to open file");
    return null;}
    }
    
    public static String getName(){return name;}
    
}
