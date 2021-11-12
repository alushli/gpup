import exceptions.GeneralException;
import graph.Graph;
import exceptions.XmlException;

public class EngineMain {
    public static void main(String[] args) throws XmlException {


    try {
        String filePath = "resources/ex1-small.xml";
        Graph validGraph, graph;
        EngineManager engineManager = new EngineManager();
        graph = engineManager.load(filePath);
        validGraph = graph;
        System.out.println(graph.getGraphInfo());
    } catch (GeneralException e) {
        System.out.println(e.errorInfo() + e.getMessage());
    }

    }


}
