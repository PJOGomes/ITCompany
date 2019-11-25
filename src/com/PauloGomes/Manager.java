package com.PauloGomes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Manager {

    //Empty constructor
    public Manager() {
    }

    //Function: printProgrammer
    //Description: Function to print the details of each programmer
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void printProgrammer(ArrayList<ActiveProgrammer> list, ArrayList<ProjectTeam> project) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //For each programmer in the list
        for(ActiveProgrammer item: list)
        {
            System.out.println("-------Programmer "+item.getId()+"---------");
            System.out.println("Last Name: "+item.getLastName().toUpperCase());
           System.out.println("First Name: "+item.getFirstName());
            System.out.println("Days worked this month: "+item.getDaysWorkedMonth());
            System.out.println("Working in a project? " +item.isActive());
            //Print the start date of the programmer in it's current project
            if(item.isActive()) {
                System.out.println("Started working on the project on: "+dateFormat.format(item.getStartDatePresentProject()));
            }
            System.out.println("Wage per hour: "+item.getWage());
        }
    }

    //Function: printProject
    //Description: Function to print the details of each project
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void printProject(ArrayList<ActiveProgrammer> listap, ArrayList<ProjectTeam> list) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //For each project in the list
        for (ProjectTeam item: list) {
            System.out.println("-------Project "+item.getId()+"---------");
            System.out.println("Project name: "+item.getName());
            System.out.println("Team Members: ");
            //Get each programmer's info according to the id of the programmer
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

    //Function: printProgrammersProject
    //Description: Function to print the details of each programmer
    //
    //@Input: An arrayList of type ActiveProgrammer and an arrayList of type ProjectTeam, and an int that represents the programmer's id
    //
    //@Output: no output
    public static void printProgrammersProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, int id){
        ProjectTeam t = new ProjectTeam();
        //Get the projects id
        for(ProjectTeam pt: teams){
            if(pt.getId()==id){
                t = pt;
            }
        }
        //Get the programmer according to the id
        for (int i = 0; i <t.getMembers().size() ; i++) {
            for(ActiveProgrammer p: programmers)
            {
                if(t.getMembers().get(i).equals(Integer.toString(p.getId()))){
                    System.out.println(p.getId() + " - "+p.getFirstName()+" "+p.getLastName());
                }
            }
        }
    }

    //Function: printInactive
    //Description: Function to print the programmers that are not working in any project
    //
    //@Input:An arrrayList of type ActiveProgrammer
    //
    //@Output: no output
    public static void printInactive(ArrayList<ActiveProgrammer> programmers) {
        //For each programmer in the programmer's list
        for(ActiveProgrammer prog: programmers) {
            //If he isn't working in a project
            if(prog.isActive()==false){
                System.out.println(prog.getId() + " - "+prog.getFirstName()+" "+prog.getLastName());
            }
        }
    }

    //Function: report
    //Description: Function that prints the company's report according to the project's specification
    //
    //@Input: An arrayList of type ActiveProgrammer, and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void report (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

        Menu menu = new Menu();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("IT COMPANY - Report");
        System.out.println("IT Company is actually composed of "+teams.size()+" project teams, and "+programmers.size()+" programmers");
        int totalDays=0;
        int active=0;
        //Get the number of days worked by each programmer an add them
        for(ActiveProgrammer prog: programmers){
            totalDays +=prog.getDaysWorkedMonth();
            if(prog.isActive()||(!prog.isActive()&&prog.getDaysWorkedMonth()!=0)) {
                active++;
            }
        }
        int daysMissing = 0;
        int dif=0;
        //Check the days left in the current projects
        for(ProjectTeam proj: teams) {
            LocalDate localDate = Menu.getSysDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int startMonth=localDate.getMonthValue();
            LocalDate localDateFinal = proj.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int endMonth = localDateFinal.getMonthValue();
            //If the project ends this current month
            if(startMonth==endMonth){
                dif = localDateFinal.getDayOfMonth()-localDate.getDayOfMonth()+1;
                daysMissing += dif*proj.getMembers().size();
            } else { //If the project ends in a different month get the days until the end of the month
                if(startMonth==1||startMonth==3||startMonth==5||startMonth==7||startMonth==8||startMonth==10||startMonth==12)
                {
                    dif = 31-localDate.getDayOfMonth()+1;
                    daysMissing += dif*proj.getMembers().size();
                } else if(startMonth==2&&localDate.isLeapYear()){
                    dif = 28-localDate.getDayOfMonth()+1;
                    daysMissing += dif*proj.getMembers().size();
                }else if(startMonth==2&&!localDate.isLeapYear()){
                    dif = 27-localDate.getDayOfMonth()+1;
                    daysMissing += dif*proj.getMembers().size();
            } else {
                    dif = 30-localDate.getDayOfMonth()+1;
                    daysMissing += dif*proj.getMembers().size();
                }
            }

        }
        System.out.println("This month "+active+" programmers have worked "+totalDays+" days, and "+daysMissing+ " days left worked");
        System.out.println("Project team details");
        //Get each project's info
        for(ProjectTeam pt: teams){
            System.out.println("Project Team : "+ pt.getId() + " - "+pt.getName());
            for (int i = 0; i <pt.getMembers().size() ; i++) {
                for(ActiveProgrammer prog: programmers){
                    if(prog.getId()==Integer.parseInt(pt.getMembers().get(i))){
                        System.out.println(prog.getLastName().toUpperCase()+", "+prog.getFirstName()+", in charge of "+pt.getFunctions().get(i)+" from "+dateFormat.format(prog.getStartDatePresentProject())
                                +" to "+dateFormat.format(pt.getEndDate())+", has worked "+prog.getDaysWorkedMonth()+" days this month (total salary "+prog.calculateSalary(prog)+"€)");
                        break;
                    }
                }
            }
        }
    }

    //Function: updateDate
    //Description: Function that updates the system's date by onde day
    //
    //@Input: An arrayList of type ActiveProgrammer, and an arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void updateDate (ArrayList<ActiveProgrammer> programmmers, ArrayList<ProjectTeam> projects) throws ParseException {
            Menu menu = new Menu();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            LocalDate localDate = Menu.getSysDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int startMonth=localDate.getMonthValue();
            menu.setSysDate(new Date(Menu.getSysDate().getTime()+ TimeUnit.DAYS.toMillis( 1 )));
            System.out.println("Date changed to: "+dateFormat.format(Menu.getSysDate()));
            LocalDate localDateFinal = Menu.getSysDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int endMonth = localDateFinal.getMonthValue();
            //If startMonth and endMonth are the same, we update the number of days worked in that month by the active programmers
            if(startMonth==endMonth){
                for(ActiveProgrammer prog: programmmers)
                {
                    if(prog.isActive()){
                        prog.setDaysWorkedMonth(prog.getDaysWorkedMonth()+1);
                    }
                }
            } else {
                //If startMonth and endMonth are different, the month is different and so we set the number of days worked in that month to 0
                for(ActiveProgrammer prog: programmmers)
                {
                        prog.setDaysWorkedMonth(0);
                }
            }
            //Check if any projects ended
       if(projects.size()==0) {
           System.out.println("No project on course in this date");
            return;
       }
        //If a project has ended (by end date)
        for(int j=0; j<projects.size();j++){
            if(projects.get(j).getEndDate().before(new Date(Menu.getSysDate().getTime()+ TimeUnit.DAYS.toMillis( 1 )))){
                for (int i = 0; i <projects.get(j).getMembers().size() ; i++) {
                    int id=Integer.parseInt(projects.get(j).getMembers().get(i));
                    //Edit the programmers details for the programmers working in that project
                    for(ActiveProgrammer prog: programmmers)
                    {
                        if(prog.getId()==id){
                            prog.setActive(false);
                            prog.setStartDatePresentProject(dateFormat.parse("00/00/0000") );
                            //Update the database file
                            CRUDDatabase.updateFile(programmmers, projects, Integer.toString(prog.getId()),"ActiveProgrammer");
                        }
                    }
                }
                //Send the project to history
                CRUDDatabase.saveHistory(projects.get(j));
                //Delete the project from the database
                CRUDDatabase.deleteFile(programmmers, projects, projects.get(j).getId(), "projects");
                //Remove the project from the list os projects
                projects.remove(projects.indexOf(projects.get(j)));


            }
        }

    }

    //Function: printHistory
    //Description: A function to print the history of ended or deleted projects
    //
    //@Input: An arrayList of type ProjectTeam
    //
    //@Output: no output
    public static void printHistory(ArrayList<ProjectTeam>historyList){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //If the history database is empty
        if(historyList.size()==0){
            System.out.println("There are no projects in history");
            return;
        }
        //If the history database has projects, print it's informations
        for (ProjectTeam item: historyList) {
            System.out.println("-------Project "+item.getId()+"---------");
            System.out.println("Project name: "+item.getName());
            System.out.println("Team Members: ");
            for (int i = 0; i <item.getMembers().size() ; i++) {
                System.out.println("Programmer Id: "+item.getMembers().get(i)+" with function "+item.getFunctions().get(i));
            }
            System.out.println("Project started: "+dateFormat.format(item.getBeginDate()) );
            System.out.println("Project ended: "+dateFormat.format(item.getEndDate()) );
        }
    }
}
