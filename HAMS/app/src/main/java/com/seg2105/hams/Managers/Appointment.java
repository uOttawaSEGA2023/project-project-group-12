package com.seg2105.hams.Managers;

import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Patient;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String date;
    private Doctor doctor;
    private Patient patient;
    private String status;
    private int appointmentID;

    public Appointment(){}

    public Appointment(String date, Doctor doctor, Patient patient, String status, int appointmentID) {
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.status = status;
        this.appointmentID = appointmentID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }
}
