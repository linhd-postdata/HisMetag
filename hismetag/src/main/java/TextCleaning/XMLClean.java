package TextCleaning;

import IOModule.Output;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author krustirdiez
 */


public class XMLClean {

	public static void slugify(String input, Output output) throws IOException {
		// BufferedWriter output= new BufferedWriter(new
		// FileWriter("salida/Poema_del_Mio_Cid.xml"));
		//System.out.println("esto en slugify"+input+" "+salida);
              /*  FileReader fre = new FileReader (input);
               BufferedReader br = new BufferedReader(fre);
		BufferedWriter output = new BufferedWriter(new FileWriter(salida));
                //System.out.println("estoy aqui"+input);
		BufferedReader entrada = new BufferedReader(br);*/
		
		String line;
		String texto = "";
                
		// texto+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

		// Lectura del fichero
               /* texto+=entrada.readLine()+ "\n";
		while ((line = entrada.readLine()) != null) {
                        
			texto += line + '\n';
		}*/
                texto=input;
		texto = texto.trim();
		// System.out.println ("la entrada que lee java"+texto);

		texto = texto.replace('\u00f1', '\001');
		texto = texto.replace('\u00E7', '\002');
                texto = texto.replace('\u00e1', '\003');
                texto = texto.replace('\u00e9', '\004');
		texto = texto.replace('\u00ed', '\005');
                texto = texto.replace('\u00f3', '\006');
                texto = texto.replace('\u00fa', '\007');
		texto = texto.replace('\u00c1', '\011');
               // texto = texto.replace('\u00c9', '\012');
                texto = texto.replace('\u00cd', '\013');
		texto = texto.replace('\u00d3', '\014');
                texto = texto.replace('\u00da', '\015');
                texto = texto.replace('\u00d1', '\016');
		texto = texto.replace('\u00C7', '\017');
               
               
 

		texto = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\[fol. [0-9]+[a-z]\\]", "")
				.replaceAll("\\( \\)", "").replaceAll("<name>", "<namepara>")
				.replaceAll("\\{RMK([:a-zA-Z& /0-9]|\\.)+\\}", "").replaceAll("\\{RMK: [a-zA-Z0-9]+\\.\\}", "")
				.replaceAll("\\{IN[0-9].\\}", "").replaceAll("\\{AD.", "").replaceAll("\\{CB[0-9].", "")
				.replaceAll("&", "y").replaceAll("(\n)+", "\n").replaceAll("<a>", "a").replaceAll("<e>", "e")
				.replaceAll("<i>", "i").replaceAll("<o>", "o").replaceAll("<u>", "u").replaceAll("\\)", "")
				.replaceAll("\\?", "").replaceAll("<s>", "s").replaceAll("\\[\\^\\d", "").replaceAll("\\*", "")
				.replaceAll("\\(\\[\\^2", "").replaceAll("\\[\\(\\^[0-9]", "").replaceAll("\\]\\)", "")
				.replaceAll("\\)\\]", "").replaceAll("\\[ \\]", " ").replaceAll("\\(\\^", "").replaceAll("\\[", "")
				.replaceAll("\\]", "").replaceAll("\\<ptr target='(.)*'/\\>\\<geo\\>(.*)\\</geo\\>", "")

				.replaceAll("\\^0-9", "").replaceAll("\\^", "").replaceAll("<(santos)>", "$1")
				.replaceAll("<(santas)>", "$1").replaceAll("<([a-zA-Z']{1,3})>", "$1")
				.replaceAll("<([a-zA-Z']{1,1})>", "$1").replaceAll("<([a-zA-Z']{2,2})>", "$1")
				.replaceAll("<([a-zA-Z']{3,3})>", "$1").replaceAll("<([a-zA-Z']{4,4})>", "$1")
				.replaceAll("<([a-zA-Z']{5,5})>", "$1").replaceAll("<(,[a-zA-Z']{6,6})>", "$1").replaceAll("\\(", "")
				.replaceAll("\\( \\)", "").replaceAll("%[0-9]", "").replaceAll("<namepara>", "<name>")
				.replaceAll(" +", " ").replaceAll("^ ", "").replaceAll(" $", "").replaceAll("\\s*$", "")
				.replaceAll(" " + '\n', "\n").replaceAll(" </", "</")
                                .replaceAll("^ ", "")
                                .replaceAll('\n' + " ", "\n");
                
                
		texto = texto.replace( '\001','\u00f1');
		texto = texto.replace( '\002','\u00E7');
                texto = texto.replace( '\003','\u00e1');
                texto = texto.replace( '\004','\u00e9');
		texto = texto.replace( '\005','\u00ed');
                texto = texto.replace( '\006','\u00f3');
                texto = texto.replace( '\007','\u00fa');
		texto = texto.replace( '\011','\u00c1');
                /*texto = texto.replace( '\012','\u00c9');*/
                
                
               
                
                
               texto = texto.replace( '\013','\u00cd');
		texto = texto.replace( '\014','\u00d3');
                texto = texto.replace( '\015','\u00da');
                texto = texto.replace( '\016','\u00d1');
		texto = texto.replace( '\017','\u00C7');
                
                
		// System.out.println ("la entrada que lee java"+texto);
                output.setOutput(texto);
		//output.write(texto);

		//output.close();
	}

