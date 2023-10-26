package com.seg2105.hams.Users;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.seg2105.hams.UI.UserCallback;

import java.util.HashMap;
import java.util.Map;


public class UserManager {
    private static User currentUser;

    // Used to put a user object in the database
    public static boolean putUserInDatabase(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Push the object to the database
        Map<String, Object> userData = new HashMap<>();
        userData.put("userData", user);
        userData.put("userType", user.getRole());
        databaseReference.child(user.getUUID()).setValue(userData);
        return true;

    }

    // Used to get a user object from the database
    public static void getUserFromDatabase(String UUID, UserCallback callback) {
        // Get the datareference of child of users with matching UUID
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(UUID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If datareference is found
                if (dataSnapshot.exists()) {
                    // Get the userType as String
                    String userType = dataSnapshot.child("userType").getValue(String.class);
                    // Get the userData as a DataSnapshot
                    DataSnapshot userDataSnapshot = dataSnapshot.child("userData");

                    // Depending on userType, deserialize into corresponding instance
                    if ("admin".equals(userType)) {
                        currentUser = userDataSnapshot.getValue(Administrator.class);
                        callback.onUserLoaded();
                    } else if ("patient".equals(userType)) {
                        currentUser = userDataSnapshot.getValue(Patient.class);
                        callback.onUserLoaded();
                    } else if ("doctor".equals(userType)) {
                        currentUser = userDataSnapshot.getValue(Doctor.class);
                        callback.onUserLoaded();
                    }
                    Log.d("currentUser", currentUser.toString());
                } else {
                    callback.onFailure("User with UUID " + UUID + " does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
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
