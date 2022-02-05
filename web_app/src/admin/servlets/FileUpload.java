package admin.servlets;

import com.google.gson.Gson;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@MultipartConfig
@WebServlet(name = "uploadFile", urlPatterns = "/upload-file")
public class FileUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String userNameFromSession = SessionUtils.getUserName(request);
        if(userNameFromSession==null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            PrintWriter out = response.getWriter();
            Collection<Part> parts = request.getParts();
            StringBuilder fileContent = new StringBuilder();
            for (Part part : parts) {
                fileContent.append(readFromInputStream(part.getInputStream()));
            }
            Set<String> errors = new HashSet<>();
            EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
            InputStream inputStream = new ByteArrayInputStream(fileContent.toString().getBytes());
            engineManager.load(inputStream, errors);
            if(errors.isEmpty()){
                System.out.println("On upload file, request URI is: "+ request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                Gson gson = new Gson();
                out.println(gson.toJson(errors));
                out.flush();
            }
        }


    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}
