/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;
import java.io.*;
import WordProcessing.*;

import Data.GeositesData;
import Data.PleiadesData;
import DataStructures.*;
import Recognition.Terms;
import Recognition.TypesTerms;
import java.util.*;

/**
 *
 * @author M Luisa Díez Platas
 */
public class Output {
    File file;
    //static public BufferedWriter output;
    static public String output="";
    static public String outputCSV="";
    static public ArrayList<JsonClass> JsonList= new ArrayList<JsonClass>();
    static public JsonClass jsonobject;
    File dataFile;
    File dataFileP;
    File dataFileM;
    File dataFileR;
    File dataFilePR;
    File dataFileO;
    static public String dataOut="";
    static public FileWriter outputData;
    static public FileWriter outputPlaces;
    static public FileWriter outputRoles;
    static public FileWriter outputPersNames;
    static public FileWriter outputOrgs;
    static public FileWriter outputMedieval;
    
    static public ArrayList<RoleTreeNode> salida=new ArrayList<RoleTreeNode>();
    
    public Output(){
        JsonList= new ArrayList<JsonClass>();
        output="";
    }
   public Output(String name, String data,String place,String rol, String pers, String org,String medieval) throws java.io.FileNotFoundException, java.io.IOException{
      
       ArrayList<JsonClass> JsonList = new ArrayList<JsonClass>();
      // System.out.println("que pasa en los archivos despues "+name);
       file=new File(name);
     // System.out.println("que pasa en los archivos despues"+file);
       if (file.exists()){
           
           
//            output = new BufferedWriter(new OutputStreamWriter(
           //         new FileOutputStream(file),"UTF8"));
      // output= new FileWriter(file);
       }else{
           
          // System.out.println("existe el archivo");
           FileOutputStream f=new FileOutputStream(file);
          //  System.out.println("existe el archivo2");
            OutputStreamWriter o=new OutputStreamWriter(f,"UTF8");
           // System.out.println("existe el archivo3");
          //     output = new BufferedWriter(o);
          // output= new FileWriter(file);
       }
       
        
       dataFile=new File(data);
       if (dataFile.exists()){
       outputData= new FileWriter(dataFile);
       }else{
           outputData= new FileWriter(dataFile);
       }
       
      // System.out.println("nose si paso por aqui");
       dataFileP=new File(place);
       //System.out.println("SE CONSTRIYE PLACE"+place);
       if (dataFileP.exists()){
    	   outputPlaces= new FileWriter(dataFileP);
       }else{
           outputPlaces= new FileWriter(dataFileP);
       }
       
       //System.out.println("nose si paso por aqui1");
       dataFileR=new File(rol);
       if (dataFileR.exists()){
    	   outputRoles= new FileWriter(dataFileR);
       }else{
           outputRoles= new FileWriter(dataFileR);
       }
       
       //System.out.println("nose si paso por aqui2");
       dataFilePR=new File(pers);
       if (dataFilePR.exists()){
    	   outputPersNames= new FileWriter(dataFilePR);
       }else{
           outputPersNames= new FileWriter(dataFilePR);
       }
       
       //System.out.println("nose si paso por aqui3");
       dataFileO=new File(org);
       if (dataFileO.exists()){
    	   outputOrgs= new FileWriter(dataFileO);
       }else{
           outputOrgs= new FileWriter(dataFileO);
       }
       
       //System.out.println("nose si paso por aqui4" + medieval);
       dataFileM=new File(medieval);
       
       if (dataFileM.exists()){
          outputMedieval= new FileWriter(dataFileM);
       }else{
           outputMedieval= new FileWriter(dataFileM);
       }
       
       
   } 
   
  public static void writeSalida() throws java.io.FileNotFoundException,java.io.IOException{
	
	   
	  for (int i=0;i<salida.size();i++){
           // System.out.println("salida "+salida.get(i).root.string);
         
		// output.write(salida.get(i).inOrden());
                 output+=salida.get(i).inOrden();
                 
	  }
	 
  }
   public static void writeHeader()throws java.io.FileNotFoundException,java.io.IOException{
	
        //output.write(dataOut);
        // output+=dataOut;
        
	
	/*output.write("<TEI xmlns='http://www.tei-c.org/ns/1.0'>");
	
	output.write("<teiHeader>"+"<fileDesc>"+"<titleStmt>"+"<title>LINHD: Mediaeval Iberia</title>"+"<publicationStmt>"+"<p>21-10-2016</p>"+"</publicationStmt>");
	output.write("<sourceDesc>"+"<p>"+"<link target=''/>"+"</p>"+"</sourceDesc>"+"</titleStmt>"+"</fileDesc>"+"</teiHeader>");
	output.write("\n<text>\n"+"<body>\n"+"<div>\n"+"<p>\n");
	*/
       output+="<xml>"+"\n";
       
   }
   
