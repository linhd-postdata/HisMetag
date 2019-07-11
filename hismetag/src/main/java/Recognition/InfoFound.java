/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recognition;

/**
 *
 * @author M Luisa Díez Platas
 */
public class InfoFound {
    public String uri=" ";
    public String current=" ";
    public String description=" ";
    public String gazetteer=" ";
    
    
    public InfoFound(){
        
    }
    
    public InfoFound(InfoFound inf){
        uri=inf.uri;
        current=inf.current;
        description=inf.description;
        gazetteer=inf.gazetteer;
    }
    
    public InfoFound(String ur,String cur,String des,String gaz){
        uri=ur;
        current=cur;
        description=des;
        gazetteer=gaz;
    }
    
}
