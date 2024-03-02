package com.ngts.chat.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.OffsetDateTime;

public class CommonUtils {

    public static int generateMsgId(){
        Long timestamp = System.currentTimeMillis();
        return timestamp.intValue();
    }

    public static String generateUID(){

        int length = 64;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        //System.out.println(generatedString);
        return generatedString;
    }

    public static OffsetDateTime currentDateTime(){
        OffsetDateTime offsetDateTime   = OffsetDateTime.now();
        return offsetDateTime;
    }

    public static OffsetDateTime getTokenValidTime(){
        OffsetDateTime offsetDateTime   = OffsetDateTime.now();
        offsetDateTime.plusDays(7);
        return offsetDateTime;
    }
    public static void main(String[] args) {
        for (int i=0; i < 10 ; i++)
        System.out.println(CommonUtils.generateUID());
    }
}