    public static void writeTEIHeader()throws java.io.FileNotFoundException,java.io.IOException{
	
        /*output.write("<TEI xmlns='http://www.tei-c.org/ns/1.0'>");
	
	output.write("<teiHeader>"+"<fileDesc>"+"<titleStmt>"+"<title>LINHD: Mediaeval Iberia</title>"+"<publicationStmt>"+"<p>21-10-2016</p>"+"</publicationStmt>");
	output.write("<sourceDesc>"+"<p>"+"<link target=''/>"+"</p>"+"</sourceDesc>"+"</titleStmt>"+"</fileDesc>"+"</teiHeader>");
	output.write("\n<text>\n"+"<body>\n"+"<div>\n"+"<p>\n");*/
	//output.write("<xml version='1.0' encoding='UTF-8'>");
        output+="<xml version='1.0' encoding='UTF-8'>";
       // output.write("<xml>"+'\n');
       
   }
   
        public static void writeTEIFooter()throws java.io.FileNotFoundException,java.io.IOException{
	
	
	/*output.write("\n</p>\n"+"</div>\n"+"</body>\n"+"</text>\n"+"</TEI>");
	output.write('\n'+"</xml>");*/
           // output.write("</xml>");
            output+="</xml>";
           
       
   }
    
   
    public static void writeFooter()throws java.io.FileNotFoundException,java.io.IOException{
	
	
	//output.write("\n</p>\n"+"</div>\n"+"</body>\n"+"</text>\n"+"</TEI>");
	//output.write('\n'+"</xml>");
        output+="</xml>";
       
   }
    
    public static void writeDataHeader()throws java.io.FileNotFoundException,java.io.IOException{
        outputData.write("TERM;POSITION;WORD;TYPE;MODE;URI;CURRENT_TERM;AMBIGUOUS;ANOTHER_MEANING;ANOTHER_MEANING_URI;DESCRIPTION;GAZETTEER;VERIFICATION;GEO\n");
        outputPlaces.write("TERM;POSITION;WORD;TYPE;MODE;URI;CURRENT_TERM;AMBIGUOUS;ANOTHER_MEANING;ANOTHER_MEANING_URI;DESCRIPTION;GAZETTEER;VERIFICATION;GEO\n");
        outputPersNames.write("TERM;POSITION;WORD;TYPE;MODE;URI;CURRENT_TERM;AMBIGUOUS;ANOTHER_MEANING;ANOTHER_MEANING_URI;DESCRIPTION;GAZETTEER;VERIFICATION;GEO\n");
    outputRoles.write("TERM;POSITION;WORD;TYPE;MODE;URI;CURRENT_TERM;AMBIGUOUS;ANOTHER_MEANING;ANOTHER_MEANING_URI;DESCRIPTION;GAZETTEER;VERIFICATION;GEO\n");
    outputOrgs.write("TERM;POSITION;WORD;TYPE;MODE;URI;CURRENT_TERM;AMBIGUOUS;ANOTHER_MEANING;ANOTHER_MEANING_URI;DESCRIPTION;GAZETTEER;VERIFICATION;GEO\n");
    }
    
    public static void writeDataOthersHeader() throws java.io.FileNotFoundException, java.io.IOException{
      outputPlaces.write("TERM;POSITION;WORD;TYPE;MODE;URI;CURRENT_TERM;AMBIGUOUS;ANOTHER_MEANING;ANOTHER_MEANING_URI;DESCRIPTION;GAZETTEER;VERIFICATION;GEO\n");
      
    }
    public static void writeMedievalHeader()throws java.io.FileNotFoundException,java.io.IOException{
        outputMedieval.write("TERM;CURRENT-NAME;PLEIADES-URI;GEONAMES-URI;DESCRIPTION;TYPE-TERM;DOCUMENT;GAZETTEER-PROVENNANCE\n");
            
    }
    
