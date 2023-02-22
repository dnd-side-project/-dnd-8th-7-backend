package com.dnd8th.util;

import com.dnd8th.error.exception.block.DateFormatInvalidException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DateParser {

    public Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new DateFormatInvalidException();
        }
    }
}
