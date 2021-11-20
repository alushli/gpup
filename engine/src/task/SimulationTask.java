package task;

import Enums.TargetRunStatus;
import dtoObjects.SimulationSummeryDTO;
import graph.Graph;
import target.Target;
import Enums.TargetStatus;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SimulationTask {
    public static Graph graphStatic;//save last graph(copy)
    public static int count = 0;//how many simulations ran

    private SimulationSummeryDTO summery;
    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;


    public  SimulationTask(Graph graph, int timePerTarget, double chancePerTarget, double chanceWarning, boolean isRandom,
                           boolean fromScratch, Consumer<String> consumer){
        if(fromScratch || (!fromScratch && graphStatic == null) ){
            graphStatic = new Graph(graph);
            count = 0;
        }else{
            count++;
        }
        this.processTime = timePerTarget;
        this.chanceSuccess = chancePerTarget;
        this.chanceWarning = chanceWarning;
        this.isRandom = isRandom;
        this.summery = new SimulationSummeryDTO();
        initGraph();
        run(consumer);
    }

    private void initGraph(){
        for (Target target : graphStatic.getGraphMap().keySet()){
            target.setStatus(TargetStatus.FROZEN);
        }
    }
    public void run(Consumer<String> consumer){
        long startTime = System.currentTimeMillis();
        List<Target> runnableList = graphStatic.getRunnableTargets();
        Set<Target> skipped = new HashSet<>();
        Set<Target> failed = new HashSet<>();
        Set<Target> succeed = new HashSet<>();
        while (!runnableList.isEmpty()){
            Target target = runnableList.remove(runnableList.size()-1);
            boolean isSucceed= target.run(this.processTime,this.chanceSuccess,this.chanceWarning,this.isRandom,this.summery, consumer);
            if(isSucceed){
                succeed.add(target);
                handleSucceed(target, graphStatic,runnableList,summery, consumer);
            }else{
                failed.add(target);
                handleFailure(target, graphStatic, skipped,summery,consumer);
            }
        }
        long endTime = System.currentTimeMillis();
        summery.setHMS(convertMillisToHMS(endTime-startTime));
        summery.setCollections(failed,succeed,skipped);
    }


    public SimulationSummeryDTO getSummery() {
        return summery;
    }

    private String convertMillisToHMS(long millis){
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    private void handleSucceed(Target target, Graph graph, List<Target> runnableList, SimulationSummeryDTO simulationSummeryDTO,
                               Consumer<String> consumer){
        Set<Target> targetsReq = target.getRequiredForList();
        for(Target target1 : targetsReq){
            graph.removeConnection(target1, target);
            if(graph.isRunable(target1) && target1.getStatus() != TargetStatus.SKIPPED){
                consumer.accept("Target "+ target1.getName()+ " turned WAITING.");
                simulationSummeryDTO.addOutput("Target "+ target1.getName()+ " turned WAITING.");
                target1.setStatus(TargetStatus.WAITING);
                runnableList.add(target1);
            }
        }
        graphStatic.removeFromGraph(target);
    }

    private void handleFailure(Target target, Graph graph, Set<Target> skipped, SimulationSummeryDTO simulationSummeryDTO,Consumer<String > consumer){
        for (Target target1 : target.getRequiredForList()){
            handleFailureRec(target1, graph, skipped, simulationSummeryDTO,consumer);
        }
    }
    private void handleFailureRec(Target target, Graph graph, Set<Target> skipped, SimulationSummeryDTO simulationSummeryDTO
    ,Consumer<String > consumer){
        skipped.add(target);
        consumer.accept("Target: "+target.getName()+ " turned skipped");
        simulationSummeryDTO.addOutput("Target: "+target.getName()+ " turned skipped");
        target.setStatus(TargetStatus.SKIPPED);
        Set<Target> requireForTargets = target.getRequiredForList();
        for (Target target1 : requireForTargets){
            handleFailureRec(target1, graph, skipped, simulationSummeryDTO,consumer);
        }
    }

}
