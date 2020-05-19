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
public class ResultItem {
    double total;
    double totalAutomatic;
    double totalChecked;
    double precision;
    double  recall;
    double  F1;
    
    ResultItem(){
      total=0.0;
    totalChecked=0.0;
    totalAutomatic=0.0;
    precision=0.0;
    recall=0.0;
    F1=0.0;  
    }
    
    ResultItem(double t, double ta, double  tc,double p, double r, double f){
    total=t;
    totalAutomatic=ta;
    totalChecked=tc;
    precision=p;
    recall=r;
    F1=f;
    }
    
    public void write(){
        System.out.println("total-"+total);
        System.out.println("totalAutomatic-"+totalAutomatic);
        System.out.println("totalChecked-"+totalChecked);
        System.out.println("precision-"+precision);
        System.out.println("recall-"+recall);
        System.out.println("F1-"+F1);
    }
}
