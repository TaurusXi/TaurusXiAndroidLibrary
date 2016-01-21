package org.taurusxi.taurusxilibrary

import android.app.Application
import org.taurusxi.taurusxicommon.manager.AppManager

/**
 * Created by wumin on 16/1/21.
 */

class AppKT :Application(){

    override fun onCreate() {
        super.onCreate()
        AppManager.getInstance().attachApplication(this)
    }
}