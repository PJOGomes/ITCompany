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
    private boolean active;

    public ActiveProgrammer() {
    }

    //   Personalized Constructor

    public ActiveProgrammer(int id, String firstName, String lastName, int daysWorkedMonth, double wage, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.daysWorkedMonth = daysWorkedMonth;
        this.wage = wage;
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


    //  Methods


    @Override
    public void addProgrammer(ArrayList<ActiveProgrammer> programmer) {

    }

    @Override
    public void editProgrammer(ArrayList<ActiveProgrammer> programmer) {

    }

    @Override
    public void deleteProgrammer( ArrayList<ActiveProgrammer> programmer) {

    }

    @Override
    public void calculateSalary(ActiveProgrammer person) {

    }

    @Override
    public void adjustDaysWorked(ActiveProgrammer person, Date date) {

    }
}
