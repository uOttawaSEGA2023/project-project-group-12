package com.seg2105.hams.Managers;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String dateTime;
    private String doctorUUID;
    private String patientUUID;
    private String status;
    private String appointmentID;

    public Appointment(){}

    public Appointment(String dateTime, String doctorUUID, String patientUUID, String status, String appointmentID) {
        this.dateTime = dateTime;
        this.doctorUUID = doctorUUID;
        this.patientUUID = patientUUID;
        this.status = status;
        this.appointmentID = appointmentID;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDoctorUUID() {
        return doctorUUID;
    }

    public void setDoctorUUID(String doctorUUID) {
        this.doctorUUID = doctorUUID;
    }

    public String getPatientUUID() {
        return patientUUID;
    }

    public void setPatientUUID(String patientUUID) {
        this.patientUUID = patientUUID;
    }
}
