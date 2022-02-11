package components.generalComponents.workerTasks;

import javafx.scene.control.CheckBox;

public class WorkerTasksFX {
    private String name;
    private Integer workers;
    private Integer targets;
    private Integer doneTargets;
    private Integer byMe;
    private Integer credit;
    private CheckBox activeSelect;
    private CheckBox unsubscribeSelect;

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

    public CheckBox getUnsubscribeSelect() {
        return unsubscribeSelect;
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
        this.unsubscribeSelect = new CheckBox();
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
        this.unsubscribeSelect = new CheckBox();
        this.unsubscribeSelect.setSelected(true);
    }
}
