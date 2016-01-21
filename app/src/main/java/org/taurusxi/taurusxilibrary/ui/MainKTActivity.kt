package org.taurusxi.taurusxilibrary.ui

import android.os.Bundle
import android.view.View
import org.jetbrains.anko.find
import org.taurusxi.taurusxi.kotlin.activity.TXKotlinActivity
import org.taurusxi.taurusxicommon.activity.CustomTXActivity
import org.taurusxi.taurusxilibrary.R

/**
 * Created by wumin on 16/1/21.
 */
class MainKTActivity:TXKotlinActivity() {
    override val layoutResource: Int = R.layout.activity_main
    //    override val layoutResource: Int
//        get() = R.layout.activity_main



    override fun initView(savedInstanceState: Bundle?) {

    }

    public fun demoIntent(view: View){
        val bundle = Bundle()
        bundle.putString("key","痴痴缠缠")
        CustomTXActivity.startFragment(context,bundle,DetailKtFragment::class.java)
    }

}