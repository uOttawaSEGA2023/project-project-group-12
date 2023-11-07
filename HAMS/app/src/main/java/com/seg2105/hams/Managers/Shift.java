package com.seg2105.hams.Managers;

import com.seg2105.hams.Users.Doctor;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Shift implements Serializable {
    private String start;
    private String end;
    private String shiftID;


    public Shift(){}

    public Shift(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftID() {
        return shiftID;
    }
}
