package tasks.simulation;

import Enums.SimulationEntryPoint;
import dtoObjects.TargetRuntimeDTO;
import dtoObjects.TaskSummeryDTO;
import engineManager.EngineManager;
import javafx.concurrent.Task;
import tasks.TaskToUI;
import tasks.UIAdapter;

import javax.swing.text.StyledEditorKit;
import java.util.Set;
import java.util.Timer;
import java.util.function.Consumer;

public class SimulationTask extends Task<Boolean> {
    public EngineManager engineManager;
    private TaskToUI taskToUI;
    private int processTime;
    private double chanceTargetSuccess;
    private double chanceTargetWarning;
    private boolean isRandom;
    private SimulationEntryPoint entryPoint;
    private Consumer<String> consumer;

    private boolean isDone;
    private boolean isPause;

    public SimulationTask(EngineManager engineManager, UIAdapter uiAdapter, int processTime, double chanceTargetSuccess, double chanceTargetWarning, boolean isRandom, SimulationEntryPoint entryPoint, Consumer<String> consumer){
        this.engineManager = engineManager;
        this.processTime = processTime;
        this.chanceTargetSuccess = chanceTargetSuccess;
        this.chanceTargetWarning = chanceTargetWarning;
        this.isRandom = isRandom;
        this.entryPoint = entryPoint;
        this.consumer = consumer;
        this.taskToUI = new TaskToUI(engineManager, uiAdapter);
    }

    @Override
    protected Boolean call() throws Exception {
        Thread t = new Thread(this.taskToUI);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        this.engineManager.runSimulate(this.processTime, this.chanceTargetSuccess, this.chanceTargetWarning, this.isRandom, this.entryPoint, this.consumer);
        return null;
    }

}
