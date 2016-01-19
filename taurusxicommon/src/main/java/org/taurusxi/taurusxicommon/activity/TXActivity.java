package org.taurusxi.taurusxicommon.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.taurusxi.taurusxicommon.annotation.Keyboard;
import org.taurusxi.taurusxicommon.manager.AppManager;
import org.taurusxi.taurusxicommon.utils.MLog;
import org.taurusxi.taurusxicommon.utils.StringUtils;
import org.taurusxi.taurusxicommon.utils.logger.LogLevel;
import org.taurusxi.taurusxicommon.utils.logger.Logger;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by wumin on 16/1/14.
 */
public abstract class TXActivity extends TXSwipeActivity {

    protected String TAG = "UNDEFINE_TAG";
    protected TXActivity context;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase(savedInstanceState);
        setContentView(getContentView());
        initView(savedInstanceState);
        initEvents(savedInstanceState);
        initData(savedInstanceState);
        setKeyBoardListener();
        //初始化Logger
        MLog.init();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected abstract int getContentView();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initEvents(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    private void initBase(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        context = this;
        AppManager.getInstance().addActivity(this);
    }


    public void showShortToast(String msg) {
        if (context != null) {
            Toast.makeText(context, StringUtils.formatNull(msg), Toast.LENGTH_SHORT).show();
        }
    }

    public void showLongToast(String msg) {
        if (context != null) {
            Toast.makeText(context, StringUtils.formatNull(msg), Toast.LENGTH_LONG).show();
        }
    }

    public void showLocationToast(String msg) {
        if (context != null) {
            Toast toast = Toast.makeText(context, StringUtils.formatNull(msg), Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.TOP, 0, DisplayUtil.dip2px(56) + DisplayUtil.statusBarHeight);
            toast.show();
        }
    }


    /**
     * 设置键盘监听
     */
    private void setKeyBoardListener() {
        long currentTimeMillis = System.currentTimeMillis();
        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        boolean flag = false;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Keyboard.class)) {
                flag = true;
                break;
            }

        }
        Log.e("TAG", "flag:" + flag + "_time:" + (System.currentTimeMillis() - currentTimeMillis));
        if (!flag) {
            return;
        }
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        if (swipeBackLayout != null) {
            swipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
                @Override
                public void onScrollStateChange(int state, float scrollPercent) {

                }

                @Override
                public void onEdgeTouch(int edgeFlag) {

                }

                @Override
                public void onScrollOverThreshold() {

                }
            });

        }
    }

    /**
     * 隐藏键盘
     */
    protected final void hideKeyBoard() {
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    protected final void showKeyBoard() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    protected boolean needSwipeBack() {
        return true;
    }


    @Override
    protected void onDestroy() {
        context = null;
        ButterKnife.unbind(this);
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
