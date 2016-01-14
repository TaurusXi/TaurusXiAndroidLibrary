package org.taurusxi.taurusxicommon.utils;

import android.os.Build;

/**
 * Created by root on 15-3-31.
 */
public final class VersionUtils {
    public static final boolean HAS_FROYO;
    public static final boolean HAS_GINGERBREAD;
    public static final boolean HAS_HONEYCOMB;
    public static final boolean HAS_KITKAT;
    public static final boolean HAS_LOLLIPOP;
    public static final boolean HAS_JELLY_BEAN;

    static {
        int version = Build.VERSION.SDK_INT;
        HAS_FROYO = version >= Build.VERSION_CODES.FROYO;
        HAS_GINGERBREAD = version >= Build.VERSION_CODES.GINGERBREAD;
        HAS_HONEYCOMB = version >= Build.VERSION_CODES.HONEYCOMB;
        HAS_KITKAT = version >= Build.VERSION_CODES.KITKAT;
        HAS_LOLLIPOP = version >= Build.VERSION_CODES.LOLLIPOP;
        HAS_JELLY_BEAN = version >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 2.2
     */
    public static boolean hasFroyo() {
        return HAS_FROYO;
    }

    /**
     * 2.3
     */
    public static boolean hasGingerbread() {
        return HAS_GINGERBREAD;
    }

    /**
     * 4.0
     */
    public static boolean hasHoneycomb() {
        return HAS_HONEYCOMB;
    }

    public static boolean hasJellyBean() {
        return HAS_JELLY_BEAN;
    }

    /**
     * 4.4.2
     */
    public static boolean hasKitkat() {
        return HAS_KITKAT;
    }

    /**
     * 5.0
     */
    public static boolean hasLollipop() {
        return HAS_LOLLIPOP;
    }

}
