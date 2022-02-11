package logic.taskManager;

import java.util.ArrayList;
import java.util.List;

public class TargetsDetails {
    private List<RunTimeTargetDetails> targetDetails = new ArrayList<>();

    public synchronized void addToList(RunTimeTargetDetails runTimeTargetDetails){
        this.targetDetails.add(runTimeTargetDetails);
    }
}
