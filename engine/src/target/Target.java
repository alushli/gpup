package target;

import Enums.TargetPosition;
import Enums.TargetRunStatus;
import Enums.TargetStatus;

import dtoObjects.SerialSetFXDTO;
import graph.SerialSet;

import java.util.*;

public class Target {
    private String name;
    private TargetRunStatus runStatus;
    private TargetStatus status;
    private Set<Target> dependsOnList;
    private Set<Target> requiredForList;
    private String generalInfo;
    private Map<String, SerialSet> serialSetMap;

    public Map<String, SerialSet> getSerialSetMap() {
        return serialSetMap;
    }

    public Set<String> getSerialSetFXDTO() {
        return serialSetMap.keySet();
    }


    public Map<String, Target> getAllHangingByTypeOfTargets(String typeOfDependency){
        Map<String, Target> map = new HashMap<>();
        getAllDepRec(map, typeOfDependency,this);
        if(map.containsKey(this.getName())){
            map.remove(this.getName());
        }
        return map;
    }

    private void getAllDepRec(Map<String, Target> map, String typeOfDependency, Target root){
        Collection<Target> targets = root.getHangingListByType(typeOfDependency);
        for (Target target : targets){
            if(!map.containsKey(target.name)){
                map.put(target.getName(), target);
                getAllDepRec(map,typeOfDependency,target);
            }
        }
    }

    public Set<Target> getHangingListByType(String type){
        if(type.equals("dependsOn")){
            return dependsOnList;
        }else{
            return requiredForList;
        }
    }

    /* the function update run status property */
    public void setRunStatus(TargetRunStatus runStatus) {
        this.runStatus = runStatus;
    }

    /* the function update status property */
    public void setStatus(TargetStatus status) {
        this.status = status;
    }

    public void addSerialSet(SerialSet serialSet){
        this.serialSetMap.put(serialSet.getName(), serialSet);
    }

    /* the function create new target */
    public Target(String name){
        this.name = name.trim().toUpperCase();
        dependsOnList = new HashSet<>();
        requiredForList = new HashSet<>();
        this.runStatus = TargetRunStatus.NONE;
        this.status = TargetStatus.NONE;
        this.serialSetMap = new HashMap<>();
    }

    /* copy constructor */
    public Target(Target other){
        this.name = other.getName();
        this.runStatus = other.getRunStatus();
        this.status = other.getStatus();
        this.generalInfo = other.getGeneralInfo();
        duplicateDependsOnList(other.getDependsOnList());
        duplicateRequiredForList(other.getRequiredForList());
        this.serialSetMap = other.serialSetMap;
    }

    /* the function duplicate dependsOn list of target */
    private void duplicateDependsOnList(Set<Target> other){
        for(Target target: other){
            this.dependsOnList.add(target);
        }
    }

    /* the function duplicate RequiredFor list of target */
    private void duplicateRequiredForList(Set<Target> other){
        for(Target target: other){
            this.requiredForList.add(target);
        }
    }

    /* the function update the general info of target */
    public void updateGeneralInfo(String generalInfo){
        this.generalInfo = generalInfo;
    }

    /* the function return target run status */
    public TargetRunStatus getRunStatus() {
        return runStatus;
    }

    /* the function return target status */
    public TargetStatus getStatus() {
        return status;
    }

    /* the function return target general information */
    public String getGeneralInfo() {
        return generalInfo;
    }

    /* thr function calculate and return the target position */
    public TargetPosition getPosition(){
        int sizeDepends = this.dependsOnList.size(), sizeRequired = this.requiredForList.size();
        if(sizeDepends == 0 && sizeRequired == 0)
            return TargetPosition.INDEPENDENT;
        else if(sizeDepends == 0)
            return TargetPosition.LEAF;
        else if(sizeRequired == 0)
            return TargetPosition.ROOT;
        else
            return TargetPosition.MIDDLE;

    }

    /* the function return the target name */
    public String getName(){
        return this.name;
    }

    @Override
    /* the function return information about the target */
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(name, target.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /* the function return thr dependsOn list of target */
    public Set<Target> getDependsOnList() {
        return dependsOnList;
    }

    /* the function return thr requiredFor list of target */
    public Set<Target> getRequiredForList() {
        return requiredForList;
    }

    //TO-DO- for check if input is invalid - check if already is in the other list
    /* the function add the target to dependsOn list */
    public void addToDependsOnList(Target targetToAdd){
        this.dependsOnList.add(targetToAdd);
    }

    /* the function add the target to requiredFor list */
    public void addToRequiredForList(Target targetToAdd){
        this.requiredForList.add(targetToAdd);
    }

    /* the function remove the target from requiredFor list */
    public void removeTargetFromRequiredList(Target targetToRemove){
        this.requiredForList.remove(targetToRemove);
    }

    /* the function remove the target from dependsOn list */
    public void removeTargetFromDependsList(Target targetToRemove){
        this.dependsOnList.remove(targetToRemove);
    }

    /* the function return true if target exist on dependsOn list */
    public boolean isInDependsOnList(Target target){
        return dependsOnList.contains(target);
    }

    /* the function return true if target exist on requiredFor list */
    public boolean isInRequiredForList(Target target){
        return requiredForList.contains(target);
    }

    public boolean isCanRunSerial(){
        for (SerialSet serialSet : this.serialSetMap.values()){
            if(serialSet.isRun()){
                return false;
            }
        }
        return true;
    }

    public void openAllSerialSets(){
        for (SerialSet serialSet : this.serialSetMap.values()){
           serialSet.setRun(false);
        }
    }

    public void moveToWaitingInAllSerials(){
        for (SerialSet serialSet : serialSetMap.values()){
            serialSet.moveToWait(this);
        }
    }

    public void handleOpenSerials(){
        for (SerialSet serialSet : this.serialSetMap.values()){
            if(serialSet.getWaiting().size() > 0){

            }
        }
    }
}
