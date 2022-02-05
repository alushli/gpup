package dtoObjects;

import target.Target;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class TargetsPathDTO {
    private List<String> targets;

    public TargetsPathDTO(LinkedHashSet<Target> targets){
        this.targets = new ArrayList<>();
        for (Target target:targets){
            this.targets.add(target.getName());
        }
    }

    public TargetsPathDTO(List<Target> targets){
        this.targets = new ArrayList<>();
        for (Target target:targets){
            this.targets.add(target.getName());
        }
    }

}
