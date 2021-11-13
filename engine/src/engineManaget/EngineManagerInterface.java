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
    void load(String filePath) throws XmlException;
    GraphDTO getGraphGeneralInfo() throws MenuOptionException;
    TargetDTO getTargetInfo(String targetName) throws MenuOptionException;
    List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection) throws MenuOptionException;
    SimulationSummeryDTO runSimulate(SimulationEntryPoint entryPoint);
    LinkedHashSet<TargetDTO> getTargetCircle(String targetName) throws MenuOptionException;
}
