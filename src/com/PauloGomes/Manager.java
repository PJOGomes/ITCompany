package com.PauloGomes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Manager {

    public Manager() {
    }

    public static void printProgrammer(ArrayList<ActiveProgrammer> list, ArrayList<ProjectTeam> project) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for(ActiveProgrammer item: list)
        {
            System.out.println("-------Programmer "+item.getId()+"---------");
            System.out.println("Last Name: "+item.getLastName().toUpperCase());
           System.out.println("First Name: "+item.getFirstName());
            System.out.println("Days worked this month: "+item.getDaysWorkedMonth());
            System.out.println("Working in a project? " +item.isActive());
            if(item.isActive()) {
                System.out.println("Started working on the project on: "+dateFormat.format(item.getStartDatePresentProject()));
            }
            System.out.println("Wage per hour: "+item.getWage());
        }
    }

    public static void printProject(ArrayList<ActiveProgrammer> listap, ArrayList<ProjectTeam> list) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (ProjectTeam item: list) {
            System.out.println("-------Project "+item.getId()+"---------");
            System.out.println("Project name: "+item.getName());
            System.out.println("Team Members: ");
            for (int i = 0; i <item.getMembers().size() ; i++) {
                int id = Integer.parseInt(item.getMembers().get(i));
                ActiveProgrammer temp = new ActiveProgrammer();
                for (ActiveProgrammer programmer: listap)
                {
                    if(programmer.getId()== id)
                    {
                        temp = programmer;
                    }
                }
                System.out.println(temp.getLastName().toUpperCase()+", "+temp.getFirstName()+ ", in charge of "+item.getFunctions().get(i));
            }
            System.out.println("Project started: "+dateFormat.format(item.getBeginDate()) );
            System.out.println("Project will end: "+dateFormat.format(item.getEndDate()) );
        }
    }

    public static void printProgrammersProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, int id){
        ProjectTeam t = new ProjectTeam();
        for(ProjectTeam pt: teams){
            if(pt.getId()==id){
                t = pt;
            }
        }
        for (int i = 0; i <t.getMembers().size() ; i++) {
            for(ActiveProgrammer p: programmers)
            {
                if(t.getMembers().get(i).equals(Integer.toString(p.getId()))){
                    System.out.println(p.getId() + " - "+p.getFirstName()+" "+p.getLastName());
                }
            }
        }
    }

    public static void printInactive(ArrayList<ActiveProgrammer> programmers) {
        for(ActiveProgrammer prog: programmers) {
            if(prog.isActive()==false){
                System.out.println(prog.getId() + " - "+prog.getFirstName()+" "+prog.getLastName());
            }
        }
    }

    public static void report (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }

    public static void updateDate (ArrayList<ActiveProgrammer> programmmers, ArrayList<ProjectTeam> teams, Date date) {

    }
}
