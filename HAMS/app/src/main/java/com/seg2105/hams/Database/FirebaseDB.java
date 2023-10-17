package com.seg2105.hams.Database;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDB {
    private void getValueFromKey(FirebaseUser user, String key) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        // Query the database to get the role for the specific UID
        usersRef.child(user.getUid()).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Handle the data change event
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue(String.class);
                    //return value;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.println(Log.ERROR, "Error retrieving", "Database error.");
            }
        });

    }

//    public void getUserFromDatabase(FirebaseUser user) {
//        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
//
//        // Query the database to get the role for the specific UID
//        usersRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Handle the data change event
//                if (dataSnapshot.exists()) {
//                    String value = dataSnapshot.getValue(String.class);
//                    //return value;
//                }
//
//            }
//}
}