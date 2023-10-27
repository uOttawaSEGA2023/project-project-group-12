package com.seg2105.hams.Users;

public abstract class Person extends User{
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String address;
    protected String status;

    public Person(String UUID, String email) {
        super(UUID, email);
    }

    public Person (String UUID, String email, String firstName, String lastName, String phoneNumber, String address, String status) {
        super(UUID, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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
}
