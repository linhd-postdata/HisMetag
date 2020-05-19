/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluarf1;
import java.util.ArrayList;
/**
 *
 * @author Mª Luisa Díez Platas
 */
public class ListResultItemDocs {
    ArrayList<ResultItemDocs> resultDocs=new ArrayList<ResultItemDocs>();
    
    
    public void put (ResultItemDocs rid){
       
        resultDocs.add(rid);
    }
    
    public ResultItemDocs get (int i){
       return resultDocs.get(i);
    }
    
    
}
