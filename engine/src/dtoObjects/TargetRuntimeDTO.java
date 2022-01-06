package dtoObjects;

import Enums.TargetPosition;
import Enums.TargetRunStatus;
import Enums.TargetRuntimeStatus;
import target.Target;

import java.util.HashSet;
import java.util.Set;

public class TargetRuntimeDTO {
    private String name;
    private TargetPosition position;
    private Set<String> serialSets;
    private TargetRuntimeStatus status;
    private long joinWaitingTimeStamp;
    private long processStartTimeStamp;
    private Set<String> dependsOn;
    private Set<String> skippedBecause;
    private TargetRunStatus finishStatus;
    private TargetRuntimeStatus prevStatus;
    private Boolean synchroObj;


    public TargetRuntimeDTO(Target target, Boolean synchroObj){
        this.name = target.getName();
        this.position = target.getPosition();
        this.serialSets = new HashSet<>(target.getSerialSetMap().keySet());
        this.status = TargetRuntimeStatus.FROZEN;
        this.skippedBecause = new HashSet<>();
        this.dependsOn = new HashSet<>();
        for (Target target1 : target.getDependsOnList()){
            this.dependsOn.add(target1.getName());
        }
        this.prevStatus = null;
        this.synchroObj = synchroObj;
    }

    public TargetRuntimeStatus getPrevStatus() {
        return prevStatus;
    }

    public void setStatus(TargetRuntimeStatus status) {
        this.prevStatus = this.status;
        this.status = status;
        if(status.equals(TargetRuntimeStatus.IN_PROCESS)){
            this.processStartTimeStamp = System.currentTimeMillis();
        }else if(status.equals(TargetRuntimeStatus.WAITING)){
            this.joinWaitingTimeStamp = System.currentTimeMillis();
        }
    }

    public void setFinishStatus(TargetRunStatus finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getName() {
        return name;
    }

    public TargetPosition getPosition() {
        return position;
    }

    public Set<String> getSerialSets() {
        return serialSets;
    }

    public TargetRuntimeStatus getStatus() {
        return status;
    }

    public long getJoinWaitingTimeStamp() {
        return System.currentTimeMillis() - joinWaitingTimeStamp;
    }

    public long getProcessStartTimeStamp() {
        return System.currentTimeMillis() - processStartTimeStamp;
    }

    public Set<String> getDependsOn() {
        return dependsOn;
    }

    public Set<String> getSkippedBecause() {
        return skippedBecause;
    }

    public TargetRunStatus getFinishStatus() {
        return finishStatus;
    }

    public void removeDependency(String targetToRemove){
        this.dependsOn.remove(targetToRemove);
    }

    public  void addToSkippedBecause(String targetToAdd){
        this.skippedBecause.add(targetToAdd);
    }

}
