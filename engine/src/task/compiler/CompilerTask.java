package task.compiler;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import Enums.TargetStatus;
import exceptions.TaskException;
import graph.Graph;
import target.Target;
import task.Task;
import task.simulation.SimulationTaskManager;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class CompilerTask extends Task implements Runnable {
    private String sourceFolder;
    private String productFolder;
    private List<Consumer<String>> consumersList;


    public  CompilerTask(Graph graph, String sourceFolder, String productFolder, Consumer<String> consumer, Target target,
                         CompilerTaskManager manager) throws TaskException {
        this.consumersList = new ArrayList<>();
        this.sourceFolder = sourceFolder;
        this.productFolder = productFolder;
        this.target = target;
        this.consumersList.add(consumer);
        Consumer<String> consumerFile = s ->printTargetToFile(s);
        this.consumersList.add(consumerFile);
        this.managerCompiler = manager;
        this.folderPath = managerCompiler.getFolderPath();
    }

    @Override
    public void run() {
        synchronized (this.managerCompiler.getPausedObj()){
            try{
                while (this.managerCompiler.isPaused()){
                    this.managerCompiler.getPausedObj().wait();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        synchronized (this.managerCompiler.getTaskRuntimeDTO()){
            this.managerCompiler.getTaskRuntimeDTO().getTargetByName(target.getName()).setStatus(TargetRuntimeStatus.IN_PROCESS);
        }
        try{
            long startTime = System.currentTimeMillis();
            this.path = createTargetFile(target.getName());
            writeToConsumers(consumersList, "Target "+ target.getName() + " start run.");
            target.setStatus(TargetStatus.IN_PROCESS);
            int result;
            InputStream errorStream = null;
            String error = "";
            if(target.getGeneralInfo() != null) {
                String source = this.sourceFolder;
                String path = target.getGeneralInfo().replace(".", File.separator);
                Path pathFile = Paths.get(source, "/", path);
                writeToConsumers(consumersList, "File: " + pathFile.toString() + " going to perform compilation");
                Path newFolder = Paths.get(this.productFolder);
                //String[] command = {"/bin/bash", "-c", "javac -d " + newFolder + " -cp " + newFolder + " " + pathFile + ".java"};
                String[] command = {"cmd.exe", "/c", "javac -d " + newFolder + " -cp " + newFolder + " " + pathFile + ".java"};
                String executionLine = "";
                for(String str:command){
                    executionLine += str;
                }
                writeToConsumers(consumersList, "Full execution line: " + executionLine);
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                Process process = processBuilder.start();
                process.waitFor();
                result = process.exitValue();
                errorStream = process.getErrorStream();
            } else{
                result = 1;
            }
            target.setStatus(TargetStatus.FINISHED);
            if(result == 0)
                target.setRunStatus(TargetRunStatus.SUCCESS);
            else {
                target.setRunStatus(TargetRunStatus.FAILURE);
                int c = 0;
                while ((c = errorStream.read()) != -1) {
                    error += (char)c;
                }
            }
            long endTime = System.currentTimeMillis();
            //this.summery.addToTargets(target, convertMillisToHMS(time));
            writeToConsumers(consumersList, "Target: " + target.getName()+" done with status: "+ target.getRunStatus().toString());
            if(!error.equals(""))
                writeToConsumers(consumersList, "Target failed with error message: " + error);
            writeToConsumers(consumersList, "Process time for  "+ target.getName()+ " is: " + (endTime - startTime) +" ms");
            this.managerCompiler.handleFinish(target.getRunStatus(),target,consumersList,this);
        }catch (Exception e){
            System.out.println("Error in compiler task - run()");
            e.printStackTrace();
        }
    }

}
