package menu;

import dtoObjects.GraphDTO;
import engineManager.EngineManager;
import exceptions.MenuOptionException;

public class TargetGraphInfoOption implements MenuOption{
    @Override
    /* the function start the menu option */
    public void start() {
        EngineManager engineManager = Menu.getEngineManager();
        try {
            engineManager.checkRunXml();
            GraphDTO graphDTO = engineManager.getGraphGeneralInfo();
            System.out.println("There are " + graphDTO.getCountTargets() + " targets on the graph.");
            System.out.println("There are " + graphDTO.getCountRoots() + " Roots, " + graphDTO.getCountMiddles() + " Middles, "
                    + graphDTO.getCountLeaves() + " Leaves, " + graphDTO.getCountIndependents() + " Independents targets on the graph.");
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
        }
    }
}
