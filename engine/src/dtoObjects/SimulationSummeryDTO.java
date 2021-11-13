package dtoObjects;

import java.util.ArrayList;
import java.util.List;

public class SimulationSummeryDTO {
    private List<String> outputs;
    private String HMS;

    public SimulationSummeryDTO(){
        this.outputs = new ArrayList<>();
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
}
