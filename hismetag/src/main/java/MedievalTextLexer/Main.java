/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedievalTextLexer;

import ContextProcessing.ContextualList;
import Data.FreelingPlaceNamesTable;
import Data.NewProperNamesTable;
import Data.ProperName;
import IOModule.Input;
import IOModule.Output;

import IOModule.GenerateJson;

import java.io.*;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

import DataStructures.*;
import Recognition.*;
import StringInProcess.WordList;
import WordProcessing.Bigramms;
import WordProcessing.LevenshteinDistance;
import WordProcessing.WordTransformations;



import java.util.regex.*;



/**
 *
 * @author M Luisa Díez Platas
 */
public class Main {

    /**
     * @param args the command line arguments
     * 
     *
     */
	
	private String run_program( String name){
		 try{
                String inputfile=name;
		
		
        String newString=inputfile.replaceAll("\\s+", " ");
                WordList wordList=new WordList(inputfile);
                System.out.println("la lista tiene "+wordList.wordList.size());


        
         

         // Lectura del fichero
         
          
            File archivo = new File (this.getClass().getResource( "/freeling-data.txt" ).toURI());           
            Data.FreelingPlaceNamesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/reverse-rules.txt" ).toURI());           
            Data.ReverseRulesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/rules.txt" ).toURI());           
            Data.RulesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/geonames-data.txt" ).toURI());
            Data.GeositesPlaceNamesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/medieval-data.txt" ).toURI());  
            Data.MedievalPlaceNamesTable.createTable(archivo);
           
            archivo = new File (this.getClass().getResource( "/org.txt" ).toURI()); 
            Data.OrgCollectiveTable.createTable(archivo);
             
      
            // Data.MedievalNewPlaceNamesTable.createTable(resources);
            archivo = new File (this.getClass().getResource( "/pleiades-data.txt" ).toURI()); 
            Data.PleiadesPlaceNamesTable.createTable(archivo);

            String prueba=String.valueOf(Data.PleiadesPlaceNamesTable.pletable.size());
            archivo = new File (this.getClass().getResource( "/cleaning-data.txt" ).toURI());
            Data.StopWordsTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/deity.txt" ).toURI());
            Data.DeityTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/prepositions.txt" ).toURI());
            Data.PrepositionsTable.createTable(archivo);
              //System.out.println("uno esta aqui");
         
            archivo = new File (this.getClass().getResource( "/context-place-terms.txt" ).toURI());
            Data.ContextPlaceTerms.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/context-place-names.txt" ).toURI());
            Data.ContextPlaceNames.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/family-data.txt" ).toURI());
            Data.FamilyNamesTable.createTable(archivo);
           
            archivo = new File (this.getClass().getResource( "/apodos.txt" ).toURI());
            Data.NickNamesTable.createTable(archivo);
             
            archivo = new File (this.getClass().getResource( "/des.txt" ).toURI());
            Data.DesiTable.createTable(archivo);
            
           archivo = new File (this.getClass().getResource( "/verbos.txt" ).toURI());
            Data.VerbsTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/articles.txt" ).toURI());
            Data.ArticleTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/saint-names.txt" ).toURI());
            Data.NewSaintNamesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/mythological-place.txt" ).toURI());
            Data.MythologicalPlaceNameTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/common-names.txt" ).toURI());
            Data.CommonNamesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/proper-names.txt" ).toURI());
            Data.ProperNamesTable.createTable(archivo);
         
            archivo = new File (this.getClass().getResource( "/proper-names.txt" ).toURI());
            Data.NewProperNamesTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/posesivo.txt" ).toURI());
            Data.PosessiveTable.createTable(archivo);
            
            archivo = new File (this.getClass().getResource( "/apposition-prepositions.txt" ).toURI());
            Data.AppositionPrepositionsTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/authority-data.txt" ).toURI());
            Data.AuthorityNamesTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/saints-data.txt" ).toURI());
            Data.SaintsTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/geographical-data.txt" ).toURI());
            Data.GeographicNamesTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/building.txt" ).toURI());
            Data.BuildingsTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/previous-names.txt" ).toURI());
            Data.PreviousNamesTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/treatment-data.txt" ).toURI());
            Data.TreatmentsTable.createTable(archivo);

            archivo = new File (this.getClass().getResource( "/new-ambiguous-terms-found.txt" ).toURI());
            Data.AmbiguousTermsFoundTable.createTable(archivo);
           
             //System.out.println("cuatro esta aqui"+inputfile);
            String newfile=inputfile.substring(0, inputfile.length()-3)+"xml";
            newfile=name+".xml";
            
            String ninput="/"+inputfile;

         //System.out.println("el texto que leo es"+inputfile+" "+newfile);
         
            
        //    System.out.println("lalalla "+inputfile+"  "+name);
        /*   FileReader stream=new FileReader(newfile);
          */
          /*  String outputdir="archivos";
           String output=outputdir+'/'+name+".xml";

             String outputData=outputdir+'/'+name+"-AllTerms.csv"; 
             String outputDataP=outputdir+'/'+name+"-Placenames.csv"; 
             String outputDataR=outputdir+'/'+name+"-Roles.csv";
             String outputDataPN=outputdir+'/'+name+"-PersNames.csv";
             String outputDataO=outputdir+'/'+name+"-OrgNames.csv";
            
             //String outputDataM=resources+"dataFiles/medieval-gazeteer.csv";
            
            String outputDataM=outputdir+"medieval-gazeteer.csv";
            
             
             
             //new Output(output,outputData,outputDataP,outputDataR,outputDataPN,outputDataO,outputDataM); 
            
             */
             
          Output output = new Output();

             
      /*        BufferedReader buffer = new BufferedReader(stream);
           //System.out.println("el texto leido es "+buffer);*/
            /*String porra="Toledo\n";
             java.io.Reader pru=new java.io.StringReader(porra);*/
             Lexer lexer= new Lexer(inputfile, output);
           
             
         // Output.writeHeader(); 

           //  Output.writeDataHeader();
           /*  Output.writeMedievalHeader();*/
             //System.out.println("la lematizacion"+WordTransformations.lemmVerbs("dizen"));  
            
        Token token;
        while(!lexer.isFin()) {
            // Obtener el token analizado y mostrar su información
            token = lexer.yylex();

            }
            
        //IOModule.JsonClass objeto=new IOModule.JsonClass("dolores","3","2",Terms.APN.toString(),TypesTerms.FT.toString(),"http://www.com.es","","","","","la desxcerioc","Freeling","False","1.2");
          // IOModule.JsonClass objeto1=new IOModule.JsonClass("tela","3","2",Terms.APN.toString(),TypesTerms.FT.toString(),"http://www.com.es","","","","","la desxcerioc","Freeling","False","1.2");
           //ArrayList<IOModule.JsonClass> JsonList=new ArrayList<IOModule.JsonClass>();
                   
        //   ArrayList<IOModule.JsonClass> JsonList=Output.JsonList;       
           //Output.JsonList.add(objeto);
           //JsonList.add(objeto1);
           
            
            
            //Output.output.write(Output.dataOut);

           
          //  System.out.println("no se por qué no sale ");
          output.writeSalida();
         //   for (int i=0; i<Output.salida.size(); i++)
          
           // System.out.println("EL OUTPUT  "+Output.salida.get(i).root.string+ " "+Output.salida.get(i).root.type);
         //Output.writeFooter();
        /*  //   System.out.println("estoy aqui"+Lexer.fin);
          //   System.out.println("la salida");
             
             //System.out.println("la salida "+Output.salida.get(0).root.string);
//             System.out.println("el tamño de la salida "+Output.salida.length());
            
        
            //    Matcher encaja = patron.matcher(Output.salida);

       
      //String nueva = encaja.replaceAll(" ");
            //    String endOutput=encaja.replaceAll(" ");
        
             
      //     Output.output.write(endOutput);
           
           //System.out.println("ESCRIBO LA WORDBAG "+Lexer.wbag.tam());
           
           
         //  System.out.println("el final");

            Data.MedievalPlaceNamesTable.updateFile();
                    
			Data.AmbiguousTermsFoundTable.updateFile();
			
			Data.NewProperNamesTable.updateFile();
			Data.ProperNamesTable.updateFile();
			Data.MedievalNewPlaceNamesTable.updateFile();
                        
		//	System.out.println("es la definitiva");
                        
			Output.close();
		//	System.out.println("terminado");
        
           
           //System.out.println("despues de cleaning final");
        IOModule.ConvertirXML.csvSalida(fileXml,outputdir+'/'+name+".csv");
                        
           // System.out.println("\n **** :\n"+output);
                        
                       
			System.exit(0);

}*/  
            wordList.updateJsonList(output.getJsonList());
            String fileXml=TextCleaning.XMLClean.cleaning(output.getOutput());
            
            String salida=GenerateJson.finalJSON(output.getJsonList(), fileXml);


            return salida;

		}
		catch (Exception e){
		//System.out.println ("la excepcion"+e.toString());
		System.exit(0);
                return "";
	}
        }
		
	
   public static String ejecutar(String args) {
        // TODO code application logic here
    /*	if(args.length<4){
    		System.out.println("Faltan argumentos. ");
    		
    	}
    	else if(args.length>4){
    		System.out.println("Sobran argumentos");
    			
    	}
    	else{*/
    		Main m=new Main();
            System.out.println("Entra main");
    		String salida = m.run_program(args);
    		System.out.println("Sale main");
    		return 	salida;
    		
    	
        
    }
        
   
    
    public static void generarlexer(String path){
     File file=new File(path);
    // System.out.println("WWW"+file);
       // JFlex.Main.generate(file);
    }
}
