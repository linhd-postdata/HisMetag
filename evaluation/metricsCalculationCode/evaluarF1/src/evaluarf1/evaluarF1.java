/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluarf1;
import java.io.*;
import java.util.HashMap;
/**
 *
 * @author Mª Luisa Díez Platas
 */
public class evaluarF1 {

   
    static ListEvaluationItem handTagged;
    static ListEvaluationItem automaticTagged;
    static ListEvaluationItem handTaggedAtt;
    static ListEvaluationItem automaticTaggedAtt;
        static ListEvaluationItem handTaggedCent;
    static ListEvaluationItem automaticTaggedCent;
     static ListResultItem listResult=new ListResultItem();
     static double totalHand=0.0;
     static double totalAuto=0.0;
     static double totalChecked=0.0;
     static ListResultItem listResultAtt=new ListResultItem();
     static double totalHandAtt=0.0;
     static double totalAutoAtt=0.0;
     static double totalCheckedAtt=0.0;
   static ListResultItem listResultCent=new ListResultItem();
       static double totalHandCent=0.0;
     static double totalAutoCent=0.0;
     static double totalCheckedCent=0.0;
     static Output outputdocs;
     static Output outputdocsMacroAverage;
     static HashMap<String,ListResultItemDocs> docsResult=new HashMap<String,ListResultItemDocs>();
     static ListResultItem listResultMacro=new ListResultItem();
    
    public static String[] burbujaPalabras (String lista_palabras[]){
        boolean ordenado=false;
        int cuentaIntercambios=0;
        //Usamos un bucle anidado, saldra cuando este ordenado el array
        while(!ordenado){
            for(int i=0;i<lista_palabras.length-1;i++){
                if (lista_palabras[i].compareToIgnoreCase(lista_palabras[i+1])>0){
                    //Intercambiamos valores
                    String aux=lista_palabras[i];
                    lista_palabras[i]=lista_palabras[i+1];
                    lista_palabras[i+1]=aux;
                    //indicamos que hay un cambio
                    cuentaIntercambios++;
                }
            }
            //Si no hay intercambios, es que esta ordenado.
            if (cuentaIntercambios==0){
                ordenado=true;
            }
            //Inicializamos la variable de nuevo para que empiece a contar de nuevo
            cuentaIntercambios=0;
        }
        return lista_palabras;
    }
    
    /**
     * 
     * @param tag
     * @return matched taggs in the category
     */
    static double totalMatch(String tag){
        double  match=0.0;
        for (int i=0; i<automaticTagged.totalTags();i++){
            
            EvaluationItem item=automaticTagged.get(i);
            if (item.tag.equals(tag)){
                
            if (handTagged.hasItemTotalMatch(item,tag)){
                
                match++;
            }}
        }
        return match;
    }
    
    /**
     * 
     * @return matched taggs in the century or period
     */
     static double totalMatchCentury(){
        double  match=0.0;
        for (int i=0; i<automaticTaggedCent.list.size();i++){
            EvaluationItem item=automaticTaggedCent.get(i);
            if (handTaggedCent.hasItemTotalMatch(item)){
                
                match++;
            }
        }
        return match;
    }
     
     /**
      * 
      * @param tag
      * @return matched tag attributes
      */
    static  double  totalMatchAttr(String tag){
         double  match=0.0;
        for (int i=0; i<automaticTagged.totalTags();i++){
            EvaluationItem item=automaticTagged.get(i);
            if (item.tag.equals(tag) &&  !(item.attrib.equals("")))
            if (handTagged.hasItemTotalAttributeMatch(item)){
                
                match++;
            }
        }
        return match;
    }
    
    /**
     * 
     * @param tag
     * totals by tagges and documents
     * @throws IOException 
     */
         
    static void calculateTotals(String tag,  String name) throws IOException{
         double total=automaticTagged.totalTags(tag);
         double totalhand=handTagged.totalTags(tag);
         
         double match=totalMatch(tag);
         
         listResult.putTotal(tag, totalhand);
         listResult.putTotalAutomatic(tag, total);
         listResult.putTotalChecked(tag, match);
         totalAuto+=total;
         totalHand+=totalhand;
         totalChecked+=match;
         double pre=(total>0)?match/total:0;
         double rec=(totalhand>0)?match/totalhand:0;
         double F1=(pre==0 && rec==0)?0:2*(pre*rec/(pre+rec));
         
         ResultItemDocs rid=new ResultItemDocs(name,total,totalhand,match,pre,rec,F1);
         docsResult.get(tag).put(rid );
         
         outputdocs.write(name+";"+tag+";"+pre+";"+rec+";"+F1+";"+total+";"+totalhand+";"+match+"\n");
        
    }
    
