package menu;

import dtoObjects.GraphDTO;
import engineManaget.EngineManager;
import exceptions.MenuOptionException;

public class TargetGraphInfoOption implements MenuOption{
    @Override
    public void start() {
        EngineManager engineManager = Menu.getEngineManager();
        try {
            GraphDTO graphDTO = engineManager.getGraphGeneralInfo();
            System.out.println("There are " + graphDTO.getCountTargets() + " targets on the graph.");
            System.out.println("There are " + graphDTO.getCountRoots() + " Roots, " + graphDTO.getCountMiddles() + " Middles, "
                    + graphDTO.getCountLeaves() + " Leaves, " + graphDTO.getCountIndependents() + " Independents targets on the graph.");
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
        }
    }
}
