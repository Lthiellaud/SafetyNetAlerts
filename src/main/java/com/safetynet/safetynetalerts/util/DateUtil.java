package com.safetynet.safetynetalerts.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private Date now;

    public DateUtil(){
        this.now = new Date();
    }

    public Integer age(Date birthdate) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        if (birthdate !=null) {
            int d1 = Integer.parseInt(format.format(birthdate));
            int d2 = Integer.parseInt(format.format(now));
            return (d2 - d1) / 10000;
        } else {
            return -1;
        }
    }

}
