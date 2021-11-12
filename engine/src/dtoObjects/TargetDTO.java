package dtoObjects;

import target.Target;
import target.TargetRunStatus;
import target.TargetStatus;
import java.util.ArrayList;
import java.util.List;

public class TargetDTO {
    private String name;
    // private TargetPosition position;
    private TargetRunStatus runStatus;
    private TargetStatus status;
    private List<TargetDTO> dependsOnList;
    private List<TargetDTO> requiredForList;
    private String generalInfo;

    /* constructor */
    public TargetDTO(Target other){
        this.name = other.getName();
        this.runStatus = other.getRunStatus();
        this.status = other.getStatus();
        this.generalInfo = other.getGeneralInfo();
        this.dependsOnList = duplicateDependsOnList(other.getDependsOnList());
        this.requiredForList = duplicateRequiredForList(other.getRequiredForList());
    }

    /* the function duplicate dependsOn list of target */
    private List<TargetDTO> duplicateDependsOnList(List<Target> other){
        List<TargetDTO> dependsOn = new ArrayList<>();
        for(Target target: other){
            TargetDTO targetToAdd = new TargetDTO(target);
            dependsOn.add(targetToAdd);
        }
        return dependsOn;
    }

    /* the function duplicate RequiredFor list of target */
    private List<TargetDTO> duplicateRequiredForList(List<Target> other){
        List<TargetDTO> requiredFor = new ArrayList<>();
        for(Target target: other){
            TargetDTO targetToAdd = new TargetDTO(target);
            requiredFor.add(targetToAdd);
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
    public List<TargetDTO> getDependsOnList() {
        return dependsOnList;
    }

    /* the function return target RequiredFor list */
    public List<TargetDTO> getRequiredForList() {
        return requiredForList;
    }

    /* the function return target general information */
    public String getGeneralInfo() {
        return generalInfo;
    }
}
