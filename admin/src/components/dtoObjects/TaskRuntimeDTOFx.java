package components.dtoObjects;

import target.Target;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskRuntimeDTOFx {
    private Map<String, TargetRuntimeDTOFX> map;
    private int countFinished;
    private int countTotal;
    private Boolean synchroObj;

    public TaskRuntimeDTOFx(Collection<Target> targets){
        this.map = new HashMap<>();
        this.synchroObj = true;
        for (Target target : targets){
            this.map.put(target.getName(), new TargetRuntimeDTOFX(target));
        }
        this.countFinished = 0;
        this.countTotal = this.map.size();
    }

    public int getRemain(){
        return countTotal - countFinished;
    }

    public Map<String, TargetRuntimeDTOFX> getMap() {
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

    public TargetRuntimeDTOFX getTargetByName(String name){
        return this.map.get(name);
    }

    public void upFinish(){
        this.countFinished++;
    }

    public void updatePositions(){
        for (TargetRuntimeDTOFX targetRuntimeDTO: map.values()){
            if(targetRuntimeDTO.getRequiredFor().size() == 0 && targetRuntimeDTO.getDependsOn().size() == 0)
                targetRuntimeDTO.setPosition("independent");
            else if(targetRuntimeDTO.getRequiredFor().size() == 0)
                targetRuntimeDTO.setPosition("root");
            else if(targetRuntimeDTO.getDependsOn().size() == 0)
                targetRuntimeDTO.setPosition("leaf");
            else
                targetRuntimeDTO.setPosition("middle");
        }
    }
}
