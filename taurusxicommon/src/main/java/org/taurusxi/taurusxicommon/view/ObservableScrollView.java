package org.taurusxi.taurusxicommon.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import org.taurusxi.taurusxicommon.listener.ObserverLinstener;

import java.lang.ref.WeakReference;


/**
 * Created on 15/3/2.
 *
 * @author xicheng
 * @email 505591443@qq.com
 * @github https://github.com/TaurusXi
 */
public class ObservableScrollView extends ScrollView {


//    private final static int START = 0;
//    private final static int END = 1;
    private final static int SCROLLING = 2;
    private final static int UNKOWN = -19232;
    private static final Object obj = new Object();
    private boolean isReadyedForStart = true;
    private int oldScrollY = UNKOWN;
    private ObserverLinstener scrollViewListener = null;
    private Interhandler handler;

    public ObserverLinstener getScrollViewListener() {
        return scrollViewListener;
    }

    public void setScrollViewListener(ObserverLinstener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    public ObservableScrollView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        handler = new Interhandler(this);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScroll(t, oldt);
            if (t<=0){
                scrollViewListener.scrollTop();
            }
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int eventAction = event.getAction();
        switch (eventAction) {
            case MotionEvent.ACTION_UP:
                Message msg = new Message();
                msg.what = SCROLLING;
                msg.arg1 = getScrollY();
                handler.sendMessageDelayed(msg, 10);
                isReadyedForStart = true;
                oldScrollY = UNKOWN;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isReadyedForStart) {
                    synchronized (obj) {
                        if (isReadyedForStart) {
                            if (oldScrollY == UNKOWN) {
                                oldScrollY = getScrollY();
                            }
                            int newY = getScrollY();
                            if (newY != oldScrollY) {
                                isReadyedForStart = false;
                                scrollViewListener.onStartScroll();
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                isReadyedForStart = true;
                oldScrollY = getScrollY();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }



    private static class Interhandler extends Handler {

        private WeakReference<ObservableScrollView> viewWeakReference;

        public Interhandler(ObservableScrollView observableScrollView) {
            this.viewWeakReference = new WeakReference<ObservableScrollView>(observableScrollView);
        }

        @Override
        public void handleMessage(Message msg) {

            ObservableScrollView observableScrollView = viewWeakReference.get();
            if (observableScrollView == null) {
                return;
            }

            switch (msg.what) {
                case SCROLLING:
                    int y = observableScrollView.getScrollY();
                    if (msg.arg1 == y) {
                        if (observableScrollView.scrollViewListener != null) {
                            observableScrollView.scrollViewListener.onEndScroll();
                            if(y + observableScrollView.getHeight() >=  observableScrollView.computeVerticalScrollRange()){
                                //ScrollView滑动到底部了
                                observableScrollView.scrollViewListener.scrollBottom();
                            }
                        }
                    } else {
                        Message newMsg = new Message();
                        newMsg.what = SCROLLING;
                        newMsg.arg1 = y;
                        observableScrollView.handler.sendMessageDelayed(newMsg, 10);
                    }
                    break;
            }

        }
    }
}
