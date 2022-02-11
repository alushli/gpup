package admin.servlets;

import com.google.gson.Gson;
import dtoObjects.TargetsPathDTO;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "isTaskExist", urlPatterns = "/check-task-name")
public class IsTaskExist extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String taskName = req.getParameter("taskName");
            if(taskName == null || taskName.isEmpty() ){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
                if(engineManager == null){
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                }else{
                    if(engineManager.isTaskExist(taskName)){
                        String errorMessage = "Task name " + " already exists. Please enter a different task name.";
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.getOutputStream().print(errorMessage);
                    }else{
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }
                }
            }
        }
    }
}
