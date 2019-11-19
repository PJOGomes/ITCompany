package com.PauloGomes;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public static void loadMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws java.lang.Exception{
        Scanner scanner = new Scanner(System.in);
        String insertedPassword;
        int i = 0;
        System.out.println("Please wait while the program loads");
        System.out.println("-----------------------------------\n\n\n");
        while(i < 21) {
            System.out.print("    [");
            for (int j=0;j<i;j++) {
                System.out.print("#");
            }

            for (int j=0;j<20-i;j++) {
                System.out.print(" ");
            }

            System.out.print("] "+  i*5 + "%");
            if(i<20) {
                System.out.print("\r");
                Thread.sleep(250);
            }
            i++;
        }
        System.out.println("\n\n\n\n-----------------------------------");
        System.out.println("Database loaded.\nPlease enter password to access Program or quit to exit:");
        int count = 0;
        while(true){
            insertedPassword = scanner.nextLine();
            if(insertedPassword.equals("quit")) {
                System.out.println("Exiting Program");
                System.exit(-1);
            } else if(insertedPassword.equals("Formation123")) {
                System.out.println("Password Accepted!");
                mainMenu(programmers, teams);
            } else {
                count++;
                if(count<3)
                {
                    System.out.println("Wrong Password!\nYou have tried "+count+ " time(s). You only have " +(3-count)+" left.\nPlease enter password to access Program or quit to exit:");
                } else {
                    System.out.println("You have exceded the number of tries. The program will now close");
                    System.exit(-2);
                }
            }
        }
    }

    private static void mainMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {
        Scanner scanner = new Scanner(System.in);
        String option;
        while(true) {
            System.out.println("\n\n*************************************************");
            System.out.println("      ,---------------------------,");
            System.out.println("      |  /---------------------\\  |");
            System.out.println("      | |     Computer          | |");
            System.out.println("      | |      Services         | |");
            System.out.println("      | |       Company         | |");
            System.out.println("      | |                       | |");
            System.out.println("      |  \\_____________________/  |");
            System.out.println("      |___________________________|");
            System.out.println("    ,---\\_____     []     _______/-------,");
            System.out.println("   /         /______________\\           / |");
            System.out.println("  /___________________________________ /  |");
            System.out.println("  |                                   |   |    )");
            System.out.println("  |  _ _ _                 [-------]  |   |   (");
            System.out.println("  |  o o o                 [-------]  |  /    _)_");
            System.out.println("  |__________________________________ |/     /  /");
            System.out.println("  /-------------------------------------/|   ( )/");
            System.out.println("  /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/ /");
            System.out.println("  /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/ /");
            System.out.println("  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.println("\nPlease enter an option:\n");
            System.out.println("1 - See Programmers List");
            System.out.println("2 - Edit Programmer's Details");
            System.out.println("3 - Insert Programmer");
            System.out.println("4 - Delete Programmer");
            System.out.println("5 - See Project Teams");
            System.out.println("6 - Edit Project Team");
            System.out.println("7 - Create Project Team");
            System.out.println("8 - Delete Project Team");
            System.out.println("9 - Company Report");
            System.out.println("10 - Set System's Date");
            System.out.println("0 - Exit Program");
            System.out.println("*************************************************\nPlease enter your choice: \n");
            option = scanner.nextLine();
            switch (option){
                case "1":
                    System.out.println("Option1");
                    subMenu();
                    break;
                case "2":
                    System.out.println("Option2");
                    subMenu();
                    break;
                case "3":
                    System.out.println("Option3");
                    subMenu();
                    break;
                case "4":
                    System.out.println("Option4");
                    subMenu();
                    break;
                case "5":
                    System.out.println("Option5");
                    subMenu();
                    break;
                case "6":
                    System.out.println("Option6");
                    subMenu();
                    break;
                case "7":
                    System.out.println("Option7");
                    subMenu();
                    break;
                case "8":
                    System.out.println("Option8");
                    subMenu();
                    break;
                case "9":
                    System.out.println("Option9");
                    subMenu();
                    break;
                case "10":
                    System.out.println("Option10");
                    subMenu();
                    break;
                case "0":
                    System.out.println("Thank you for using our Program");
                    System.exit(0);
                default:
                    System.out.println("You must enter a valid option");
            }
        }
    }

    private static void subMenu(){
        Scanner scanner = new Scanner(System.in);
        String option;
        while(true){
            System.out.println("\n\n*************************************************");
            System.out.println("1 - Go back to main menu");
            System.out.println("0 - Quit Program");
            System.out.println("*************************************************\nPlease enter your choice: \n");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    return;
                case "0":
                    System.out.println("Thank you for using our Program");
                    System.exit(0);
                default:
                    System.out.println("You must enter a valid option");
            }
        }

    }

}




