package org.taurusxi.taurusxi.kotlin.utils

import android.view.View
import org.taurusxi.taurusxi.kotlin.fragment.TXKotlinFragment

/**
 * Created by wumin on 16/1/21.
 */

inline fun <reified T : View> TXKotlinFragment.find(id: Int): T = rootView?.findViewById(id) as T

fun <T> with(t:T,body:T.()->Unit){
    t.body()
}
