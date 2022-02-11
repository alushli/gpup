package worker.servlets;


import UsersManager.UsersManager;
import com.google.gson.Gson;
import dtoObjects.RegisterTaskDTO;
import engineManager.EngineManager;
import general.ServletsUtils;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import task.TaskOperator;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "taskSubscribe", urlPatterns = "/task-subscribe")
public class TaskSubscribe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String taskName = req.getParameter("taskName");
            if(taskName == null || taskName.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
                UsersManager usersManager = ServletsUtils.getUserManager(getServletContext());
                if(engineManager == null) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                }else{
                    engineManager.subscribeUser(usersManager.getWorker(userNameFromSession), taskName);
                    PrintWriter out = resp.getWriter();
                    Gson gson = new Gson();
                    TaskOperator taskOperator = engineManager.getTaskByName(taskName);
                    out.println(gson.toJson(new RegisterTaskDTO(taskOperator)));
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            }
        }
    }
}
