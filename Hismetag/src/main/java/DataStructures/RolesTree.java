/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;

import IOModule.Output;
import java.util.*;
/**
 *
 * @author mluisadiez
 */

public class RolesTree {
    public RoleTreeNode tree;
    public RoleTreeNode actua;
    
public RolesTree(){
    tree=null;
}

public void insertPred(RoleTreeNode node, BagData info)
{
   RoleTreeNode aux=new RoleTreeNode(info);
   
   node.addPred(aux);


}

public void insertSuc(RoleTreeNode node, BagData info)
{
   RoleTreeNode aux=new RoleTreeNode(info);
   
   node.addSuc(aux);

}

public String inOrden() throws java.io.IOException,java.io.FileNotFoundException{ 
    
    
  
    return tree.inOrden();
  }



 
}
