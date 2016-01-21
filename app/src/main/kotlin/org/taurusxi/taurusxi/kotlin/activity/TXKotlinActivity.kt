package org.taurusxi.taurusxi.kotlin.activity

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import me.imid.swipebacklayout.lib.SwipeBackLayout
import org.taurusxi.taurusxicommon.activity.TXSwipeActivity
import org.taurusxi.taurusxicommon.annotation.Keyboard
import org.taurusxi.taurusxicommon.manager.AppManager
import org.taurusxi.taurusxicommon.utils.StringUtils

/**
 * Created by wumin on 16/1/21.
 */
 abstract class TXKotlinActivity :TXSwipeActivity(){

    protected final val TAG:String by lazy { getLogTag() }

    protected abstract val layoutResource: Int

    protected val inputMethodManager:InputMethodManager by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    protected var context:TXKotlinActivity? = null

    override fun needSwipeBack(): Boolean {
        return true
    }

    override final fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        initBase(savedInstanceState)
        initView(savedInstanceState)
        initEvent(savedInstanceState)
        initData(savedInstanceState)
        setKeyBoardListener()
    }

    protected fun getLogTag():String{
        return this.javaClass.simpleName
    }

    protected fun initBase(savedInstanceState: Bundle?){
        context = this
        AppManager.getInstance().addActivity(this)
    }

    private fun setKeyBoardListener(){
        val currentTimes = System.currentTimeMillis()
        val clazz = this.javaClass
        val fields = clazz.declaredFields;
        var flag = false

        for (field in fields){
            if(field.isAnnotationPresent(Keyboard::class.java)){
                flag = true
                break
            }
        }

        if(!flag){
            return
        }
        val swipeLayout = getSwipeBackLayout()
        swipeLayout?.addSwipeListener(object:SimpleSwipeListener(){
            override fun onScrollStateChange(state: Int, scrollPercent: Float) {
                hideKeyBoard()
            }
        })

    }

    protected open fun initView(savedInstanceState: Bundle?){

    }

    protected open fun initData(savedInstanceState: Bundle?){

    }

    protected open fun initEvent(savedInstanceState: Bundle?){

    }

    protected fun showShortToast(msg:String){
        if (context!=null) {
            Toast.makeText(context!!,StringUtils.formatNull(msg),Toast.LENGTH_SHORT).show()
        }
    }


    protected fun hideKeyBoard(){
        inputMethodManager.hideSoftInputFromInputMethod(getWindow().decorView.windowToken,0)
    }

    protected fun showKeyBoard(){
        inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onDestroy() {
        context = null
        super.onDestroy()
    }

    open class SimpleSwipeListener():SwipeBackLayout.SwipeListener{
        override fun onScrollStateChange(state: Int, scrollPercent: Float) {

        }

        override fun onEdgeTouch(edgeFlag: Int) {

        }

        override fun onScrollOverThreshold() {

        }

    }
}