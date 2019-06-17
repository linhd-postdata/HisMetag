/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.PrintWriter;

/**
 *
 * @author mluisadiez
 */

@WebServlet(name = "postText", urlPatterns = {"/uploadtext"})
public class postText extends HttpServlet{
     private static final long serialVersionUID = 1L;
        
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/text;charset=UTF-8");
            final String path = request.getParameter("texto");
            PrintWriter out;
    String title = "Simple Servlet Output";

    // primero selecciona el tipo de contenidos y otros campos de cabecera de la respuesta
    response.setContentType("text/html");
    // Luego escribe los datos de la respuesta
    out = response.getWriter();
    out.println(MedievalTextLexer.Main.ejecutar(path));
    out.close();
            
            
        }
     
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }
     
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }
     
        public String getFileName(Part part) {
            String contentHeader = part.getHeader("content-disposition");
            String[] subHeaders = contentHeader.split(";");
            for(String current : subHeaders) {
                if(current.trim().startsWith("filename")) {
                    int pos = current.indexOf('=');
                    String fileName = current.substring(pos+1);
                    return fileName.replace("\"", "");
                }
            }
            return null;
        }
}
