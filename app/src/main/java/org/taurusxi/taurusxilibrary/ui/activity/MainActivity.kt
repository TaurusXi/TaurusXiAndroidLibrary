package org.taurusxi.taurusxilibrary.ui.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import org.jetbrains.anko.find
import org.taurusxi.taurusxi.kotlin.activity.TXKotlinActivity
import org.taurusxi.taurusxi.kotlin.activity.startFragment
import org.taurusxi.taurusxicommon.utils.MLog
import org.taurusxi.taurusxilibrary.R
import org.taurusxi.taurusxilibrary.model.Artist
import org.taurusxi.taurusxilibrary.ui.adapter.ImageAdapter
import org.taurusxi.taurusxilibrary.ui.fragment.DetailKtFragment
import org.taurusxi.taurusxilibrary.ui.presenter.MainPresenter
import org.taurusxi.taurusxilibrary.ui.scroll.RecyclerViewScrollWrapper
import org.taurusxi.taurusxilibrary.ui.view.MainView

/**
 * Created by wumin on 16/1/21.
 */
class MainActivity : TXKotlinActivity(),MainView {

    val imageAdapter:ImageAdapter by lazy { ImageAdapter() }

    override val layoutResource: Int = R.layout.activity_main

    val presenter = MainPresenter(this)

    val recycler by lazy { find<RecyclerView>(R.id.recycler) }
//    val background by lazy { findOptional<View>(R.id.background) }

    override fun initView(savedInstanceState: Bundle?) {
        val scrollWrapper = RecyclerViewScrollWrapper(recycler)
        init(scrollWrapper)
    }

    fun init(scrollWrapper: RecyclerViewScrollWrapper) {
        recycler.adapter = imageAdapter
        imageAdapter.onItemClickListener = {  onArtistClicked(it) }
        scrollWrapper.scrollObservers.add {
//            background?.translationY = (-it.scrollY / 2).toFloat()
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        presenter.showGridNetworkData()
    }

    override fun onResume() {
        super.onResume()
        MLog.e("大帅阳今天不帅")
    }

    override fun showArtist(artists: List<Artist>) {
        imageAdapter.items = artists
    }

    override fun goToDetail() {
        throw UnsupportedOperationException()
    }

    fun onArtistClicked(item: Artist) {
        val bundle = Bundle()
        bundle.putString("key","痴痴缠缠")
        startFragment<DetailKtFragment>()
    }

    fun xx(){

        val arrayData = listOf(1,2,3,4,5,6,7,8,9,10)
        arrayData.filter { it>5 }.map { it*2 }

    }
}