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
                //TODO: Change programmer isActive
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

        Date startDate = new Date();

        System.out.println("When will the project end: (dd/MM/yyyy format)");
        String end = scanner.nextLine();
        Date endDate = dateFormat.parse(end);

        ProjectTeam proj = new ProjectTeam(lastId+1, name, members, functions,startDate,endDate);
        CRUDDatabase write = new CRUDDatabase();
        write.createFile(programmers, teams, proj);
        teams.add(proj);

    }

    public static void editProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {
        ProjectTeam t = new ProjectTeam();
        t = teams.get(0);
//        t.members.add("3");
//        t.members.add("4");
        CRUDDatabase db = new CRUDDatabase();
        db.updateFile(programmers, teams, Integer.toString(t.getId()), "ProjectTeam");
        t.members.remove(3);
        db.updateFile(programmers, teams, Integer.toString(t.getId()), "ProjectTeam");
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

    public static void checkEndDate (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }
}
