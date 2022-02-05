package servlets;

import UsersManager.UsersManager;
import com.google.gson.Gson;
import engineManager.EngineManager;
import general.ServletsUtils;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getAllUsers", urlPatterns = "/get-all-users")
public class GetAllUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        String userNameFromSession = SessionUtils.getUserName(req);
        if(userNameFromSession==null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            PrintWriter out = resp.getWriter();
            UsersManager usersManager = ServletsUtils.getUserManager(getServletContext());
            Gson gson = new Gson();
            out.println(gson.toJson(usersManager.getAllUsers()));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
