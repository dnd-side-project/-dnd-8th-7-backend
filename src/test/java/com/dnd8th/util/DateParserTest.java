package com.dnd8th.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateParserTest {

    private DateParser dateParser = new DateParser();

    @Test
    void getDayOfWeek() {
        //given
        Date date = dateParser.getDate(2023, 3, 1);

        //when
        String dayOfWeek = dateParser.getDayOfWeek(date);

        //then
        assertThat(dayOfWeek).isEqualTo("수");
    }

    @Test
    void getDayOfWeekInt() {
        //given
        Date date = dateParser.getDate(2023, 3, 1);

        //when
        Integer dayOfWeekInt = dateParser.getDayOfWeekInt(date);

        //then
        assertThat(dayOfWeekInt).isEqualTo(4);
    }

    @Test
    void getDate() {
        //given
        int year = 2023;
        int month = 3;
        int date = 1;

        //when
        Date getDate = dateParser.getDate(year, month, date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate);

        //then
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(2023);
        assertThat(cal.get(Calendar.MONTH) + 1).isEqualTo(3);
        assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
    }

    @Test
    void parseDate() {
        //given
        String date = "2023-03-01";

        //when
        Date parseDate = dateParser.parseDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate);

        //then
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(2023);
        assertThat(cal.get(Calendar.MONTH) + 1).isEqualTo(3);
        assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
    }

    @Test
    void testToString() {
        //given
        Date date = dateParser.getDate(2023, 3, 1);

        //when
        String toString = dateParser.toString(date);

        //then
        assertThat(toString).isEqualTo("2023-03-01");
    }
}
