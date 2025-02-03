package com.stock_tracker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormat {


    /**
     * Converts a Unix timestamp string to a formatted local date-time string.
     *
     * @param datetime Unix timestamp as a string.
     * @return Formatted date-time string in the pattern "yyyy-MM-dd HH:mm:ss".
     */

    public String localDateTime(String datetime) {
        LocalDateTime formattedDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(Long.parseLong(datetime)),
            ZoneId.systemDefault()
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formattedDateTime.format(formatter);
    }
    
}
