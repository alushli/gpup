package components.tasks.compiler;

import components.adminEnums.SimulationEntryPointAdmin;
import components.adminEnums.TasksNameAdmin;
import components.tasks.TaskToUI;
import components.tasks.UIAdapter;
import engineManager.EngineManager;
import javafx.concurrent.Task;

import java.util.Collection;
import java.util.function.Consumer;

public class CompilerTask extends Task<Boolean> {
    private TaskToUI taskToUI;
    private Collection<String> targetsToRun;
    private String sourceFolder;
    private String productFolder;
    private SimulationEntryPointAdmin entryPoint;
    private int maxParallel;
    private Consumer<String> consumer;

    public CompilerTask(UIAdapter uiAdapter, Collection<String> targetsToRun, String sourceFolder, String productFolder , SimulationEntryPointAdmin entryPoint, Consumer<String> consumer, int maxParallel){
        this.targetsToRun = targetsToRun;
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
        this.entryPoint = entryPoint;
        this.consumer = consumer;
        this.maxParallel = maxParallel;
        this.taskToUI = new TaskToUI(uiAdapter, TasksNameAdmin.COMPILATION);
    }

    @Override
    protected Boolean call() throws Exception {
        Thread t = new Thread(this.taskToUI);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();

        // run task
        //this.engineManager.runCompiler(this.targetsToRun, this.sourceFolder, this.productFolder, this.entryPoint, this.consumer, this.maxParallel);

        return Boolean.TRUE;
    }

}
