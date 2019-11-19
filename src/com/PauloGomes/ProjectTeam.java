package com.PauloGomes;

import java.util.Date;

public class ProjectTeam {
    private int id;
    private int name;
    private ActiveProgrammer[] members;
    private String[] functions;
    private Date beginDate;
    private Date endDate;

//    Personalized Constructor

    public ProjectTeam(int id, int name, ActiveProgrammer[] members, String[] functions, Date beginDate, Date endDate) {
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

    public ActiveProgrammer[] getMembers() {
        return members;
    }

    public void setMembers(ActiveProgrammer[] members) {
        this.members = members;
    }

    public String[] getFunctions() {
        return functions;
    }

    public void setFunctions(String[] functions) {
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

}