    public static void writeName(String word, Recognition.Terms info,Recognition.TypesTerms type) throws java.io.FileNotFoundException,java.io.IOException{
    	
       
        String tipo="found";
    	String role="";
        dataOut+=word;
        //output.write("hola    "+dataOut);
        output+=dataOut;
        if (info.toString()=="RNN"){
                
                
            tipo="nickName";
           // output.write("<persName type='nickName'>"+word+"</persName>");
            output+="<persName type='nickName'>"+word+"</persName>";
        }
        else if(info.toString()=="PN") tipo="found";
      	else if(info.toString()=="APN") tipo="ambiguos";
      	else if(info.toString()=="PPN") tipo="proposed";
      	else if(info.toString()=="STN"){
      		tipo="found";
      		role="<roleName>Saint</roleName>";
      	}
      	else if(info.toString()=="ASTN"){
      		tipo="ambiguos";
      		role="<roleName>Saint</roleName>";
      	}
      	else if(info.toString()=="PSTN"){
      		tipo="proposed";
      		role="<roleName>Saint</roleName>";
      	}
      	else tipo="unknown";
    	//output.write("<persName type='"+tipo+"' >"+role+word+"</persName>");
        output+="<persName type='"+tipo+"' >"+role+word+"</persName>";
    	//Lexer.numWord++;
    }
    
      public static void writeName(String word, Recognition.Terms info,Recognition.TypesTerms type, String nameType) throws java.io.FileNotFoundException,java.io.IOException{
    	if (nameType.equals("person")){
            //output.write("<persName >"+word+"</persName>");
            output+="<persName >"+word+"</persName>";
        }else{
    	//output.write("<persName type='"+nameType+"' >"+word+"</persName>");
            output+="<persName type='"+nameType+"' >"+word+"</persName>";
        }
    	//Lexer.numWord++;
    }
    
    public static void writePlaceName(String word,String info,Recognition.Terms type) throws java.io.FileNotFoundException,java.io.IOException{
      	String tipo="found";
      	if(type.toString()=="PLN") tipo="found";
      	else if(type.toString()=="APLN") tipo="ambiguos";
      	else if(type.toString()=="PPLN") tipo="proposed";
      	else tipo="unknown";
    	String geo="0 0";
        
       // System.out.println("de sevilla"+word);
      
    	if(info.contains("pleiades")){
            
           
            
            //System.out.println("Contiene en pleiades "+info);
    		PleiadesData d=Data.PleiadesPlaceNamesTable.get(word);
                
                if (d==null) geo="0 0";
                else geo=d.lat+" "+d.longd;
                
                
                
    	}
        else if (info.contains("geonames")){
            
            
    		GeositesData d=Data.GeositesPlaceNamesTable.get(word);
                
    		//geo=d.lat+" "+d.lon;
                geo="0 0";
             
                
    	}else{
            info="";
            //System.out.println("este es "+word);
            
        }
        //output.write("<placeName  type='"+tipo+"'><ptr target="+'"'+info+'"'+"/><geo>"+geo+"</geo>"+word+"</placeName>");
       output+="<placeName  type='"+tipo+"'><ptr target="+'"'+info+'"'+"/><geo>"+geo+"</geo>"+word+"</placeName>";
// System.out.println("de sevillllallla"+word);
        //Lexer.numWord++;
    }
    
     public static void writeRoleName(String word,Recognition.Terms type, String rol) throws java.io.FileNotFoundException,java.io.IOException{
      	if (rol.equals("H")){
             //output.write("<roleName  type='honorific'>"+word+"</roleName>");
            output+="<roleName  type='honorific'>"+word+"</roleName>";
        }else if (rol.equals("F")){
             //output.write("<roleName  type='family'>"+word+"</roleName>");
            output+="<roleName  type='family'>"+word+"</roleName>";
        } else{
        // output.write("<roleName  >"+word+"</roleName>");
             output+= "<roleName  >"+word+"</roleName>";
        }
        //Lexer.numWord++;
    }
     
       public static void writeRoleName(RoleTreeNode rtn) throws java.io.FileNotFoundException,java.io.IOException{
        RoleTreeNode rnode=rtn;
        BagData bgd=rtn.root;
        String nueva=bgd.string.replace("\n", "");
      /*  outputRoles.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+"\n");
        outputData.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+"\n");
        outputCSV+=nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+";"+" "+"\n";
       */
        
       JsonList.add(new JsonClass(nueva,String.valueOf(bgd.position),String.valueOf(bgd.nword),bgd.type.toString(),bgd.subtype.toString()," "," "," "," "," "," "," "," "," "));
        //System.out.println("estoy escribienod en el
    }
     
     
     public static void write(RoleTreeNode rtn)throws java.io.FileNotFoundException,java.io.IOException{
    	salida.add(rtn);
        
        
        //System.out.println ("estamos vendo que se envía a la salida "+rtn.root.position+"RRR"+rtn.root.string+" "+rtn.root.nword+" "+Lexer.context);
        
        //System.out.println("otra vez");
    	//output.write(dataOut);
        //output.write(salida);
    }
    
