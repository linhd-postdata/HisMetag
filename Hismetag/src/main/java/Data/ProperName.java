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
public class ProperName {
    public String name;
    public String document=" ";
    public String provenance;
    public String type=" ";
    public ProperName(String _name,String _document,String _provenance){
        name=_name;
        document=_document;
        provenance=_provenance;
    }
     public ProperName(String _name,String _document,String _provenance, String _type){
        name=_name;
        document=_document;
        provenance=_provenance;
        type=_type;
    }
}
