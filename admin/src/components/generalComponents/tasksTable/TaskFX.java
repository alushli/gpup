package components.generalComponents.tasksTable;

import dtoObjects.GeneralGraphInfoDTO;
import dtoObjects.TaskDTO;
import javafx.scene.control.CheckBox;

public class TaskFX {
    private String name;
    private String admin;
    private GeneralGraphInfoDTO generalGraphInfoDTO;
    private int totalPrice;
    private int numOfWorkers;
    private CheckBox select;

    private String graphName;
    private int countTargets;
    private int countLeaves;
    private int countIndependents;
    private int countRoots;
    private int countMiddles;

    public TaskFX(TaskDTO taskDTO){
        this.name = taskDTO.getName();
        this.admin = taskDTO.getAdmin();
        this.generalGraphInfoDTO = taskDTO.getGeneralGraphInfoDTO();

        this.graphName = generalGraphInfoDTO.getName();
        this.countTargets = generalGraphInfoDTO.getCountTargets();
        this.countLeaves = generalGraphInfoDTO.getCountLeaves();
        this.countIndependents = generalGraphInfoDTO.getCountIndependents();
        this.countRoots = generalGraphInfoDTO.getCountRoots();
        this.countMiddles = generalGraphInfoDTO.getCountMiddles();

        this.totalPrice = taskDTO.getTotalPrice();
        this.numOfWorkers = taskDTO.getNumOfWorkers();
        this.select = new CheckBox();
    }

    public String getName() {
        return name;
    }

    public String getAdmin() {
        return admin;
    }

    public GeneralGraphInfoDTO getGeneralGraphInfoDTO() {
        return generalGraphInfoDTO;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getNumOfWorkers() {
        return numOfWorkers;
    }

    public CheckBox getSelect() {
        return select;
    }

    public boolean isSelect(){
        return select.isSelected();
    }

    public void setSelect(boolean select){
        this.select.setSelected(select);
    }

    public String getGraphName() {
        return graphName;
    }

    public int getCountTargets() {
        return countTargets;
    }

    public int getCountLeaves() {
        return countLeaves;
    }

    public int getCountIndependents() {
        return countIndependents;
    }

    public int getCountRoots() {
        return countRoots;
    }

    public int getCountMiddles() {
        return countMiddles;
    }
}
