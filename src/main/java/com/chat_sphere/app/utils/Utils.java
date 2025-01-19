package com.chat_sphere.app.utils;

import com.chat_sphere.app.constant.Constant;
import com.chat_sphere.app.dtos.Reply;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static String getUserPhoneNumber(Reply reply) {
        return reply.getAttribute(Constant.LOGGED_IN_USER_PHONE_NUMBER) == null ? "" : reply.getAttribute(Constant.LOGGED_IN_USER_PHONE_NUMBER).toString();
    }

    public static String toISOString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure the time is in UTC
        return sdf.format(date);
    }

    public static Date fromISOString(String isoString){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(isoString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
