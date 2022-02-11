package User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker extends User{
    private int numOfThreads;
    private int threadInUse;

    public Worker(String name, int numOfThreads){
        this.name = name;
        this.numOfThreads = numOfThreads;
        this.threadInUse = 0;
        this.isActive = true;
    }

}
