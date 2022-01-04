package task.simulation;

import Enums.TargetRunStatus;
import exceptions.TaskException;
import graph.Graph;
import target.Target;
import Enums.TargetStatus;
import task.Task;

import java.util.*;
import java.util.function.Consumer;


public class SimulationTask extends Task implements Runnable{
    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;
    List<Consumer<String>> consumersList;


    public  SimulationTask(Graph graph, int timePerTarget, double chancePerTarget, double chanceWarning, boolean isRandom,
                           Consumer<String> consumer, Target target, SimulationTaskManager manager) throws TaskException {
        this.consumersList = new ArrayList<>();

        this.processTime = timePerTarget;
        this.chanceSuccess = chancePerTarget;
        this.chanceWarning = chanceWarning;
        this.isRandom = isRandom;
        this.target = target;
        this.consumersList.add(consumer);
        Consumer<String> consumerFile = s ->printTargetToFile(s);
        this.consumersList.add(consumerFile);
        this.manager = manager;
        this.folderPath = manager.getFolderPath();

   }

    @Override
    public void run() {
        try{
            this.path = createTargetFile(target.getName());
            int time = this.processTime;
            writeToConsumers(consumersList, "Target "+ target.getName() + " start run.");
            if(target.getGeneralInfo() != null){
                writeToConsumers(consumersList, "General info of the target: " + target.getGeneralInfo());
            }
            target.setStatus(TargetStatus.IN_PROCESS);
            Random random = new Random();
            if(isRandom){
                time = random.nextInt(time);
            }
            long chanceInt = Math.round(chanceSuccess*10);
            boolean isSuccess = (random.nextInt(9)< chanceInt);
            writeToConsumers(consumersList, "Target "+ target.getName()+ " start sleep");
            Thread.sleep(time);
            writeToConsumers(consumersList, "Target "+ target.getName()+ " done sleep");
            target.setStatus(TargetStatus.FINISHED);
            if(isSuccess){
                if(random.nextInt(9)<= Math.round(chanceWarning*10)){
                    target.setRunStatus(TargetRunStatus.WARNING);
                }else{
                    target.setRunStatus(TargetRunStatus.SUCCESS);
                }
            }else{
                target.setRunStatus(TargetRunStatus.FAILURE);
            }
            //this.summery.addToTargets(target, convertMillisToHMS(time));
            writeToConsumers(consumersList, "Target: " + target.getName()+" done with status: "+target.getRunStatus().toString());
            writeToConsumers(consumersList, "Target "+ target.getName()+ " time: " + convertMillisToHMS(time));
            this.manager.handleFinish(target.getRunStatus(),target,consumersList,this);
        }catch (Exception e){
            System.out.println("Error in simulation task - run()");
        }
    }


}

