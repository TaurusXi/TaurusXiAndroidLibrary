package org.taurusxi.taurusxilibrary.api


import org.taurusxi.taurusxicommon.exception.TXBaseException
import org.taurusxi.taurusxicommon.exception.TXBusiFailureException
import org.taurusxi.taurusxicommon.exception.TXModelEmptyException
import org.taurusxi.taurusxicommon.exception.TXRouterException
import org.taurusxi.taurusxicommon.exception.TXUnKnownException
import org.taurusxi.taurusxicommon.listener.TXResponce
import org.taurusxi.taurusxicommon.network.listener.NetworkResponce
import org.taurusxi.taurusxicommon.task.NetworkTask
import org.taurusxi.taurusxicommon.utils.JSONHelper
import org.taurusxi.taurusxilibrary.model.CodeStatus
import org.taurusxi.taurusxilibrary.model.TXBaseModel


/**
 * Created by xicheng on 15/5/11.
 *
 *
 * 中间层 处理 业务 Model 容错
 */
class NetworkMiddleHandler<MODEL : TXBaseModel>(private val clazz: Class<MODEL>, private val TXResponce: TXResponce<MODEL>?) : NetworkResponce<NetworkTask, String, MODEL, TXBaseException> {

    override fun onSuccessResponse(task: NetworkTask, responseData: String?) {
        var data: MODEL? = null
        var isJsonParseExceptionFlag = true
        try {
            data = JSONHelper.getInstance().fromGson(responseData, clazz)
        } catch (e: Exception) {
            if (responseData != null && !responseData.startsWith("{")) {
                isJsonParseExceptionFlag = false
            }
        }

        if (data == null) {
            if (isJsonParseExceptionFlag) {
                TXResponce!!.onError(TXModelEmptyException(), null)
            } else {
                TXResponce!!.onError(TXRouterException(), null)
            }
            return
        }
        when (data.code) {
        //TODO 统一处理 Code码
            CodeStatus.SUCCESS -> TXResponce!!.onSuccess(data)
            CodeStatus.BUSI_ERROR -> TXResponce!!.onError(TXBusiFailureException(), data)
            CodeStatus.NO_APPID -> {
            }
            CodeStatus.NO_FILE -> {
            }
            CodeStatus.PRODUCTION_HAS_COLLECT -> TXResponce!!.onSuccess(data)
            CodeStatus.PIC_HAS_SAVE -> {
            }
            CodeStatus.USER_HAS_ATTENTION -> TXResponce!!.onSuccess(data)
            CodeStatus.DYNAMIC_STATE_HAS_COLLECT -> TXResponce!!.onSuccess(data)
            CodeStatus.DYNAMIC_STATE_NO_COLLECT -> TXResponce!!.onSuccess(data)
            else -> //默认 抛出 未知异常，// 后续应该保持 未知异常的数据于 XML,根据事件（每次APP启动）将未知的异常信息传输到服务端
                //，便于日志分析
                TXResponce!!.onError(TXUnKnownException(), data)
        }// TODO 没有 APP_ID
        // TODO 没有 文件
        // TODO 作品已经收藏
        // TODO 图片已经保存
    }

    override fun onError(task: NetworkTask, errorException: TXBaseException, errorModel: MODEL) {
        TXResponce!!.onError(errorException, errorModel)
    }

    override fun networkFinish(task: NetworkTask): Boolean {
        return TXResponce != null && TXResponce.onFinish(task)
    }
}
