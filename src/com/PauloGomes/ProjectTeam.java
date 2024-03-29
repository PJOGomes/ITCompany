package com.PauloGomes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ProjectTeam {
    private int id;
    private String name;
    private ArrayList<String> members;
    private ArrayList<String> functions;
    private Date beginDate;
    private Date endDate;

    public ProjectTeam() {
    }

    //    Personalized Constructor
    public ProjectTeam(int id, String name, ArrayList<String> members, ArrayList<String> functions, Date beginDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.functions = functions;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }


//    Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<String> functions) {
        this.functions = functions;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


//    Methods

    //Function: createProject
    //Description: A function to create a new project according to the informations given by the user
    //
    //@Input:An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void createProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException {

        int count = teams.size();
        int lastId = teams.get(count-1).getId();
        String addId;
        ArrayList<String> members = new ArrayList<>();
        ArrayList<String> functions = new ArrayList<>();
        boolean addAnother = true;
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Enter the project name: ");
        String name = scanner.nextLine();
        int countInactive =0;
        //Check the number of available programmers to work in the project
        for (ActiveProgrammer prog: programmers)
        {
            if(!prog.isActive())
                countInactive++;
        }
        //If there are insuficient programmers
        if(countInactive<2)
        {
            System.out.println("There aren't enough programmers available to start a new project");
            return;
        }
        //Present the available programmers
        System.out.println("Choose the programmers available for the project (Insert Id of minimum 2 programmers):");
        for (ActiveProgrammer prog: programmers)
        {
            if(!prog.isActive()) {
                System.out.println("Programmer Id: "+prog.getId()+ " -> "+prog.getLastName().toUpperCase()+", "+prog.getFirstName()+" is available");
            }
        }
        while (addAnother) {
            System.out.println("Enter the programmer ID:");
            addId = scanner.nextLine();
            boolean restart = false;
            for(ActiveProgrammer prog: programmers){
                //If the choosen id is from a non available programmer
                if(prog.getId()==Integer.parseInt(addId)&&prog.isActive()==true)
                {
                    System.out.println("The choosen programmer is not available");
                    restart = true;
                    break;
                }
            }
            if(restart == true)
            {
                continue;
            }
            System.out.println("Do you want to add another programmer to the project? (Y-yes, Other key -no)");
            String option = scanner.nextLine();
            if(option.equals("Y")||option.equals("y")) {
                members.add(addId);
                //Change the programmers info if he is selected
                for(ActiveProgrammer prog: programmers){
                    if(prog.getId()==Integer.parseInt(addId)){
                        prog.setActive(true);
                        prog.setStartDatePresentProject(Menu.getSysDate());
                    }
                    CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()), "ActiveProgrammer");
                }
                addAnother = true;

            } else {
                members.add(addId);
                for(ActiveProgrammer prog: programmers){
                    if(prog.getId()==Integer.parseInt(addId)){
                        prog.setActive(true);
                        prog.setStartDatePresentProject(Menu.getSysDate());
                    }
                    CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()), "ActiveProgrammer");
                }
                //If the project has less than two programmers
                if(members.size()<2) {
                    System.out.println("For a project to be valid, you must have at least two active programmers. \n1 - Add another programmer\n2 - Exit and delete current data");
                    boolean exit = false;
                    while (!exit) {
                        String choice = scanner.nextLine();
                        switch (choice) {
                            case "1":
                                addAnother=true;
                                break;
                            case "2":
                                //Change the previous programmers details to non active type
                                for(ActiveProgrammer prog: programmers){
                                    if(prog.getId()==Integer.parseInt(addId)){
                                        prog.setActive(false);
                                        prog.setStartDatePresentProject(dateFormat.parse("00/00/0000"));
                                    }
                                    CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()), "ActiveProgrammer");
                                }
                                return;
                            default:
                                System.out.println("Please enter a valid option");
                        }
                    }
                }
                break;
            }
        }
        //Set each inserted programmer's functions
        for (int i = 0; i <members.size() ; i++) {
            String searchId = members.get(i);
            String function;
            for(ActiveProgrammer prog: programmers){
                if(prog.getId()==Integer.parseInt(searchId)){
                    System.out.println("Enter the function for "+prog.getFirstName()+" "+prog.getLastName());
                    function = scanner.nextLine();
                    functions.add(function);
                }
            }
        }
        //Set the beginning date of the project to the current date
        System.out.println("The start date of the project will be today (System Date)");
        //Get the end date of the project
        System.out.println("When will the project end: (dd/MM/yyyy format)");
        String end = scanner.nextLine();
        Date endDate = dateFormat.parse(end);

        //Create the new project
        ProjectTeam proj = new ProjectTeam(lastId+1, name, members, functions,Menu.getSysDate(),endDate);
        CRUDDatabase write = new CRUDDatabase();
        //Add project to the database
        write.createFile(programmers, teams, proj);
        //Add project to the list
        teams.add(proj);

    }

    //Function: editProject
    //Description: A function to edit a project according to the informations given by the user
    //
    //@Input:An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void editProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException {
        ProjectTeam t = new ProjectTeam();
        CRUDDatabase db = new CRUDDatabase();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Choose the Id of the project you want to edit: ");
        Manager.printProject(programmers, teams);
        int choosenId =0;
        if(scanner.hasNextInt()){
            choosenId = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Enter a valid Id");
            editProject(programmers, teams);
        }
        //get the project the user choose
        for(ProjectTeam proj: teams){
            if(proj.getId() == choosenId) {
                t = proj;
            }
        }
        System.out.println("Editing project " +t.getId());
        String option;
        boolean exit = false;
        while(!exit){
            System.out.println("1 - Edit Project Name: ");
            System.out.println("2 - Edit Project Team: ");
            System.out.println("3 - Edit Project Team Functions: ");
            System.out.println("4 - Edit Project's Start Date: ");
            System.out.println("5 - Edit Project's End Date: ");
            System.out.println("0 - End Edit");
            option = scanner.nextLine();
            switch(option){
                case "1":
                    System.out.println("Change Projet Name from "+t.getName()+" to: ");
                    String name = scanner.nextLine();
                    t.setName(name);
                    break;
                case "2":
                    editTeam(t, programmers, teams);
                    break;
                case "3":
                    System.out.println("Choose the functions to edit:");
                    for (int i = 0; i <t.getFunctions().size() ; i++) {
                        System.out.println(i+1+" - " + t.getFunctions().get(i));
                    }
                    int choice =0;
                    if(scanner.hasNextInt())
                    {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    } else {
                        System.out.println("error");
                        break;
                    }
                    System.out.println("Enter the new function:");
                    String function = scanner.nextLine();
                    t.getFunctions().set(choice-1, function);
                    break;
                case "4":
                    Date today = new Date();
                    //Do not allow to change a project's date if it has started
                    if(t.getBeginDate().before(today))
                    {
                        System.out.println("You can't edit the start date of an ongoing project");
                        editProject(programmers, teams);
                    }
                    System.out.println("The project was starting on "+dateFormat.format(t.getBeginDate())+" and will be changed to: (dd/MM/yyy format)");
                    String date = scanner.nextLine();

                    if(checkProjectDates(dateFormat.parse(date), t.getEndDate()))
                    {
                        t.setBeginDate(dateFormat.parse(date));
                        break;
                    } else {
                        System.out.println("You did not enter a valid data");
                        editProject(programmers, teams);
                    }
                    break;
                case "5":
                    Date dt = new Date();
                    System.out.println("The project was ending on "+dateFormat.format(t.getEndDate())+" and will be changed to: (dd/MM/yyy format)");
                    date = scanner.nextLine();
                    if(dateFormat.parse(date).before(dt)){
                        System.out.println("you can't set an end date prior to today");
                        editProject(programmers, teams);
                    }
                    if(checkProjectDates(t.getBeginDate(), dateFormat.parse(date)))
                    {
                        t.setEndDate(dateFormat.parse(date));
                        break;
                    } else {
                        System.out.println("You did not enter a valid data");
                        editProject(programmers, teams);
                    }
                    break;
                case "0":

                    exit = true;
                    return;
                default:
                    System.out.println("Please enter a valid option:");
                            break;
            }
        }
        //Change the project in the database
        db.deleteFile(programmers,teams, t.getId(), "projects");
        db.createFile(programmers, teams, t);

    }

    //Function: editTeam
    //Description: A function to edit a project's team members according to the informations given by the user
    //
    //@Input:An object of type ProjectTeam that represents the project being edited arrayList of type ActiveProgrammer
    // and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void editTeam(ProjectTeam t, ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String option;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean exit = false;
        while (!exit) {
            System.out.println("1 - Add Programmer to the team");
            System.out.println("2 - Remove Programmer from the team");
            System.out.println("3 - Exchange Programmer in the team");
            System.out.println("0 - Return to the editing menu");
            option = scanner.nextLine();
            switch(option){
                case "1":
                    int count=0;
                    //Check the available programmers
                    for(ActiveProgrammer prog: programmers)
                    {

                        if(!prog.isActive()){
                            count++;
                        }
                    }
                    if(count==0) {
                        System.out.println("There are no available programmers to add");
                        return;
                    }
                    System.out.println("Choose the Id of the programmer you want to add:");
                        Manager.printInactive(programmers);
                        int choosenId = 0;
                        if(scanner.hasNextInt()){
                            choosenId=scanner.nextInt();
                            scanner.nextLine();
                            //Validate if choosen ID is available
                            for(ActiveProgrammer prog: programmers){
                                if(prog.getId()==choosenId&&prog.isActive()==false)
                                {
                                    System.out.println("The choosen programmer is not available.");
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Enter a valid Id");
                            break;
                        }
                        t.members.add(Integer.toString(choosenId));
                        //Set the new programmer's functions
                    System.out.println("What will be the programmer's function?");
                    String function = scanner.nextLine();
                    t.getFunctions().add(function);
                        for(ActiveProgrammer prog: programmers){
                            if(prog.getId()==choosenId){
                                prog.setActive(true);
                                prog.setStartDatePresentProject(Menu.getSysDate());
                                CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()),"ActiveProgrammer");
                            }
                        }
                    break;
                case "2":
                    System.out.println("Choose the Id of the programmer you want to remove");
                    Manager.printProgrammersProject(programmers, teams, t.getId());
                    int choosen=0;
                        if(scanner.hasNextInt()){
                            choosen=scanner.nextInt();
                            scanner.nextLine();
                            //Validate if the Programmer to remove is in the Project's team
                            if(!t.getMembers().contains(Integer.toString(choosen))){
                                System.out.println("The choosen programmer is not in the project");
                                break;
                            }
                        } else {
                            System.out.println("Enter a valid Id");
                            break;
                        }
                        //Check the number of programmers in the project
                        if(t.getMembers().size()<2){
                            System.out.println("You cannot have a project with only one programmer. If you continue the project will be removed. \nDo you want to continue? (Y - yes; other key: no)");
                            boolean exitLoop = false;
                            while(!exitLoop){
                                String choice = scanner.nextLine();
                                if(choice.equals("Y")||choice.equals("y")){
                                    //Delete the project and set the programmer info in the project to the non active options
                                    for (int i = 0; i <t.getMembers().size() ; i++) {
                                        int id=Integer.parseInt(t.getMembers().get(i));
                                        for(ActiveProgrammer prog: programmers)
                                        {
                                            if(prog.getId()==id){
                                                prog.setActive(false);
                                                prog.setStartDatePresentProject(dateFormat.parse("00/00/0000") );
                                                CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()),"ActiveProgrammer");
                                            }
                                        }
                                    }
                                    System.out.println("Project "+t.getId()+" will be deleted");
                                    CRUDDatabase.deleteFile(programmers,teams, t.getId(), "projects");
                                    return;
                                } else {
                                    return;
                                }
                            }
                        }
                        int index = t.getMembers().indexOf(Integer.toString(choosen));
                        t.getMembers().remove(index);
                        t.getFunctions().remove(index);
                        //Remove the choosen programmer
                        for(ActiveProgrammer prog: programmers)
                        {
                            if(prog.getId()==choosen){
                                prog.setActive(false);
                                prog.setStartDatePresentProject(dateFormat.parse("00/00/0000") );
                                CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()),"ActiveProgrammer");
                            }
                        }
                    break;
                case "3":
                    int count2=0;
                    //Check the available programmers to exchange
                    for(ActiveProgrammer prog: programmers)
                    {

                        if(!prog.isActive()){
                            count2++;
                        }
                    }
                    if(count2==0) {
                        System.out.println("There are no available programmers to exchange");
                        return;
                    }
                    System.out.println("Choose the Id of the programmer you want to add:");
                    Manager.printInactive(programmers);
                    String chooseEntering = scanner.nextLine();
                    System.out.println("Choose the Id of the programmer you want to remove:");
                    Manager.printProgrammersProject(programmers, teams, t.getId());
                    String chooseExit = scanner.nextLine();
                    int indexEntrance = Integer.parseInt(chooseEntering);
                    int indexExit = Integer.parseInt(chooseExit);
                    t.getMembers().add(chooseEntering);
                    t.getMembers().remove(t.getMembers().indexOf(chooseExit));
                    System.out.println("Entrada "+indexEntrance +" Saida "+indexExit);
                    //Set the infos of the two programmers, one to active, and the other to innactive
                    for(ActiveProgrammer p2: programmers) {
                        if(p2.getId()==indexEntrance){
                            p2.setActive(true);
                            p2.setStartDatePresentProject(Menu.getSysDate());
                            CRUDDatabase.updateFile(programmers, teams, Integer.toString(p2.getId()),"ActiveProgrammer");
                        } else if(p2.getId()==indexExit) {
                            p2.setActive(false);
                            p2.setStartDatePresentProject(dateFormat.parse("00/00/0000"));
                            CRUDDatabase.updateFile(programmers, teams, Integer.toString(p2.getId()),"ActiveProgrammer");
                        }
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Please enter a valid option");
            }

        }
    }

    //Function: deleteProject
    //Description: A function to delete a  project according to the informations given by the user
    //
    //@Input:An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void deleteProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Choose a project Id to delete the project: ");
        Manager.printProject(programmers, teams);
        Scanner scanner = new Scanner(System.in);
        int id = 0;
        if (scanner.hasNextInt()) {
            id = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Please choose a valid option: ");
            deleteProject(programmers, teams);
        }
        //Update the info of the team members of the project to innactive
        for(int j=0; j<teams.size();j++){
            if (teams.get(j).getId() == id) {
                for (int i = 0; i < teams.get(j).getMembers().size(); i++) {
                    int idChoice = Integer.parseInt(teams.get(j).getMembers().get(i));
                    for (ActiveProgrammer prog : programmers) {
                        if (prog.getId() == idChoice) {
                            prog.setActive(false);
                            prog.setStartDatePresentProject(dateFormat.parse("00/00/0000"));
                            CRUDDatabase.updateFile(programmers, teams, Integer.toString(prog.getId()), "ActiveProgrammer");
                        }
                    }
                }
                //Save the project in the history file
                CRUDDatabase.saveHistory(teams.get(j));
                //Remove file from list
                teams.remove(teams.indexOf(teams.get(j)));
            }
        }
        //Update database
        CRUDDatabase.deleteFile(programmers, teams, id, "projects");
    }

    //Function: checkProjectDates
    //Description: A function to check if the start and end dates of a project are valid
    //
    //@Input:An object of type Date that represents the start date of a project and an object of type Date that represents
    // the end date of a project
    //
    //@Output: true if the dates are valid, false if the dates are invalid
    public static boolean checkProjectDates(Date dateStart, Date dateEnd){
        if(dateStart.after(dateEnd)){
            return false;
        } else if(dateEnd.before(dateStart))
        {
            return false;
        } else {
            return true;
        }
    }
}
