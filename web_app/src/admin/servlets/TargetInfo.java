package admin.servlets;

import com.google.gson.Gson;
import dtoObjects.TargetDTO;
import dtoObjects.TargetsPathDTO;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "targetInfo", urlPatterns = "/target-info")
public class TargetInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String graphName = req.getParameter("graphName");
            String targetName = req.getParameter("targetName");
            if(graphName == null || graphName.isEmpty() || targetName==null || targetName.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
                if(engineManager == null){
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                }else{
                    Gson gson = new Gson();
                    PrintWriter out = resp.getWriter();
                    TargetDTO targetDTO = engineManager.getTarget(graphName,targetName);
                    if(targetDTO != null){
                        out.println(gson.toJson(targetDTO));
                        resp.setStatus(HttpServletResponse.SC_OK);
                        out.flush();
                    }else{
                        resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    }
                }
            }
        }
    }
}
