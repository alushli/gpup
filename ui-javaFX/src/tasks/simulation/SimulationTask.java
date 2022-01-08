package tasks.simulation;

import Enums.SimulationEntryPoint;
import Enums.TasksName;
import engineManager.EngineManager;
import javafx.concurrent.Task;
import tasks.TaskToUI;
import tasks.UIAdapter;

import java.util.Collection;
import java.util.function.Consumer;

public class SimulationTask extends Task<Boolean> {
    public EngineManager engineManager;
    private TaskToUI taskToUI;
    private Collection<String> targetsToRun;
    private int processTime;
    private double chanceTargetSuccess;
    private double chanceTargetWarning;
    private boolean isRandom;
    private SimulationEntryPoint entryPoint;
    private int maxParallel;
    private Consumer<String> consumer;

    public SimulationTask(EngineManager engineManager, UIAdapter uiAdapter, Collection<String> targetsToRun, int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPoint entryPoint, Consumer<String> consumer, int maxParallel){
        this.engineManager = engineManager;
        this.targetsToRun = targetsToRun;
        this.processTime = processTime;
        this.chanceTargetSuccess = chanceTargetSuccess;
        this.chanceTargetWarning = chanceTargetWarning;
        this.isRandom = isRandom;
        this.entryPoint = entryPoint;
        this.consumer = consumer;
        this.maxParallel = maxParallel;
        this.taskToUI = new TaskToUI(engineManager, uiAdapter, TasksName.SIMULATION);
    }

    @Override
    protected Boolean call() throws Exception {
        Thread t = new Thread(this.taskToUI);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        this.engineManager.runSimulate(this.targetsToRun, this.processTime, this.chanceTargetSuccess, this.chanceTargetWarning, this.isRandom, this.entryPoint, this.consumer, this.maxParallel);

        return Boolean.TRUE;
    }

}
