package com.seg2105.hams.Users;

public class Person extends User{
    protected boolean isRegistered;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String address;

    public Person(String UUID, String email) {
        super(UUID, email);
    }

    public Person (String UUID, String email, boolean isRegistered, String firstName, String lastName, String phoneNumber, String address) {
        super(UUID, email);
        this.isRegistered = isRegistered;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
