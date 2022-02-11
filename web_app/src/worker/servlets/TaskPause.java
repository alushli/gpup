package worker.servlets;


import UsersManager.UsersManager;
import engineManager.EngineManager;
import general.ServletsUtils;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "taskPause", urlPatterns = "/task-pause")
public class TaskPause extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String taskName = req.getParameter("taskName");
            String action = req.getParameter("action");
            if(taskName == null || taskName.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
                UsersManager usersManager = ServletsUtils.getUserManager(getServletContext());
                if(engineManager == null) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                }else{
                    if(action.equals("pause")){
                        engineManager.pauseTask(usersManager.getWorker(userNameFromSession), taskName);
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }else if(action.equals("resume")){
                        engineManager.resumeTask(usersManager.getWorker(userNameFromSession), taskName);
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }else{
                        resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    }
                }
            }
        }
    }
}
