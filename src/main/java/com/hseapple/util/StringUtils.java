package com.hseapple.util;

public class StringUtils {

    public static String formatRequest(String url, String path, Object... args) {
        String format = String.format(url + path, args);
        return format;
    }
}
