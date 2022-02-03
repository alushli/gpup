package admin.servlets;


import com.google.gson.Gson;
import dtoObjects.TargetDTO;
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
import java.util.List;


@WebServlet(name = "generalInfo", urlPatterns = "/general-info")
public class GeneralInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String graphName = req.getParameter("graphName");
            if(graphName == null || graphName.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
                if(engineManager == null){
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                }else{
                    Graph graph = engineManager.getGraph(graphName);
                    if(graph != null){
                        Gson gson = new Gson();
                        PrintWriter out = resp.getWriter();
                        out.println(gson.toJson(graph));
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
