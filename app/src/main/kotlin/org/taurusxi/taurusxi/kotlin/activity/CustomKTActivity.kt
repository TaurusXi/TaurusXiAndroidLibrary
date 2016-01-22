package org.taurusxi.taurusxi.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import org.taurusxi.taurusxilibrary.R
import org.taurusxi.taurusxicommon.utils.MLog

/**
 * Created by wumin on 16/1/14.
 */
class CustomKTActivity : TXKotlinActivity() {
    override val layoutResource: Int
        get() = R.layout.activity_custom

    override fun initView(savedInstanceState: Bundle?) {
        updateFragment(savedInstanceState)
    }

    private fun updateFragment(savedInstanceState: Bundle?) {
        val intent = intent ?: return

        val bundleExtra = intentData
        val clazz = intent.getSerializableExtra(FRAGMENT_CLASS) as Class<out Fragment>

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment? = null
        try {
            fragment = clazz.newInstance()
            if (bundleExtra != null) {
                fragment!!.arguments = bundleExtra
            }
        } catch (e: Exception) {
            MLog.e(TAG, "类反射加载失败")
        }

        fragmentTransaction.replace(R.id.custom_content_layout, fragment, FRAGMENT_TAG)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun initEvent(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {

        public val BUNDLE_EXTRA = "b_extra"
        public val FRAGMENT_CLASS = "f_class"
        public val FRAGMENT_TAG = "f_tag"

        fun startFragment(activity: Activity, bundle: Bundle?, clazz: Class<out Fragment>) {

            if (activity == null) {
                return
            }
            val intent = Intent(activity, CustomKTActivity::class.java)
            if (bundle != null) {
                intent.putExtra(BUNDLE_EXTRA, bundle)
            }
            intent.putExtra(FRAGMENT_CLASS, clazz)
            activity.startActivity(intent)

        }

        fun startFragment(activity: Activity, clazz: Class<out Fragment>) {
            startFragment(activity, null, clazz)
        }
    }
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