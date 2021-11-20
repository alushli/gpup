package dtoObjects;

import target.Target;

import java.util.*;

public class SimulationSummeryDTO {
    private List<String> outputs;
    private String HMS;
    private Set<String> skipped, success, failed;

    public SimulationSummeryDTO(){
        this.outputs = new ArrayList<>();
        this.success = new HashSet<>();
        this.failed = new HashSet<>();
        this.skipped = new HashSet<>();
    }

    public void addOutput(String output){
        outputs.add(output);
    }

    @Override
    public String toString() {
        return "SimulationSummeryDTO{" +
                "outputs=" + outputs +
                '}';
    }

    public String getHMS() {
        return HMS;
    }

    public void setHMS(String HMS) {
        this.HMS = HMS;
    }

    public List<String> getOutputs(){
        return outputs;
    }

    public void setSkipped(Collection<Target> skipped) {
        skipped.forEach((skip)->this.skipped.add(skip.getName()));
    }

    public void setSuccess(Collection<Target> success) {
        success.forEach((success1)->this.success.add(success1.getName()));
    }

    public void setFailed(Collection<Target> failed) {
        failed.forEach((fail)->this.failed.add(fail.getName()));
    }

    public void setCollections(Collection<Target> failed,Collection<Target> success, Collection<Target> skipped ){
        setFailed(failed);
        setSkipped(skipped);
        setSuccess(success);
    }
}
