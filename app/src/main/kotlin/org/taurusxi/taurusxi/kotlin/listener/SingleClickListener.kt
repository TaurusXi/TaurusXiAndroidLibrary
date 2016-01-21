package org.taurusxi.taurusxi.kotlin.listener

import android.view.View
import android.view.ViewConfiguration

/**
 * Created by wumin on 16/1/21.
 */

class SingleClickListener(val click:(v: View)->Unit):View.OnClickListener{

    companion object{
        private val DOUBLE_CLICK_TIMEOUT = ViewConfiguration.getDoubleTapTimeout()
    }

    private var clickTimeOut = DOUBLE_CLICK_TIMEOUT;
    constructor(t:Int,click: (View) -> Unit):this(click){
        clickTimeOut = t
    }

    private var lastClick:Long = 0L

    override fun onClick(v: View) {
        if(getLastClickTimeout()>clickTimeOut){
            lastClick = System.currentTimeMillis()
            click(v)
        }
    }

    private fun getLastClickTimeout(): Long {
        return System.currentTimeMillis() - lastClick
    }

}