    /**
     * totals  by attributes of the tagges
     * @param tag 
     */
    static void calculateTotalsAtt(String tag)  {
       
         double total=automaticTagged.totalTagsAtt(tag);
         double totalhand=handTagged.totalTagsAtt(tag);
         System.out.println("TOtalllllll"+total);
         double match=totalMatchAttr(tag);
         listResultAtt.putTotal(tag, totalhand);
         listResultAtt.putTotalAutomatic(tag, total);
         listResultAtt.putTotalChecked(tag, match);
         totalAutoAtt+=total;
         totalHandAtt+=totalhand;
         totalCheckedAtt+=match;
         
    }
    
    /**
     * totals by century or period
     * @param century 
     */
    
     static void calculateTotalsCent(String century) {
         double total=automaticTaggedCent.list.size();
         double totalhand=handTaggedCent.list.size();
         double match=totalMatchCentury();
         
         totalAutoCent+=total;
         totalHandCent+=totalhand;
         totalCheckedCent+=match;
         
    }
     
     /**
      * micro-average: precision, recall and F1 by centuuries  or periods
      * @param century
      * @param file
      * @throws IOException 
      */
     static void calculateMetricsCent(String century, Output file)  throws IOException{
         double precision=totalCheckedCent/totalAutoCent;
         double recall=totalCheckedCent/totalHandCent;
         double F1=2*((precision*recall)/(precision+recall));
         
         file.write(century, String.valueOf(precision), String.valueOf(recall), String.valueOf(F1));
         
         totalAutoCent=0.0;
         totalHandCent=0.0;
         totalCheckedCent=0.0;
           
         
     }
     
     
     /**
      * calculation of precision by tagges
      * @param tag
      * @return 
      */
    static double precision(String tag){
        double match=listResult.getTotalChecked(tag);
        double total=listResult.getTotalAutomatic(tag);
          double matchAtt=listResultAtt.getTotalChecked(tag);
        double totalAtt=listResultAtt.getTotalAutomatic(tag);
      
        listResult.putPrecision(tag, match/total);
        listResultAtt.putPrecision(tag, matchAtt/totalAtt);
       
        return match/total;
    }
    
    /**
     * calculation of recall by tagges
     * @param tag
     * @return 
     */
    static public double recall(String tag){
        double match=listResult.getTotalChecked(tag);
        double total=listResult.getTotal(tag);
        double matchAtt=listResultAtt.getTotalChecked(tag);
        double totalAtt=listResultAtt.getTotal(tag);
       
        listResult.putRecall(tag, match/total);
        listResultAtt.putRecall(tag, matchAtt/totalAtt);
           
        return match/total;
    }
    
    /**
     * Calculation of F1 by tagges
     * @param tag
     * @return 
     */
    static public double F1(String tag){
        double  precision=listResult.getPrecision(tag);
        double recall=listResult.getRecall(tag);
         double  precisionAtt=listResultAtt.getPrecision(tag);
        double recallAtt=listResultAtt.getRecall(tag);
        listResult.putF1(tag, 2*(precision*recall/(precision+recall)));
        listResultAtt.putF1(tag, 2*(precisionAtt*recallAtt/(precisionAtt+recallAtt)));
        return 2*(precision*recall/(precision+recall));
    }
    
    /**
     * calculation of the overall precision by means of micro average
     * @return 
     */
    static double overallPrecision(){
        
        return totalChecked/totalAuto;
    }
    
    /**
     * calculation of the overall recall by means of micro average
     * @return 
     */
    static public double overallRecall(){
       return totalChecked/totalHand;
    }
    
    /**
     * calculation of the overall F1 by means of micro average
     * @return 
     */
    static public double overallF1(){
        double  precision=overallPrecision();
        double recall=overallRecall();
        
        return 2*(precision*recall/(precision+recall));
    }
    
    /**
     * calculation of the overall precision by means of micro average by attributes
     * @return 
     */
    static double overallPrecisionAtt(){
        
        return totalCheckedAtt/totalAutoAtt;
    }
    
    /**
     * calculation of the overall recall by means of micro average  by attributes 
     * @return 
     */
    static public double overallRecallAtt(){
       return totalCheckedAtt/totalHandAtt;
    }
    
