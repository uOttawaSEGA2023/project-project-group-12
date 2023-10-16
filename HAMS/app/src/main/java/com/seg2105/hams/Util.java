package com.seg2105.hams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String getDateYYYYMMDD () {
        LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return currentDate.format(formatter);
    }
}
