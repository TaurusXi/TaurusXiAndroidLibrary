package org.taurusxi.taurusxilibrary.api

import org.taurusxi.taurusxicommon.listener.TXResponce
import org.taurusxi.taurusxicommon.network.NetworkHelper
import org.taurusxi.taurusxicommon.task.NetworkTask
import org.taurusxi.taurusxilibrary.model.CircleModel

object ApiImpl {

    /**
     * 4.1	圈子列表V1
     */
    fun getCircleList(networkResponse: TXResponce<CircleModel>) {
        val networkTask = NetworkTask("http://api.boluofan.com.cn/blf_api/circle/list", NetworkTask.Method.POST)
        networkTask.addHeader("appId", "DVD2015042517595987700")
        networkTask.addHeader("UA", "A|2.3.0_online_release_04|5.1.1|SM-G925F|android|DVD2015042517595987700|a4b1ea140bdd3c00a385545477a05391")
        networkTask.addParams("userId", "")
        networkTask.debugTag = "getCircleList"
        networkTask.setNetworkNewResponce(NetworkMiddleHandler(CircleModel::class.java, networkResponse))
        NetworkHelper.getInstance().addTask(networkTask)
    }
}
