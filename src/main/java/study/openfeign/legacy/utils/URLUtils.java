package study.openfeign.legacy.utils;

public class URLUtils {

    private URLUtils() {
    }

    public static String createURL(AuthProperties authProperties) {
        System.out.println(authProperties.mapping());
        return authProperties.mapping();
    }
}
