package admin.servlets;


import com.google.gson.Gson;
import dtoObjects.GeneralGraphInfoDTO;
import dtoObjects.TargetsPathDTO;
import engineManager.EngineManager;
import general.SessionUtils;
import graph.Graph;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "findCircle", urlPatterns = "/find-circle")
public class FindCircle extends HttpServlet {
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
                    TargetsPathDTO targetsPathDTO = engineManager.getTargetCircle(targetName, graphName);
                    if(targetsPathDTO != null){
                        out.println( gson.toJson(targetsPathDTO));
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
