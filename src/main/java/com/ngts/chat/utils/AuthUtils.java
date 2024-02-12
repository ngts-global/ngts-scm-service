package com.ngts.chat.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;

public class AuthUtils {

    public static String className = AuthUtils.class.getName();

    public void getRequestCookies(ServletServerHttpRequest httpServletRequest){
        String rawCookie = httpServletRequest.getHeaders().get(HttpHeaders.SET_COOKIE) != null ?  httpServletRequest.getHeaders().get(HttpHeaders.SET_COOKIE) .toString(): "";
        System.out.println(className + " Cookie in request headers " + rawCookie);
        String[] rawCookieParams = rawCookie.split(";");

        for(String rawCookieNameAndValue :rawCookieParams)
        {
            String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
        }
    }
}
