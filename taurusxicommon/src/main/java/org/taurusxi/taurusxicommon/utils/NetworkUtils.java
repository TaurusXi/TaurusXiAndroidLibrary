package org.taurusxi.taurusxicommon.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.taurusxi.taurusxicommon.manager.AppManager;


/**
 * Created by xicheng on 15/7/28.
 */
public class NetworkUtils {

    /**
     * 没有网络
     */
    public static final int NETWORKTYPE_INVALID = 0;
    public static final String NETWORKTYPE_INVALID_STR = "noconnect";
    /**
     * wap网络
     */
    public static final int NETWORKTYPE_WAP = 1;
    public static final String NETWORKTYPE_WAP_STR = "wap";
    /**
     * 2G网络
     */
    public static final int NETWORKTYPE_2G = 2;
    public static final String NETWORKTYPE_2G_STR = "2g";
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    public static final int NETWORKTYPE_3G = 3;
    public static final String NETWORKTYPE_3G_STR = "3g";
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 4;
    public static final String NETWORKTYPE_WIFI_STR = "wifi";

    private static final long mTmpTime = 0;

    private static final int INTER_TIME = 30 * 1000;

    private static int sNetworkType = -1;

    /**
     * 检查网络是否可用
     */
    public static boolean checkNetState(Context context) {
        if (context == null) {
            return false;
        }
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }


    public static int getNetworkType() {
        long currentTime = System.currentTimeMillis();
        if (sNetworkType == -1 || currentTime - mTmpTime > INTER_TIME) {
            sNetworkType = getNetWorkType(AppManager.getInstance().getApplication().getApplicationContext());
        }
        return sNetworkType;
    }


    public static String getNetworkTypeStr(Context context) {

        String result = "";
        switch (getNetWorkType(context)) {
            case NETWORKTYPE_WIFI:
                result = NETWORKTYPE_WIFI_STR;
                break;
            case NETWORKTYPE_3G:
                result = NETWORKTYPE_3G_STR;
                break;
            case NETWORKTYPE_2G:
                result = NETWORKTYPE_2G_STR;
                break;
            case NETWORKTYPE_WAP:
                result = NETWORKTYPE_WAP_STR;
                break;
            default:
                result = NETWORKTYPE_INVALID_STR;
                break;
        }

        return result;
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G},          *{@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}* <p>{@link #NETWORKTYPE_WIFI}
     */
    public static int getNetWorkType(Context context) {
        int mNetWorkType = 0;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                MLog.e("NetworkUtils", "网络类型：" + telephonyManager.getNetworkType());
                return false;
        }
    }
}
