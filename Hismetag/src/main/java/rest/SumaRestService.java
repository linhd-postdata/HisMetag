/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;
 
/**
*
* @author mluisadiez
*/

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
 
@Path("/process")
public class ProcessRestService {
 

    

 @POST
@Path("/processpost")
@Produces("text/txt")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public String processTextPost(@FormParam("texto") String name) {
 String finalRes=MedievalTextLexer.Main.ejecutar(name);
 name="";
return finalRes;
}

}