    public static void write(String string)throws java.io.FileNotFoundException,java.io.IOException{
    	//System.out.println("lasalidadwelas clasese"+string+"d");
        //System.out.println("el tex "+string+"aaa"+Lexer.context);
        dataOut+=string;
       
        
     //   System.out.println ("estamos vendo que se envía a la salida "+dataOut+"RRR");
        if(!string.trim().equals(".")){
    	//if(string.trim().length()>0) //Lexer.numWord++;  
    	}
        //System.out.println("otra vez");
    	//output.write(dataOut);
        //output.write(salida);
    }
    
   
    
    public static void write(String string, String info, String type ) throws java.io.FileNotFoundException,java.io.IOException{
        dataOut+=string;
       //System.out.println("hola    "+dataOut);
      //salida+=string;
      if (info=="CN"){
          //output.write("<name  type='common name' >"+string+"</name>");
          output+="<name  type='common name' >"+string+"</name>";
      }else{
    	  //output.write("<name  type='unkown' >"+string+"</name>");
          output+="<name  type='unknown' >"+string+"</name>";
      }
      //Lexer.numWord++;
    }
    
    
    public static void writePlaceData(RoleTreeNode rtn)throws java.io.FileNotFoundException,java.io.IOException{
        RoleTreeNode rnode=rtn;
        BagData bgd=rtn.root;
        //System.out.println("el bagdatatattta"+rtn.root.string+bgd.type);
        if (bgd.type==Terms.PLN || bgd.type==Terms.APLN){
        	
        String geo="0:0";
        String uri;
        String ambi="NO";
        if (bgd.type==Terms.ACN || bgd.type==Terms.APLN || bgd.type==Terms.APN || bgd.type==Terms.ADD){
            ambi="SI";
        }
        
       // System.out.println("writedata"+bgd.infoPlace);
       if (bgd.infoPlace!=null){
        if (rnode.root.infoPlace.uri.contains("pleiades")){
                PleiadesData d=Data.PleiadesPlaceNamesTable.get(WordTransformations.capitalize(rnode.root.string));
                if (d==null) geo="0 0"; else geo=d.lat+":"+d.longd;
            	}
                else if (rnode.root.infoPlace.uri.contains("geonames")){
                	//System.out.println("los geonames para eso");
            		GeositesData d=Data.GeositesPlaceNamesTable.get(WordTransformations.capitalize(rnode.root.string));
            		if (d==null) geo="0 0"; else geo=d.lat+":"+d.lon;
            	}else{
                    uri="''";
                    geo="''";
                        
                        }
       }
                    String verif="NO";
                    
                   // System.out.println("como esta el ambiente"+ambi);
                   
                        if (bgd.subtype!=TypesTerms.FT) verif="SI";
       String nueva=bgd.string.replace("\n", "");
       /* outputPlaces.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+bgd.infoPlace.uri+";"+bgd.infoPlace.current+";"+ambi+";"+" "+";"+" "+";"+bgd.infoPlace.description+";"+bgd.infoPlace.gazetteer+";"+verif+";"+geo+"\n");
        outputData.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+bgd.infoPlace.uri+";"+bgd.infoPlace.current+";"+ambi+";"+" "+";"+" "+";"+bgd.infoPlace.description+";"+bgd.infoPlace.gazetteer+";"+verif+";"+geo+"\n");
        outputCSV+=nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+bgd.infoPlace.uri+";"+bgd.infoPlace.current+";"+ambi+";"+" "+";"+" "+";"+bgd.infoPlace.description+";"+bgd.infoPlace.gazetteer+";"+verif+";"+geo+"\n";
       */        JsonList.add(new JsonClass(nueva,String.valueOf(bgd.position),String.valueOf(bgd.nword),bgd.type.toString(),bgd.subtype.toString(),bgd.infoPlace.uri.toString(),bgd.infoPlace.current.toString(),ambi," "," ",bgd.infoPlace.description.toString(),bgd.infoPlace.gazetteer.toString(),verif,geo));

        
        
        }   
       
        //System.out.println("estoy escribienod en el archivo de datos"+rtn.root.string); 
    }
  
