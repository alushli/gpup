package logic.taskManager;

import components.generalComponents.targetsTask.TargetsTaskFX;

import java.util.ArrayList;
import java.util.List;

public class TargetsDetails {
    private List<RunTimeTargetDetails> targetDetails = new ArrayList<>();

    public synchronized void addToList(RunTimeTargetDetails runTimeTargetDetails){
        this.targetDetails.add(runTimeTargetDetails);
    }

    public synchronized List<TargetsTaskFX> getAll(){
        List<TargetsTaskFX> targetsTaskFXES = new ArrayList<>();
        for (RunTimeTargetDetails runTimeTargetDetails : this.targetDetails){
            targetsTaskFXES.add(new TargetsTaskFX(runTimeTargetDetails.getTargetName(), runTimeTargetDetails.getTaskName(),
                    runTimeTargetDetails.getTaskType(),runTimeTargetDetails.getRunStatus(), runTimeTargetDetails.getCredit() , runTimeTargetDetails.getLogsManager().getLogs()));
        }

        return targetsTaskFXES;
    }

}
