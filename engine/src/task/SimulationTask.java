package task;

import Enums.TargetRunStatus;
import Enums.TasksName;
import dtoObjects.SimulationSummeryDTO;
import exceptions.TaskException;
import graph.Graph;
import target.Target;
import Enums.TargetStatus;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SimulationTask {
    public static Graph graphStatic; //save last graph(copy)
    public static int count = 0; //how many simulations ran

    private SimulationSummeryDTO summery;
    private int processTime;
    private boolean isRandom;
    private double chanceSuccess ,chanceWarning;
    private String folderPath, path;

    /* constructor */
    public  SimulationTask(Graph graph, int timePerTarget, double chancePerTarget, double chanceWarning, boolean isRandom,
                           boolean fromScratch, Consumer<String> consumer) throws TaskException {
        if(fromScratch ){
            graphStatic = new Graph(graph);
            count = 0;
        }else if(!fromScratch && graphStatic == null){
            consumer.accept("Simulation has not run yet on the current graph so it will run from scratch.");
            graphStatic = new Graph(graph);
            count = 0;
        }
        else{
            if(graphStatic.getGraphMap().size() == 0){
                consumer.accept("The graph is empty, you can choose to run again from scratch.");
            }
            count++;
        }
        this.processTime = timePerTarget;
        this.chanceSuccess = chancePerTarget;
        this.chanceWarning = chanceWarning;
        this.isRandom = isRandom;
        this.summery = new SimulationSummeryDTO();
        initGraph();
        this.folderPath =saveSimulationFolder();
        run(consumer);
    }

    /* the function initialize the graph */
    private void initGraph(){
        for (Target target : graphStatic.getGraphMap().keySet()){
            target.setStatus(TargetStatus.FROZEN);
        }
    }

    /* the function run the simulation */
    public void run(Consumer<String> consumer){
        long startTime = System.currentTimeMillis();
        List<Target> runnableList = graphStatic.getRunnableTargets();
        Set<Target> skipped = new HashSet<>();
        Set<Target> failed = new HashSet<>();
        Set<Target> succeed = new HashSet<>();
        Set<Target> warnings = new HashSet<>();

        Consumer<String> consumerFile = s ->printTargetToFile(s);
        List<Consumer<String>> consumersList = new ArrayList<>();
        consumersList.add(consumer);
        consumersList.add(consumerFile);
        while (!runnableList.isEmpty()){
            Target target = runnableList.remove(runnableList.size()-1);
            boolean isSucceed= runTarget(target,consumersList);
            if(isSucceed){
                if(target.getRunStatus().equals(TargetRunStatus.WARNING)){
                    warnings.add(target);
                }else{
                    succeed.add(target);
                }
                handleSucceed(target, graphStatic,runnableList, consumersList);
            }else{
                failed.add(target);
                handleFailure(target, skipped, consumersList);
            }
        }
        long endTime = System.currentTimeMillis();
        summery.setHMS(convertMillisToHMS(endTime-startTime));
        summery.setCounts(skipped.size(), failed.size(),succeed.size(), warnings.size());
    }

    /* the function return summery of simulation */
    public SimulationSummeryDTO getSummery() {
        return summery;
    }

    /* the function convert millis to HMS */
    private String convertMillisToHMS(long millis){
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    /* the function handle succeed target */
    private void handleSucceed(Target target, Graph graph, List<Target> runnableList, List<Consumer<String>> consumersList){
        Set<Target> targetsReq = target.getRequiredForList();
        for(Target target1 : targetsReq){
            graph.removeConnection(target1, target);
            if(graph.isRunnable(target1) && !(target1.getStatus().equals(TargetStatus.SKIPPED))){
                writeToConsumers(consumersList, "Target "+ target1.getName()+ " turned WAITING.");
                target1.setStatus(TargetStatus.WAITING);
                runnableList.add(target1);
            }
        }
        graphStatic.removeFromGraph(target);
    }

    /* the function handle failure target */
    private void handleFailure(Target target, Set<Target> skipped, List<Consumer<String>> consumersList){
        for (Target target1 : target.getRequiredForList()){
            handleFailureRec(target1, skipped, consumersList, target);
        }
    }

    /* help function for handleFailure */
    private void handleFailureRec(Target target, Set<Target> skipped, List<Consumer<String>> consumersList, Target dad){
        skipped.add(target);
        writeToConsumers(consumersList,"Target: "+target.getName()+ " turned skipped (because "+ dad.getName()+" failed/skipped)");
        target.setRunStatus(TargetRunStatus.SKIPPED);
        summery.addToTargets(target, "skipped ");
        Set<Target> requireForTargets = target.getRequiredForList();
        for (Target target1 : requireForTargets){
            handleFailureRec(target1, skipped,consumersList, target);
        }
    }

    /* the function save the simulation folder */
    public String saveSimulationFolder() throws TaskException {
        Graph graph = graphStatic;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        String strDate = simpleDateFormat.format(date);
        File folder = new File(graph.getWorkingDirectory()+ "\\" + TasksName.SIMULATION + "-" + strDate);
        if(folder.mkdir())
            return folder.getAbsolutePath();
        else
            throw new TaskException("The path doesn't exist or has invalid characters, please change the xml and upload again.");
    }

    /* the function run target */
    public boolean runTarget(Target target, List<Consumer<String>> consumersList){
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
            this.summery.addToTargets(target, convertMillisToHMS(time));
            writeToConsumers(consumersList, "Target: " + target.getName()+" done with status: "+target.getRunStatus().toString());
            writeToConsumers(consumersList, "Target "+ target.getName()+ " time: " + convertMillisToHMS(time));
            return isSuccess;
        }catch (Exception e){
        }
        return false;
    }

    /* the function write to the consumers */
    private void writeToConsumers(List<Consumer<String>> consumersList, String str){
        consumersList.get(0).accept(str);
        consumersList.get(1).accept(str);
    }

    /* the function create target simulation files */
    private String createTargetFile(String targetName){
        try{
        File file = new File(this.folderPath +"\\" + targetName+".txt");
        file.createNewFile();
        return this.folderPath +"\\" + targetName+".txt";
        } catch (Exception e){
            return null;
        }
    }

    /* the function write to target simulation file */
    private void printTargetToFile(String str){
        try {
            FileWriter file = new FileWriter(this.path, true);
            file.write(str + "\n\r");
            file.close();
        }catch (Exception e){}
        }
}
