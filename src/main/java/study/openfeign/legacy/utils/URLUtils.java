package study.openfeign.legacy.utils;

import study.openfeign.legacy.properties.AuthProperties;

public class URLUtils {

    private URLUtils() {
    }

    public static String createURL(AuthProperties authProperties) {
        return authProperties.mapping();
    }
}
