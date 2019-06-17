/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author M Luisa Díez Platas
 */
public class PleiadesData {
    public String name;
    public String description=""; 
    public String plename;
    public String lat;
    public String longd;
    public PleiadesData(){name=" ";description=" ";plename=" ";lat="0";longd="0"; }
    public PleiadesData(String _name,String _description,String _plename, String _lat, String _lon){
        name=_name;
        description=_description;
        plename=_plename;
        lat=_lat;
        longd=_lon;
    }
    
}
