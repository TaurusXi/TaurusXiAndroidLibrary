package org.taurusxi.taurusxi.kotlin.utils

import android.content.Context
import android.view.View
import org.jetbrains.anko.internals.AnkoInternals
import org.taurusxi.taurusxi.kotlin.fragment.TXKotlinFragment
import org.taurusxi.taurusxicommon.network.NetworkHelper

/**
 * Created by wumin on 16/1/21.
 */

inline fun <reified T : View> TXKotlinFragment.find(id: Int): T = rootView?.findViewById(id) as T

fun <T> with(t:T,body:T.()->Unit){
    t.body()
}

@AnkoInternals.NoBinding
val Context.vibrator: android.os.Vibrator
    get() = getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator

@AnkoInternals.NoBinding
val Context.layoutInflater: android.view.LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater
