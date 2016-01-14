package org.taurusxi.taurusxicommon.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.taurusxi.taurusxicommon.TXConfig;
import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.exception.TXNetworkException;
import org.taurusxi.taurusxicommon.exception.TXNetworkParseException;
import org.taurusxi.taurusxicommon.exception.TXNoConnectionException;
import org.taurusxi.taurusxicommon.exception.TXServerException;
import org.taurusxi.taurusxicommon.exception.TXTimeoutException;
import org.taurusxi.taurusxicommon.exception.TXUnKnownException;
import org.taurusxi.taurusxicommon.manager.AppManager;
import org.taurusxi.taurusxicommon.network.listener.NetworkResponce;
import org.taurusxi.taurusxicommon.network.request.StringBaseRequest;
import org.taurusxi.taurusxicommon.task.NetworkTask;
import org.taurusxi.taurusxicommon.utils.MD5;
import org.taurusxi.taurusxicommon.utils.MLog;
import org.taurusxi.taurusxicommon.utils.NetworkUtils;

import java.util.Map;

/**
 * Created by wumin on 16/1/14.
 */
public class NetworkHelper {

    private static final String TAG = NetworkHelper.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static volatile NetworkHelper networkHelper;

    public static NetworkHelper getInstance() {
        if (networkHelper == null) {
            synchronized (NetworkHelper.class) {
                if (networkHelper == null) {
                    networkHelper = new NetworkHelper(AppManager.getInstance().getApplication().getApplicationContext());
                }
            }
        }
        return networkHelper;
    }
    private NetworkHelper(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void addTask(final NetworkTask networkTask) {
        final NetworkResponce networkResponce = networkTask.getNetworkResponce();
        boolean checkNetState = NetworkUtils.checkNetState(AppManager.getInstance().getApplication().getApplicationContext());
        if (!checkNetState) {
            if (networkResponce != null && networkResponce.networkFinish(networkTask)) {
                networkResponce.onError(networkTask, new TXNoConnectionException(), null);
            }
            return;
        }

        final long oldTime = System.currentTimeMillis();
        if (networkTask.getMethod() == NetworkTask.Method.GET) {
            setNetworkUrl(networkTask);
        }
        StringBaseRequest stringRequest = new StringBaseRequest(networkTask.getMethod(), networkTask.getHttpUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO 做成 钩子，实现性能优化
                if (networkResponce != null && networkResponce.networkFinish(networkTask)) {
                    networkResponce.onSuccessResponse(networkTask, response);
                }
                if (TXConfig.DEBUG) {
                    long persistTime = System.currentTimeMillis() - oldTime;
                    String responce = "接口调用时间:" + persistTime + " ms ," + networkTask.toString() + "  , reponse:" + response;
                    if (persistTime > 1500) {
                        MLog.e(TAG, responce);
                    } else {
                        MLog.d(TAG, responce);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                MLog.e(TAG, error.toString());
                if (networkResponce != null) {
                    if (networkResponce.networkFinish(networkTask)) {
                        TXBaseException TXBaseException = null;
                        if (error == null) {
                            TXBaseException = new TXUnKnownException();
                        } else {
                            if (error instanceof NoConnectionError) {
                                TXBaseException = new TXNoConnectionException(error);
                            } else if (error instanceof NetworkError) {
                                TXBaseException = new TXNetworkException(error);
                            } else if (error instanceof ParseError) {
                                TXBaseException = new TXNetworkParseException(error);
                            } else if (error instanceof ServerError) {
                                TXBaseException = new TXServerException(error);
                            } else if (error instanceof TimeoutError) {
                                TXBaseException = new TXTimeoutException(error);
                            } else {
                                TXBaseException = new TXUnKnownException();
                            }
                        }
                        networkResponce.onError(networkTask, TXBaseException, null);
                    }
                }
                if (TXConfig.DEBUG) {
                    long persistTime = System.currentTimeMillis() - oldTime;
                    String responce = "接口调用时间:" + persistTime + " ms ," + networkTask.toString() + " , error:" + error.getLocalizedMessage();
                    if (persistTime > 1500) {
                        MLog.e(TAG, responce);
                    } else {
                        MLog.d(TAG, responce);
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (networkTask.getMethod() != NetworkTask.Method.GET) {
                    return networkTask.getParams();
                } else {
                    return super.getParams();
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return networkTask.getHeader();
            }
        };

        stringRequest.setTag(networkTask.getTag());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
        stringRequest.setCacheKey(makeCacheKey(networkTask.getHttpUrl(), networkTask.getParams()));
        stringRequest.setShouldCache(networkTask.isShouldCacheFlag());
        mRequestQueue.add(stringRequest);

    }

    private String makeCacheKey(String httpUrl, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.append(entry.getKey());
            urlBuilder.append("=");
            urlBuilder.append(entry.getValue());
            urlBuilder.append("&");
        }
        return MD5.getMessageDigest(httpUrl + urlBuilder.toString());
    }

    private void setNetworkUrl(NetworkTask networkTask) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(networkTask.getHttpUrl());
        urlBuilder.append("?");
        int mapSize = networkTask.getParams().size();
        int position = 0;
        for (Map.Entry<String, String> entry : networkTask.getParams().entrySet()) {
            urlBuilder.append(entry.getKey());
            urlBuilder.append("=");
            urlBuilder.append(entry.getValue());
            if (position != mapSize - 1) {
                urlBuilder.append("&");
            }
            position++;
        }
        networkTask.setHttpUrl(urlBuilder.toString());
    }
}
