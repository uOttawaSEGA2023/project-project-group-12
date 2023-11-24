package com.seg2105.hams.Managers;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String startDateTime;
    private String endDateTime;
    private String doctorUUID;
    private String patientUUID;
    private String status;
    private String appointmentID;

    public Appointment(){}

    public Appointment(String startDateTime, String endDateTime, String doctorUUID, String patientUUID, String status, String appointmentID) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.doctorUUID = doctorUUID;
        this.patientUUID = patientUUID;
        this.status = status;
        this.appointmentID = appointmentID;
    }

    public Appointment(String startDateTime, String endDateTime, String doctorUUID, String patientUUID, String status) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.doctorUUID = doctorUUID;
        this.patientUUID = patientUUID;
        this.status = status;
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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setSEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
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
