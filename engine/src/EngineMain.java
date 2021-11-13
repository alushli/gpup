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
        engineManager.load(filePath);


        SimulationTask simulationTask = new SimulationTask();
        simulationTask.run(engineManager.getGraph(),500,0.2,false);
    } catch (GeneralException e) {
        System.out.println(e.errorInfo() + e.getMessage());
    }
    }

    }

