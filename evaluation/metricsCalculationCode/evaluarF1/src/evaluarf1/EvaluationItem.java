/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluarf1;

/**
 *
 * @author Mª Luisa Díez Platas
 */
public class EvaluationItem {
    String tag;
    String attrib;
    String value;
    int offset;
    int endchar;
    
    public EvaluationItem(String t,String at,String val,int of,int end){
        tag=t;
        attrib=at;
        value=val;
        offset=of;
        endchar=end;
    }
    
    
}
