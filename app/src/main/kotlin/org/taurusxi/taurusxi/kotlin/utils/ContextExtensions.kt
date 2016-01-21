package org.taurusxi.taurusxi.kotlin.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import org.taurusxi.taurusxi.kotlin.activity.CustomKTActivity
import org.taurusxi.taurusxi.kotlin.activity.TXKotlinActivity

/**
 * Created by wumin on 16/1/21.
 */

public object Constants {
    public val ACTIVITY_EXTRA = "a_extra"
}

inline public fun <reified T:TXKotlinActivity> Activity.startActivity(extraData:Bundle? = null){
    if(this == null){
        return
    }
    val intent = Intent(this, T::class.java)
    if (extraData != null) {
        intent.putExtra(Constants.ACTIVITY_EXTRA, extraData)
    }
    ActivityCompat.startActivity(this,intent,null)
}


inline public fun <reified T:Fragment> Activity.startFragment(extraData:Bundle? = null){
    if(this == null){
        return
    }
    val intent = Intent(this, CustomKTActivity::class.java)
    if (extraData != null) {
        intent.putExtra(CustomKTActivity.BUNDLE_EXTRA, extraData)
    }
    intent.putExtra(CustomKTActivity.FRAGMENT_CLASS, T::class.java)
    ActivityCompat.startActivity(this,intent,null)
}

