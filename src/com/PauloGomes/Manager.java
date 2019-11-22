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

        Menu menu = new Menu();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("IT COMPANY - Report");
        System.out.println("IT Company is actually composed of "+teams.size()+" project teams, and "+programmers.size()+" programmers");
        int totalDays=0;
        int active=0;
        for(ActiveProgrammer prog: programmers){
            totalDays +=prog.getDaysWorkedMonth();
            if(prog.isActive()||(!prog.isActive()&&prog.getDaysWorkedMonth()!=0)) {
                active++;
            }
        }
        int daysMissing = 0;
        int dif=0;
        for(ProjectTeam proj: teams) {
            LocalDate localDate = Menu.getSysDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int startMonth=localDate.getMonthValue();
            LocalDate localDateFinal = proj.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int endMonth = localDateFinal.getMonthValue();
            if(startMonth==endMonth){
                dif = localDateFinal.getDayOfMonth()-localDate.getDayOfMonth()+1;
                daysMissing += dif*proj.getMembers().size();
            } else {
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
            System.out.println("This month "+active+" programmers have worked "+totalDays+" days, and "+daysMissing+ " days left worked");
            System.out.println("Project team details");
            for(ProjectTeam pt: teams){
                System.out.println("Project Team : "+ pt.getId() + " - "+pt.getName());
                for (int i = 0; i <pt.getMembers().size() ; i++) {
                    for(ActiveProgrammer prog: programmers){
                        if(prog.getId()==Integer.parseInt(pt.getMembers().get(i))){
                            System.out.println(prog.getLastName().toUpperCase()+", "+prog.getFirstName()+", in charge of "+pt.getFunctions().get(i)+" from "+dateFormat.format(prog.getStartDatePresentProject())
                            +" to "+dateFormat.format(pt.getEndDate())+", has worked "+prog.getDaysWorkedMonth()+" days this month (total salary "+prog.calculateSalary(prog));
                        }
                    }
                    }
                }
            }
    }

    public static void updateDate (ArrayList<ActiveProgrammer> programmmers, ArrayList<ProjectTeam> projects) throws ParseException {
            Menu menu = new Menu();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            LocalDate localDate = Menu.getSysDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int startMonth=localDate.getMonthValue();
            menu.setSysDate(new Date(Menu.getSysDate().getTime()+ TimeUnit.DAYS.toMillis( 1 )));
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
            for(ProjectTeam proj: projects){
                if(proj.getEndDate().before(Menu.getSysDate())){
                    for (int i = 0; i <proj.getMembers().size() ; i++) {
                        int id=Integer.parseInt(proj.getMembers().get(i));
                        for(ActiveProgrammer prog: programmmers)
                        {
                            if(prog.getId()==id){
                                prog.setActive(false);
                                prog.setStartDatePresentProject(dateFormat.parse("00/00/0000") );
                                CRUDDatabase.updateFile(programmmers, projects, Integer.toString(prog.getId()),"ActiveProgrammer");
                            }
                        }
                    }
                    CRUDDatabase.deleteFile(programmmers, projects, proj.getId(), "projects");
                }
            }


    }
}
