package dtoObjects;

public class GraphDTO {
    private String name;
    private int activeTasks;
    private int totalTasks;
    private boolean canSimulation;
    private boolean canCompilation;

    public GraphDTO(String name, int activeTasks, int totalTasks) {
        this.name = name;
        this.activeTasks = activeTasks;
        this.totalTasks = totalTasks;
    }

    public GraphDTO(String name, int activeTasks, int totalTasks, boolean canCompilation, boolean canSimulation) {
        this.name = name;
        this.activeTasks = activeTasks;
        this.totalTasks = totalTasks;
        this.canCompilation = canCompilation;
        this.canSimulation = canSimulation;
    }
    public String getName() {
        return name;
    }

    public int getActiveTasks() {
        return activeTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActiveTasks(int activeTasks) {
        this.activeTasks = activeTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public boolean isCanSimulation() {
        return canSimulation;
    }

    public boolean isCanCompilation() {
        return canCompilation;
    }
}
