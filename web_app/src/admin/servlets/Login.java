package admin.servlets;

import UsersManager.UsersManager;
import general.Constant;
import general.ServletsUtils;
import general.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminLogin", urlPatterns = "/admin-login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        String userNameFromSession = SessionUtils.getUserName(req);
        UsersManager usersManager = ServletsUtils.getUserManager(getServletContext());

        if(userNameFromSession == null){//user is not l ogged in
            String userNameFromReqParams = req.getParameter(Constant.USERNAME);
            if(userNameFromReqParams == null || userNameFromReqParams.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }else{
                userNameFromReqParams = userNameFromReqParams.trim();
                synchronized (this){
                    if(usersManager.isAdminExist(userNameFromReqParams)){
                        String errorMessage = "Admin " + userNameFromReqParams + " already exists. Please enter a different Admin name.";
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.getOutputStream().print(errorMessage);
                    }else{
                        try{
                            usersManager.addAdmin(userNameFromReqParams);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        req.getSession(true).setAttribute(Constant.USERNAME, userNameFromReqParams);
                        System.out.println("On login, request URI is: "+ req.getRequestURI());
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }
                }
            }
        }else{
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
