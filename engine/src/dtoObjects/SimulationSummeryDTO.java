package dtoObjects;

import java.util.ArrayList;
import java.util.List;

public class SimulationSummeryDTO {
    private List<String> outputs;

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

    public List<String> getOutputs(){
        return outputs;
    }
}
