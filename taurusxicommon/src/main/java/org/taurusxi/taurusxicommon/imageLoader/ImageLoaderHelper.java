package org.taurusxi.taurusxicommon.imageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.taurusxi.taurusxicommon.imageLoader.listener.ImageLoaderListener;
import org.taurusxi.taurusxicommon.manager.AppManager;

/**
 * Created by wumin on 16/1/14.
 */
public class ImageLoaderHelper {

    private static final String TAG = ImageLoaderHelper.class.getSimpleName();
    private static volatile ImageLoaderHelper instance;
    Picasso picasso;

    public static ImageLoaderHelper getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderHelper.class) {
                if (instance == null) {
                    instance = new ImageLoaderHelper(AppManager.getInstance().getApplication());
                }
            }
        }
        return instance;
    }

    private ImageLoaderHelper(Context context) {
        picasso = Picasso.with(context);
    }


    public void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, 0, 0, null);
    }

    public void loadImage(ImageView imageView, String url, ImageLoaderListener listener) {
        loadImage(imageView, url, 0, 0, listener);
    }

    public void loadImage(final ImageView imageView, final String url, int width, int height, final ImageLoaderListener listener) {
        if (TextUtils.isEmpty(url)){
            return;
        }
        RequestCreator requestCreator = picasso.load(url).placeholder(android.R.color.darker_gray).error(android.R.color.holo_red_dark);
        requestCreator.tag(url);
        if (width > 0 && height > 0) {
            requestCreator.resize(width, height);
        }
        requestCreator.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (listener != null) {
                    listener.loadComplete(url, imageView, null);
                }
            }

            @Override
            public void onError() {
                if (listener != null) {
                    listener.loadFail(url, imageView);
                }
            }
        });

        if (listener != null) {
            listener.loadStart();
        }
    }


    public void cancleImage(ImageView imageView) {
        picasso.cancelRequest(imageView);
    }

    public void cancleImageTag(Object tag) {
        picasso.cancelTag(tag);
    }
}
