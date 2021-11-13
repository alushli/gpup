package menu;

import Enums.SimulationEntryPoint;
import dtoObjects.SimulationSummeryDTO;
import dtoObjects.TargetDTO;
import engineManaget.EngineManager;
import exceptions.MenuOptionException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class RunTaskOption implements MenuOption{

    @Override
    /* the function start the menu option */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        EngineManager engineManager = Menu.getEngineManager();
        try {
            double chanceTargetSuccess = 0, chanceTargetWarning = 0;
            int processTime = 0;
            String isRandomString, entryPointString;
            boolean isRandom = false;
            Set<String> errors = new HashSet<>();
            SimulationSummeryDTO simulationSummeryDTO;
            engineManager.checkRunXml();
            System.out.println("Enter processing time for all targets (as integer):");
            if(scanner.hasNextInt())
                processTime = scanner.nextInt();
            else
                errors.add("Process time need to be integer");
            scanner.nextLine();
            System.out.println("Enter if processing time is random (Y/N):");
            isRandomString = scanner.nextLine();
            System.out.println("Enter the probability that it will end successfully (as double):");
            if(scanner.hasNextDouble())
                chanceTargetSuccess = scanner.nextDouble();
            else
                errors.add("The probability that it will end successfully need to be double");
            System.out.println("If end with success, enter the probability that it will end with warning (as double):");
            if(scanner.hasNextDouble())
                chanceTargetWarning = scanner.nextDouble();
            else
                errors.add("The probability that it will end with warning need to be double");
            scanner.nextLine();
            System.out.println("Do you want to start running from scratch? (Y/N):");
            entryPointString = scanner.nextLine();
            checkParams(chanceTargetSuccess, chanceTargetWarning, isRandomString, entryPointString, errors);
            if(isRandomString.equals("Y"))
                isRandom = true;
            if(entryPointString.equals("Y"))
                simulationSummeryDTO = engineManager.runSimulate(processTime, chanceTargetSuccess, chanceTargetWarning, isRandom, SimulationEntryPoint.FROM_SCRATCH);
            else
                simulationSummeryDTO = engineManager.runSimulate(processTime, chanceTargetSuccess, chanceTargetWarning,isRandom ,SimulationEntryPoint.INCREMENTAL);

            /* ********************** */
            System.out.println(simulationSummeryDTO.getHMS());
        /* ********************** */

        } catch (MenuOptionException e){
            System.out.println(e.errorInfo() + e.getMessage());
            continu(e.getMessage());
        }
    }

    /* the function return to the start function */
    private void continu(String error){
        if(!error.equals(MenuOptionException.getXmlLoadError()))
            start();
    }

    private void checkParams(double chanceTargetSuccess,double chanceTargetWarning, String isRandomString, String entryPointString, Set<String> errors) throws MenuOptionException {
        if(chanceTargetSuccess < 0 || chanceTargetSuccess > 1)
            errors.add("The probability that it will end successfully need to be between 0 to 1");
        if(chanceTargetWarning < 0 || chanceTargetWarning > 1)
            errors.add("The probability that it will end with warning need to be between 0 to 1");
        if(!isRandomString.equals("Y") && !isRandomString.equals("N") )
            errors.add("Please enter valid char for random process time (Y/N)");
        if(!entryPointString.equals("Y") && !entryPointString.equals("N"))
            errors.add("Please enter valid char for start from scratch (Y/N)");
        checkErrors(errors);
    }

    private void checkErrors(Set<String> errors) throws MenuOptionException {
        if(!errors.isEmpty())
            throw new MenuOptionException(errors.toString());
    }
}
