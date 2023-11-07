package com.seg2105.hams.Managers;

import com.seg2105.hams.Users.Doctor;

public class Rating {
    private int rating;
    private Doctor doctor;

    public Rating(){}

    public Rating(int rating, Doctor doctor) {
        this.rating= rating;
        this.doctor = doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
