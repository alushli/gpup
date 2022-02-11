package logic.workerEngine;

import logic.registeredExecution.RegisteredExecution;

import java.util.HashMap;
import java.util.Map;

public class WorkerEngine {
    private String name;
    private int numOfThreads;
    private int credits;
    private Map<String, RegisteredExecution> registeredExecutionMap = new HashMap<>();



    public String getName() {
        return name;
    }

    public int getNumOfThreads() {
        return numOfThreads;
    }

    public int getCredits() {
        return credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfThreads(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Map<String, RegisteredExecution> getRegisteredExecutionMap() {
        return registeredExecutionMap;
    }
}
