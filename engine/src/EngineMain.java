import exception.GeneralException;
import graph.Graph;
import xml.XmlException;

public class EngineMain {
    public static void main(String[] args) throws XmlException {
//        try{
//            File file = new File("resources/ex1-small.xml");
//            JAXBContext jaxbContext = JAXBContext.newInstance(GPUPDescriptor.class);
//            Unmarshaller jaxbUnmar = jaxbContext.createUnmarshaller();
//            GPUPDescriptor targets = (GPUPDescriptor) jaxbUnmar.unmarshal(file);
//            System.out.println(targets);
//        }catch (Exception e){
//        }


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
//        Target a = new Target("a");
//        Target b = new Target("b");
//        Target c = new Target("c");
//        Target d = new Target("d");
//        Target e = new Target("e");
//        Target f = new Target("f");
////        a.addToRequiredForList(d);
////        b.addToRequiredForList(d);
////        b.addToRequiredForList(e);
////        c.addToRequiredForList(e);
////        d.addToRequiredForList(e);
////        d.addToDependsOnList(a);
////        d.addToDependsOnList(b);
////        e.addToDependsOnList(b);
////        e.addToDependsOnList(d);
////        e.addToDependsOnList(c);
////        a.addToRequiredForList(b);
////        b.addToDependsOnList(a);
////        e.addToDependsOnList(d);
////        e.addToDependsOnList(b);
////        e.addToDependsOnList(c);
////        d.addToDependsOnList(b);
////        //d.addToDependsOnList(a);
////        a.addToDependsOnList(d);
////        b.addToDependsOnList(a);
////        a.addToRequiredForList(b);
////        a.addToRequiredForList(d);
////        a.addToRequiredForList(c);
////        c.addToRequiredForList(d);
////        d.addToRequiredForList(e);
////        e.addToRequiredForList(f);
////        f.addToRequiredForList(a);
////        f.addToRequiredForList(b);
//        a.addToDependsOnList(c);
//        a.addToDependsOnList(b);
//        a.addToDependsOnList(d);
//        c.addToDependsOnList(d);
//        d.addToDependsOnList(e);
//        f.addToDependsOnList(a);
//        f.addToDependsOnList(b);
//        e.addToDependsOnList(f);
//        b.addToDependsOnList(c);
//        d.addToDependsOnList(b);
//
//
//        Graph graph = new Graph();
//        graph.addToGr(f);
//        graph.addToGr(a);
//        graph.addToGr(b);
//        graph.addToGr(c);
//        graph.addToGr(d);
//        graph.addToGr(e);
//
//
//        SimulationTask sim = new SimulationTask();
//        //sim.run(graph);
//       // System.out.println(graph.getGraphInfo());
//        List<List<Target>> list = new ArrayList<>();
//        list = graph.findAllPaths(b,f);
//        for(List<Target> ls: list){
//            //ls.add(0, e);
//            System.out.println(ls.toString());
////            System.out.println("New");
////            System.out.println(e.toString());
////            for(Target target: ls){
////                System.out.println(target.toString());
////            }
//        }
//        System.out.println("********************");
//       LinkedHashSet<Target> set = graph.findCircle(b);
//        for(Target target:set){
//            System.out.println(target);
//        }
//
//    }

}
