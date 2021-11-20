package menu;

import Enums.SimulationEntryPoint;
import dtoObjects.SimulationSummeryDTO;
import engineManager.EngineManager;
import exceptions.MenuOptionException;

import java.util.*;
import java.util.function.Consumer;

public class RunTaskOption implements MenuOption{

    @Override
    /* the function start the menu option */
    public void start() {
        Consumer<String> consumer = s-> System.out.println(s);
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            double chanceTargetSuccess = 0, chanceTargetWarning = 0;
            int processTime = 0;
            String isRandomString, entryPointString;
            boolean isRandom = false;
            SimulationSummeryDTO simulationSummeryDTO;
            engineManager.checkRunXml();
            processTime = askForProcessTime(scanner);
            scanner.nextLine();
            isRandomString = askForRandomString(scanner);
            chanceTargetSuccess = askForChanceTargetSuccess(scanner);
            chanceTargetWarning = askForChanceTargetWarning(scanner);
            scanner.nextLine();
            entryPointString = askForEntryPointString(scanner);
            if (isRandomString.equalsIgnoreCase("Y"))
                isRandom = true;
            if (entryPointString.equalsIgnoreCase("Y"))
                simulationSummeryDTO = engineManager.runSimulate(processTime, chanceTargetSuccess, chanceTargetWarning, isRandom, SimulationEntryPoint.FROM_SCRATCH, consumer);
            else
                simulationSummeryDTO = engineManager.runSimulate(processTime, chanceTargetSuccess, chanceTargetWarning, isRandom, SimulationEntryPoint.INCREMENTAL,consumer);

            /* ********************** */
            System.out.println(simulationSummeryDTO.toString());
            /* ********************** */
        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
            continu(e.getMessage());
        }
    }

    private int askForProcessTime(Scanner scanner) {
        int processTime = 0;
        System.out.println("Enter processing time for all targets (positive integer):");
        while (!scanner.hasNextInt()) {
            System.out.println("Process time need to be positive integer");
            scanner.next();
        }
        processTime = scanner.nextInt();
        return processTime;
    }

    private String askForRandomString(Scanner scanner) {
        String isRandomString;
        System.out.println("Enter if processing time is random (Y/N):");
        isRandomString = scanner.nextLine();
        while(!isRandomString.equalsIgnoreCase("Y") && !isRandomString.equalsIgnoreCase("N")){
            System.out.println("Please enter valid char for random process time (Y/N)");
            isRandomString = scanner.nextLine();
        }
        return isRandomString;
    }

    private double askForChanceTargetSuccess(Scanner scanner) {
        double chanceTargetSuccess = 0;
        System.out.println("Enter the probability that it will end successfully (between 0 to 1):");
        while(!scanner.hasNextDouble()){
            System.out.println("The probability that it will end successfully need to be between 0 to 1");
            scanner.nextLine();
        }
        chanceTargetSuccess = scanner.nextDouble();
        while (chanceTargetSuccess < 0 || chanceTargetSuccess > 1){
            System.out.println("The probability that it will end successfully need to be between 0 to 1");
            chanceTargetSuccess = scanner.nextDouble();
        }
        return chanceTargetSuccess;
    }

    private double askForChanceTargetWarning(Scanner scanner) {
        double chanceTargetWarning = 0;
        System.out.println("If end with success, enter the probability that it will end with warning (between 0 to 1):");
        while(!scanner.hasNextDouble()){
            System.out.println("The probability that it will end with warning need to be between 0 to 1");
            scanner.nextLine();
        }
        chanceTargetWarning = scanner.nextDouble();
        while (chanceTargetWarning < 0 || chanceTargetWarning > 1){
            System.out.println("The probability that it will end with warning need to be between 0 to 1");
            chanceTargetWarning = scanner.nextDouble();
        }
        return chanceTargetWarning;
    }

    private String askForEntryPointString(Scanner scanner) {
        String entryPointString;
        System.out.println("Do you want to start running from scratch? (Y/N):");
        entryPointString = scanner.nextLine();
        while(!entryPointString.equalsIgnoreCase("Y") && !entryPointString.equalsIgnoreCase("N")){
            System.out.println("Please enter valid char for start from scratch (Y/N)");
            entryPointString = scanner.nextLine();
        }
        return entryPointString;
    }

    /* the function return to the start function */
    private void continu(String error){
        if(!error.equals(MenuOptionException.getXmlLoadError()))
            start();
    }
}
