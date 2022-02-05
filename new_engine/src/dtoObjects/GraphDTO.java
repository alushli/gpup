package dtoObjects;

public class GraphDTO {
    private String name;
    private int activeTasks;
    private int totalTasks;

    public GraphDTO(String name, int activeTasks, int totalTasks) {
        this.name = name;
        this.activeTasks = activeTasks;
        this.totalTasks = totalTasks;
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
}
