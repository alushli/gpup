package target;

import graph.Graph;
import scema.generated.GPUPTargetDependencies;
import xml.XmlException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Target {
    private String name;
   // private TargetPosition position;
    private TargetRunStatus runStatus;
    private TargetStatus status;
    private List<Target> dependsOnList;
    private List<Target> requiredForList;
    private String generalInfo;

    /* the function create new target */
    public Target(String name){
        this.name = name;
        dependsOnList = new ArrayList<>();
        requiredForList = new ArrayList<>();
    }

    /* the function update the general info of target */
    public void updateGeneralInfo(String generalInfo){
        this.generalInfo = generalInfo;
    }

    /* the function load the dependsOnList and requiredForList of the target */
   public void load(GPUPTargetDependencies gpupTargetDependencies, Graph graph, Set<String> validTargetExist, Set<String> errorTargetsDependencies) throws XmlException{
       for (GPUPTargetDependencies.GPUGDependency gpupDependency : gpupTargetDependencies.getGPUGDependency()) {
           Target target;
           if (!graph.getGraphMap().containsKey(new Target(gpupDependency.getValue()))) {
               target = new Target(gpupDependency.getValue());
               graph.addToGr(target);
               validTargetExist.add(target.getName());
           } else {
               target = graph.getTargetByName(gpupDependency.getValue());
           }
           if (gpupDependency.getType().equals(DependencyTypes.REQUIRED_FOR.toString())) {
               if(!this.requiredForList.contains(target))
                   this.addToRequiredForList(target);
               if(!target.dependsOnList.contains(this))
                   target.addToDependsOnList(this);
               checkConflictDependencies(target, DependencyTypes.REQUIRED_FOR, errorTargetsDependencies);
           } else if (gpupDependency.getType().equals(DependencyTypes.DEPENDS_ON.toString())) {
               if(!this.dependsOnList.contains(target))
                   this.addToDependsOnList(target);
               if(!target.requiredForList.contains(this))
                   target.addToRequiredForList(this);
               checkConflictDependencies(target, DependencyTypes.DEPENDS_ON,errorTargetsDependencies);
           }
       }
   }

   /* the function return true if there is conflict between the types of dependencies of targets and false else */
    void checkConflictDependencies(Target target, DependencyTypes dependencyTypes, Set<String> errorTargetsDependencies) throws XmlException{
        if (dependencyTypes == DependencyTypes.DEPENDS_ON){
            if (this.dependsOnList.contains(target) && target.dependsOnList.contains(this))
                errorTargetsDependencies.add("target " + this.getName() + " and target " + target.getName() + " can not be dependent on each other");
        } else if(dependencyTypes == DependencyTypes.REQUIRED_FOR){
            if (this.requiredForList.contains(target) && target.requiredForList.contains(this))
                errorTargetsDependencies.add("target " + this.getName() + " and target " + target.getName() + " can not be required for each other");
        }
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

    //************
    @Override
    /* the function return information about the target */
    public String toString() {
//        return "name: " + name + " position: " + position.toString() + " list of depends on: **" + " list of required from: **" +
//                " general information: " + generalInfo;
        return name;
    }

    @Override
    /* equals function between targets */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(name, target.name);
    }

    @Override
    /* hash code function for target */
    public int hashCode() {
        return Objects.hash(name);
    }

    /* the function return thr dependsOn list of target */
    public List<Target> getDependsOnList() {
        return dependsOnList;
    }

    /* the function return thr requiredFor list of target */
    public List<Target> getRequiredForList() {
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

    /* the function remove the target from required targets dependsOn list */
    public void run(){
        if(this.dependsOnList.size() == 0) {
            for (Target target : this.requiredForList) {
                target.removeTargetFromDependsList(this);
            }
        }else{
            // need to throw exception
        }
    }

    /* the function remove the target from requiredFor list */
    public void removeTargetFromRequiredList(Target targetToRemove){
        this.requiredForList.remove(targetToRemove);
    }

    /* the function remove the target from dependsOn list */
    public void removeTargetFromDependsList(Target targetToRemove){
        this.dependsOnList.remove(targetToRemove);
    }
}
