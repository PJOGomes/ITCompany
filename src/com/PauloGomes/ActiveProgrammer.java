package com.PauloGomes;

import java.util.ArrayList;
import java.util.Date;

public class ActiveProgrammer implements Programmer{
    private int id;
    private String firstName;
    private String lastName;
    private Date startDatePresentProject;
    private int daysWorkedMonth;
    private double wage;
    private int paymentMethod;
    private boolean active;

    public ActiveProgrammer() {
    }

//   Personalized Constructor

    public ActiveProgrammer(int id, String firstName, String lastName, Date startDatePresentProject, int daysWorkedMonth, double wage, int paymentMethod, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDatePresentProject = startDatePresentProject;
        this.daysWorkedMonth = daysWorkedMonth;
        this.wage = wage;
        this.paymentMethod = paymentMethod;
        this.active = active;
    }


//    Getters And Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getStartDatePresentProject() {
        return startDatePresentProject;
    }

    public void setStartDatePresentProject(Date startDatePresentProject) {
        this.startDatePresentProject = startDatePresentProject;
    }

    public int getDaysWorkedMonth() {
        return daysWorkedMonth;
    }

    public void setDaysWorkedMonth(int daysWorkedMonth) {
        this.daysWorkedMonth = daysWorkedMonth;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    //  Methods


    @Override
    public void addProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) {
        CRUDDatabase db = new CRUDDatabase();
        ActiveProgrammer prog = new ActiveProgrammer();
        db.createFile(programmer, teams, prog);
    }

    @Override
    public void editProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) {

    }

    @Override
    public void deleteProgrammer( ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) {

    }

    @Override
    public void calculateSalary(ActiveProgrammer person) {

    }

    @Override
    public void adjustDaysWorked(ActiveProgrammer person, Date date) {

    }

}
