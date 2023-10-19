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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {
    private static FirebaseDatabase databaseInstance;
    private static Gson gson;

    public FirebaseDB() {
        // Initialize Firebase Database reference
        databaseInstance = FirebaseDatabase.getInstance();
        // Initialize GSON
        gson = new Gson();
    }

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

    public static boolean serializeToFirebase(Object object, String root, String ID) throws Exception {
        String json = gson.toJson(object);
        Map<String, Object> objectMap = new Gson().fromJson(json, HashMap.class);

        DatabaseReference objectRef = databaseInstance.getReference(root).child(ID);
        objectRef.updateChildren(objectMap);
        return true;
    }

    public static <T> T deserializeFromFirebase(String root, String ID, Class<T> objectType) {
        DatabaseReference objectRef = databaseInstance.getReference(root).child(ID);
        DataSnapshot dataSnapshot = objectRef.get().getResult();

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
    }

    public interface FirebaseCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

}