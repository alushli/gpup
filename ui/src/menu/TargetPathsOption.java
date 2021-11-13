package menu;

import dtoObjects.TargetDTO;
import engineManaget.EngineManager;
import exceptions.MenuOptionException;
import java.util.List;
import java.util.Scanner;

public class TargetPathsOption implements MenuOption{
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            String srcName, desName, dependency;
            List<List<TargetDTO>> list;
            System.out.println("Enter target src name:");
            srcName = scanner.nextLine();
            System.out.println("Enter target des name:");
            desName = scanner.nextLine();
            System.out.println("Enter dependency type (requiredFor or dependsOn):");
            dependency = scanner.nextLine();
            list = engineManager.getTargetsPath(srcName, desName,dependency);
            printLists(list);
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
        }
    }

    private void printLists(List<List<TargetDTO>> list){
        if(list.size() == 0)
            System.out.println("There is no path between the targets on the selected dependency.");
        else{
            while(!list.isEmpty()){
                for(List<TargetDTO> targetDTOList: list){
                    System.out.print("The path is: ");
                    for(TargetDTO targetDTO: targetDTOList){
                        if(targetDTO.getName().equals(targetDTOList.get(0).getName()))
                            System.out.print(targetDTO.getName());
                        else
                            System.out.print("->" + targetDTO.getName());
                    }
                    System.out.println();
                }
            }
        }
    }
}
