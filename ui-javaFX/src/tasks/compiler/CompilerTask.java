package tasks.compiler;

import Enums.SimulationEntryPoint;
import Enums.TasksName;
import engineManager.EngineManager;
import javafx.concurrent.Task;
import tasks.TaskToUI;
import tasks.UIAdapter;

import java.util.Collection;
import java.util.function.Consumer;

public class CompilerTask extends Task<Boolean> {
    public EngineManager engineManager;
    private TaskToUI taskToUI;
    private Collection<String> targetsToRun;
    private String sourceFolder;
    private String productFolder;
    private SimulationEntryPoint entryPoint;
    private int maxParallel;
    private Consumer<String> consumer;

    public CompilerTask(EngineManager engineManager, UIAdapter uiAdapter, Collection<String> targetsToRun, String sourceFolder, String productFolder ,SimulationEntryPoint entryPoint, Consumer<String> consumer, int maxParallel){
        this.engineManager = engineManager;
        this.targetsToRun = targetsToRun;
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
        this.entryPoint = entryPoint;
        this.consumer = consumer;
        this.maxParallel = maxParallel;
        this.taskToUI = new TaskToUI(engineManager, uiAdapter, TasksName.COMPILATION);
    }

    @Override
    protected Boolean call() throws Exception {
        Thread t = new Thread(this.taskToUI);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        this.engineManager.runCompiler(this.targetsToRun, this.sourceFolder, this.productFolder, this.entryPoint, this.consumer, this.maxParallel);

        return Boolean.TRUE;
    }

}
