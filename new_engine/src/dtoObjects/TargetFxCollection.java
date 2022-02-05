package dtoObjects;

import java.util.ArrayList;
import java.util.Collection;

public class TargetFxCollection {
    private Collection<TargetFXDTO> targets;

    public TargetFxCollection(){
        this.targets = new ArrayList<>();
    }

    public void addToTarget(TargetFXDTO targetFXDTO){
        this.targets.add(targetFXDTO);
    }

    public Collection<TargetFXDTO> getTargetFXDTOS() {
        return targets;
    }
}
