package org.taurusxi.taurusxilibrary.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.taurusxi.taurusxi.kotlin.extension.singleClick
import org.taurusxi.taurusxi.kotlin.fragment.TXKotlinFragment
import org.taurusxi.taurusxi.kotlin.utils.find
import org.taurusxi.taurusxi.kotlin.utils.hasKitKat
import org.taurusxi.taurusxi.kotlin.utils.with
import org.taurusxi.taurusxicommon.activity.CustomTXActivity
import org.taurusxi.taurusxicommon.adapter.SimpleTXAdapter
import org.taurusxi.taurusxicommon.imageLoader.ImageLoaderHelper
import org.taurusxi.taurusxicommon.imageLoader.listener.SimpleImageLoaderListener
import org.taurusxi.taurusxicommon.keyValueModel.utils.SettingHelper
import org.taurusxi.taurusxicommon.listener.SimpleTXResponce
import org.taurusxi.taurusxicommon.task.NetworkTask
import org.taurusxi.taurusxicommon.utils.MLog
import org.taurusxi.taurusxilibrary.R
import org.taurusxi.taurusxilibrary.api.ApiImpl
import org.taurusxi.taurusxilibrary.model.CircleModel
import kotlin.properties.Delegates

/**
 * Created by wumin on 16/1/21.
 */
class DetailKtFragment : TXKotlinFragment(), View.OnClickListener {

    companion object{
        var count = 1
    }

    override val layoutResource: Int = R.layout.fragment_detail

    val fromDataTextView by lazy { find<TextView>(R.id.from_data) }

    val intentTextView by lazy { find<TextView>(R.id.intent) }

    val listView by lazy { find<ListView>(R.id.listview) }

    var simpleAdapter by Delegates.notNull<SampleAdapter>()

    var dd:String by Delegates.observable(String()){
        prop,old,new ->
            MLog.e("prop:${prop}__old:${old}__new:${new}")
    }

    override fun initToolBar(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        dd = "AAA"
        simpleAdapter = SampleAdapter(context as Context)
        listView.adapter = simpleAdapter

        find<Button>(R.id.network).singleClick {
            ApiImpl.getCircleList(object : SimpleTXResponce<CircleModel>(){
                override fun onSuccess(model: CircleModel?) {
                    simpleAdapter.setCount(model?.datas)
                }

                override fun onFinish(task: NetworkTask?): Boolean {
                    return checkContext()
                }
            })
        }
        find<Button>(R.id.intent).singleClick(500) {

        }
        find<Button>(R.id.sql_save).setOnClickListener(this)

        with(listView) {
            val layout = layoutParams

        }

        hasKitKat {

        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        if(intentData==null){}
    }

    override fun initEvent(savedInstanceState: Bundle?) {
        context?.async() {
            Thread.sleep(2000)
            activityUiThreadWithContext{
                MLog.i("回调至主线程:_${System.currentTimeMillis()}")
            }
            MLog.i("延时:_${System.currentTimeMillis()}")
        }

        MLog.i("info:_${System.currentTimeMillis()}")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.intent ->{
                count++
                CustomTXActivity.startFragment(context, DetailKtFragment::class.java)
            }
            R.id.network ->{
                ApiImpl.getCircleList(object : SimpleTXResponce<CircleModel>(){
                    override fun onSuccess(model: CircleModel?) {
                        simpleAdapter.setCount(model?.datas)
                    }

                    override fun onFinish(task: NetworkTask?): Boolean {
                        return checkContext()
                    }
                })
            }
            R.id.sql_save ->{
                val instance = SettingHelper.getInstance()
                instance.saveInt("demo",-1)
            }

        }
    }


    class SampleAdapter(context: Context): SimpleTXAdapter<CircleModel.Circle, ViewHolder>(context){

        override fun onCreateViewHodler(convertView: View?): ViewHolder? {
            return ViewHolder(convertView!!)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
            val circle = listData.get(position)
            ImageLoaderHelper.getInstance().loadImage(viewHolder!!.imageView,"http://pic.shantoo.cn/" +circle.coverKey,object : SimpleImageLoaderListener(){
                override fun loadComplete(url: String?, imageView: ImageView?, bitmap: Bitmap?) {
                    MLog.e(TAG,"url:${url}—加载完成")
                }
            })
        }

        override fun getItemLayout() = R.layout.item_view

    }

    class ViewHolder (val view: View){
        val imageView: ImageView = view.find(R.id.image)
    }
}