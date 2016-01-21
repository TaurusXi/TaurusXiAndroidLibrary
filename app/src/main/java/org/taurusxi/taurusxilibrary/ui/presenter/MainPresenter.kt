package org.taurusxi.taurusxilibrary.ui.presenter

import org.taurusxi.taurusxicommon.utils.JSONHelper
import org.taurusxi.taurusxilibrary.model.Artist
import org.taurusxi.taurusxilibrary.model.DataModel
import org.taurusxi.taurusxilibrary.model.SampleData
import org.taurusxi.taurusxilibrary.ui.view.MainView

/**
 * Created by wumin on 16/1/21.
 */
class MainPresenter(override val view:MainView
                     ):Presenter<MainView> {
    public fun showGridNetworkData(){
        val data: DataModel? = JSONHelper.getInstance().fromGson(SampleData.SAMPLE_DATA, DataModel::class.java)
        view.showArtist(data?.similarartists?.artistList ?:listOf(Artist()))
    }
}