package admin.servlets;

import com.google.gson.Gson;
import dtoObjects.TaskRuntimeForAdmin;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getTaskRuntime", urlPatterns = "/get-task-runtime")
public class GatTaskRuntime extends HttpServlet {
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
                    Gson gson = new Gson();
                    PrintWriter out = resp.getWriter();
                    TaskRuntimeForAdmin taskRuntimeForAdmin = engineManager.getTaskForAdmin(taskName);
                    if(taskRuntimeForAdmin != null){
                        out.println(gson.toJson(taskRuntimeForAdmin));
                        out.flush();
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }else{
                        resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    }

                }
            }
        }
    }
}
