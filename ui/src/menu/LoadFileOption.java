package menu;

import engineManaget.EngineManager;
import exceptions.XmlException;
import java.util.Scanner;

public class LoadFileOption implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start(){
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            System.out.println("Enter full path of xml - note that the previous file will be overwritten: (if you want to go back to the menu, enter -1)");
            String xmlPath = scanner.nextLine();
            if(!xmlPath.equals("-1")) {
                engineManager.load(xmlPath);
                System.out.println("The xml was uploaded successfully");
            }
        } catch (XmlException e){
            System.out.println(e.errorInfo() + e.getMessage());
            this.start();
        }
    }
}
