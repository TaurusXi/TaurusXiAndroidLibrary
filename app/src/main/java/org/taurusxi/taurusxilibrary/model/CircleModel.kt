package org.taurusxi.taurusxilibrary.model


import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Administrator on 2015/4/7.
 * 圈子
 */
class CircleModel : TXBaseModel() {
    var count: String? =null
    var datas: ArrayList<Circle>? =null

    override fun toString(): String {
        return "CircleBean{datas=$datas}"
    }

    class Circle : Serializable {
        var coverKey: String? =null
        var fansCount: String? =null
        var bgKey: String? =null
        /**
         * 主持人名字
         */
        var presentName: String? =null
        /**
         * 内容数
         */
        var feedCount: String? =null
        var circleDesc: String? =null
        var isFocus: String? =null
        var circleId: String? =null
        var circleName: String? =null

        override fun toString(): String {
            return "Circle{coverKey='$coverKey', fansCount='$fansCount', bgKey='$bgKey', presentName='$presentName', feedCount='$feedCount', circleDesc='$circleDesc', isFocus='$isFocus', circleId='$circleId', circleName='$circleName'}"
        }
    }
}
