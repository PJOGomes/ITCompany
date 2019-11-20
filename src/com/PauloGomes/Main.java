package com.PauloGomes;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws java.lang.Exception{
        ArrayList<ActiveProgrammer> programmers = new ArrayList<>();
        ArrayList<ProjectTeam> projects = new ArrayList<>();
        Menu menu = new Menu();

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice == "a")
        {
            menu.loadMenu(programmers, projects);
        }
        else
        {
            menu.mainMenu(programmers, projects);
        }

    }


}
