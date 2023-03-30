package com.dnd8th.util;

import com.dnd8th.error.exception.block.DateFormatInvalidException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DateParser {

    private static SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd");

    public Date getDate(int year, int month, int date) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Date(cal.getTimeInMillis());
    }

    public Date parseDate(String date) {
        try {
            return formatting.parse(date);
        } catch (ParseException e) {
            throw new DateFormatInvalidException();
        }
    }

    public String toString(Date date) {
        return formatting.format(date);
    }
}
