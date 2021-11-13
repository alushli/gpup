package menu;

import dtoObjects.TargetDTO;
import engineManaget.EngineManager;
import exceptions.MenuOptionException;
import java.util.Scanner;

public class TargetInfoOption implements MenuOption{
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            System.out.println("Enter target name:");
            String targetName = scanner.nextLine();
            TargetDTO targetDTO = engineManager.getTargetInfo(targetName);
            System.out.println("Target name: " + targetDTO.getName());
            System.out.println("Target position: " + targetDTO.getPosition().toString());
            System.out.println("Target that he depend on them: " + targetDTO.getDependsOnList().toString());
            System.out.println("Target that he required for them: " + targetDTO.getRequiredForList().toString());
            if(targetDTO.getGeneralInfo() != null)
                System.out.println("Target general text: " + targetDTO.getGeneralInfo());
            else
                System.out.println("The target doesn't have general text");
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
        }
    }
}
