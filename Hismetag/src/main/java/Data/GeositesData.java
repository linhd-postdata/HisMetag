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
public class GeositesData {
    public String name;
    public String alternativename=""; 
    public String geoname;
    public String lat;
    public String lon;
    public GeositesData(){name=" ";alternativename=" ";geoname=" ";lat="0";lon="0"; }
    public GeositesData(String _name,String _alternativename,String _geoname, String _lat, String _lon){
        name=_name;
        alternativename=_alternativename;
        geoname=_geoname;
        lat=_lat;
        lon=_lon;
    }
    
}
