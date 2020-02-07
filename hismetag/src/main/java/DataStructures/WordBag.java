/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package DataStructures;

import ContextProcessing.ContextualList;
import IOModule.Output;
import MedievalTextLexer.Lexer;
import Recognition.Terms;
import java.util.*;

/**
 *
 * @author mluisadiez
 */
public class WordBag {
 public ArrayList<BagData> wbag; 
 private Lexer lexer;
 private Output output;
 
 public WordBag(Lexer lexer, Output output){
     this.lexer=lexer;
     this.output=output;
     wbag=new ArrayList<BagData>();
     
 }
 
 public void put(BagData bd){
     //System.out.println("he entrado por el put de bag "+bd.string+this.lexer.context.getContext()+bd.position);
     if (bd.string!=" ")
     wbag.add(bd);
 }
 
 public int tam(){
     return wbag.size();
 }
 
 public BagData get(int i){
     return wbag.get(i);
 }
 
 public void clean(){
     //System.out.println("voy a limpiar"+wbag.size());
     for (int i=(wbag.size()-1);i>=0;i--){
        // System.out.println("la i "+i);
        wbag.remove(i);
     }
 }
 public void borrar(BagData bgd){
     wbag.remove(bgd);
 }
 
public void borrar(Terms tt,int pos){
   try{
    BagData item=wbag.get(pos);
    if (pos>=0 && tt==Terms.ART){
        wbag.remove(pos);
        this.output.write(new RoleTreeNode (item));
    }
   }catch (Exception e){;}
}
 

 
 public boolean contieneNP(){
     for (int i=0;i<wbag.size();i++){
         if (wbag.get(i).type==Terms.PPN) return true;
     }
     return false;
 }
 
 public void restart() throws java.io.FileNotFoundException,java.io.IOException{
  //System.out.println ("la bolsa 1 "+wbag.size());
    for (int i=0; i<wbag.size();i++){
        
        if (wbag.get(i).string==" ") wbag.remove(i);
    }
    String ultimo="";
    //System.out.println("escribir bagdata");this.escribir();
     ArrayList<BagData> aux=new ArrayList<BagData>();
     if (wbag.size()>0){
         //System.out.println("el arbol de ca ");
         if (wbag.size()>=2 && wbag.get(wbag.size()-2).type==Terms.Y){
             
             if (wbag.get(wbag.size()-1).type==Terms.DE) {
                 
                 aux.add(wbag.get(wbag.size()-2));
                  aux.add(wbag.get(wbag.size()-1));
                  wbag.remove(wbag.get(wbag.size()-1));
                  wbag.remove(wbag.get(wbag.size()-1));
                         }
             
             
         }
         if (wbag.get(wbag.size()-1).type==Terms.ART){
          //       System.out.println ("he entrado por el articulo de restart");
                 ultimo=wbag.get(wbag.size()-1).string;
                 wbag.remove(wbag.get(wbag.size()-1));
          //       System.out.println ("he entrado por el articulo de restart"+this.lexer.wbag.tam());
                 
             }
         
    if (wbag.size()>0)     {
     RolesTree tree=DataAlgorithms.DataAlgorithms.roleAlgorithm(this);
     
     this.output.write(tree.tree);
    }
     for (int i=0;i<aux.size();i++){
         this.output.write(new RoleTreeNode(aux.get(i).string));
     }
     if (ultimo!="") this.output.write(new RoleTreeNode(ultimo));
     }
     // System.out.println ("la bolsa 3");
     this.clean();
     //this.lexer.lastToken="";
     this.lexer.clearPreviousContext();
     
    
     //this.lexer.context.changeContext(ContextualList.INITIAL,this.lexer.context, "", "");
    // System.out.println("LA WORDBAG NO ME LO PERMITE");
 }
 
 public boolean containsTypeTerms(Terms term){
     for (int i=0; i<wbag.size();i++){
         if (wbag.get(i).type==term) return true;
     }
     return false;
 }
 
 
 public BagData getLast(){
     return wbag.get(wbag.size()-1);
 }
 public void writeBag() throws java.io.FileNotFoundException,java.io.IOException{
     boolean pn=false;
     //System.out.println("  "+wbag.size());
     if (this.contieneNP()) {
    //	 System.out.println("contiene NP");
         pn=true;
         this.output.write("<persName>");
     }
     for (int i=0;i<wbag.size();i++){
    //	  System.out.println("la i"+i);
         BagData data=wbag.get(i);
        // System.out.println("la bolsa es "+data.string);
         if (data.type==Terms.RNH){
          this.output.write("<roleName type='honorific' suptype='"+data.subtype+"'>"
                  +data.string+"</roleName>");   
     } else if (data.type==Terms.RNA){
    	
         this.output.write("<roleName>"+data.string);
         
         while(i<wbag.size() || data.type!=Terms.RN  ){
        //	 System.out.println("la bolsa "+wbag.get(i)+" ");
        	 i++;
           //  System.out.println("la bolsa "+wbag.get(i)+" "+i);
             if (data.infoPlace.gazetteer!=" "){
                 this.output.write("<placeName><ptr ref="+'"'+data.infoPlace.uri+'"'+">"+data.string+"</ptr></placeName>");
             }
             else this.output.write(data.string);
             
         }
         
                   
     }else if (data.type==Terms.RNF){
         this.output.write("<roleName type='famili'>"+data.string);
         while(data.type!=Terms.RN || i<wbag.size()){
             i++;
             if (data.type!=Terms.UN){
                 this.output.write("<persName>"+data.string+"</persName>");
             }else this.output.write(data.string);
         }
     }
     }
 }
 
 public void escribir(){
     for (int i=0;i<wbag.size();i++) System.out.println("XXX"+wbag.get(i).string+" "+wbag.get(i).type);
 }
}
