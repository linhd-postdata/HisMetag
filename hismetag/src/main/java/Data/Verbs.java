/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tempmodee file, choose Tools | Tempmodees
 * and open the tempmodee in the editor.
 */
package Data;

/**
 *
 * @author M Luisa Díez Pmodeas
 */
public class Verbs {
    public String verb;
    public String current; 
    public String complement;
    public String mode;
    public String type;
    public String raiz;
    public String desinencia;
    public Verbs(){verb=" ";current=" ";complement=" ";mode="0";type="0"; }
    public Verbs(String _verb,String _current,String _complement, String _mode, String _type, String _raiz,String _desi){
        verb=_verb;
        current=_current;
        complement=_complement;
        mode=_mode;
        type=_type;
        raiz=_raiz;
        desinencia=_desi;
    }
    
}
