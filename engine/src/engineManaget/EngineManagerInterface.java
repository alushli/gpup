package engineManaget;

import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import exceptions.MenuOptionException;
import exceptions.XmlException;
import Enums.SimulationEntryPoint;
import target.Target;

import java.util.LinkedHashSet;
import java.util.List;

public interface EngineManagerInterface {

    /* the function load the graph */
    void load(String filePath) throws XmlException;

    /* the function return graph info */
    GraphDTO getGraphGeneralInfo() throws MenuOptionException;

    /* the function return target info */
    TargetDTO getTargetInfo(String targetName) throws MenuOptionException;

    /* the function return targets paths */
    List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection) throws MenuOptionException;

    /* the function return simulation info */
    SimulationSummeryDTO runSimulate(SimulationEntryPoint entryPoint);

    /* the function return target circle */
    LinkedHashSet<TargetDTO> getTargetCircle(String targetName) throws MenuOptionException;
}
