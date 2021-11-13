package task;

import dtoObjects.SimulationSummeryDTO;
import graph.Graph;
import target.Target;
import Enums.TargetStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SimulationTask {
    private static Graph graphStatic;

    //need to check if needed output during the run or only after.
    public SimulationSummeryDTO run(Graph graph, int timePerTarget, double chancePerTarget,double chanceWarning, boolean isRandom){
        long startTime = System.currentTimeMillis();
        SimulationSummeryDTO simulationSummeryDTO = new SimulationSummeryDTO();
        List<Target> runnableList = graph.getRunnableTargets();
        Set<Target> skipped = new HashSet<>();
        Set<Target> failed = new HashSet<>();
        Set<Target> succssed = new HashSet<>();
        while (!runnableList.isEmpty()){
            Target target = runnableList.remove(runnableList.size()-1);
            boolean isSucceed= target.run(timePerTarget, chancePerTarget, chanceWarning,isRandom, simulationSummeryDTO);
            if(isSucceed){
                succssed.add(target);
                handleSucceed(target, graph,runnableList,simulationSummeryDTO);
            }else{
                failed.add(target);
                handleFailure(target, graph, skipped,simulationSummeryDTO);
            }
        }
        long endTime = System.currentTimeMillis();
        simulationSummeryDTO.setHMS(convertMillisToHMS(endTime-startTime));
        return simulationSummeryDTO;
    }

    private String convertMillisToHMS(long millis){
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    private void handleSucceed(Target target, Graph graph, List<Target> runnableList, SimulationSummeryDTO simulationSummeryDTO){
        Set<Target> targetsReq = target.getRequiredForList();
        for(Target target1 : targetsReq){
            graph.removeConnection(target1, target);
            if(graph.isRunable(target1) && target1.getStatus() != TargetStatus.SKIPPED){
                simulationSummeryDTO.addOutput("Target "+ target1.getName()+ " turned WAITING.");
                target1.setStatus(TargetStatus.WAITING);
                runnableList.add(target1);
            }
        }
    }

    private void handleFailure(Target target, Graph graph, Set<Target> skipped, SimulationSummeryDTO simulationSummeryDTO){
        for (Target target1 : target.getRequiredForList()){
            handleFailureRec(target1, graph, skipped, simulationSummeryDTO);
        }
    }
    private void handleFailureRec(Target target, Graph graph, Set<Target> skipped, SimulationSummeryDTO simulationSummeryDTO){
        skipped.add(target);
        if(target.getStatus() != TargetStatus.SKIPPED){
            simulationSummeryDTO.addOutput("Target: "+target.getName()+ " turned skipped");
            target.setStatus(TargetStatus.SKIPPED);
        }
        graph.removeFromGraph(target);
        Set<Target> requireForTargets = target.getRequiredForList();
        for (Target target1 : requireForTargets){
            handleFailureRec(target1, graph, skipped, simulationSummeryDTO);
        }
    }

}
