package worker.servlets;

import UsersManager.UsersManager;
import com.google.gson.Gson;
import general.ServletsUtils;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getTargetsToRun", urlPatterns = "/get-target-to-run")
public class GetTargetsToRun extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else{
              int amount = Integer.parseInt(req.getParameter("amount"));
              UsersManager usersManager = ServletsUtils.getUserManager(getServletContext());
              Gson gson = new Gson();
              PrintWriter out = resp.getWriter();
              out.println(gson.toJson(usersManager.getTargetToRun(userNameFromSession,amount)));
              resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
