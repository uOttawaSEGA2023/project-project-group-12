package com.seg2105.hams.Users;

public class Doctor extends Person {
    private String employeeNumber;
    private String[] specialties;

    public Doctor() {};

    public Doctor (String UUID, String email, String firstName, String lastName, String phoneNumber, String address,String employeeNumber, String[] specialties) {
        super(UUID, email, firstName, lastName, phoneNumber, address);
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
    }
    @Override
    public String getRole() {
        return "doctor";
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String[] getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String[] specialties) {
        this.specialties = specialties;
    }


}
