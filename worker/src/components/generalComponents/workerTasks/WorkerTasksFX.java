package components.generalComponents.workerTasks;

import dtoObjects.WorkerTaskDTO;
import javafx.scene.control.CheckBox;
import logic.registeredExecution.RegisteredExecution;

public class WorkerTasksFX {
    private String name;
    private Integer workers;
    private Integer targets;
    private Integer doneTargets;
    private Integer byMe;
    private Integer credit;
    private CheckBox activeSelect;
    private String status;
    private int totalTargets;

    public WorkerTasksFX(RegisteredExecution registeredExecution){
        this.name = registeredExecution.getName();
        this.workers = registeredExecution.getWorkers();
        this.doneTargets = registeredExecution.getDoneTargets();
        this.byMe = registeredExecution.getTargetIPerformed();
        this.credit = byMe * registeredExecution.getPricePerTarget();
        this.activeSelect = new CheckBox();
        this.status = registeredExecution.getTaskStatus().toString();
        this.totalTargets = registeredExecution.getTotalTargets();
    }

    public int getTotalTargets() {
        return totalTargets;
    }

    public String getName() {
        return name;
    }

    public Integer getWorkers() {
        return workers;
    }

    public Integer getTasksCount() {
        return targets;
    }

    public Integer getDoneTargets() {
        return doneTargets;
    }

    public Integer getByMe() {
        return byMe;
    }

    public Integer getCredit() {
        return credit;
    }

    public CheckBox getActiveSelect() {
        return activeSelect;
    }


    public WorkerTasksFX(String name, Integer workers, Integer targets,
                         Integer doneTargets, Integer byMe, Integer credit)  {
        this.name = name;
        this.workers = workers;
        this.targets = targets;
        this.doneTargets = doneTargets;
        this.byMe = byMe;
        this.credit = credit;
        this.activeSelect = new CheckBox();
    }

    public WorkerTasksFX(WorkerTasksFX other){
        this.name = other.name;
        this.workers = other.workers;
        this.targets = other.targets;
        this.doneTargets = other.doneTargets;
        this.byMe = other.byMe;
        this.credit = other.credit;
        this.activeSelect = new CheckBox();
        this.activeSelect.setSelected(true);
    }

    public Integer getTargets() {
        return targets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
