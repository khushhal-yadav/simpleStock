package com.stock.exception;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by khush on 06/11/2016.
 */
public class Test {

    public static void main(String args[]) {

        char [] b7={'0','a', 't', 'l', 's', 'i', 'n'};
        int t = 123;

        int i=t;
        StringBuffer sb = new StringBuffer();
        while (i>0){
            int m=i%7;
            sb.append(b7[m]);
            i=i/7;
        }

        System.out.println(sb.reverse());



    }
}
