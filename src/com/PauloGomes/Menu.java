package com.PauloGomes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Menu {

    private static Date sysDate= new Date();

    //Getter and Setter
    public static Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    //Function: loadMenu
    //Description: function to display a progress bar to represent a mock load bar progress and to request the user's password to access the program
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void loadMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws java.lang.Exception{
        Scanner scanner = new Scanner(System.in);
        String insertedPassword;
        int i = 0;
        System.out.println("Please wait while the program loads");
        System.out.println("-----------------------------------\n\n\n");
        //Create loadbar
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
                //Adjust the duration
                Thread.sleep(250);
            }
            i++;
        }
        System.out.println("\n\n\n\n-----------------------------------");
        System.out.println("Database loaded.\nPlease enter password to access Program or quit to exit:");
        int count = 0;
        while(true){
            //Get the password inserted by user
            insertedPassword = scanner.nextLine();
            if(insertedPassword.equals("quit")) {
                System.out.println("Exiting Program");
                System.exit(-1);
            } else if(insertedPassword.equals("Formation123")) {
                System.out.println("Password Accepted!");
                mainMenu(programmers, teams);
            } else {
                count++;
                //If password is wrong
                if(count<3)
                {
                    System.out.println("Wrong Password!\nYou have tried "+count+ " time(s). You only have " +(3-count)+" left.\nPlease enter password to access Program or quit to exit:");
                } else {
                    //If the user failed the password more than three times
                    System.out.println("You have exceded the number of tries. The program will now close");
                    System.exit(-2);
                }
            }
        }
    }

    //Function: mainMenu
    //Description: function to load the database and to give the user options to access submenus
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void mainMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        String option;
        ActiveProgrammer person = new ActiveProgrammer();
        ProjectTeam project = new ProjectTeam();
        Manager manager = new Manager();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CRUDDatabase database = new CRUDDatabase();
        //Load the info from the xml file used as database
        database.readFile(programmers, teams);
        //If the database doesn´t have the minimum requirements load the mockup database
        if(programmers.size()<4||teams.size()<2){
            System.out.println("The database does not have the minimal requirements\nA mockup database will be uploaded");
            database.readMockup(programmers, teams);
        }
        Menu menu = new Menu();
        //Get the system date saved in the database
        menu.setSysDate(CRUDDatabase.readDate());
        String today = dateFormat.format(getSysDate());
        boolean exit = false;
        while(!exit) {
            System.out.println("\n\n*************************************************");
            System.out.println("               "+today+"                        ");
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
            System.out.println("1 - Programmer Menu");
            System.out.println("2 - Project Menu");
            System.out.println("3 - Company Menu");
            System.out.println("0 - Exit Program");
            System.out.println("*************************************************\nPlease enter your choice: \n");
            option = scanner.nextLine();
            switch (option){
                case "1":
                    programmerMenu(programmers, teams);
                    break;
                case "2":
                    projectMenu(programmers, teams);
                    break;
                case "3":
                   companyMenu(programmers, teams);
                case "0":
                    System.out.println("Thank you for using our Program");
                    //Save the current info in teh xml Files
                    CRUDDatabase.saveDate();
                    CRUDDatabase.saveExit();
                    System.exit(0);
                default:
                    System.out.println("You must enter a valid option");
            }
        }
    }

    //Function: programmerMenu
    //Description: function to display to the user several options about programmer related options
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    private static void programmerMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        String option;
        ActiveProgrammer person = new ActiveProgrammer();
        boolean exit=false;
        while(!exit){
            System.out.println("======== Programmer Menu ========");
            System.out.println("1 - See Programmers");
            System.out.println("2 - Edit Programmer");
            System.out.println("3 - Create Programmer");
            System.out.println("4 - Delete Programmer");
            System.out.println("0 - Return to main menu");
            option = scanner.nextLine();
            switch (option){
                case "1":
                    Manager.printProgrammer(programmers, teams);
                    subMenu();
                    break;
                case "2":
                    person.editProgrammer(programmers, teams);
                    subMenu();
                    break;
                case "3":
                    person.addProgrammer(programmers, teams);
                    subMenu();
                    break;
                case "4":
                    person.deleteProgrammer(programmers, teams);
                    subMenu();
                    break;
                case "0":
                    mainMenu(programmers, teams);
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }

    }

    //Function: projectMenu
    //Description: function to display to the user several options about project related options
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    private static void projectMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        String option;
        ProjectTeam project = new ProjectTeam();
        boolean exit=false;
        while(!exit){
            System.out.println("========== Project Menu ==========");
            System.out.println("1 - See Projects List");
            System.out.println("2 - Edit Project");
            System.out.println("3 - Insert Project");
            System.out.println("4 - Delete Project");
            System.out.println("0 - Return to main menu");
            option = scanner.nextLine();
            switch (option){
                case "1":
                    Manager.printProject(programmers, teams);
                    subMenu();
                    break;
                case "2":
                    project.editProject(programmers, teams);
                    subMenu();
                    break;
                case "3":
                    project.createProject(programmers, teams);
                    subMenu();
                    break;
                case "4":
                    project.deleteProject(programmers, teams);
                    subMenu();
                    break;
                case "0":
                    mainMenu(programmers, teams);
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }

    //Function: companyMenu
    //Description: function to display to the user several options about company related options
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    private static void companyMenu(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        String option;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);
        boolean exit=false;
        while(!exit){
            System.out.println("========== Company Menu ==========");
            System.out.println("1 - Company Report");
            System.out.println("2 - Update System's Date");
            System.out.println("3 - Reload database");
            System.out.println("4 - View past projects");
            System.out.println("0 - Return to main menu");
            option = scanner.nextLine();
            switch (option){
                case "1":
                    Manager.report(programmers, teams);
                    subMenu();
                    break;
                case "2":
                    Manager.updateDate(programmers, teams);
                    subMenu();
                    break;
                case "3":
                    System.out.println("The database from the last system exit will be reloaded");
                    //Load backup will undo all changes made in the current session
                    CRUDDatabase.loadBackup();
                    CRUDDatabase.readFile(programmers, teams);
                    subMenu();
                    break;
                case "4":
                    //Get the projects in the history xml file and print it
                    ArrayList<ProjectTeam> historyList = new ArrayList<>();
                    CRUDDatabase.readHistory(historyList);
                    Manager.printHistory(historyList);
                case "0":
                    mainMenu(programmers, teams);
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }

    //Function: subMenu
    //Description: function to display to the user the option to retunr to the last menu used or exit the program
    //
    //@Input:
    //
    //@Output: no output
    private static void subMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String option;
        while(true){
            System.out.println("\n\n*************************************************");
            System.out.println("1 - Go back to menu");
            System.out.println("0 - Quit Program");
            System.out.println("*************************************************\nPlease enter your choice: \n");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    return;
                case "0":
                    System.out.println("Thank you for using our Program");
                    CRUDDatabase.saveDate();
                    CRUDDatabase.saveExit();
                    System.exit(0);
                default:
                    System.out.println("You must enter a valid option");
            }
        }

    }

}