    /**
     * calculation of the overall F1 by means of micro average  by attributes
     * @return 
     */
    static public double overallF1Att(){
        double  precision=overallPrecisionAtt();
        double recall=overallRecallAtt();
        
        return 2*(precision*recall/(precision+recall));
    }
    
    /**
     * calculation of the overall precision by means of micro average  by centuries or periods
     * @return 
     */
    static double centuriesPrecision(){
        
        return totalChecked/totalAuto;
    }
    
    /**
     * calculation of the overall recall by means of micro average  by centuries  or  periods
     * @return 
     */
    
    static public double centuriesRecall(){
       return totalChecked/totalHand;
    }
    
    /**
     * calculation of the overall F1 by means of micro average  by centuries  or  periods
     * @return 
     */
    static public double centuriesF1(){
        double  precision=overallPrecision();
        double recall=overallRecall();
        
        return 2*(precision*recall/(precision+recall));
    }
    
    /**
     * Docs classification by periods
     * @param period
     * @throws IOException 
     */
    static public void classificationByCenturies(String period)throws IOException{
       
       File dirhand = new File("manual"); 
             File dirauto=new File("automatico"); 
             
             FileReader fhand=null;
             FileReader fautomatic=null;
             
          String[] ficherosHand = dirhand.list();
          String[] ficherosAuto = dirauto.list();
          
         ficherosHand=burbujaPalabras(ficherosHand);
         ficherosAuto=burbujaPalabras(ficherosAuto);
           
         
          
          for (int x=1;x<9;x++){
              if (ficherosHand[x].contains(period)){
                  System.out.println("siglos"+ficherosHand[x]);
         
           fhand=new FileReader("manual/"+ficherosHand[x]);
          
            handTaggedCent=new ListEvaluationItem(fhand);
             
               
                fautomatic=new FileReader("automatico/"+ficherosAuto[x]);
                
               automaticTaggedCent=new ListEvaluationItem(fautomatic);
               
                 System.out.println(ficherosAuto[x]);
                 calculateTotalsCent(period);
              }
             
                        
    
        
          }
        
        
    }
    
    /**
     * Calculation by centuries  
     * @param file
     * @throws IOException 
     */
    static public void calculateByCenturies(Output file)throws IOException{
        classificationByCenturies("12");
        calculateMetricsCent("Siglo XII",file);
        
        classificationByCenturies("13");
            calculateMetricsCent("Siglo XIII",file);
        classificationByCenturies("14");
           calculateMetricsCent("Siglo XIV",file);
        classificationByCenturies("16");
           calculateMetricsCent("Siglo XVI",file);
        
        
    }
    
    /**
     * Calculation by years period
     * @param file
     * @throws IOException 
     */
       static public void calculateByYearsPeriod(Output file)throws IOException{
        classificationByCenturies("PER1");
        calculateMetricsCent("1150-1200",file);
        
        classificationByCenturies("PER2");
            calculateMetricsCent("1200-1250",file);
        classificationByCenturies("PER3");
           calculateMetricsCent("1250-1300",file);
        classificationByCenturies("PER4");
           calculateMetricsCent("1300-1350",file);
           classificationByCenturies("PER5");
           calculateMetricsCent("1350-1370",file);
           classificationByCenturies("PER6");
           calculateMetricsCent("1400-1500",file);
           classificationByCenturies("PER7");
           calculateMetricsCent("1501-",file);
        
        
    }
       
    /**
     * calculation by tagges
     * @param name
     * @throws IOException 
     */
    static public void calculateByTags(String  name)  throws IOException {
        
        System.out.println("EL NOMB RE"+name);
      calculateTotals("placeName",name);
      calculateTotals("persName",name);
      calculateTotals("orgName",name);
      calculateTotals("roleName",name);
  
      
    }
    
    /**
     * calculation  by attributes
     */
     static public void calculateByAttr(){
        
      calculateTotalsAtt("persName");
    
      calculateTotalsAtt("roleName");
  
    }
     
     /**
      * calculation of tagges metrics
      */
     static public void calculateMetrics(){
         precision("placeName");
          recall("placeName");
          F1("placeName");
         precision("persName");
          recall("persName");
          F1("persName");
           precision("orgName");
          recall("orgName");
          F1("orgName");
           precision("roleName");
          recall("roleName");
          F1("roleName");
       
         
     }
     
