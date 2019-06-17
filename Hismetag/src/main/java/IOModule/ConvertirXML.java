/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.regex.*;

/**
 *
 * @author mluisadiez
 */



public class ConvertirXML {
    
      static long numChar=0;
        static    int numTag=0;
         static   int numPlaceName=0;
        static    int numPersName=0;
         static   int numOrgName=0;
         static   int numGeogName=0;
         static   long numCharChild=0;
         static   int numRoleName=0;
         static   long numEndChar=0;
         static   int numWord=0;
         static    long numEndChild=0;
            static BufferedWriter output;
            
            
public static void inicializar(){
    
     numChar=0;
         numTag=0;
          numPlaceName=0;
         numPersName=0;
         numOrgName=0;
          numGeogName=0;
          numCharChild=0;
         numRoleName=0;
         numEndChar=0;
         numWord=0;
         numEndChild=0;
}
    
public static void slugify(BufferedReader input,String salida) throws IOException{
        //BufferedWriter output= new BufferedWriter(new FileWriter("salida/Poema_del_Mio_Cid.xml"));
        BufferedWriter output= new BufferedWriter(new FileWriter(salida));

       // System.out.println("estoy aqui");
        String line;
        String texto="";
        
        //texto+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        
         // Lectura del fichero
         
         while((line=input.readLine())!=null){
            texto+=line+'\n';
         }
        texto = texto.trim();
        
        
        
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\[fol. [0-9]+[a-z]\\]", "")
                 .replaceAll("\\( \\)", "")
                .replaceAll("<name>", "<namepara>")
               .replaceAll("\\{RMK[:a-zA-Z ./0-9,]+\\}","")
                .replaceAll("\\{RMK: [a-zA-Z0-9]+\\.\\}","")
                .replaceAll("\\{IN[0-9].\\}","")
                .replaceAll("\\{AD.","")
               .replaceAll("\\{CB[0-9].", "")
               .replaceAll("\\}", "")
               .replaceAll("&", "y")
               .replaceAll("(\n)+","\n")
               .replaceAll("<a>","a")
               .replaceAll("<e>","e")
               .replaceAll("<i>","i")
               .replaceAll("<o>","o")
               .replaceAll("<u>","u")
                .replaceAll("\\)","")
                .replaceAll("\\?","")
                .replaceAll("<s>","s")
                .replaceAll("\\[\\^\\d", "")
                .replaceAll("\\*","")
         .replaceAll("\\(\\[\\^2", "")
                .replaceAll("\\[\\(\\^[0-9]", "")
         .replaceAll("\\]\\)", "")
                .replaceAll("\\)\\]", "")
         .replaceAll("\\[ \\]", " ")
                .replaceAll("\\(\\^", "")
         .replaceAll("\\[", "")
                .replaceAll("\\]", "")
              //  .replaceAll("\\<ptr target='(.)*'/\\>\\<geo\\>(.*)\\</geo\\>","")
               
                .replaceAll("\\^0-9", "")
                .replaceAll("\\^", "")
                .replaceAll("<(santos)>", "$1")
                .replaceAll("<(santas)>", "$1")
               .replaceAll("<([a-zA-Z']{1,3})>", "$1")
                .replaceAll("<([a-zA-Z']{1,1})>", "$1")
                .replaceAll("<([a-zA-Z']{2,2})>", "$1")
                .replaceAll("<([a-zA-Z']{3,3})>", "$1")
                .replaceAll("<([a-zA-Z']{4,4})>", "$1")
                .replaceAll("<([a-zA-Z']{5,5})>", "$1")
                .replaceAll("<(,[a-zA-Z']{6,6})>", "$1")
                .replaceAll("<([a-zA-Z']{6,6})>", "$1")
                .replaceAll("<([^aAoO][a-zA-Z]{6,6})>", "$1")
                .replaceAll("\\(", "")
                .replaceAll("\\( \\)","")
                .replaceAll("%[0-9]", "")
                 .replaceAll("<namepara>", "<name>")
                
                .replaceAll(" +" ," ")
                .replaceAll("^ ","")
                .replaceAll(" $", "")
                .replaceAll("\\s*$","")
                .replaceAll(" "+'\n',"\n")
                .replaceAll(" </","</")
                .replaceAll("^ ","")
                .replaceAll('\n'+" ","\n")
                .replaceAll("-\n","")
                .replaceAll("</ptr>geo[^<]+</geo>", "</ptr>")
                ;
       
        output.write(texto);
 
        output.close();
}
static public void escribirNodo(Node nodo) throws IOException{
        if (nodo.getNodeType()!=3){
         //   System.out.println("lenode que estoy visiando "+nodo.getNodeName()+numChar+" "+nodo.getNodeValue());
              numEndChar=numChar+nodo.getTextContent().length()-1;
                         numTag++;
             //            System.out.println ("estoy escribinedo e nodo "+nodo.getNodeName()+" "+nodo.getTextContent().length()+nodo.getTextContent());
      
                         
                         if(nodo.getNodeName()=="persName") numPersName++;
                         if(nodo.getNodeName()=="placeName") numPlaceName++;
                         if(nodo.getNodeName()=="orgName") numOrgName++;
                         if(nodo.getNodeName()=="geogName") numGeogName++;
                         if(nodo.getNodeName()=="roleName") numRoleName++;
            
            String type="";
         if(nodo.hasAttributes()) {
                           
                            NamedNodeMap attrs = nodo.getAttributes(); 
                            
                        for(int j = 0 ; j<attrs.getLength() ; j++) {
                        Attr attribute = (Attr)attrs.item(j); 
                        if (attribute.getName()=="type") type=attribute.getValue();
                        
                            }
                       }
         String ptr="";
         String geo="";
         if (nodo.hasChildNodes()){
            NodeList hijos=nodo.getChildNodes();
            for (int i=0; i<hijos.getLength();i++){
                if (hijos.item(i).getNodeName()=="ptr"){
                 ptr=((Attr)hijos.item(i).getAttributes().item(0)).getValue();
                }
                if (hijos.item(i).getNodeName()=="geo"){
                    geo=hijos.item(i).getNodeValue();
                }
            }
         }
            if ( !(nodo.getNodeName()=="ptr") && !(nodo.getNodeName()=="geo")){
                    String texto=nodo.getTextContent().replace("\n", "");
                          output.write(nodo.getNodeName()+";"+type+";"+texto+";"+numChar+";"+numEndChar+";"+nodo.getChildNodes().getLength()+";"+"no"+";"+ptr+";"+geo+"\n");
                     
                         }
            for (int i=0; i<nodo.getChildNodes().getLength();i++) escribirNodo(nodo.getChildNodes().item(i));
            numChar=numEndChar;
        }
        else{
            
         //   System.out.println("VISITACO "+nodo.getNodeName()+numChar+" "+nodo.getNodeValue());
            numEndChar=numChar+nodo.getTextContent().length();
            numChar=numEndChar;
        }
            
    }

