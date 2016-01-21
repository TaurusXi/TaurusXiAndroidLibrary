package org.taurusxi.taurusxi.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import org.taurusxi.taurusxicommon.R
import org.taurusxi.taurusxicommon.activity.TXActivity
import org.taurusxi.taurusxicommon.utils.MLog

/**
 * Created by wumin on 16/1/14.
 */
class CustomKTActivity : TXActivity() {

    override fun getContentView(): Int {
        return R.layout.custom_activity_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        updateFragment(savedInstanceState)
    }

    private fun updateFragment(savedInstanceState: Bundle?) {
        val intent = intent ?: return

        val bundleExtra = intent.getBundleExtra(BUNDLE_EXTRA)
        val clazz = intent.getSerializableExtra(FRAGMENT_CLASS) as Class<out Fragment> ?: return

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

    override fun initEvents(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {

        public  val BUNDLE_EXTRA = "b_extra"
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
