package components.dtoObjects;


import components.adminEnums.TargetRunStatusAdmin;
import components.adminEnums.TargetRuntimeStatusAdmin;
import target.Target;
import java.util.HashSet;
import java.util.Set;

public class TargetRuntimeDTOFX {
    private String name;
    private String position;
    private Set<String> serialSets;
    private TargetRuntimeStatusAdmin status;
    private long joinWaitingTimeStamp;
    private long processStartTimeStamp;
    private Set<String> dependsOn;
    private Set<String> skippedBecause;
    private TargetRunStatusAdmin finishStatus;
    private TargetRuntimeStatusAdmin prevStatus;
    private Set<String> requiredFor;


    public TargetRuntimeDTOFX(Target target){
        this.name = target.getName();
        this.position = target.getPosition().toString();
        this.status = TargetRuntimeStatusAdmin.FROZEN;
        this.skippedBecause = new HashSet<>();
        this.dependsOn = new HashSet<>();
        for (Target target1 : target.getDependsOnList()){
            this.dependsOn.add(target1.getName());
        }
        this.requiredFor = new HashSet<>();

        for (Target target1: target.getRequiredForList()){
            this.requiredFor.add(target1.getName());
        }

        this.prevStatus = null;
        this.finishStatus = TargetRunStatusAdmin.NONE;
    }

    public Set<String> getRequiredFor() {
        return requiredFor;
    }

    public TargetRuntimeStatusAdmin getPrevStatus() {
        return prevStatus;
    }

    public void setStatus(TargetRuntimeStatusAdmin status) {
        this.prevStatus = this.status;
        this.status = status;
        if(status.equals(TargetRuntimeStatusAdmin.IN_PROCESS)){
            this.processStartTimeStamp = System.currentTimeMillis();
        }else if(status.equals(TargetRuntimeStatusAdmin.WAITING)){
            this.joinWaitingTimeStamp = System.currentTimeMillis();
        }
    }

    public void setFinishStatus(TargetRunStatusAdmin finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public Set<String> getSerialSets() {
        return serialSets;
    }

    public TargetRuntimeStatusAdmin getStatus() {
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

    public TargetRunStatusAdmin getFinishStatus() {
        return finishStatus;
    }

    public void removeDependency(String targetToRemove){
        this.dependsOn.remove(targetToRemove);
    }

    public  void addToSkippedBecause(String targetToAdd){
        this.skippedBecause.add(targetToAdd);
    }

    public void setPosition(String  position){
        this.position = position;
    }
}
