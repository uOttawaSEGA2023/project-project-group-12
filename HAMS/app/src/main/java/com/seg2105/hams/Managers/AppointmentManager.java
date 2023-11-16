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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentManager {
    public static void getAppointmentsFromDatabase(Person person, UserCallback callback) {
        List<String> appointmentIDs = person.getAppointments();
        List<Appointment> appointments = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If datareference is found
                try { //added try catch block for now since doctor isn't initialized with a list of appointments. catches NullPointerException
                    for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                        if (appointmentIDs.contains(appointmentSnapshot.child("appointmentID").getValue(String.class))) {
                            Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                            appointments.add(appointment);
                        }
                    }
                } catch (Exception e) {

                }
                if (appointments.isEmpty()) {
                    callback.onFailure("No shifts.");
                } else {
                    callback.onListLoaded(appointments);
                }

            }
            public void onCancelled (@NonNull DatabaseError databaseError){
                // Handle errors
                callback.onFailure(databaseError.getMessage());
            }

        });



    }

    public static void putAppointmentInDatabase(Appointment appointment, UserCallback callback) {
        DatabaseReference appointmentsReference = FirebaseDatabase.getInstance().getReference("appointments");
        DatabaseReference doctorReference = FirebaseDatabase.getInstance().getReference("users");


        //check if doctor associated with appointment autoaccepts appointments
        if ("true".equals(doctorReference.child(appointment.getDoctorUUID()).child("userData").child("automaticallyApprove"))){
            appointment.setStatus("accepted");
        }

        // Generate a unique ID for the new shift using push()
        DatabaseReference newAppointmentRef = appointmentsReference.push();

        appointment.setAppointmentID(newAppointmentRef.getKey());

        // Set the value of the new shift object to the generated unique ID
        newAppointmentRef.setValue(appointment);


        callback.onSuccess();
    }
    public static void removeAppointmentFromDataBase(Appointment appointment, UserCallback callback) {
        DatabaseReference appointmentReference = FirebaseDatabase.getInstance().getReference("appointments").child(appointment.getAppointmentID());

        appointmentReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
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
