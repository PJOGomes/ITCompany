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


    public static void createProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) throws ParseException {

        //TODO: implement validations
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
        System.out.println("Choose the programmers available for the project (Insert Id of minimum 2 programmers):");
        for (ActiveProgrammer prog: programmers)
        {
            if(!prog.isActive()) {
                System.out.println("Programmer Id: "+prog.getId()+ " -> "+prog.getLastName().toUpperCase()+", "+prog.getFirstName()+" is available");
            }
        }
        while (addAnother) {
            addId = scanner.nextLine();
            System.out.println("Do you want to add another programmer to the project? (Y-yes, Other key -no)");
            String option = scanner.nextLine();
            if(option.equals("Y")||option.equals("y")) {
                members.add(addId);
                //TODO: Change programmer StartDate
                for(ActiveProgrammer prog: programmers){
                    if(prog.getId()==Integer.parseInt(addId)){
                        prog.setActive(true);
//                        prog.setStartDatePresentProject();
                    }
                }
                //TODO: Change Database Info
                addAnother = true;

            } else {
                members.add(addId);
                //TODO: Change programmer isActive
                //TODO: Change Database Info
                addAnother=false;
                break;
            }
        }
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
        Date today = new Date();
        System.out.println("The start date of the project will be today");
        System.out.println("When will the project end: (dd/MM/yyyy format)");
        String end = scanner.nextLine();
        Date endDate = dateFormat.parse(end);

        ProjectTeam proj = new ProjectTeam(lastId+1, name, members, functions,today,endDate);
        CRUDDatabase write = new CRUDDatabase();
        write.createFile(programmers, teams, proj);
        teams.add(proj);

    }

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
        db.deleteFile(programmers,teams, t.getId(), "projects");
        db.createFile(programmers, teams, t);

    }

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
                    System.out.println("What will be the programmer's function?");
                    String function = scanner.nextLine();
                    t.getFunctions().add(function);
                        for(ActiveProgrammer prog: programmers){
                            if(prog.getId()==choosenId){
                                prog.setActive(true);
                                Date date = new Date();
                                prog.setStartDatePresentProject(date);
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
                        int index = t.getMembers().indexOf(Integer.toString(choosen));
                        t.getMembers().remove(index);
                        t.getFunctions().remove(index);
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
                    for(ActiveProgrammer p2: programmers) {
                        if(p2.getId()==indexEntrance){
                            Date date=new Date();
                            p2.setActive(true);
                            p2.setStartDatePresentProject(date);
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

    public static void deleteProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {
        System.out.println("Choose a project Id to delete the project: ");
        Manager.printProject(programmers, teams);
        Scanner scanner = new Scanner(System.in);
        int id =0;
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Please choose a valid option: ");
            deleteProject(programmers, teams);
        }
        CRUDDatabase.deleteFile(programmers, teams, id, "projects");


    }


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
