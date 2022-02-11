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

@WebServlet(name = "createTask", urlPatterns = "/create-task")
public class CreateNewTask extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();
            Properties properties = new Properties();
            properties.load(req.getInputStream());
            String taskType = req.getParameter("taskType");
            String graphName = req.getParameter("graphName");
            String jsonOfTask = properties.getProperty("task");
            EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
            if(taskType.equals("compilation")){
                NewCompilationTaskDetails newCompilationTaskDetails = gson.fromJson(jsonOfTask, NewCompilationTaskDetails.class);
                engineManager.addTask(new CompilationTaskOperator(newCompilationTaskDetails, userNameFromSession, engineManager.getGraph(graphName)));
                resp.setStatus(HttpServletResponse.SC_OK);
            }else{
                NewSimulationTaskDetails newSimulationTaskDetails = gson.fromJson(jsonOfTask, NewSimulationTaskDetails.class);
                engineManager.addTask(new SimulationTaskOperator(newSimulationTaskDetails, userNameFromSession, engineManager.getGraph(graphName)));
                resp.setStatus(HttpServletResponse.SC_OK);
            }

        }
    }
}
