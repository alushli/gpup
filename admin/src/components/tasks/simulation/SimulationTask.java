package components.tasks.simulation;

import components.adminEnums.SimulationEntryPointAdmin;
import components.adminEnums.TasksNameAdmin;
import components.tasks.TaskToUI;
import components.tasks.UIAdapter;
import javafx.concurrent.Task;
import java.util.Collection;
import java.util.function.Consumer;

public class SimulationTask extends Task<Boolean> {
    private TaskToUI taskToUI;
    private Collection<String> targetsToRun;
    private int processTime;
    private double chanceTargetSuccess;
    private double chanceTargetWarning;
    private boolean isRandom;
    private SimulationEntryPointAdmin entryPoint;
    private int maxParallel;
    private Consumer<String> consumer;

    public SimulationTask(UIAdapter uiAdapter, Collection<String> targetsToRun, int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPointAdmin entryPoint, Consumer<String> consumer, int maxParallel){
        this.targetsToRun = targetsToRun;
        this.processTime = processTime;
        this.chanceTargetSuccess = chanceTargetSuccess;
        this.chanceTargetWarning = chanceTargetWarning;
        this.isRandom = isRandom;
        this.entryPoint = entryPoint;
        this.consumer = consumer;
        this.maxParallel = maxParallel;
        this.taskToUI = new TaskToUI(uiAdapter, TasksNameAdmin.SIMULATION);
    }

    @Override
    protected Boolean call() throws Exception {
        Thread t = new Thread(this.taskToUI);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();

        //run task
        //this.engineManager.runSimulate(this.targetsToRun, this.processTime, this.chanceTargetSuccess, this.chanceTargetWarning, this.isRandom, this.entryPoint, this.consumer, this.maxParallel);

        return Boolean.TRUE;
    }

}
