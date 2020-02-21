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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.apache.commons.io.IOUtils;

@Path("/file")
public class FileRestService {


    @GET
    @Path("/{file}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("file") String file) {

        File fileDownload = new File(getClass().getClassLoader().getResource(file).getFile());
        ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + file);
        return response.build();
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(MultipartFormDataInput input) {

        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {

            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                File f = new File(getClass().getClassLoader().getResource(".").getFile());
                String path = f.getAbsolutePath() + "/" + fileName;

                File temp = new File(path);
                System.out.println(temp);
                if(temp.exists())
                   return Response.status(303).entity("File already exists").build();

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path

                writeFile(bytes,path);

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return Response.status(200)
                .entity("Uploaded the file named:  " + fileName).build();

    }


    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateFile(MultipartFormDataInput input) {

        String fileName = "";
        String reponse = "Upload the file named: ";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {

            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                File f = new File(getClass().getClassLoader().getResource(".").getFile());
                String path = f.getAbsolutePath() + "/" + fileName;

                File temp = new File(path);
                System.out.println(temp);
                if(temp.exists())
                    reponse = "Updated the file named: ";

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path

                writeFile(bytes,path);

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return Response.status(200)
                .entity(reponse + fileName).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String fileList() {
        // Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(getClass().getClassLoader().getResource(".").getFile());

        // Populates the array with names of files and directories
        pathnames = f.list();

        String json = new Gson().toJson(pathnames);
        return json;
    }


    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
}
