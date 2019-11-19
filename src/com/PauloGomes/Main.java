package com.PauloGomes;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws java.lang.Exception{
        ArrayList<ActiveProgrammer> programmers = new ArrayList<>();
        ArrayList<ProjectTeam> projects = new ArrayList<>();
        Menu menu = new Menu();
        menu.loadMenu(programmers, projects);
    }


}
