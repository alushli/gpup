package menu;

import engineManaget.EngineManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    final int FIRST_OPTION = 1;
    final int LAST_OPTION = 6;
    final int EXIT_OPTION = 7;
    private static EngineManager engineManager;

    /* menu constructor */
    public Menu(){
        this.engineManager = new EngineManager();
    }

    /* the function return the engine manager */
    public static EngineManager getEngineManager(){ return engineManager;}

    /* the function print the menu */
    public void printMenu(){
        System.out.println("Select option (press number 1-6):");
        System.out.println("1. Load new file");
        System.out.println("2. Get general information about targets graph");
        System.out.println("3. Get information about target");
        System.out.println("4. Find all paths between 2 targets");
        System.out.println("5. Run task");
        System.out.println("6. Find circle that includes target");
        System.out.println("7. Exit");
    }

    /* the function load the specific option according to user input */
    public void runOption(int option){
        switch (option){
            case 1:
                new LoadFileOption().start();
                break;
            case 2:
                new TargetGraphInfoOption().start();
                break;
            case 3:
                new TargetInfoOption().start();
                break;
            case 4:
                new TargetPathsOption().start();
                break;
            case 5:
                new RunTaskOption().start();
                break;
            case 6:
                new TargetCircleOption().start();
        }
    }

    /* the function run the program */
    public void start(){
        Scanner scanner = new Scanner(System.in);
        int choose = 0;
        do{
            try {
                printMenu();
                choose = scanner.nextInt();
                if(choose <= LAST_OPTION && choose >=FIRST_OPTION)
                    runOption(choose);
                else if(choose != EXIT_OPTION){
                    System.out.println("please select a valid option");
                }
            }catch (InputMismatchException e){
                System.out.println("please enter a number (1-6)");
                scanner.nextLine();
            }
        } while (choose != EXIT_OPTION);
        System.exit(1);
    }
}
