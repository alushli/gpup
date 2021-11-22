package menu;

import dtoObjects.TargetDTO;
import engineManager.EngineManager;
import exceptions.MenuOptionException;

import java.lang.reflect.Array;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TargetCircleOption implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start(){
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            engineManager.checkRunXml();
            System.out.println("Enter target name:");
            System.out.println("If you want to go back to the menu, enter -1");
            String targetName = scanner.nextLine().trim();
            if(!targetName.equals("-1")) {
                LinkedHashSet<TargetDTO> list = engineManager.getTargetCircle(targetName);
                printLists(list, targetName);
            }
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
            continu(e.getMessage());
        }
    }

    /* the function print the circle path */
    private void printLists(LinkedHashSet<TargetDTO> list, String targetName){
        if(list.size() == 0)
            System.out.println("There is no circle with the selected target.");
        else {
            System.out.print("The circle is: ( ");
            for (TargetDTO targetDTO : list) {
                System.out.print(targetDTO.getName() + " ");
            }
            System.out.println(")");
        }
    }

    /* the function return to the start function */
    private void continu(String error){
        if(!error.equals(MenuOptionException.getXmlLoadError()))
            start();
    }
}
