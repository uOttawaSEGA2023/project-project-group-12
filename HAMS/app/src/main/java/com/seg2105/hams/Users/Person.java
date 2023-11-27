package com.seg2105.hams.Users;

import java.util.List;
import java.util.Map;

public abstract class Person extends User{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String address;
    private String status;
    private Map<String, String> appointments;

    public Person(String UUID, String email) {
        super(UUID, email);
    }

    public Person (String UUID, String email, String firstName, String lastName, String phoneNumber, String dateOfBirth, String address, String status) {
        super(UUID, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth=dateOfBirth;
        this.address = address;
        this.status = status;

    }

    public Person() {
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Map<String,String> getAppointments() {
        return appointments;
    }

    public void setAppointments(Map<String,String> appointments) {
        this.appointments = appointments;
    }
}
