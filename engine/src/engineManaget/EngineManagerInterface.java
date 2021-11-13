package engineManaget;

import dtoObjects.GraphDTO;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import exceptions.XmlException;
import target.SimulationEntryPoint;

import java.util.List;

public interface EngineManagerInterface {
    void load(String filePath) throws XmlException;
    GraphDTO getGraphGeneralInfo();
    TargetDTO getTargetInfo(String targetName);
    List<List<TargetDTO>> getTargetsPath(String src, String des, String typeOfConnection);
    SimulationSummeryDTO runSimulate(SimulationEntryPoint entryPoint);
}
