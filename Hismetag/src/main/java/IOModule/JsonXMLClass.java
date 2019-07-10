/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;

import java.util.*;

/**
 *
 * @author mluisadiez
 */
public class JsonXMLClass {
   ArrayList<JsonClass> entities;
   String xml;
   JsonXMLClass(ArrayList<JsonClass> ent,String xml){
       entities=ent;
       this.xml=xml;
   }
   
}
