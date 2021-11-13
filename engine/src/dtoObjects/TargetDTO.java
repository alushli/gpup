package dtoObjects;

import Enums.TargetPosition;
import target.Target;
import Enums.TargetRunStatus;
import Enums.TargetStatus;

import java.util.HashSet;
import java.util.Set;

public class TargetDTO {
    private String name;
    private TargetPosition position;
    private TargetRunStatus runStatus;
    private TargetStatus status;
    private Set<String> dependsOnList;
    private Set<String> requiredForList;
    private String generalInfo;

    /* constructor */
    public TargetDTO(Target other){
        this.name = other.getName();
        this.position = other.getPosition();
        this.runStatus = other.getRunStatus();
        this.status = other.getStatus();
        this.generalInfo = other.getGeneralInfo();
        this.dependsOnList = duplicateDependsOnList(other.getDependsOnList());
        this.requiredForList = duplicateRequiredForList(other.getRequiredForList());
    }

    /* the function duplicate dependsOn list of target */
    private Set<String> duplicateDependsOnList(Set<Target> other){
        Set<String> dependsOn = new HashSet<>();
        for(Target target: other){
            dependsOn.add(target.getName());
        }
        return dependsOn;
    }

    /* the function duplicate RequiredFor list of target */
    private Set<String> duplicateRequiredForList(Set<Target> other){
        Set<String> requiredFor = new HashSet<>();
        for(Target target: other){
            requiredFor.add(target.getName());
        }
        return requiredFor;
    }

    /* the function return target name */
    public String getName() {
        return name;
    }

    /* the function return target run status */
    public TargetRunStatus getRunStatus() {
        return runStatus;
    }

    /* the function return target status */
    public TargetStatus getStatus() {
        return status;
    }

    /* the function return target DependsOn list */
    public Set<String> getDependsOnList() {
        return dependsOnList;
    }

    /* the function return target RequiredFor list */
    public Set<String> getRequiredForList() {
        return requiredForList;
    }

    /* the function return target general information */
    public String getGeneralInfo() {
        return generalInfo;
    }

    /* the function return target position */
    public TargetPosition getPosition() {
        return position;
    }
}
