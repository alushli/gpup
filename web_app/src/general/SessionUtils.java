package general;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUserName(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constant.USERNAME) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }
}
