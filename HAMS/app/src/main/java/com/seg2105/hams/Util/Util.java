package com.seg2105.hams.Util;

import com.seg2105.hams.Managers.Appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class Util {
    /**
     * Returns today's date in internation standard format using LocalDate and DateTimeFormatter.
     * @return the date in YYYY-MM-DD format
     */
    public static String getDateYYYYMMDD () {
        LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return currentDate.format(formatter);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean appointmentIsPast(Appointment appointment) {
        String dateString = appointment.getEndDateTime();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(dateString);
            // Get the current date and time
            Date currentDate = new Date();

            // Compare the given date with the current date
            if (date.before(currentDate)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String fieldsToAddress(Map<String, String> addressValues){
        if (isNullOrEmpty(addressValues.get("street"))||isNullOrEmpty(addressValues.get("city"))||isNullOrEmpty(addressValues.get("province"))||isNullOrEmpty(addressValues.get("country"))||isNullOrEmpty(addressValues.get("postalCode"))) {return null;}
        String street = addressValues.get("street");
        String city = addressValues.get("city");
        String province = addressValues.get("province");
        String country = addressValues.get("country");
        String postalCode = addressValues.get("postalCode");
        return street + ", " + city +  ", " + province + ", " + country + ", " + postalCode;
    }
}
