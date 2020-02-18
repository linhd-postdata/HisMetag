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
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
//import javax.ws.rs.core.Response.ResponseBuilder;

//import org.apache.commons.io.IOUtils;
//import org.jboss.resteasy.plugins.providers.multipart.InputPart;
//import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
//
//
@Path("/file")
public class FileRestService {


    //@POST
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    //public Response uploadFile(MultipartFormDataInput input) throws IOException {
//
    //    Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
//
    //    // Get file data to save
    //    List<InputPart> inputParts = uploadForm.get("attachment");
//
    //    for (InputPart inputPart : inputParts) {
    //        try {
//
    //            MultivaluedMap<String, String> header = inputPart.getHeaders();
    //            String fileName = getFileName(header);
//
    //            // convert the uploaded file to inputstream
    //            InputStream inputStream = inputPart.getBody(InputStream.class, null);
//
    //            byte[] bytes = IOUtils.toByteArray(inputStream);
//
    //            String path = System.getProperty("user.home") + File.separator + "uploads";
    //            File customDir = new File(path);
//
    //            if (!customDir.exists()) {
    //                customDir.mkdir();
    //            }
    //            fileName = customDir.getCanonicalPath() + File.separator + fileName;
    //            writeFile(bytes, fileName);
//
    //            return Response.status(200).entity("Uploaded file name : " + fileName).build();
//
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //    return null;
    //}
//
    //@GET
    //@Path("/download")
    //@Produces(MediaType.APPLICATION_OCTET_STREAM)
    //public Response downloadFile(@QueryParam("file") String file) {
//
    //    String path = System.getProperty("user.home") + File.separator + "uploads";
    //    File fileDownload = new File(path + File.separator + file);
    //    ResponseBuilder response = Response.ok((Object) file);
    //    response.header("Content-Disposition", "attachment;filename=" + file);
    //    return response.build();
    //}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String fileList() {
        // Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(
                getClass().getClassLoader().getResource(".").getFile()
        );

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

    // Utility method
    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
        System.out.println("Written: " + filename);
    }
}
