package task;

import Enums.TargetStatus;
import engineManager.EngineManager;
import target.Target;
import task.simulation.SimulationTaskManager;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public abstract class Task implements Runnable{
    protected Target target;
    protected String folderPath, path;
    protected SimulationTaskManager manager;

    /* the function initialize the graph */
    protected void initGraph(){
        for (Target target : EngineManager.graphStatic.getGraphMap().keySet()){
            target.setStatus(TargetStatus.FROZEN);
        }
    }

    /* the function write to the consumers */
    public void writeToConsumers(List<Consumer<String>> consumersList, String str){
        consumersList.get(0).accept(str);
        consumersList.get(1).accept(str);
    }

    /* the function create target simulation files */
    protected String createTargetFile(String targetName){
        try {
            Path path = Paths.get(this.folderPath, targetName + ".txt");
            File file = new File(path.toUri());
            file.createNewFile();
            return path.toUri().getPath();
        } catch (Exception e){
            return null;
        }
    }

    /* the function write to target simulation file */
    protected void printTargetToFile(String str){
        try {
            FileWriter file = new FileWriter(this.path, true);
            file.write(str + "\n\r");
            file.close();
        }catch (Exception e){}
        }

    public static String convertMillisToHMS(long millis){
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    public String getTargetName(){
        return target.getName();
    }

}
