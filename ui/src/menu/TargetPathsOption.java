package menu;

import dtoObjects.TargetDTO;
import engineManager.EngineManager;
import exceptions.MenuOptionException;
import java.util.List;
import java.util.Scanner;

public class TargetPathsOption implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            engineManager.checkRunXml();
            String srcName, desName, dependency;
            List<List<TargetDTO>> list;
            System.out.println("Enter target src name:");
            System.out.println("If you want to go back to the menu, enter -1");
            srcName = scanner.nextLine();
            if(!srcName.equals("-1")) {
                System.out.println("Enter target des name:");
                System.out.println("If you want to go back to the menu, enter -1");

                desName = scanner.nextLine();
                if(!desName.equals("-1")) {
                    System.out.println("Enter dependency type (R for requiredFor or D for dependsOn):");
                    System.out.println("If you want to go back to the menu, enter -1");
                    dependency = scanner.nextLine();
                    if(!dependency.equals("-1")) {
                        list = engineManager.getTargetsPath(srcName, desName, dependency);
                        printLists(list);
                    }
                }
            }

        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
            continu(e.getMessage());
        }
    }


    private void printLists(List<List<TargetDTO>> lists){
        if(lists.isEmpty()){
            System.out.println("There is no path between the targets on the selected dependency.");
        }else{
            for (List<TargetDTO> list:lists){
                printList(list);
                System.out.println();
            }
        }
    }

    private void printList(List<TargetDTO> list){
        System.out.print("The path is: ");
        int i =1;
        for (TargetDTO targetDTO:list){
            if(i==list.size()){
                System.out.print(targetDTO.getName());
            }else{
                System.out.print(targetDTO.getName()+"->");
            }
            i++;
        }
    }



    /* the function return to the start function */
    private void continu(String error){
        if(!error.equals(MenuOptionException.getXmlLoadError()))
            start();
    }
}
