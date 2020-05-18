/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluarf1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Mª Luisa Díez Platas
 */
public class Output {
    static public BufferedWriter tagsOutput;
    static public File tagsFile;
  
   public Output(String name) throws java.io.FileNotFoundException, java.io.IOException{
       tagsFile=new File(name);
      
       if (tagsFile.exists()){
       tagsOutput= new BufferedWriter(new FileWriter(tagsFile));
       }else{
           tagsOutput= new BufferedWriter(new FileWriter(tagsFile));
       }
   }
   
   public void header()throws java.io.FileNotFoundException,java.io.IOException{
       
               tagsOutput.write("TAG;PRECISION;RECALL;F1\n");

   }
   
   public void headerCent() throws java.io.FileNotFoundException,java.io.IOException{
       tagsOutput.write("SIGLO;PRECISION;RECALL;F1\n");
   }
    public void headerDoc() throws java.io.FileNotFoundException,java.io.IOException{
       tagsOutput.write("NAME;TAG;PRECISION;RECALL;F1;AUTOMATIC_TOTAL;HAND_TOTAL,TOTAL_MATCHED\n");
      
   }
   
   public void writeTag(String tag) throws java.io.FileNotFoundException,java.io.IOException{
       double  precision=evaluarF1.listResult.getPrecision(tag);
       double recall=evaluarF1.listResult.getRecall(tag);
       double F1=evaluarF1.listResult.getF1(tag);
       tagsOutput.write(tag+";"+String.valueOf(precision)+";"+String.valueOf(recall)+";"+String.valueOf(F1)+"\n");
   }
    public void writeTagAtt(String tag) throws java.io.FileNotFoundException,java.io.IOException{
       double  precision=evaluarF1.listResultAtt.getPrecision(tag);
       double recall=evaluarF1.listResultAtt.getRecall(tag);
       double F1=evaluarF1.listResultAtt.getF1(tag);
       tagsOutput.write(tag+";"+String.valueOf(precision)+";"+String.valueOf(recall)+";"+String.valueOf(F1)+"\n");
   }
    
     public void writeTagMacro(String tag) throws java.io.FileNotFoundException,java.io.IOException{
       double  precision=evaluarF1.listResultMacro.getPrecision(tag);
       double recall=evaluarF1.listResultMacro.getRecall(tag);
       double F1=evaluarF1.listResultMacro.getF1(tag);
       tagsOutput.write(tag+";"+String.valueOf(precision)+";"+String.valueOf(recall)+";"+String.valueOf(F1)+"\n");
   }
    
    public void write(String line) throws java.io.FileNotFoundException,java.io.IOException{
       
       tagsOutput.write(line);
   }
   
        public void writeTagCent(String cent) throws java.io.FileNotFoundException,java.io.IOException{
       double  precision=evaluarF1.listResultAtt.getPrecision(cent);
       double recall=evaluarF1.listResultAtt.getRecall(cent);
       double F1=evaluarF1.listResultAtt.getF1(cent);
       tagsOutput.write("Siglo "+cent+";"+String.valueOf(precision)+";"+String.valueOf(recall)+";"+String.valueOf(F1)+"\n");
   }
   
   public  void write(String tag, String p, String r, String F1) throws java.io.FileNotFoundException,java.io.IOException{
       tagsOutput.write(tag+";"+p+";"+r+";"+F1+"\n");
   }
   
   public void close() throws java.io.FileNotFoundException,java.io.IOException{
       tagsOutput.close();
   }
            
}
