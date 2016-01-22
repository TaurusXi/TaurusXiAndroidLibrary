package org.taurusxi.taurusxi.kotlin.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by wumin on 16/1/21.
 */
abstract class TXKotlinFragment : Fragment() {

    protected final val TAG: String by lazy { this.javaClass.simpleName }

    protected abstract val layoutResource: Int

    protected val intentData:Bundle? by lazy { getArguments() }

    var rootView: View? = null
    //    protected val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(TXSwipeActivity.INPUT_METHOD_SERVICE) as InputMethodManager }

    protected var context: Activity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.context = context as? Activity
    }

    override fun onDetach() {
        this.context = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(layoutResource, container, false)
        initView(savedInstanceState)

        initEvent(savedInstanceState)
        initData(savedInstanceState)
        return rootView
    }

    protected open fun initView(savedInstanceState: Bundle?) {

    }

    protected open fun initData(savedInstanceState: Bundle?) {

    }

    protected open fun initEvent(savedInstanceState: Bundle?) {

    }

    protected fun checkContext(): Boolean {
        return context != null
    }

}