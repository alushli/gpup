package worker.servlets;

import com.google.gson.Gson;
import dtoObjects.TargetUpdate;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "UpdateTargetRun", urlPatterns = "/update-target-run")
public class UpdateTargetRun extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            Gson gson = new Gson();
            Properties properties = new Properties();
            properties.load(req.getInputStream());
            String json = properties.getProperty("target");
            TargetUpdate targetUpdate = gson.fromJson(json, TargetUpdate.class);
            EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
            engineManager.updateTarget(targetUpdate, userNameFromSession);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
