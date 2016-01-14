package org.taurusxi.taurusxicommon.utils;

import android.util.Log;

import org.taurusxi.taurusxicommon.TXConfig;

/**
 * Created by wumin on 16/1/14.
 */
public class MLog {

    public final static boolean FLAG = TXConfig.DEBUG;

    public static void e(String tag, String msg) {
        if (FLAG)
            Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (FLAG)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (FLAG)
            Log.v(tag, msg);
    }
}
