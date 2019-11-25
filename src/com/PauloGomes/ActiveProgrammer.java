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

    //Function: addProgrammer
    //Description: function to add a programmer to the programmer's List
    //
    //@Input: An arraylist of type ActiveProgrammer and an arraylist of type ProjectTeam
    //
    //@Output: No return
    @Override
    public void addProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        date = dateFormat.parse("00/00/0000");
        CRUDDatabase db = new CRUDDatabase();
        int size = programmer.size();
        int last = programmer.get(size-1).getId();

        //Get the programmer's info from the user
        System.out.println("Programmer's first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Programmer's last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Programmer's wage: ");
        double value = scanner.nextDouble();
        System.out.println("Programmer's payment Method (50% or 100%): ");
        if(scanner.hasNextInt()){
            int paymentMethod = scanner.nextInt();
            scanner.nextLine();
            if(paymentMethod!=50&&paymentMethod!=100){
                System.out.println("You didn't enter a valid payment otpion");
                return;
            }
        }
        //Create the user
        ActiveProgrammer prog = new ActiveProgrammer(last+1, firstName, lastName, date, 0, value, paymentMethod, false);
        //Add user to arraylist
        programmer.add(prog);
        //Add user to XML file
        db.createFile(programmer, teams, prog);
    }

    //Function: editProgrammer
    //Description: function to edit a programmer from the programmer's List
    //
    //@Input: An arraylist of type ActiveProgrammer and an arraylist of type ProjectTeam
    //
    //@Output: No return
    @Override
    public void editProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) {
        ActiveProgrammer p = new ActiveProgrammer();
        String option ="";
        int choosenId = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the Id of the programmer you want to edit:");
        //Print the current programmers in the list
        for(ActiveProgrammer prog: programmer){
            System.out.println(prog.getId()+ " - "+prog.getLastName().toUpperCase()+", "+prog.getFirstName());
        }
        //Check if the user inputs an integer
        if(scanner.hasNextInt())
        {
            choosenId = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Choose a valid option");
            editProgrammer(programmer, teams);
        }
        //Check if the integer entered by the user is a programmer in the list
        for(ActiveProgrammer prog: programmer){
            if(prog.getId()==choosenId)
            {
                p = prog;
            }
        }
        if(p==null)
        {
            System.out.println("The choosen programmer is not in the company.");
            return;
        }
        //Present the editing options
        System.out.println("Editing programmer "+p.getId());
        Boolean exit = false;
        //Choose options to edit
        while(!exit){
            System.out.println("1 - Edit first name:");
            System.out.println("2 - Edit last name:");
            System.out.println("3 - Edit wage:");
            System.out.println("4 - Edit payment method:");
            System.out.println("5 - End Edit");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Change first name from "+p.getFirstName()+" to:");
                    String temp = scanner.nextLine();
                    p.setFirstName(temp);
                    break;
                case "2":
                    System.out.println("Change first name from "+p.getLastName()+" to:");
                    String aux = scanner.nextLine();
                    p.setLastName(aux);
                    break;
                case "3":
                    System.out.println("Change wage from "+p.getWage()+" to:");
                    Double value = scanner.nextDouble();
                    p.setWage(value);
                    break;
                case "4":
                    System.out.println("Change payment method from "+p.getPaymentMethod()+"% to: (50% or 100%");
                    if(scanner.hasNextInt()) {
                        int pay = scanner.nextInt();
                        scanner.nextLine();
                        if(pay==50||pay==100)
                        {
                            p.setPaymentMethod(pay);
                        } else {
                            System.out.println("You didn't insert a valid payment method");
                            return;
                        }
                    } else {
                        System.out.println("You didn't insert a valid payment method");
                        return;
                    }
                    break;
                case "5":
                    System.out.println("Editing ended");
                    exit = true;
                    break;
                default:
                    System.out.println("Please choose a valid option");
                    break;
            }
        }
        //Update user in the database
        CRUDDatabase update = new CRUDDatabase();
        update.updateFile(programmer, teams, Integer.toString(choosenId), "ActiveProgrammer");
    }

    //Function: deleteProgrammer
    //Description: function to delete a programmer from the programmer's List
    //
    //@Input: An arraylist of type ActiveProgrammer and an arraylist of type ProjectTeam
    //
    //@Output: No return
    @Override
    public void deleteProgrammer( ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams) {
        System.out.println("Choose a programmer Id to delete the project: ");
        Manager.printProgrammer(programmer, teams);
        Scanner scanner = new Scanner(System.in);
        int id =0;
        //Check if user inputs an integer
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Please choose a valid option: ");
            deleteProgrammer(programmer, teams);
        }

        //Only allow to delete programmers that are currently not in a project
        for(ActiveProgrammer p: programmer){
            if(p.getId()==id){
                if(p.isActive())
                {
                    System.out.println("You cannot delete a programmer that is currently working in a project.\nPlease remove the programmer from the project and then delete him");
                    return;
                }
            }
        }
        //Remove programmer from the xml file
        CRUDDatabase.deleteFile(programmer, teams, id, "programmers");
        int indexRemove=-1;
        for(ActiveProgrammer prog: programmer) {
            if(prog.getId()==id){
                indexRemove = programmer.indexOf(prog);
            }
        }
        //Remove programmer from the list
        programmer.remove(indexRemove);
        System.out.println("Programmer deleted with success");
    }

    //Function: calculateSalary
    //Description: function to calculate a programmer's salary until the current date
    //
    //@Input: an object of type ActiveProgrammer
    //
    //@Output: A double corresponding to the salary
    @Override
    public double calculateSalary(ActiveProgrammer person) {
        return person.getDaysWorkedMonth()*person.getWage()*person.getPaymentMethod()/100;
    }

}
