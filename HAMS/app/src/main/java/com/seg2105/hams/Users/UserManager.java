package com.seg2105.hams.Users;

import static com.seg2105.hams.Database.FirebaseDB.*;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.seg2105.hams.Database.FirebaseDB;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class UserManager {
    private static User currentUser;


    public static boolean putUserInFirebase(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Push the object to the database
        Map<String, User> users = new HashMap<>();
        users.put(user.getUUID(), user);
        databaseReference.setValue(users);
        return true;

    }

    public static void getUserFromDatabase(String UUID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever
                // data at this location is updated.
                if (dataSnapshot.exists()) {
                    // dataSnapshot.getChildren() gives you the snapshot of all children under "users"
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Use the User class to deserialize the data from the snapshot
                        currentUser = snapshot.getValue(User.class);
                        break;
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

    }
    public static boolean isLoggedIn() {
        return currentUser==null;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static String getCurrentUser() {
        return currentUser.toString();
    }
}
