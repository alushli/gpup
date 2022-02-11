package components.tasks;

import components.adminEnums.TargetRunStatusAdmin;
import components.adminEnums.TargetRuntimeStatusAdmin;
import components.adminEnums.TasksNameAdmin;
import components.dtoObjects.TargetRuntimeDTOFX;
import components.dtoObjects.TaskRuntimeDTOFx;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TaskToUI implements Runnable {
    private TaskRuntimeDTOFx taskRuntimeDTO;
    private UIAdapter uiAdapter;
    private boolean isDone = false;
    private TasksNameAdmin tasksName;

    public TaskToUI(UIAdapter uiAdapter, TasksNameAdmin taskName) {
        this.uiAdapter = uiAdapter;
        this.tasksName = taskName;
    }

    @Override
    public void run() {
        try {
            Set<TargetRuntimeDTOFX> frozen = new HashSet<>();
            Set<TargetRuntimeDTOFX> waiting = new HashSet<>();
            Set<TargetRuntimeDTOFX> process = new HashSet<>();
            Set<TargetRuntimeDTOFX> skipped = new HashSet<>();
            Set<TargetRuntimeDTOFX> failed = new HashSet<>();
            Set<TargetRuntimeDTOFX> warning = new HashSet<>();
            Set<TargetRuntimeDTOFX> success = new HashSet<>();
            int i = 0;
            //this.engineManager.setTaskRun(true);
            while (!isDone) {
//                boolean canRun = false;
//                if (this.tasksName.equals(TasksNameAdmin.SIMULATION) && this.engineManager.getSimulationTaskManager() != null && this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO() != null) {
//                    canRun = true;
//                    this.taskRuntimeDTO = this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO();
//                }
//                else if(this.tasksName.equals(TasksNameAdmin.COMPILATION) && this.engineManager.getCompilerTaskManager() != null && this.engineManager.getCompilerTaskManager().getTaskRuntimeDTO() != null) {
//                    canRun = true;
//                    this.taskRuntimeDTO = this.engineManager.getCompilerTaskManager().getTaskRuntimeDTO();
//                }
//                if(canRun){
//                    synchronized (this.synchroObj) {
//                        clearSets(frozen, waiting, process, skipped, failed, success, warning);
//                        if (i == 0) {
//                            this.uiAdapter.addFrozen(collectionToSet(this.taskRuntimeDTO.getMap().values()));
//                            i++;
//                        }
//                        updateUI(frozen, waiting, process, skipped, failed, success, warning);
//                        if (this.taskRuntimeDTO.getCountTotal() == this.taskRuntimeDTO.getCountFinished()) {
//                            isDone = true;
//                            this.engineManager.setTaskRun(false);
//                        }
//                    }
//                }
//                Thread.sleep(100);
            }
//            clearSets(frozen, waiting, process, skipped, failed, success, warning);
//            synchronized (this.synchroObj){
//                if(this.tasksName.equals(TasksName.SIMULATION))
//                    this.taskRuntimeDTO = this.engineManager.getSimulationTaskManager().getTaskRuntimeDTO();
//                else
//                    this.taskRuntimeDTO = this.engineManager.getCompilerTaskManager().getTaskRuntimeDTO();
//                updateUI(frozen, waiting, process, skipped, failed, success, warning);
//                this.engineManager.setTaskRuntimeDTO(null, this.tasksName);
//            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateUI(Set<TargetRuntimeDTOFX> frozen, Set<TargetRuntimeDTOFX> waiting, Set<TargetRuntimeDTOFX> process, Set<TargetRuntimeDTOFX> skipped,
                          Set<TargetRuntimeDTOFX> failed, Set<TargetRuntimeDTOFX> success, Set<TargetRuntimeDTOFX> warning){
        this.uiAdapter.updateTotalTargets(String.valueOf(this.taskRuntimeDTO.getCountTotal()));
        this.uiAdapter.updateFinishTargets(String.valueOf(this.taskRuntimeDTO.getCountFinished()));
        this.uiAdapter.updateProgressBar((float) this.taskRuntimeDTO.getCountFinished() / this.taskRuntimeDTO.getCountTotal());
        for (TargetRuntimeDTOFX targetRuntimeDTO : this.taskRuntimeDTO.getMap().values()) {
            if(!targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.NONE)){
                if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.FAILURE))
                    failed.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.SUCCESS))
                    success.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.WARNING))
                    warning.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getFinishStatus().equals(TargetRunStatusAdmin.SKIPPED))
                    skipped.add(targetRuntimeDTO);
            }
            else{
                if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.WAITING))
                    waiting.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.IN_PROCESS))
                    process.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.SKIPPED))
                    skipped.add(targetRuntimeDTO);
                else if(targetRuntimeDTO.getStatus().equals(TargetRuntimeStatusAdmin.FROZEN))
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

    private void clearSets(Set<TargetRuntimeDTOFX> frozen, Set<TargetRuntimeDTOFX> waiting, Set<TargetRuntimeDTOFX> process, Set<TargetRuntimeDTOFX> skipped,
                           Set<TargetRuntimeDTOFX> failed, Set<TargetRuntimeDTOFX> success, Set<TargetRuntimeDTOFX> warning){
        frozen.clear();
        waiting.clear();
        process.clear();
        skipped.clear();
        failed.clear();
        success.clear();
        warning.clear();
    }

    private Set<TargetRuntimeDTOFX> collectionToSet(Collection<TargetRuntimeDTOFX> targetRuntimeDTOS){
        Set<TargetRuntimeDTOFX> setTargets = new HashSet<>();
        for(TargetRuntimeDTOFX targetRuntimeDTO:targetRuntimeDTOS){
            setTargets.add(targetRuntimeDTO);
        }
        return setTargets;
    }
}