package org.taurusxi.taurusxilibrary.ui.view

import org.taurusxi.taurusxilibrary.model.Artist

/**
 * Created by wumin on 16/1/21.
 */
interface MainView :PresentationView {
    fun showArtist(artists: List<Artist>)
    fun goToDetail()
}