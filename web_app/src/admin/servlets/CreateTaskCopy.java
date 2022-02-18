package admin.servlets;

import com.google.gson.Gson;
import dtoObjects.NewCompilationTaskDetails;
import dtoObjects.NewSimulationTaskDetails;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import task.CompilationTaskOperator;
import task.SimulationTaskOperator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "createTaskCopy", urlPatterns = "/create-task-copy")
public class CreateTaskCopy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            String taskName = req.getParameter("taskName");
            String runType = req.getParameter("runType");
            EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
            boolean fromScratch;
            if(runType.equalsIgnoreCase("Incremental")){
                fromScratch = false;
            }else{
                fromScratch = true;
            }
            engineManager.duplicateList(taskName, userNameFromSession, fromScratch);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
