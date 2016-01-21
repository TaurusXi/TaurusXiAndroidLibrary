package org.taurusxi.taurusxilibrary.ui.activity

import android.os.Bundle
import android.view.View
import org.taurusxi.taurusxi.kotlin.activity.TXKotlinActivity
import org.taurusxi.taurusxi.kotlin.utils.startFragment
import org.taurusxi.taurusxilibrary.R
import org.taurusxi.taurusxilibrary.ui.fragment.DetailKtFragment

/**
 * Created by wumin on 16/1/21.
 */
class MainActivity : TXKotlinActivity() {

    override val layoutResource: Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    public fun demoIntent(view: View){
        val bundle = Bundle()
        bundle.putString("key","痴痴缠缠")
        startFragment<DetailKtFragment>(bundle)
    }

}