package org.taurusxi.taurusxicommon.task;

import org.taurusxi.taurusxicommon.network.listener.NetworkResponce;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wumin on 16/1/14.
 */
public class NetworkTask implements TXTask {
    @Override
    public int getIdentify() {
        return TaskIdentify.NETWORK_TASK_ID;
    }

    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

    public NetworkTask(String url) {
        this(url, Method.POST);
    }


    public NetworkTask(String url, int method) {
        this.httpUrl = url;
        this.tag = httpUrl;
        this.method = method;
        this.debugTag = "NoDebugTag";
        this.shouldCacheFlag = false;
        //addHeader("ApiVersion", HeaderUtils.getApiVersion());
    }

    private String httpUrl;

    private String tag;

    private int method;

    private String debugTag;

    private boolean shouldCacheFlag;

    public boolean isShouldCacheFlag() {
        return shouldCacheFlag;
    }

    public void setShouldCacheFlag(boolean shouldCacheFlag) {
        this.shouldCacheFlag = shouldCacheFlag;
    }

    public void setDebugTag(String debugTag) {
        this.debugTag = debugTag;
    }

    public String getDebugTag() {
        return debugTag;
    }

    private Map<String, String> params = new HashMap<>();
    private Map<String, String> header = new HashMap<>();

    public NetworkResponce getNetworkResponce() {
        return networkResponce;
    }

    public void setNetworkNewResponce(NetworkResponce networkResponce) {
        this.networkResponce = networkResponce;
    }

    private NetworkResponce networkResponce;


    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public void addParams(String key, String value) {
        if (value != null) {
            params.put(key, value);
        }
    }

    public void addHeader(String key, String value) {
        if (value != null) {
            header.put(key, value);
        }
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(debugTag);
        stringBuilder.append(" : ");
        stringBuilder.append("NetworkTask:[ ");
        stringBuilder.append("httpUrl:" + httpUrl);
        stringBuilder.append(" , ");
        stringBuilder.append("method:" + method);
        stringBuilder.append(" , ");
        stringBuilder.append("header:" + header.toString());
        stringBuilder.append(" , ");
        stringBuilder.append("params:" + params.toString());
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}
