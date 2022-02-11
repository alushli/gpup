package dtoObjects;

import task.TaskOperator;

public class TaskDTO {
    private String name;
    private String admin;
    private GeneralGraphInfoDTO generalGraphInfoDTO;
    private int totalPrice;
    private int numOfWorkers;

    public TaskDTO(TaskOperator taskOperator){
        this.name = taskOperator.getName();
        this.admin = taskOperator.getAdmin();
        this.generalGraphInfoDTO = new GeneralGraphInfoDTO(taskOperator.getOrigin());
        this.totalPrice = taskOperator.getPricePerTarget() * this.generalGraphInfoDTO.getCountTargets();
        this.numOfWorkers = taskOperator.getWorkerMap().size();
    }

    public TaskDTO(String name, String admin, GeneralGraphInfoDTO generalGraphInfoDTO, int totalPrice, int numOfWorkers) {
        this.name = name;
        this.admin = admin;
        this.generalGraphInfoDTO = generalGraphInfoDTO;
        this.totalPrice = totalPrice;
        this.numOfWorkers = numOfWorkers;
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
}
