package org.taurusxi.taurusxi.kotlin.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import me.imid.swipebacklayout.lib.SwipeBackLayout
import org.jetbrains.anko.find
import org.taurusxi.taurusxi.kotlin.utils.Constants
import org.taurusxi.taurusxicommon.activity.TXSwipeActivity
import org.taurusxi.taurusxicommon.annotation.Keyboard
import org.taurusxi.taurusxicommon.manager.AppManager
import org.taurusxi.taurusxicommon.utils.StringUtils
import org.taurusxi.taurusxilibrary.R

/**
 * Created by wumin on 16/1/21.
 */
abstract class TXKotlinActivity : TXSwipeActivity() {

    protected final val TAG: String by lazy { this.javaClass.simpleName }

    protected abstract val layoutResource: Int

    protected val inputMethodManager: InputMethodManager by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    protected var context: TXKotlinActivity? = null

    val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    /**
     *  Activity Intent传递过来的数据
     * */
    protected val intentData: Bundle? by lazy {
        if (intent == null) {
            null
        } else {
            intent.getBundleExtra(Constants.ACTIVITY_EXTRA)
        }
    }

    /**
     *  是否需要 滑动退出界面，默认为 true
     * */
    override fun needSwipeBack(): Boolean {
        return true
    }

    override final fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        initBase(savedInstanceState)
        initView(savedInstanceState)
        initToolBar(savedInstanceState)
        initEvent(savedInstanceState)
        initData(savedInstanceState)
        setKeyBoardListener()
    }

    private  fun initToolBar(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        toolbar.background.alpha = 255
        actionBar?.title = "MAIN"
        initActionBar(savedInstanceState)
    }

    /**
     *  初始ActionBar
     * */
    protected open fun initActionBar(savedInstanceState: Bundle?){

    }

    /**
     *  初始化Base
     * */
    protected fun initBase(savedInstanceState: Bundle?) {
        context = this
        AppManager.getInstance().addActivity(this)
    }

    private fun setKeyBoardListener() {
        val currentTimes = System.currentTimeMillis()
        val clazz = this.javaClass
        val fields = clazz.declaredFields;
        var flag = false

        for (field in fields) {
            if (field.isAnnotationPresent(Keyboard::class.java)) {
                flag = true
                break
            }
        }

        if (!flag) {
            return
        }
        val swipeLayout = getSwipeBackLayout()
        swipeLayout?.addSwipeListener(object : SimpleSwipeListener() {
            override fun onScrollStateChange(state: Int, scrollPercent: Float) {
                hideKeyBoard()
            }
        })

    }

    /*
     * 初始化 视图 入口
     */
    protected open fun initView(savedInstanceState: Bundle?) {

    }

    /**
     * 初始化 数据 入口
     * */
    protected open fun initData(savedInstanceState: Bundle?) {

    }

    /**
     * 初始化 事件 入口
     * */
    protected open fun initEvent(savedInstanceState: Bundle?) {

    }

    /**
     * Toast 显示
     * */
    protected fun showShortToast(msg: String) {
        if (context != null) {
            Toast.makeText(context!!, StringUtils.formatNull(msg), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 隐藏 键盘
     * */
    protected fun hideKeyBoard() {
        inputMethodManager.hideSoftInputFromInputMethod(getWindow().decorView.windowToken, 0)
    }

    /**
     * 显示 键盘
     * */
    protected fun showKeyBoard() {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onDestroy() {
        context = null
        super.onDestroy()
    }

    open class SimpleSwipeListener() : SwipeBackLayout.SwipeListener {
        override fun onScrollStateChange(state: Int, scrollPercent: Float) {

        }

        override fun onEdgeTouch(edgeFlag: Int) {

        }

        override fun onScrollOverThreshold() {

        }

    }
}