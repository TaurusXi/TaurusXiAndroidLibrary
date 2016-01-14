package org.taurusxi.taurusxicommon.imageLoader.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by wumin on 16/1/14.
 */
public abstract class SimpleImageLoaderListener implements ImageLoaderListener {

    public void loadStart(){}

    public void loadFail(String url, ImageView imageView){}

//    public void loadComplete(String url, ImageView imageView, Bitmap bitmap){}
}
