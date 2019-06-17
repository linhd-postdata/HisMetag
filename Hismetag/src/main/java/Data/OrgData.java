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
public class OrgData {
    public String name;
    public String currentname; 
    public String pleiadesname;
    public String geoname;
    public String type;
    public String document;
    public String description;
    public String provenance;
    public String typeg;
    public OrgData(){name=" ";currentname=" ";geoname=" ";pleiadesname=" "; type=" "; document=" ";provenance=" ";}
    public OrgData(String _name,String _currentname,String _pleiadesname,String _geoname, String _description,String _type, String _document, String _provenance,String _typeg){
        name=_name;
        currentname=_currentname;
        pleiadesname=_pleiadesname;
        geoname=_geoname;
        description=_description;
        type=_type;
        document=_document;
        provenance=_provenance;
        typeg=_typeg;
    }
    
}
