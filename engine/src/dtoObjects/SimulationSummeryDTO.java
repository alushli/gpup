package dtoObjects;

import Enums.TargetRunStatus;
import Enums.TargetStatus;
import com.sun.org.apache.bcel.internal.generic.RET;
import target.Target;

import java.util.*;

public class SimulationSummeryDTO {
    private String HMS;
    private Set<TargetRunSimulationSum> targets;
    private int countSkipped, countSuccess, countWarning, countFailed;

    public SimulationSummeryDTO(){
        this.targets = new HashSet<>();
        this.countFailed = 0;
        this.countSkipped = 0;
        this.countSuccess = 0;
        this.countWarning = 0;
    }

    public void setCounts(int countSkipped, int countFailed, int countSuccess, int countWarning){
        setCountFailed(countFailed);
        setCountSkipped(countSkipped);
        setCountSuccess(countSuccess);
        setCountWarning(countWarning);
    }

    public void setCountSkipped(int countSkipped) {
        this.countSkipped = countSkipped;
    }

    public void setCountSuccess(int countSuccess) {
        this.countSuccess = countSuccess;
    }

    public void setCountWarning(int countWarning) {
        this.countWarning = countWarning;
    }

    public void setCountFailed(int countFailed) {
        this.countFailed = countFailed;
    }

    @Override
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

    public String getHMS() {
        return HMS;
    }

    public void setHMS(String HMS) {
        this.HMS = HMS;
    }

    public void addToTargets(Target target, String HMS){
        this.targets.add(new TargetRunSimulationSum(HMS, target.getRunStatus().toString(),target.getName()));
    }

}
