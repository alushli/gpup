import engineManaget.EngineManager;
import exceptions.GeneralException;
import graph.Graph;
import exceptions.XmlException;
import task.SimulationTask;

public class EngineMain {
    public static void main(String[] args) throws XmlException {
    try {
        String filePath = "resources/ex1-small.xml";
        Graph validGraph, graph;
        EngineManager engineManager = new EngineManager();
        graph = engineManager.loadHelper(filePath);
        validGraph = graph;
        System.out.println(graph.getGraphInfo());
        SimulationTask simulationTask = new SimulationTask();
        simulationTask.run(graph,500,0.5,false);
    } catch (GeneralException e) {
        System.out.println(e.errorInfo() + e.getMessage());
    }
    }


}
