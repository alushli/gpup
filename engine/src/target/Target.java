package target;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Target {
    private String name;
   // private TargetPosition position;
    private TargetRunStatus runStatus;
    private TargetStatus status;
    private List<Target> dependsOnList;
    private List<Target> requiredForList;
    private String generalInfo;

    public Target(String name){
        this.name = name;
        dependsOnList = new ArrayList<>();
        requiredForList = new ArrayList<>();
    }

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

    //************
    @Override
    public String toString() {
//        return "name: " + name + " position: " + position.toString() + " list of depends on: **" + " list of required from: **" +
//                " general information: " + generalInfo;
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

    public List<Target> getDependsOnList() {
        return dependsOnList;
    }

    public List<Target> getRequiredForList() {
        return requiredForList;
    }

    //TO-DO- for check if input is invalid - check if already is in the other list
    public void addToDependsOnList(Target targetToAdd){
        this.dependsOnList.add(targetToAdd);
    }

    public void addToRequiredForList(Target targetToAdd){
        this.requiredForList.add(targetToAdd);
    }

    public void run(){
        if(this.dependsOnList.size() == 0) {
            for (Target target : this.requiredForList) {
                target.removeTargetFromDependsList(this);
            }
        }else{
            // need to throw exception
        }
    }

    public void removeTargetFromRequiredList(Target targetToRemove){
        this.requiredForList.remove(targetToRemove);
    }

    public void removeTargetFromDependsList(Target targetToRemove){
        this.dependsOnList.remove(targetToRemove);
    }
}
