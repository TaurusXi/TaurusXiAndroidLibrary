package org.taurusxi.taurusxicommon.utils;

import android.util.Log;

import org.taurusxi.taurusxicommon.TXConfig;
import org.taurusxi.taurusxicommon.utils.logger.LogLevel;
import org.taurusxi.taurusxicommon.utils.logger.Logger;

/**
 * Created by wumin on 16/1/14.
 */
public class MLog {

    public final static boolean FLAG = TXConfig.DEBUG;

    public static void init() {
        //初始化logger操作
        Logger.init();
        // 在debug下，才显示log
        Logger.init().logLevel(MLog.FLAG ? LogLevel.FULL : LogLevel.NONE);
    }

    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }

}
