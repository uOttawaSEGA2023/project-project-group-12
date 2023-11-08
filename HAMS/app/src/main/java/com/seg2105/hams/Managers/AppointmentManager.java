package com.seg2105.hams.Managers;

import static com.seg2105.hams.Managers.UserManager.getCurrentUser;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.UserCallback;

import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    public static void getAppointmentsFromDatabase(Person person, UserCallback callback) {
        List<Shift> shifts = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(person.getUUID()).child("userData").child("shifts");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If datareference is found
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Shift shift = snapshot.getValue(Shift.class);
                        if (shift!=null) shifts.add(snapshot.getValue(Shift.class));
                    }
                    if (shifts.isEmpty()) {
                        callback.onFailure("No shifts.");
                    } else {
                        callback.onListLoaded(shifts);
                    }
                } else {
                    callback.onFailure("No shifts.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public static void putAppointmentInDatabase(Shift shift, UserCallback callback) {
        DatabaseReference shiftsReference = FirebaseDatabase.getInstance().getReference("users").child(getCurrentUser().getUUID()).child("userData").child("shifts");

        // Generate a unique ID for the new shift using push()
        DatabaseReference newShiftRef = shiftsReference.push();

        shift.setShiftID(newShiftRef.getKey());

        // Set the value of the new shift object to the generated unique ID
        newShiftRef.setValue(shift);

        UserManager.reloadUser(new UserCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onListLoaded(List persons) {}

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
    public static void removeAppointmentFromDataBase(Appointment appointment, UserCallback callback) {
        DatabaseReference shiftReference = FirebaseDatabase.getInstance().getReference("users").child(getCurrentUser().getUUID()).child("userData").child("shifts");

        shiftReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UserManager.reloadUser(new UserCallback() {
                            @Override
                            public void onSuccess() {callback.onSuccess();}

                            @Override
                            public void onListLoaded(List persons) {}

                            @Override
                            public void onFailure(String error) {
                                callback.onFailure(error);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.toString());
                    }
                });


    }
}
