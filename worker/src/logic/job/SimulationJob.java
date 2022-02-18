package logic.job;

import logic.registeredExecution.RegisteredExecution;
import logic.taskManager.RunTimeTargetDetails;
import newEnums.TargetRunStatus;

import java.util.Random;

public class SimulationJob extends GeneralJob{

    private final boolean isRandomTime;
    private final int targetProcessingTime;
    private final double successRate;
    private final double warningRate;

    public SimulationJob (RunTimeTargetDetails runTimeTargetsDetails, String targetName, String generalInfo, RegisteredExecution registeredExecutions) {
        super(runTimeTargetsDetails, targetName,generalInfo, registeredExecutions.getName());

        this.isRandomTime = registeredExecutions.isRandomTime();
        this.targetProcessingTime = registeredExecutions.getTargetProcessingTime();
        this.successRate = registeredExecutions.getSuccessRate();
        this.warningRate = registeredExecutions.getWarningRate();
    }


    @Override
    public void specificJob() {
        float runTime;

        if (isRandomTime) { // In case user wanted random processing time
            runTime = getRandNum() * targetProcessingTime;
        }
        else {
            runTime = targetProcessingTime;
        }

        this.logsManager.addToLogs("The target goes to sleep for: " + runTime + " ms.");
        this.logsManager.addToLogs("Begins to sleep.");

        //this.runTimeTargetsDetails.addLogRT(this.targetName, this.executionName, this.logsManager.getLogs());

        try {
            Thread.sleep((long) runTime);
        } catch (InterruptedException ignored) {} // Nothing to do if Thread.sleep() fails

        this.logsManager.addToLogs("Finished sleeping.");

        //this.runTimeTargetsDetails.addLogRT(this.targetName, this.executionName, this.logsManager.getLogs());

        if (getRandNum() <= successRate ) {
            if (getRandNum() <= warningRate) {
                this.runResult = TargetRunStatus.WARNING;
            }
            else {
                this.runResult = TargetRunStatus.SUCCESS;
            }
        }
        else {
            this.runResult = TargetRunStatus.FAILURE;
        }
    }

    private float getRandNum() {
        Random random = new Random();
        return random.nextFloat();
    }
}
