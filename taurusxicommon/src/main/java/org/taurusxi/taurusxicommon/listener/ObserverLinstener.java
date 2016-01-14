package org.taurusxi.taurusxicommon.listener;

import android.view.View;

/**
 * Created by xicheng on 15/8/4.
 */
public interface ObserverLinstener {

    /**
     * 回调方法， 返回MyScrollView滑动的Y方向距离
     *
     * @param scrollY 、
     */
    void onScroll(int scrollY, int oldY);

    void onStartScroll();

    void onEndScroll();

    void onScrollChanged(View view, int x, int y, int oldx, int oldy);

    void scrollBottom();

    void scrollTop();
}
