package menu;

import engineManager.EngineManager;
import java.util.Scanner;

public class SavaSystemStatus implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        System.out.println("Enter full path with file name without file extension - note that the if the file already exist, previous file will be overwritten:");
        System.out.println("If you want to go back to the menu, enter -1");
        String filePath = scanner.nextLine();
        if (!filePath.equals("-1")) {
            engineManager.saveSystemStatus(filePath);
            System.out.println("The file was save successfully");
        }
    }
}
