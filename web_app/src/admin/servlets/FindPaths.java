package admin.servlets;

import com.google.gson.Gson;
import dtoObjects.GeneralGraphInfoDTO;
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

@WebServlet(name = "findPaths", urlPatterns = "/find-paths")
public class FindPaths extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String graphName = req.getParameter("graphName");
            String dependencyType = req.getParameter("dependencyType");
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            if(graphName == null || graphName.isEmpty() || to == null || from==null || dependencyType==null){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
                if(engineManager == null){
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                }else{
                    Gson gson = new Gson();
                    PrintWriter out = resp.getWriter();
                    out.println(gson.toJson(engineManager.getTargetsPath(graphName,from,to,dependencyType)));
                    out.flush();
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            }
        }
    }
    }

