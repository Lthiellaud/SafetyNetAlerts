package com.safetynet.safetynetalerts.util;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {

    private DateUtil dateUtil = new DateUtil();
    private Date birthdate;
    private Calendar calendar = Calendar.getInstance();


    @Test
    public void ageCalculateForTodayLess2YearsPlus1Day_ShouldBe1() {
        //GIVEN
        calendar.setTime(dateUtil.getNow());
        calendar.add(Calendar.YEAR, -2);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        birthdate = calendar.getTime();

        //WHEN
        System.out.println(birthdate);
        int age = dateUtil.age(birthdate);

        //THEN
        assertThat(age).isEqualTo(1);


    }

    @Test
    public void ageCalculateForTodayLess2Years_ShouldBe2() {
        //GIVEN
        calendar.setTime(dateUtil.getNow());
        calendar.add(Calendar.YEAR, -2);
        birthdate = calendar.getTime();

        //WHEN
        System.out.println(birthdate);
        int age = dateUtil.age(birthdate);

        //THEN
        assertThat(age).isEqualTo(2);

    }

}
