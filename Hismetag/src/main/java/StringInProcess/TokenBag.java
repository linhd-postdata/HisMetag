/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package StringInProcess;

/**
 *
 * @author mluisadiez
 */
enum VerbCategory {regimen, movimiento};
public class TokenBag {
    public String word;
    public VerbCategory category;
    
    public TokenBag(String string){
        word=string;
        category=getCategory(string);
    }
    
    public VerbCategory getCategory(String s){
        
       return null; 
    }
}
