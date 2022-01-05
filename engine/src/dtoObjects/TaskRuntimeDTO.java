package dtoObjects;

import target.Target;

import java.util.*;

public class TaskRuntimeDTO {
    private Map<String, TargetRuntimeDTO> map;
    private int countFinished;
    private int countTotal;


    public TaskRuntimeDTO(Collection<Target> targets){
        this.map = new HashMap<>();
        for (Target target : targets){
            this.map.put(target.getName(), new TargetRuntimeDTO(target));
        }
        this.countFinished = 0;
        this.countTotal = this.map.size();
    }


    public int getRemain(){
        return countTotal - countFinished;
    }

    public Map<String, TargetRuntimeDTO> getMap() {
        return map;
    }

    public int getCountFinished() {
        return countFinished;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountFinished(int countFinished) {
        this.countFinished = countFinished;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public synchronized TargetRuntimeDTO getTargetByName(String name){
        return this.map.get(name);
    }
}
