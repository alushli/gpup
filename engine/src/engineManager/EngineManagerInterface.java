package engineManager;

import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import exceptions.MenuOptionException;
import exceptions.XmlException;
import Enums.SimulationEntryPoint;
import scema.generated.GPUPDescriptor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;

public interface EngineManagerInterface {

    /* the function load the graph */
    void load(String filePath) throws XmlException;

    /* the function load saved system status */
    void loadSavedSystemStatus(String filePath) throws XmlException;

    /* the function save the system status to file */
    void saveSystemStatus(String filePath) throws XmlException;

    /* the function return graph info */
    GraphDTO getGraphGeneralInfo() throws MenuOptionException;

    /* the function return target info */
    TargetDTO getTargetInfo(String targetName) throws MenuOptionException;

    /* the function return targets paths */
    List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection) throws MenuOptionException;

    /* the function return simulation info */
    SimulationSummeryDTO runSimulate(int timePerTarget, double chancePerTarget, double chanceWarning, boolean isRandom, SimulationEntryPoint entryPoint, Consumer<String> consumer);

    /* the function return target circle */
    LinkedHashSet<TargetDTO> getTargetCircle(String targetName) throws MenuOptionException;
}