    /**
     * @param args the command line arguments
     */
    public static void csvSalida(String file,String salida) throws IOException{
        // TODO code application logic here
      try{ 
          
       //   System.out.println("wwwwww"+file);
      /*  File dir=new File(entrada);
              System.out.println("estiy aqui"+dir);
      if (dir.isDirectory())System.out.println("estoy"+dir);
             String[] ficheros = dir.list();*/
              
             
      // System.out.println("la salida no la entiendo ");
           
             File f= new File(file);
       //   System.out.println("el archico  "+file);
            FileReader stream= new FileReader(f); 
      //  System.out.println("El archivo visto "+stream);
             BufferedReader buffer = new BufferedReader(stream);
      //       System.out.println("el indice "+file.lastIndexOf('/'));
            // System.out.println("la salida no la entiendo 2");
       //    System.out.println("la subcadema "+file.substring(file.lastIndexOf('/')+1));
         //   String salida="salida/"+file.substring(file.lastIndexOf('/')+1);
       //     System.out.println ("estoy despues "+salida);
            System.out.println("salida "+salida);
          // slugify(buffer,salida);
          // System.out.println("la salida no la entiendo 3");
         //  System.out.println ("la salida es "+salida);
           Document documento=null;
           try{
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
           // System.out.println("no se encuentra "+builder+" "+salida);
          //  File f1=new File(salida);
          //  System.out.println("saliaiia"+f1);
          //  System.out.println(f1.canRead());
           // System.out.println("la salida no la entiendo 5");
			documento = builder.parse(file);
                   //     System.out.println("la salida no la entiendo 6");
           } catch (ParserConfigurationException ex) {
              // System.out.println("La excepcion");
         ex.printStackTrace();
         
         
           }
                        
          //  System.out.println(salida);
              //Document documento = builder.parse(new File(args[1]));
            //    System.out.println ("que estoy hacuiendo");     
              //String csv="prueba"+salida.substring(0, salida.length()-3)+"csv";
          // System.out.println("el csv "+csv);
               output=new BufferedWriter(new FileWriter(salida));
             //  System.out.println(csv);
            output.write("Tag;Attribute;Value;Offset;EndChar;NumChilds;IsaChild\n");
          //HashMap<String,String> mapa =leertx
          //System.out.println("la salida no la entiendo 7");
        NodeList lnodes=documento.getChildNodes().item(0).getChildNodes();
        
                   int Total=0;
        for (int i=0;i<lnodes.getLength();i++){
            Node nodo=lnodes.item(i);
            escribirNodo(nodo);
        
                         
          //System.out.println("Estoye empenzadn a lerr "+nodo.getTextContent()+" "+numChar);
         
          
          
       //System.out.println("la salida no la entiendo 8");
          
          
          
          
    }
      //  System.out.println("el fina"+Total);
        output.close();
        inicializar();
        //System.out.println("la salida no la entiendo 9");
        
       }catch (Exception e){;}
    }
}
    