     public static void writeOrgData(RoleTreeNode rtn)throws java.io.FileNotFoundException,java.io.IOException{
        RoleTreeNode rnode=rtn;
        BagData bgd=rtn.root;
       // System.out.println("el bagdatatattta"+rtn.root.string+bgd.type);
        
        String ambi=" ";
        
        String verif="NO";
                    
                 //   System.out.println("como esta el ambiente"+ambi);
                   
        if (bgd.subtype!=TypesTerms.FT) verif="SI";
       String nueva=bgd.string.replace("\n", "");
       /* outputOrgs.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+ambi+";"+" "+";"+" "+";"+" "+";"+" "+";"+verif+";"+" "+"\n");
         outputData.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+ambi+";"+" "+";"+" "+";"+" "+";"+" "+";"+verif+";"+" "+"\n");
         outputCSV+=nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+ambi+";"+" "+";"+" "+";"+" "+";"+" "+";"+verif+";"+" "+"\n";
        */              JsonList.add(new JsonClass(nueva,String.valueOf(bgd.position),String.valueOf(bgd.nword),bgd.type.toString(),bgd.subtype.toString()," "," ",ambi," "," "," "," ",verif," "));

        //System.out.println("estoy escribienod en el archivo de datos"+rtn.root.string); 
    }
    
    public static void writeProperData(RoleTreeNode rtn)throws java.io.FileNotFoundException,java.io.IOException{
        RoleTreeNode rnode=rtn;
        BagData bgd=rtn.root;
       // System.out.println("el bagdatatattta"+rtn.root.string+bgd.type);
        
        String ambi="NO";
        if ( bgd.type==Terms.APN ){
            ambi="SI";
        }
        String verif="NO";
                    
                 //   System.out.println("como esta el ambiente"+ambi);
                   
        if (bgd.subtype!=TypesTerms.FT) verif="SI";
       String nueva=bgd.string.replace("\n", "");
      /*  outputPersNames.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+ambi+";"+" "+";"+" "+";"+" "+";"+" "+";"+verif+";"+" "+"\n");
         outputData.write(nueva+";"+bgd.position+";"+bgd.nword+";"+bgd.type+";"+bgd.subtype+";"+" "+";"+" "+";"+ambi+";"+" "+";"+" "+";"+" "+";"+" "+";"+verif+";"+" "+"\n");
          */            JsonList.add(new JsonClass(nueva,String.valueOf(bgd.position),String.valueOf(bgd.nword),bgd.type.toString(),bgd.subtype.toString()," "," ",ambi," "," "," "," ",verif," "));
       
        //System.out.println("estoy escribienod en el archivo de datos"+rtn.root.string); 
    }
  
    public static void writeData(String string,String position,String w,String term,String type,String uri,String current,String ambi,String other,String other_uri,String other_desc,String gaz,String verif) throws java.io.FileNotFoundException,java.io.IOException{
        
        
       /* outputData.write(string+";"+position+";"+w+";"+term+";"+type+";"+uri+";"+current+";"+ambi+";"+other+";"+other_uri+";"+other_desc+";"+gaz+";"+verif+"\n");
        outputCSV+=string+";"+position+";"+w+";"+term+";"+type+";"+uri+";"+current+";"+ambi+";"+other+";"+other_uri+";"+other_desc+";"+gaz+";"+verif+"\n";
      */  JsonList.add(new JsonClass(string,String.valueOf(position),String.valueOf(w),term,type,uri,current,ambi,other,other_uri,other_desc,gaz,verif," "));

        if (term=="PLN"||term=="PPLN"||term=="APLN"){
            
              	String geo="0:0";
            	if(uri.contains("pleiades")){
            		PleiadesData d=Data.PleiadesPlaceNamesTable.get(string);
            		if (d==null) geo="0 0"; else geo=d.lat+":"+d.longd;
            	}
                else if (uri.contains("geonames")){
            		GeositesData d=Data.GeositesPlaceNamesTable.get(string);
            		if (d==null) geo="0 0"; else geo=d.lat+":"+d.lon;
            	}else{
                    uri="''";
                    geo="''";
                        
                        }
               // outputPlaces.write(string+";"+position+";"+w+";"+term+";"+type+";"+uri+";"+verif+";"+geo+"\n");
            JsonList.add(new JsonClass(string,String.valueOf(position),String.valueOf(w),term,type,uri,current,ambi,other,other_uri,other_desc,gaz,verif," "));

            }
    }
    
    public static void close() throws java.io.FileNotFoundException,java.io.IOException{
       // output.close();
        outputData.close();
        outputPlaces.close();
        outputMedieval.close();
        outputPersNames.close();
        outputRoles.close();
        outputOrgs.close();
    }
    
   
}
