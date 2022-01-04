package graph;

import Enums.TargetRunStatus;
import target.Target;

import java.util.*;
import java.util.concurrent.ExecutorService;

public class SerialSet {
    private String name;
    private boolean isRun;
    private List<Target> waiting;
    private HashSet<Target> frozen;
    private HashMap<String, Target> allSet;
    private HashSet<Target> finished;
    private Target inProccess;


    /*Create serial set from string from the xml and graph*/
    public SerialSet(String set, Graph graph,Set<String> errors, String serialSetName ){
        this.allSet = new HashMap<>();
        this.frozen = new HashSet<>();
        this.finished = new HashSet<>();
        this.waiting = new ArrayList<>();
        this.isRun = false;
        this.name = serialSetName;

        String[] targetsArray = set.split(",");
        List<String> targetsNamesList = Arrays.asList(targetsArray);
        for (String targetName : targetsNamesList){
            Target target = graph.getTargetByName(targetName);
            if( target!= null){
                allSet.put(target.getName(), target);
                frozen.add(target);
            }else{
                errors.add("Target" + targetName + " is in serial set:" + serialSetName + " but not in graph.");
            }
        }
    }

    /*Copy constructor*/
    public SerialSet(SerialSet other){
        this.allSet = new HashMap<>(other.getAllSet());
        this.name = other.getName();
        this.frozen = new HashSet<>(other.frozen);
        this.isRun = other.isRun;
        this.waiting = new ArrayList<>(other.waiting);
        this.finished = new HashSet<>(other.finished);
    }

    public String getName() {
        return name;
    }

    public boolean isRun() {
        return isRun;
    }

    public List<Target> getWaiting() {
        return waiting;
    }

    public HashSet<Target> getFrozen() {
        return frozen;
    }

    public HashMap<String, Target> getAllSet() {
        return allSet;
    }

    public HashSet<Target> getFinished() {
        return finished;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public void setInProccess(Target target){
        this.inProccess = target;
        this.waiting.remove(target);
        setRun(true);
    }

    public synchronized Target handleFinish(){
        this.finished.add(this.inProccess);
        this.inProccess = null;
        setRun(false);
        for (Target target : this.waiting){
            if(target.isCanRunSerial() && target.getRunStatus().equals(TargetRunStatus.NONE)){
                for (SerialSet serialSet : target.getSerialSetMap().values()){
                    serialSet.setRun(true);
                    serialSet.setInProccess(target);
                    this.waiting.remove(target);
                    this.inProccess = target;
                    return target;
                }
            }
        }
        return null;
    }

    public void moveToWait(Target target){
        this.frozen.remove(target);
        this.waiting.add(target);
    }
}

