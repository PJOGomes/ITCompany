package com.PauloGomes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
    public void addProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        date = dateFormat.parse("00/00/0000");
        CRUDDatabase db = new CRUDDatabase();
        int size = programmer.size();
        int last = programmer.get(size-1).getId();

        System.out.println("Programmer's first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Programmer's last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Programmer's wage: ");
        double value = scanner.nextDouble();
        System.out.println("Programmer's payment Method (50% or 100%): ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine();

        ActiveProgrammer prog = new ActiveProgrammer(last+1, firstName, lastName, date, 0, value, paymentMethod, false);
        programmer.add(prog);
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
