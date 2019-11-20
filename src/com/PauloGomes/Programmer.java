package com.PauloGomes;

import java.util.ArrayList;
import java.util.Date;

public interface Programmer {
    void addProgrammer(ArrayList<ActiveProgrammer> programmer);
    void editProgrammer(ArrayList<ActiveProgrammer> programmer);
    void deleteProgrammer(ArrayList<ActiveProgrammer> programmer);
    void calculateSalary(ActiveProgrammer person);
    void adjustDaysWorked(ActiveProgrammer person, Date date);
}
