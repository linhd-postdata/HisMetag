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
public class FreelingData {
    public String name;
    public String currentname; 
    public String pleiadesname;
    public String geoname;
    public FreelingData(){name=" ";currentname=" ";geoname=" ";pleiadesname=" "; }
    public FreelingData(String _name,String _currentname,String _geoname, String _pleiadesname){
        name=_name;
        currentname=_currentname;
        geoname=_geoname;
        pleiadesname=_pleiadesname;
    }
    
}
