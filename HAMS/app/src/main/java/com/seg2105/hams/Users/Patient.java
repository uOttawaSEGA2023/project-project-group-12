package com.seg2105.hams.Users;

import java.io.Serializable;

public class Patient extends Person implements Serializable {
    private String healthNumber;

    public Patient(){

    }
    public Patient (String UUID, String email, String firstName, String lastName, String phoneNumber, String dateOfBirth, String address,String healthNumber, String status) {
        super(UUID, email, firstName, lastName, phoneNumber, dateOfBirth, address, status);
        this.healthNumber = healthNumber;
    }

    public String getHealthNumber() {
        return healthNumber;
    }


    @Override
    public String toString(){
        return super.toString() + " healthNumber: " + healthNumber;
    }

    @Override
    public String getRole() {
        return "patient";
    }

    @Override
    public void setRole() {

    }
}