	public static String cleaning(String input) throws IOException {
                String texto=input;
		/*BufferedReader entrada = new BufferedReader(new FileReader(input));
		String path = input.substring(0, input.lastIndexOf("/") + 1);
		
		String line;
		String texto = "";

		// texto+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

		// Lectura del fichero

		while ((line = entrada.readLine()) != null) {
			
			texto += line + '\n';
		}*/
		texto = texto.trim();

		texto = texto.replace('\u00f1', '\001');
		texto = texto.replace('\u00E7', '\002');
                texto = texto.replace('\u00e1', '\003');
                texto = texto.replace('\u00e9', '\004');
		texto = texto.replace('\u00ed', '\005');
                texto = texto.replace('\u00f3', '\006');
                texto = texto.replace('\u00fa', '\007');
		texto = texto.replace('\u00c1', '\011');
               // texto = texto.replace('\u00c9', '\012');
                texto = texto.replace('\u00cd', '\013');
		texto = texto.replace('\u00d3', '\014');
                texto = texto.replace('\u00da', '\015');
                texto = texto.replace('\u00d1', '\016');
		texto = texto.replace('\u00C7', '\017');
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD)

				.replaceAll(" +", " ")
                                .replaceAll("^ ", "")
                                .replaceAll(" $", "")
                                .replaceAll("\\s*$", "")
				//.replaceAll(" " + '\n', "\n")
                                .replaceAll(" </", "</")
                                .replaceAll("^ ", "");
                     //   .replaceAll('\n' + " ", "\n");
		/*BufferedWriter output = new BufferedWriter(new FileWriter(input));

		String input2 = input.substring(0, input.length() - 4);
		// System.out.println("SSSAALLLL"+input2);
		BufferedWriter output2 = new BufferedWriter(new FileWriter(input2 + "XML.xml"));*/
		String output="";	
		output+="<xml version='1.0' encoding='UTF-8'>";
		output+="<TEI xmlns='http://www.tei-c.org/ns/1.0'>\n";
		// System.out.println("cuakl cierra");
		output+="<teiHeader>" + "<fileDesc>" + "<titleStmt>" + "<title>LINHD: Mediaeval Iberia</title>"
				+ "<publicationStmt>" + "<p>21-10-2016</p>" + "</publicationStmt>";
		output+="<sourceDesc>" + "<p>" + "<link target=''/>" + "</p>" + "</sourceDesc>" + "</titleStmt>"
				+ "</fileDesc>" + "</teiHeader>";
		output+="\n<text>\n" + "<body>\n" + "<div>\n" + "<p>\n";
		texto = texto.replace( '\001','\u00f1');
		texto = texto.replace( '\002','\u00E7');
                texto = texto.replace( '\003','\u00e1');
                texto = texto.replace( '\004','\u00e9');
		texto = texto.replace( '\005','\u00ed');
                texto = texto.replace( '\006','\u00f3');
                texto = texto.replace( '\007','\u00fa');
		texto = texto.replace( '\011','\u00c1');
              //  texto = texto.replace( '\012','\u00c9');
                texto = texto.replace( '\013','\u00cd');
		texto = texto.replace( '\014','\u00d3');
                texto = texto.replace( '\015','\u00da');
                texto = texto.replace( '\016','\u00d1');
		texto = texto.replace( '\017','\u00C7');
		output+=texto;

		output+="\n</p>\n" + "</div>\n" + "</body>\n" + "</text>\n" + "</TEI>";
		output+='\n' + "</xml>";

		//output.close();
		// texto.replaceAll("/>", "</ptr>");
		// texto=texto.replaceAll("(<ptr[^<]+)(<geo>[^>]+)>","");
		// texto=texto.replaceAll("(<ptr
		// target=.*</geo>)((\\w|'|\\s)+)(</geogName>\\s)","$2$4");

		// texto=texto.replaceAll("\\<ptr target='(.)*'/\\>\\<geo\\>(.*)\\</geo\\>","");
		/*texto = texto.replace( '\001','\u00f1');
		texto = texto.replace( '\002','\u00E7');
                texto = texto.replace( '\003','\u00e1');
                texto = texto.replace( '\004','\u00e9');
		texto = texto.replace( '\005','\u00ed');
                texto = texto.replace( '\006','\u00f3');
                texto = texto.replace( '\007','\u00fa');
		texto = texto.replace( '\011','\u00c1');
               // texto = texto.replace( '\012','\u00c9');
                texto = texto.replace( '\013','\u00cd');
		texto = texto.replace( '\014','\u00d3');
                texto = texto.replace( '\015','\u00da');
                texto = texto.replace( '\016','\u00d1');
		texto = texto.replace( '\017','\u00C7');
		output2.write("<xml version=\"1.0\" encoding=\"UTF-8\">\n");
		output2.write(texto);
		output2.write('\n' + "</xml>");
		output2.close();*/

		return output;

	}

}
