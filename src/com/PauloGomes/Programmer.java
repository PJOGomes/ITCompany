package com.PauloGomes;

import java.util.ArrayList;
import java.util.Date;

public interface Programmer {
    void addProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams);
    void editProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams);
    void deleteProgrammer(ArrayList<ActiveProgrammer> programmer, ArrayList<ProjectTeam> teams);
    void calculateSalary(ActiveProgrammer person);
    void adjustDaysWorked(ActiveProgrammer person, Date date);
}
