package com.seg2105.hams.Users;

import com.seg2105.hams.Managers.Shift;

import java.util.HashMap;
import java.util.List;

public class Doctor extends Person {
    private String automaticallyApprove;
    private String employeeNumber;
    private List<String> specialties;
    private HashMap<String, Shift> shifts;

    public Doctor() {};

    public Doctor (String UUID, String email, String firstName, String lastName, String phoneNumber, String dateOfBirth, String address, String automaticallyApprove, String employeeNumber, List<String> specialties, String status) {
        super(UUID, email, firstName, lastName, phoneNumber, dateOfBirth, address, status);
        this.automaticallyApprove=automaticallyApprove;
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
    }
    @Override
    public String getRole() {
        return "doctor";
    }

    @Override
    public void setRole() {}

    public String getAutomaticallyApprove() {return automaticallyApprove;}
    public void setAutomaticallyApprove(String automaticallyApprove) { this.automaticallyApprove=automaticallyApprove;}
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public HashMap<String, Shift> getShifts() {
        return shifts;
    }

    public void setShifts(HashMap<String, Shift> shifts) {
        this.shifts = shifts;
    }
}
