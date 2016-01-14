package org.taurusxi.taurusxicommon.utils;

/**
 * Created by wumin on 16/1/14.
 */
public class StringUtils {

    public static String formatNull(String source) {
        return source == null ? "" : source;
    }

    public static String formatNull(String source, String defaultStr) {
        return source == null ? defaultStr : source;
    }


    public static String httpsToHttp(String source) {
        return source.replace("https", "http");
    }

    public static String formatLength(String title, int length) {
        if (title.length() > length) {
            return title.subSequence(0, length) + "...";
        }
        return title;
    }
}
