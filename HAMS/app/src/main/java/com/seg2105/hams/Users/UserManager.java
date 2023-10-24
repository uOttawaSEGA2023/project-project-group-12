package com.seg2105.hams.Users;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class UserManager {
    private static User currentUser;


    public static boolean putUserInFirebase(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Push the object to the database
        Map<String, Object> userData = new HashMap<>();
        userData.put("userData", user);
        userData.put("userType", user.getRole());
        databaseReference.child(user.getUUID()).setValue(userData);
        return true;

    }

    public static boolean getUserFromDatabase(String UUID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever
                // data at this location is updated.
                if (dataSnapshot.exists()) {
                    // dataSnapshot.getChildren() gives you the snapshot of all children under "users"
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userType = snapshot.child("userType").getValue(String.class);
                        // Use the User class to deserialize the data from the snapshot
                        if ("admin".equals(userType)) {
                            currentUser = snapshot.getValue(Administrator.class);
                            Log.d("currentUser", currentUser.toString());
                            break;
                        } else if ("patient".equals(userType)) {
                            currentUser = snapshot.getValue(Patient.class);
                            Log.d("currentUser", currentUser.toString());
                            break;
                        } else if ("doctor".equals(userType)) {
                            currentUser = snapshot.getValue(Doctor.class);
                            Log.d("currentUser", currentUser.toString());
                            break;
                        }
                    }
                } else {
                    Log.e("failed", "does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", databaseError.getDetails());

            }
        });
        return true;
    }
    public static boolean isLoggedIn() {
        return currentUser!=null;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}
