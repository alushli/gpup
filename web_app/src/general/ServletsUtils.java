package general;

import UsersManager.UsersManager;
import jakarta.servlet.ServletContext;

public class ServletsUtils {
    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";

    private static final Object userManagerLock = new Object();

    public static UsersManager getUserManager(ServletContext servletContext){
            synchronized (userManagerLock){
                if(servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null){
                   // UsersManager usersManager = new UsersManager();
                    //System.out.println("hi");
                    servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UsersManager());
                }
            }
            return (UsersManager)servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }
}
