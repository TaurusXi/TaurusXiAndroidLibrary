package org.taurusxi.taurusxilibrary.model

import com.google.gson.annotations.SerializedName

/**
 * Created by wumin on 16/1/21.
 */


//"name": "Keane",
//"mbid": "c7020c6d-cae9-4db3-92a7-e5c561cbad50",
//"match": "1",
//"url": "http://www.last.fm/music/Keane",

//"similarartists": {
//    "artist": [

//"@attr": {
//    "artist": "Coldplay"
//}

data class DataModel(val similarartists: SimilarartistModel)

data class SimilarartistModel(@SerializedName("artist") val artistList: List<Artist>, @SerializedName("@attr") val attr: Attr)

data class Artist(val name: String, val mbid: String, val match: String, val url: String, @SerializedName("image") val imageList: List<Image>)

data class Image(@SerializedName("#text") val url: String, val size: String)

data class Attr(val artist: String)