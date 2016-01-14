package org.taurusxi.taurusxilibrary;

import android.app.Application;

import org.taurusxi.taurusxicommon.manager.AppManager;

/**
 * Created by wumin on 16/1/14.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.getInstance().attachApplication(this);
    }
}
