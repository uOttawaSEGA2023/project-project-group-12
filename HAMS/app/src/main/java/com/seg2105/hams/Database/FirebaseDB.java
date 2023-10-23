package com.seg2105.hams.Database;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.User;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {
    private static FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
    private static Gson gson = new Gson();

    private void getValueFromKey(FirebaseUser user, String key) {
        DatabaseReference usersRef = databaseInstance.getReference().child("users");

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


//    public static boolean serializeToFirebase(Object object, String root, String ID) throws Exception {
//        Log.d("User info", object.toString());
//        String json = gson.toJson(object);
//        Map<String, Object> objectMap = new Gson().fromJson(json, HashMap.class);
//
//        DatabaseReference objectRef = databaseInstance.getReference(root).child(ID);
//        objectRef.updateChildren(objectMap);
//        return true;
//    }

    public static Task<User> getUserFromFirebase(String UUID) {
        // Perform Firebase database operation and return Task<User>
        return deserializeFromFirebase("users", UUID, User.class);
    }
    public static <T> Task<T> deserializeFromFirebase(String root, String ID, Class<T> objectType) {
        DatabaseReference objectRef = databaseInstance.getReference(root).child(ID);
        Task<DataSnapshot> dataSnapshotTask = objectRef.get();

        return dataSnapshotTask.continueWith(task -> {
            DataSnapshot dataSnapshot = task.getResult();
            if (dataSnapshot.exists()) {
                // Retrieve the data as a Map
                Map<String, Object> objectMap = (Map<String, Object>) dataSnapshot.getValue();
                // Convert the Map to a JSON string
                String json = gson.toJson(objectMap);
                // Deserialize the JSON to the specified object type
                return gson.fromJson(json, objectType);
            } else {
                // Object not found
                return null;
            }
        });
    }


    public interface FirebaseCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

}