package org.taurusxi.taurusxicommon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import org.taurusxi.taurusxicommon.R;
import org.taurusxi.taurusxicommon.utils.TXUtils;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by xicheng on 15/4/26.
 */
public abstract class TXSwipeActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needSwipeBack()) {
            int numCores = TXUtils.getCpuNumbs();
            if (numCores < 4) {
                setTheme(R.style.UnTransportTheme);
            } else {
                mHelper = new SwipeBackActivityHelper(this);
                mHelper.onActivityCreate();
            }
        } else {
            setTheme(R.style.UnTransportTheme);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mHelper != null) {
            mHelper.onPostCreate();
            setSwipeBackEnable(true);
        }
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper == null ? null : mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        if (mHelper != null) {
            getSwipeBackLayout().setEnableGesture(enable);
        }
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected abstract boolean needSwipeBack();


}
