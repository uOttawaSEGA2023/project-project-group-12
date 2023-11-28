package com.seg2105.hams.Managers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2105.hams.Util.UserCallback;

public class RatingManager {
    public static void createNewRating(int rating, Appointment appointment, UserCallback callback) {
        DatabaseReference appointmentsReference = FirebaseDatabase.getInstance().getReference("appointments");

        // Update the "rating" field for the specified appointment
        appointmentsReference.child(appointment.getAppointmentID()).child("rating").setValue(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });


    }

    public static void getAppointmentRating(Appointment appointment, UserCallback callback) {
        DatabaseReference appointmentsReference = FirebaseDatabase.getInstance().getReference("appointments");

        appointmentsReference.child(appointment.getAppointmentID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChild("rating")) {
                    int rating = dataSnapshot.child("rating").getValue(Integer.class);
                    callback.onSuccess(rating);
                } else {
                    // Handle the case where no rating is found
                    callback.onFailure("No rating.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }
}
