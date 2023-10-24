package com.seg2105.hams.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Patient extends Person implements Serializable {
    private String healthNumber;

    public Patient(){

    }
    public Patient (String UUID, String email, String firstName, String lastName, String phoneNumber, String address,String healthNumber) {
        super(UUID, email, firstName, lastName, phoneNumber, address);
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
        return "Patient";
    }
}
