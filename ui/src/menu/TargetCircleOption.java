package menu;

import dtoObjects.TargetDTO;
import engineManaget.EngineManager;
import exceptions.MenuOptionException;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TargetCircleOption implements MenuOption{
    @Override
    public void start(){
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            System.out.println("Enter target name:");
            String targetName = scanner.nextLine();
            LinkedHashSet<TargetDTO> list = engineManager.getTargetCircle(targetName);
            printLists(list);
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
        }
    }

    private void printLists(LinkedHashSet<TargetDTO> list){
        if(list.size() == 0)
            System.out.println("There is no circle with the selected target.");
        else{
            System.out.print("The circle is: ( ");
            while(!list.isEmpty()){
                for(TargetDTO targetDTO: list){
                    System.out.print(targetDTO + " ");
                }
            }
            System.out.println(")");
        }
    }
}
