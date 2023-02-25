package com.dnd8th.util;

import com.dnd8th.error.exception.block.DateFormatInvalidException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateParser {
    private static SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd");

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
