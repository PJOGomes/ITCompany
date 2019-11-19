package com.PauloGomes;

import java.util.ArrayList;
import java.util.Date;

public class ActiveProgrammer implements Programmer{
    private int id;
    private String firstName;
    private String lastName;
    private int totalDaysWorked;
    private int daysWorkedMonth;
    private double wage;
    private boolean active;
//   Personalized Constructor

    public ActiveProgrammer (int id, String firstName, String lastName, int totalDaysWorked, double wage, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalDaysWorked = totalDaysWorked;
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

    public int getTotalDaysWorked() {
        return totalDaysWorked;
    }

    public void setTotalDaysWorked(int totalDaysWorked) {
        this.totalDaysWorked = totalDaysWorked;
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
    public void editProgrammer(int id, ArrayList<ActiveProgrammer> programmer) {

    }

    @Override
    public void deleteProgrammer(int id, ArrayList<ActiveProgrammer> programmer) {

    }

    @Override
    public void calculateSalary(ActiveProgrammer person) {

    }

    @Override
    public void adjustDaysWorked(ActiveProgrammer person, Date date) {

    }
}
