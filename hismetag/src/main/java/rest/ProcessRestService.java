/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;
 
/**
*
* @author alfonsocuesta
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.MediaType;
import MedievalTextLexer.Main;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.apache.commons.io.IOUtils;


@Path("/process")
public class ProcessRestService {

    @POST
    @Path("/text")
//@Produces("text/txt")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String processTextPost(@FormParam("texto") String name, @HeaderParam("Accept") List <String> accept) {

        boolean tei=false;
        for (String s : accept) {
            if (s.contains("application/tei+xml")) {
                tei = true;
                break;
            }
        }
         System.out.println(tei);
         String finalRes = MedievalTextLexer.Main.ejecutar(name, tei);

         return finalRes;
    }


    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String processFilePost(MultipartFormDataInput input, @HeaderParam("Accept") List <String> accept) {

        boolean tei=false;
        for (String s : accept) {
            if (s.contains("application/tei+xml")) {
                tei = true;
                break;
            }
        }

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        StringBuilder sb = new StringBuilder();

        for (InputPart inputPart : inputParts) {

            try {

                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
                String line = buf.readLine();
                while(line != null) {
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        String finalRes = MedievalTextLexer.Main.ejecutar(sb.toString(), tei);

        return finalRes;
    }

}

