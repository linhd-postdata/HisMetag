/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluarf1;
import java.util.*;
import java.io.*;
/**
 *
 * @author Mª Luisa Díez Platas
 */
public class ListEvaluationItem {
   ArrayList<EvaluationItem> list=new ArrayList<EvaluationItem>();
   
  public ListEvaluationItem(){;}
  public ListEvaluationItem(FileReader f) throws IOException{
      BufferedReader br=new BufferedReader(f);
      String line=br.readLine();
      line=br.readLine();
      while(line!=null){
          String[] elems=line.split(";");
       
          
        
          list.add(new EvaluationItem(elems[0],elems[1],elems[2],Integer.valueOf(elems[3]),Integer.valueOf(elems[4])));
         
      line=br.readLine();
      }
      
  }
  public int totalTags(){
      return list.size();
  }
  public int totalTags(String tag){
      int tTag=0;
      for (int i=0;i<list.size();i++)
          if (list.get(i).tag.equals(tag))tTag++;
      return tTag;
  }
   
  
  public int totalTagsAtt(String tag){
      int tTag=0;
      for (int i=0;i<list.size();i++)
          if (list.get(i).tag.equals(tag) && !(list.get(i).attrib.equals("")))tTag++;
      return tTag;
  }
  public EvaluationItem get(int i){
      return list.get(i);
  }
  
   public boolean hasItemTotalMatch(EvaluationItem item){
      for (int i=0; i<list.size();i++){
           EvaluationItem it=list.get(i);
          
          if (item.tag.equals(it.tag)  && item.offset==it.offset ) return true;
      }
      return false;
  }
  public boolean hasItemTotalMatch(EvaluationItem item,String tag){
      for (int i=0; i<list.size();i++){
           EvaluationItem it=list.get(i);
           
          if (item.tag.equals(it.tag)  && item.offset==it.offset ){
              return true;
          }
      }
      return false;
  }
   
  
  public boolean hasItemTotalAttributeMatch(EvaluationItem item){
      EvaluationItem aux=item;
      aux.attrib=item.attrib.toLowerCase();
      
              
      for (int i=0; i<list.size();i++){
           EvaluationItem it=list.get(i);
           
          if (aux.tag.equals(it.tag)  && aux.attrib.equals(it.attrib) && aux.offset==it.offset ) return true;
      }
      return false;
  }
  
    public boolean hasItemTotalAttributeMatch(EvaluationItem item, String tag){
      for (int i=0; i<list.size();i++){
           EvaluationItem it=list.get(i);
          if ( item.tag.equals(tag) && item.tag.equals(it.tag)  && item.attrib.equals(it.attrib) && item.offset==it.offset ) return true;
      }
      return false;
  }
 
}
