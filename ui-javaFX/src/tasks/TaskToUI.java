package tasks;

import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import Enums.TasksName;
import dtoObjects.TargetRuntimeDTO;
import dtoObjects.TaskRuntimeDTO;
import engineManager.EngineManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TaskToUI implements Runnable {
    private EngineManager engineManager;
    private TaskRuntimeDTO taskRuntimeDTO;
    private UIAdapter uiAdapter;
    private boolean isDone = false;
    private Boolean synchroObj;
    private TasksName tasksName;

    public TaskToUI(EngineManager engineManager, UIAdapter uiAdapter, TasksName taskName) {
        this.engineManager = engineManager;
        this.uiAdapter = uiAdapter;
        this.synchroObj = engineManager.getSynchroObj();
        this.tasksName = taskName;
    }

    @Override
    public void run() {
        try {
            Set<TargetRuntimeDTO> frozen = new HashSet<>();
            Set<TargetRuntimeDTO> waiting = new HashSet<>();
            Set<TargetRuntimeDTO> process = new HashSet<>();
            Set<TargetRuntimeDTO> skipped = new HashSet<>();
            Set<TargetRuntimeDTO> failed = new HashSet<>();
            Set<TargetRuntimeDTO> warning = new HashSet<>();
            Set<TargetRuntimeDTO> success = new HashSet<>();
            int i = 0;
            while (!isDone) {
                boolean canRun = false;
                if (this.tasksName.equals(TasksName.SIMULATION) && this.engineManager.getSimulationTaskManager() != null && this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO() != null) {
                    canRun = true;
                    this.taskRuntimeDTO = this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO();
                }
                else if(this.tasksName.equals(TasksName.COMPILATION) && this.engineManager.getCompilerTaskManager() != null && this.engineManager.getCompilerTaskManager().getTaskRuntimeDTO() != null) {
                    canRun = true;
                    this.taskRuntimeDTO = this.engineManager.getCompilerTaskManager().getTaskRuntimeDTO();
                }
                if(canRun){
                    synchronized (this.synchroObj) {
                        clearSets(frozen, waiting, process, skipped, failed, success, warning);
                        if (i == 0) {
                            this.uiAdapter.addFrozen(collectionToSet(this.taskRuntimeDTO.getMap().values()));
                            i++;
                        }
                        updateUI(frozen, waiting, process, skipped, failed, success, warning);
                        if (this.taskRuntimeDTO.getCountTotal() == this.taskRuntimeDTO.getCountFinished())
                            isDone = true;
                    }
                }
                Thread.sleep(100);
            }
            clearSets(frozen, waiting, process, skipped, failed, success, warning);
            synchronized (this.synchroObj){
                if(this.tasksName.equals(TasksName.SIMULATION))
                    this.taskRuntimeDTO = this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO();
                else
                    this.taskRuntimeDTO = this.engineManager.getCompilerTaskManager().getTaskRuntimeDTO();
                updateUI(frozen, waiting, process, skipped, failed, success, warning);
                this.engineManager.setTaskRuntimeDTO(null);
                this.engineManager.setSimulationTaskManager(null);
                this.engineManager.setCompilerTaskManager(null);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateUI(Set<TargetRuntimeDTO> frozen, Set<TargetRuntimeDTO> waiting, Set<TargetRuntimeDTO> process, Set<TargetRuntimeDTO> skipped,
                          Set<TargetRuntimeDTO> failed, Set<TargetRuntimeDTO> success, Set<TargetRuntimeDTO> warning){
        this.uiAdapter.updateTotalTargets(String.valueOf(this.taskRuntimeDTO.getCountTotal()));
        this.uiAdapter.updateFinishTargets(String.valueOf(this.taskRuntimeDTO.getCountFinished()));
        this.uiAdapter.updateProgressBar((float) this.taskRuntimeDTO.getCountFinished() / this.taskRuntimeDTO.getCountTotal());
        for (TargetRuntimeDTO targetRuntimeDTO : this.taskRuntimeDTO.getMap().values()) {
            if(!targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.NONE)){
                if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.FAILURE))
                    failed.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.SUCCESS))
                    success.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.WARNING))
                    warning.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatus.SKIPPED))
                    skipped.add(targetRuntimeDTO);
            }
            else{
                if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.WAITING))
                    waiting.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.IN_PROCESS))
                    process.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.SKIPPED))
                    skipped.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatus.FROZEN))
                    frozen.add(targetRuntimeDTO);
            }
        }
        this.uiAdapter.addFrozen(frozen);
        this.uiAdapter.addWaiting(waiting);
        this.uiAdapter.addProcess(process);
        this.uiAdapter.addSkipped(skipped);
        this.uiAdapter.addFailed(failed);
        this.uiAdapter.addSuccess(success);
        this.uiAdapter.addWarning(warning);
    }

    private void clearSets(Set<TargetRuntimeDTO> frozen, Set<TargetRuntimeDTO> waiting, Set<TargetRuntimeDTO> process, Set<TargetRuntimeDTO> skipped,
                           Set<TargetRuntimeDTO> failed, Set<TargetRuntimeDTO> success, Set<TargetRuntimeDTO> warning){
        frozen.clear();
        waiting.clear();
        process.clear();
        skipped.clear();
        failed.clear();
        success.clear();
        warning.clear();
    }

    private Set<TargetRuntimeDTO> collectionToSet(Collection<TargetRuntimeDTO> targetRuntimeDTOS){
        Set<TargetRuntimeDTO> setTargets = new HashSet<>();
        for(TargetRuntimeDTO targetRuntimeDTO:targetRuntimeDTOS){
            setTargets.add(targetRuntimeDTO);
        }
        return setTargets;
    }
}