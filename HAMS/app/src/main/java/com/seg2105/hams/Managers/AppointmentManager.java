package com.seg2105.hams.Managers;

import android.util.Log;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
                    callback.onFailure("No appointments.");
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

    public static void getAvailableAppointmentsFromDoctor(Doctor doctor, UserCallback callback) {
        HashMap<String, Shift> shifts = doctor.getShifts();
        ArrayList<ArrayList<String>> availableAppointments = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.child(doctor.getUUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If datareference is found
                try { //added try catch block for now since doctor isn't initialized with a list of appointments. catches NullPointerException
                    for (DataSnapshot shiftSnapshot : dataSnapshot.child("userData").child("shifts").getChildren()) {
                        availableAppointments.addAll(convertShiftToAvailableAppointments(doctor, shiftSnapshot.getValue(Shift.class)));
                    }
                } catch (Exception e) {

                }
                if (availableAppointments.isEmpty()) {
                    callback.onFailure("No available appointments.");
                } else {
                    callback.onListLoaded(availableAppointments);
                }

            }
            public void onCancelled (@NonNull DatabaseError databaseError){
                // Handle errors
                callback.onFailure(databaseError.getMessage());
            }

        });



    }

    private static ArrayList<ArrayList<String>> convertShiftToAvailableAppointments(Doctor doctor, Shift shift) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        // Get the current date and time
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date startDate = sdf.parse(shift.getStart());
            Date endDate = sdf.parse(shift.getEnd());

            // Disregard the shift in the past
            if (endDate.before(currentTime.getTime())) {
                return result;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            // Iterate through 30-minute blocks within the shift
            while (calendar.getTime().before(endDate)) {
                String blockStart = sdf.format(calendar.getTime());

                // Move calendar ahead by 30 minutes
                calendar.add(Calendar.MINUTE, 30);

                String blockEnd = sdf.format(calendar.getTime());

                // Create an inner list with start and end times
                ArrayList<String> appointmentTimes = new ArrayList<>();
                appointmentTimes.add(blockStart);
                appointmentTimes.add(blockEnd);
                appointmentTimes.add(shift.getShiftID());
                appointmentTimes.add(doctor.getUUID());

                // Add the inner list to the result list
                result.add(appointmentTimes);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static void getAvailableDoctorsFromDatabase(String specialty, UserCallback callback) {
        List<Doctor> availableDoctors = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.orderByChild("userType").equalTo("doctor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DataSnapshot specialtiesSnapshot = snapshot.child("userData").child("specialties");

                        // Check if the user has specialties and if the given specialty is present in the array
                        if (specialtiesSnapshot.exists() && specialtiesSnapshot.getChildrenCount() > 0) {
                            for (DataSnapshot specialtySnapshot : specialtiesSnapshot.getChildren()) {
                                String userSpecialty = specialtySnapshot.getValue(String.class);

                                if (userSpecialty != null && userSpecialty.equals(specialty)) {
                                    availableDoctors.add(snapshot.child("userData").getValue(Doctor.class));
                                    break;  // Break the loop once the specialty is found for this user
                                }
                            }
                        }
                    }
                    callback.onListLoaded(availableDoctors);
                } else {
                    callback.onFailure("No doctors found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
        FirebaseDatabase.getInstance().getReference("users").child(appointment.getDoctorUUID()).child("userData").child("appointments").child(newAppointmentRef.getKey()).setValue(appointment);
        FirebaseDatabase.getInstance().getReference("users").child(appointment.getPatientUUID()).child("userData").child("appointments").child(newAppointmentRef.getKey()).setValue(appointment);
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
