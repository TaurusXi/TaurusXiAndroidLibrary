package org.taurusxi.taurusxi.kotlin.extension

import android.view.View
import org.taurusxi.taurusxi.kotlin.listener.SingleClickListener

/**
 * Created by wumin on 16/1/21.
 */

fun View.singleClick(listener:(View?)->Unit){
    setOnClickListener(SingleClickListener(listener))
}

fun View.singleClick(t:Int, listener:(View?)->Unit){
    setOnClickListener(SingleClickListener(t,listener))
}