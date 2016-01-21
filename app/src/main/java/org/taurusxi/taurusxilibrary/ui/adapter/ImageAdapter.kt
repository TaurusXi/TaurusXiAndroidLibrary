package org.taurusxi.taurusxilibrary.ui.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import org.taurusxi.taurusxi.kotlin.extension.singleClick
import org.taurusxi.taurusxi.kotlin.utils.layoutInflater
import org.taurusxi.taurusxicommon.imageLoader.ImageLoaderHelper
import org.taurusxi.taurusxicommon.imageLoader.listener.SimpleImageLoaderListener
import org.taurusxi.taurusxilibrary.R
import org.taurusxi.taurusxilibrary.model.Artist
import kotlin.properties.Delegates

/**
 * Created by wumin on 16/1/22.
 */

class ImageAdapter():RecyclerView.Adapter<ViewHolder>(){

    var items: List<Artist> by Delegates.observable(emptyList()) { prop, old, new -> notifyDataSetChanged() }

    var onItemClickListener:((Artist)->Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setItem(items.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = parent?.context?.layoutInflater?.inflate(R.layout.item_view,parent,false)
        return  ViewHolder(view!!,onItemClickListener)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

}


class ViewHolder(view: View,val onItemClickListener:((Artist)->Unit)?):RecyclerView.ViewHolder(view){
    private val title:TextView? = view.find<TextView>(R.id.title)
    private val image:ImageView? = view.find<ImageView>(R.id.image)

    fun setItem(item: Artist) {
        itemView?.singleClick { onItemClickListener?.invoke(item) }
        title?.text = item.name
        ImageLoaderHelper.getInstance().loadImage(image,item.imageList.get(2).url,object : SimpleImageLoaderListener(){
            override fun loadComplete(url: String?, imageView: ImageView?, bitmap: Bitmap?) {
            }
        })
    }
}