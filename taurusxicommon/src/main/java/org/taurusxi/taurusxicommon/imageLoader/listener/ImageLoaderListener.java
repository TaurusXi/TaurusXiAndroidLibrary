package org.taurusxi.taurusxicommon.imageLoader.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by wumin on 16/1/14.
 */
public interface ImageLoaderListener {

    void loadStart();

    void loadFail(String url,ImageView imageView);

    void loadComplete(String url, ImageView imageView, Bitmap bitmap);
}
