package tasks;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import dtoObjects.TargetRuntimeDTO;
import dtoObjects.TaskRuntimeDTO;
import engineManager.EngineManager;
import javafx.concurrent.Task;
import tasks.simulation.SimulationTask;

import java.util.HashSet;
import java.util.Set;

import static Enums.TargetRunStatus.SKIPPED;

public class TaskToUI implements Runnable {
    private EngineManager engineManager;
    private TaskRuntimeDTO taskRuntimeDTO;
    private UIAdapter uiAdapter;
    private boolean isDone = false;
    private Boolean synchroObj;

    public TaskToUI(EngineManager engineManager, UIAdapter uiAdapter) {
        this.engineManager = engineManager;
        this.uiAdapter = uiAdapter;
        this.synchroObj = engineManager.getSynchroObj();
    }

    @Override
    public void run() {
        try {
            while (!isDone) {
                if (this.engineManager.getSimulationTaskManager() != null && this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO() != null) {
                    this.taskRuntimeDTO = this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO();
                    synchronized (this.synchroObj){
                        this.uiAdapter.updateTotalTargets(String.valueOf(this.taskRuntimeDTO.getCountTotal()));
                        this.uiAdapter.updateFinishTargets(String.valueOf(this.taskRuntimeDTO.getCountFinished()));
                        this.uiAdapter.updateProgressBar((float) this.taskRuntimeDTO.getCountFinished() / this.taskRuntimeDTO.getCountTotal());
                        for (TargetRuntimeDTO targetRuntimeDTO : this.taskRuntimeDTO.getMap().values()) {
                            setUiSP(targetRuntimeDTO);
                        }
                    }
                    Thread.sleep(100);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void setUiSP(TargetRuntimeDTO target) {
        if(!target.getPrevStatus().equals(target.getStatus())){
            if(target.getPrevStatus().equals(TargetRuntimeStatus.FROZEN)) {
                if (target.getStatus().equals(TargetRuntimeStatus.WAITING))
                    this.uiAdapter.frozenToWaiting(target.getName());
                else if (target.getStatus().equals(TargetRuntimeStatus.SKIPPED))
                    this.uiAdapter.frozenToSkipped(target.getName());
                else if (target.getStatus().equals(TargetRuntimeStatus.FINISHED)) {
                    if (target.getFinishStatus().equals(TargetRunStatus.FAILURE))
                        this.uiAdapter.frozenToFailed(target.getName());
                    else if (target.getFinishStatus().equals(TargetRunStatus.WARNING))
                        this.uiAdapter.frozenToWarning(target.getName());
                    else if (target.getFinishStatus().equals(TargetRunStatus.SUCCESS))
                        this.uiAdapter.frozenToSuccess(target.getName());
                } else if (target.getStatus().equals(TargetRuntimeStatus.IN_PROCESS))
                    this.uiAdapter.frozenToProgress(target.getName());
            } else if(target.getPrevStatus().equals(TargetRuntimeStatus.WAITING)){
                if(target.getStatus().equals(TargetRuntimeStatus.IN_PROCESS))
                    this.uiAdapter.waitingToProgress(target.getName());
                else if(target.getStatus().equals(TargetRuntimeStatus.FINISHED)){
                    if(target.getFinishStatus().equals(TargetRunStatus.FAILURE))
                        this.uiAdapter.waitingToFailed(target.getName());
                    else if(target.getFinishStatus().equals(TargetRunStatus.WARNING))
                        this.uiAdapter.waitingToWarning(target.getName());
                    else if(target.getFinishStatus().equals(TargetRunStatus.SUCCESS))
                        this.uiAdapter.waitingToSuccess(target.getName());
                }
            } else if(target.getPrevStatus().equals(TargetRuntimeStatus.IN_PROCESS)) {
                if(target.getFinishStatus().equals(TargetRunStatus.FAILURE))
                    this.uiAdapter.progressToFailed(target.getName());
                else if(target.getFinishStatus().equals(TargetRunStatus.WARNING))
                    this.uiAdapter.progressToWarning(target.getName());
                else if(target.getFinishStatus().equals(TargetRunStatus.SUCCESS))
                    this.uiAdapter.progressToSuccess(target.getName());
            }

        }

    }
}