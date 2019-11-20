package com.PauloGomes;

import java.util.ArrayList;
import java.util.Date;

public class ProjectTeam {
    private int id;
    private int name;
    private ArrayList<String> members;
    private ArrayList<String> functions;
    private Date beginDate;
    private Date endDate;

//    Personalized Constructor

    public ProjectTeam(int id, int name, ArrayList<String> members, ArrayList<String> functions, Date beginDate, Date endDate) {
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

    public int getName() {
        return name;
    }

    public void setName(int name) {
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
    public static void createProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }

    public static void editProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }

    public static void deleteProject (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }

    public static void checkEndDate (ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }
}
