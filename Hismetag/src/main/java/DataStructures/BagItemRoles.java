/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;

import java.util.*;

/**
 *
 * @author mluisadiez
 */
public class BagItemRoles {
    public LinkedList<ItemRoles> bagIt;
    
    public BagItemRoles(){
        bagIt=new LinkedList<ItemRoles>();
    }
    public int tam(){return bagIt.size();}
    
    public ItemRoles get(int i){
        
        return bagIt.get(i);
    }
    
    public void add(ItemRoles itr){
        bagIt.add(itr);
    }
    
    public boolean esta(String string){
        for (int i=0; i<bagIt.size(); i++){
            if (bagIt.get(i).node.root.string==string)
                return true;
            
        }
        return false;
    }
    
    public void remove(String string){
        for (int i=0; i<bagIt.size(); i++){
            if (bagIt.get(i).node.root.string==string){
                bagIt.remove(i);
                break;
                
            }
            
        }
    }
}
