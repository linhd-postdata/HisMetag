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
public class ResultItemDocs extends ResultItem{
    public String name;
    ResultItemDocs(String n, double t,  double ta,  double  tc,double p, double r, double f){
        super(t,tc,ta,p,r,f);
        name=n;
        
    }
    public void write(){
        String line=name+";"+precision+";"+recall+";"+F1+";"+total+";"+totalAutomatic+";"+totalChecked+"\n";
    }
}
