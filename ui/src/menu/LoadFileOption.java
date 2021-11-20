package menu;

import engineManager.EngineManager;
import exceptions.XmlException;
import java.util.Scanner;

public class LoadFileOption implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        String loadStart;
        System.out.println("Do you want to start from saved system status (Y/N) ?");
        System.out.println("If you want to go back to the menu, enter -1");
        loadStart = scanner.nextLine();
        while (!loadStart.equalsIgnoreCase("Y") && !loadStart.equalsIgnoreCase("N") && !loadStart.equals("-1")) {
            System.out.println("Please enter valid char (Y/N)");
            loadStart = scanner.nextLine();
        }
        if (!loadStart.equals("-1")) {
            if (loadStart.equalsIgnoreCase("Y")) {
                askFilePathForSavedSystemStatus(scanner, engineManager);
            } else {
                askFilePathForNew(scanner, engineManager);
            }
        }
    }

    private void askFilePathForSavedSystemStatus(Scanner scanner, EngineManager engineManager){
        String xmlPath;
        try {
            System.out.println("Enter full path of the file without without file extension");
            System.out.println("If you want to go back to the menu, enter -1");
            xmlPath = scanner.nextLine();
            if (!xmlPath.equals("-1")) {
                engineManager.loadSavedSystemStatus(xmlPath);
                System.out.println("The file was uploaded successfully");
            }
        } catch (XmlException e){
            System.out.println(e.errorInfo() + e.getMessage());
            this.askFilePathForSavedSystemStatus(scanner, engineManager);
        }
    }

    private void askFilePathForNew(Scanner scanner, EngineManager engineManager){
        String xmlPath;
        try {
            System.out.println("Enter full path of xml - note that the previous file will be overwritten:");
            System.out.println("If you want to go back to the menu, enter -1");
            xmlPath = scanner.nextLine();
            if (!xmlPath.equals("-1")) {
                engineManager.load(xmlPath);
                System.out.println("The xml was uploaded successfully");
            }
        } catch (XmlException e){
            System.out.println(e.errorInfo() + e.getMessage());
            this.askFilePathForNew(scanner, engineManager);
        }
    }
}
