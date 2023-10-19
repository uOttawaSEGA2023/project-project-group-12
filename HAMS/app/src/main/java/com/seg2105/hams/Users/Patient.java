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

    public Patient(String UUID, String email) {
        super(UUID, email);
    }
    public Patient (String UUID, String email, boolean isRegistered, String firstName, String lastName, String phoneNumber, String address,String healthNumber) {
        super(UUID, email, isRegistered, firstName, lastName, phoneNumber, address);
        this.healthNumber = healthNumber;
    }



    public void deserializeFromFirebase() {

        DatabaseReference userUUIDReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userUUIDReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the data as a Map
                Map<String, Object> userDataMap = (Map<String, Object>) dataSnapshot.getValue();

                if (userDataMap != null) {
                    healthNumber =  ((String) userDataMap.get("healthNumber"));
                    address =  ((String) userDataMap.get("address"));
                    firstName =  ((String) userDataMap.get("firstName"));
                    isRegistered =  ((boolean) userDataMap.get("isRegistered"));
                    lastName =  ((String) userDataMap.get("lastName"));
                    phoneNumber =  ((String) userDataMap.get("phoneNumber"));
                    email =  ((String) userDataMap.get("email"));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Handle errors
            }
        });


        }

}
