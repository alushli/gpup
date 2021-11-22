package dtoObjects;

import target.Target;
import java.util.*;

public class SimulationSummeryDTO {
    private String HMS;
    private Set<TargetRunSimulationSum> targets;
    private int countSkipped, countSuccess, countWarning, countFailed;

    /* constructor */
    public SimulationSummeryDTO(){
        this.targets = new HashSet<>();
        this.countFailed = 0;
        this.countSkipped = 0;
        this.countSuccess = 0;
        this.countWarning = 0;
    }

    /* the function update counts property */
    public void setCounts(int countSkipped, int countFailed, int countSuccess, int countWarning){
        setCountFailed(countFailed);
        setCountSkipped(countSkipped);
        setCountSuccess(countSuccess);
        setCountWarning(countWarning);
    }

    /* the function update count skipped property */
    public void setCountSkipped(int countSkipped) {
        this.countSkipped = countSkipped;
    }

    /* the function update count success property */
    public void setCountSuccess(int countSuccess) {
        this.countSuccess = countSuccess;
    }

    /* the function update count warning property */
    public void setCountWarning(int countWarning) {
        this.countWarning = countWarning;
    }

    /* the function update count failed property */
    public void setCountFailed(int countFailed) {
        this.countFailed = countFailed;
    }

    @Override
    /* the function return to string of the class */
    public String toString() {
        String returnVal = new String();
        returnVal+= "\n\r***** Simulation Summery *****\n\r";
        returnVal += "The task ran: "+ HMS+"\n\r";
        returnVal += countSuccess+" succeeded, "+ countWarning+" succeeded with a warning, "+countSkipped +" skipped and "+countFailed+ " failed.\n\r";
        for (TargetRunSimulationSum targetRunSimulationSum: targets){
            returnVal+= targetRunSimulationSum.toString();
        }
        return returnVal;
    }

    /* the function return HMS property */
    public String getHMS() {
        return HMS;
    }

    /* the function update HMS property */
    public void setHMS(String HMS) {
        this.HMS = HMS;
    }

    /* the function add target with simulation details to target list  */
    public void addToTargets(Target target, String HMS){
        this.targets.add(new TargetRunSimulationSum(HMS, target.getRunStatus().toString(),target.getName()));
    }
}
