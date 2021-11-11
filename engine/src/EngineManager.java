import graph.Graph;
import scema.generated.GPUPConfiguration;
import scema.generated.GPUPDescriptor;
import scema.generated.GPUPTarget;
import scema.generated.GPUPTargets;
import target.Target;
import xml.Xml;
import xml.XmlException;

import java.util.HashSet;
import java.util.Set;

public class EngineManager {
    /* the function load new graph from xml file */
    public Graph load(String filePath) throws XmlException {
        Set<String> validTargetUnique = new HashSet<>();
        Set<String> validTargetExist = new HashSet<>();
        Set<String> errorTargetsDependencies = new HashSet<>();
        Set<String> errorTargetsUnique = new HashSet<>();
        Object root = Xml.readFromXml(filePath, new GPUPDescriptor());
        GPUPConfiguration gpupConfiguration = ((GPUPDescriptor) root).getGPUPConfiguration();
        GPUPTargets gpupTargets = ((GPUPDescriptor) root).getGPUPTargets();
        Graph graph = new Graph(gpupConfiguration.getGPUPGraphName(), gpupConfiguration.getGPUPWorkingDirectory());
        for(GPUPTarget gpupTarget: gpupTargets.getGPUPTarget()){
            Target target;
            String targetName = gpupTarget.getName();
            if(validTargetUnique.contains(targetName))
                errorTargetsUnique.add(targetName);
            validTargetUnique.add(targetName);
            validTargetExist.add(targetName);
            if (!graph.getGraphMap().containsKey(gpupTarget))
                graph.addToGr(new Target(targetName));
            target = graph.getTargetByName(targetName);
            if(gpupTarget.getGPUPUserData() != null)
               target.updateGeneralInfo(gpupTarget.getGPUPUserData());
            if(gpupTarget.getGPUPTargetDependencies() != null)
                target.load(gpupTarget.getGPUPTargetDependencies(), graph, validTargetExist, errorTargetsDependencies);
        }
        if(!errorTargetsUnique.isEmpty())
            throw new XmlException("targets " + errorTargetsUnique.toString() + " already exist in this file");
        if(!compareValidTargetList(validTargetUnique, validTargetExist))
            throw new XmlException("targets " + validTargetExist.toString() + " not exist in this file but other targets dependsOn " +
                    "or requiredFor them");
        if(!errorTargetsDependencies.isEmpty())
            throw new XmlException(errorTargetsDependencies.toString());
        return graph;
    }

    /* the function return true if there is a target that doesn't exist on the file but
     other target has type of dependencies with hem and false else */
    boolean compareValidTargetList(Set<String> validTargetUnique, Set<String> validTargetExist){
        for(String name: validTargetUnique){
            if(validTargetExist.contains(name))
                validTargetExist.remove(name);
        }
        if(validTargetExist.isEmpty())
            return true;
        return false;
    }
}
