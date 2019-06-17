/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;
 
/**
*
* @author Rodrigo
*/
import clases.Numeros;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import MedievalTextLexer.Main;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
 
@Path("/suma")
public class SumaRestService {
 
/*@GET
@Path("/sumar/{param}/{param2}")
public int sumarNumerosGet(@PathParam("param") int msg, @PathParam("param2") int msg2) {
 
return msg+msg2;
 
}*/
    
/*@GET
@Path("/sumar/text/{param}")*/
 @POST
@Path("/sumapost")
@Produces("text/txt")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public String sumarNumerosGet(@FormParam("texto") String name) {
 String finalRes=MedievalTextLexer.Main.ejecutar(name);
 name="";
return finalRes;
}
    
 
@POST
@Path("/sumar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public String sumarNumerosPost(String json) throws FileNotFoundException{
Gson gson = new Gson();
Numeros nums = gson.fromJson(json, Numeros.class);
String responseString = String.valueOf(nums.getNro1()+nums.getNro2());
 final PrintWriter writer=new PrintWriter("la salida");
writer.println("adios");
return responseString;
 
}
 
@PUT
@Path("/sumar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public String sumarNumerosPut(String json) {
Gson gson = new Gson();
Numeros nums = gson.fromJson(json, Numeros.class);
String responseString = String.valueOf(nums.getNro1()+nums.getNro2());
 
return responseString;
 
}
 
}
