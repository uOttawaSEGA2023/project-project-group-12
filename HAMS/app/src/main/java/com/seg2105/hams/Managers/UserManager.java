package com.seg2105.hams.Managers;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.navigation.Navigation.findNavController;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Administrator;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Users.User;
import com.seg2105.hams.Util.UserCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        databaseReference.addValueEventListener(new ValueEventListener() {
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
                        callback.onSuccess();
                    } else if ("patient".equals(userType)) {
                        currentUser = userDataSnapshot.getValue(Patient.class);
                        callback.onSuccess();
                    } else if ("doctor".equals(userType)) {
                        currentUser = userDataSnapshot.getValue(Doctor.class);
                        callback.onSuccess();
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

    // Used to get a all person from database
    public static void getPersonsFromDatabase(UserCallback callback) {
        List<Person> persons = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.orderByChild("userType").startAt("doctor").endAt("patient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If datareference is found
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userType = snapshot.child("userType").getValue(String.class);
                        // Get the userData as a DataSnapshot
                        DataSnapshot userDataSnapshot = snapshot.child("userData");

                        // add person to persons list that will eventually be returned with callback method onPersonsLoaded
                        if ("doctor".equals(userType)) {
                            persons.add(userDataSnapshot.getValue(Doctor.class));
                        } else if ("patient".equals(userType)) {
                            persons.add(userDataSnapshot.getValue(Patient.class));
                        }
                    }
                    callback.onListLoaded(persons);
                } else {
                    callback.onFailure("No-one found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    // update status of Person in database
    public static void updateStatus(String UUID, String newStatus, UserCallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(UUID).child("userData").child("status");

        // Update the status field with the new value
        databaseReference.setValue(newStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Status updated successfully
                        Log.d("TAG", "Status updated successfully");
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Log.e("TAG", "Error updating status: " + e.getMessage());
                        callback.onFailure(e.getMessage());
                    }
                });

    }

    // Get all persons from database, then update each status from pending to accepted
    public static void acceptAllPending(UserCallback callback) {
        getPersonsFromDatabase(new UserCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onListLoaded(List persons) {
                for (Object p : persons) {
                    Person person = (Person)p;
                    if ("pending".equals(person.getStatus())){
                        updateStatus(person.getUUID(), "accepted", new UserCallback() {
                            @Override
                            public void onFailure(String error) {callback.onFailure(error);}
                            @Override
                            public void onSuccess() {}

                            @Override
                            public void onListLoaded(List persons) {

                            }
                        });
                    }
                } callback.onSuccess();
            }

            @Override
            public void onFailure(String error) {callback.onFailure(error);}

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