     /**
      * output by tagges
      * @throws IOException 
      */
    static public void taggedOutput()  throws IOException{
        Output output=new  Output("ResultsByTags.csv");
          
        output.header();
        output.writeTag("placeName");
        output.writeTag("persName");
        output.writeTag("orgName");
        output.writeTag("roleName");
       
        output.write("overall", String.valueOf(overallPrecision()), String.valueOf(overallRecall()), String.valueOf(overallF1()));
        
        output.close();
    }
    
    /**
     * outputs by  attributes
     * @throws IOException 
     */
    static public void attributeOutput()throws IOException{
        Output outputAttr=new Output("ResultsForAttributes.csv");
        outputAttr.header();
        
        outputAttr.writeTagAtt("persName");
       
        outputAttr.writeTagAtt("roleName");
       
        outputAttr.write("overall", String.valueOf(overallPrecisionAtt()), String.valueOf(overallRecallAtt()), String.valueOf(overallF1Att()));
        outputAttr.close();
    }
    
    /**
     * macro-average  by tagges
     * @param tag 
     */
    static public void totalMacroAverage(String tag){
        
        ListResultItemDocs lird=docsResult.get(tag);
    
        double  precisionMacro=0.0;
        double recallMacro=0.0;
        double F1Macro=0.0;
        double size=lird.resultDocs.size();
        for (int i=0; i<lird.resultDocs.size(); i++){
            ResultItemDocs rid=lird.get(i);
            precisionMacro+=rid.precision;
            recallMacro+=rid.recall;
        }
        precisionMacro=precisionMacro/size;
        recallMacro=recallMacro/size;
        F1Macro=2*(precisionMacro*recallMacro/(precisionMacro+recallMacro));
        
        listResultMacro.putPrecision(tag, precisionMacro);
        listResultMacro.putRecall(tag, recallMacro);
        listResultMacro.putF1(tag, F1Macro);
    }
     
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
             File dirhand = new File(args[1]); 
             File dirauto=new File(args[0]); 
             
             FileReader fhand=null;
             FileReader fautomatic=null;
             
          String[] ficherosHand = dirhand.list();
          String[] ficherosAuto = dirauto.list();
          
         ficherosHand=burbujaPalabras(ficherosHand);
         ficherosAuto=burbujaPalabras(ficherosAuto);
         /* tagges results by docs */
         docsResult.put("placeName", new ListResultItemDocs());
         docsResult.put("persName", new ListResultItemDocs());
         docsResult.put("orgName", new ListResultItemDocs());
         docsResult.put("roleName", new ListResultItemDocs());
         
           
         outputdocs=new  Output("ResultsByDocs.csv");
        outputdocs.headerDoc();
          
          for (int x=1;x<10;x++){
         
           fhand=new FileReader(args[1]+"/"+ficherosHand[x]);
          
            handTagged=new ListEvaluationItem(fhand);
              handTaggedAtt=new ListEvaluationItem(fhand);  
               
                fautomatic=new FileReader(args[0]+"/"+ficherosAuto[x]);
                
               automaticTagged=new ListEvaluationItem(fautomatic);
                automaticTaggedAtt=new ListEvaluationItem(fautomatic);
                 System.out.println(ficherosAuto[x]);
             
                 calculateByTags(ficherosHand[x]);
                 calculateByAttr();       
    
        
          }
          outputdocs.close();
          
          outputdocsMacroAverage=new  Output("ResultsByDocsMacroAverage.csv");
          outputdocsMacroAverage.header();
          totalMacroAverage("placeName");
          totalMacroAverage("persName");
          totalMacroAverage("orgName");
          totalMacroAverage("roleName");
          
          outputdocsMacroAverage.writeTagMacro("placeName");
          outputdocsMacroAverage.writeTagMacro("persName");
          outputdocsMacroAverage.writeTagMacro("orgName");
          outputdocsMacroAverage.writeTagMacro("roleName");
          outputdocsMacroAverage.close();
          
         calculateMetrics();
         taggedOutput();
         attributeOutput();
         Output outputCent=new Output("ResultsForCenturies.csv");
          outputCent.headerCent();
        
         calculateByCenturies(outputCent);
         outputCent.close();
         Output outputYP=new Output("ResultsForYearsPeriod.csv");
          outputYP.headerCent();
         calculateByYearsPeriod(outputYP);
         outputYP.close();
        listResult.write();
        System.out.println("********************");
        listResultAtt.write();
        listResultCent.write();
      
        
        System.out.println("los toatl  "+totalHand+" "+totalAuto+" "+totalChecked);
          System.out.println("los toatlat "+totalHandAtt+" "+totalAutoAtt+" "+totalCheckedAtt);
        
                
    }
    
}
