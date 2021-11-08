package task;

import graph.Graph;
import target.Target;

import java.util.List;

public class SimulationTask {
    private static Graph graphStatic;

    // need to send copy of the graph
    // need to handle incremental run
    public void run(Graph graph){
        List<Target> runnableList = graph.getRunnableTargets();
        while(!graph.isEmpty()){
            for(Target target : runnableList){
                target.run();
                graph.removeTarget(target);

                System.out.println("run " + target.toString());
            }
            runnableList = graph.getRunnableTargets();
        }
        graphStatic = graph;
    }

}
