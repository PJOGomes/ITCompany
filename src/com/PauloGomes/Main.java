package com.PauloGomes;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws java.lang.Exception{
        //Create the arrayList that will contain the company's programmers
        ArrayList<ActiveProgrammer> programmers = new ArrayList<>();
        //Create the arrayList that will contain the company's projects
        ArrayList<ProjectTeam> projects = new ArrayList<>();
        Menu menu = new Menu();
        //Load the startup menu
        menu.loadMenu(programmers, projects);
    }


}
