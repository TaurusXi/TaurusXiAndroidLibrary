package org.taurusxi.taurusxicommon.network.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created on 15/4/11.
 *
 * @author xicheng
 * @email 505591443@qq.com
 * @github https://github.com/TaurusXi
 */
public class StringBaseRequest extends StringRequest {
    private String cacheKey;

    public StringBaseRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringBaseRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    @Override
    public String getCacheKey() {
        return cacheKey == null ? "" : cacheKey;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
//        return Response.success(parsed, parseCacheHeaders(response));
        return Response.success(parsed, null);
    }


    private String parseCharset(Map<String, String> headers) {
//        String contentType = headers.get(HTTP.CONTENT_TYPE);
//        if (contentType != null) {
//            String[] params = contentType.split(";");
//            for (int i = 1; i < params.length; i++) {
//                String[] pair = params[i].trim().split("=");
//                if (pair.length == 2) {
//                    if (pair[0].equals("charset")) {
//                        return pair[1];
//                    }
//                }
//            }
//        }

        return "GB2312";
    }


//    public static Cache.Entry parseCacheHeaders(NetworkResponse response) {
//        Map<String, String> headers = response.headers;
//        String apiCacheHeader = headers.get("Api-Cache-Age");
//        if (apiCacheHeader == null || apiCacheHeader.equals("")) {
//            return null;
//        }
//
//        long now = System.currentTimeMillis();
//        boolean hasApiCacheHeader = false;
//        long apiCacheAge = 0l;
//        long softExpire = 0l;
//        try {
//            apiCacheAge = Long.parseLong(apiCacheHeader);
//            hasApiCacheHeader = true;
//        } catch (Exception e) {
//            apiCacheAge = 0;
//        }
//        if (hasApiCacheHeader) {
//            softExpire = now + apiCacheAge * 1000;
//        } else {
//            softExpire = now;
//        }
//        String serverEtag = headers.get("ETag");
//        String headerValue = headers.get("Date");
//        long serverDate = 0l;
//        if (headerValue != null) {
//            serverDate = parseDateAsEpoch(headerValue);
//        }
//        Cache.Entry entry = new Cache.Entry();
//        entry.data = response.data;
//        entry.etag = serverEtag;
//        entry.softTtl = softExpire;
//        entry.ttl = entry.softTtl;
//        entry.serverDate = serverDate;
//        entry.responseHeaders = headers;
//        return entry;
//
//    }

//    public static long parseDateAsEpoch(String dateStr) {
//        try {
//            // Parse date in RFC1123 format if this header contains one
//            return DateUtils.parseDate(dateStr).getTime();
//        } catch (DateParseException e) {
//            // Date in invalid format, fallback to 0
//            return 0;
//        }
//    }

}
