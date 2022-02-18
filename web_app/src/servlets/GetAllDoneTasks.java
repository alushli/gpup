package servlets;

import com.google.gson.Gson;
import engineManager.EngineManager;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getAllDoneTasks", urlPatterns = "/get-all-done-tasks")
public class GetAllDoneTasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            PrintWriter out = resp.getWriter();
            EngineManager engineManager = (EngineManager) getServletContext().getAttribute("Engine");
            Gson gson = new Gson();
            out.println(gson.toJson(engineManager.getAllDoneOrCancelTasks()));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
