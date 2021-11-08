package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void printMenu(){
        System.out.println("Select option (press number 1-6):");
        System.out.println("1. Load new file");
        System.out.println("2. Get general information about targets graph");
        System.out.println("3. Get information about target");
        System.out.println("4. Find all paths between 2 targets");
        System.out.println("5. Run task");
        System.out.println("6. Exit");
    }

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
                new TargesPathsOption().start();
                break;
            case 5:
                new RunTaskOption().start();
                break;
        }
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        int choose = 0;
        do{
            try {
                printMenu();
                choose = scanner.nextInt();
                if(choose <= 5 && choose >=1)
                    runOption(choose);
                else if(choose != 6){
                    System.out.println("please select a valid option");
                }
            }catch (InputMismatchException e){
                System.out.println("please enter a number (1-6)");
                scanner.nextLine();
            }
        } while (choose != 6);
        System.exit(1);
    }

}
