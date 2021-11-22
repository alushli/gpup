package menu;

import dtoObjects.TargetDTO;
import engineManager.EngineManager;
import exceptions.MenuOptionException;
import java.util.Scanner;

public class TargetInfoOption implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            engineManager.checkRunXml();
            System.out.println("Enter target name:");
            System.out.println("If you want to go back to the menu, enter -1");
            String targetName = scanner.nextLine().trim();
            if(!targetName.equals("-1")) {
                TargetDTO targetDTO = engineManager.getTargetInfo(targetName);
                System.out.println("Target name: " + targetDTO.getName());
                System.out.println("Target position: " + targetDTO.getPosition().toString());
                System.out.println("Target that he depend on them: " + targetDTO.getDependsOnList().toString());
                System.out.println("Target that he required for them: " + targetDTO.getRequiredForList().toString());
                if (targetDTO.getGeneralInfo() != null)
                    System.out.println("Target general text: " + targetDTO.getGeneralInfo());
                else
                    System.out.println("The target doesn't have general text");
            }
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
            continu(e.getMessage());
        }
    }

    /* the function return to the start function */
    private void continu(String error){
        if(!error.equals(MenuOptionException.getXmlLoadError()))
            start();
    }
}
