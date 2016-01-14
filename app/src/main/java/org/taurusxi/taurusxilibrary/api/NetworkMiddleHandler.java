package org.taurusxi.taurusxilibrary.api;


import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.exception.TXBusiFailureException;
import org.taurusxi.taurusxicommon.exception.TXModelEmptyException;
import org.taurusxi.taurusxicommon.exception.TXRouterException;
import org.taurusxi.taurusxicommon.exception.TXUnKnownException;
import org.taurusxi.taurusxicommon.listener.TXResponce;
import org.taurusxi.taurusxicommon.network.listener.NetworkResponce;
import org.taurusxi.taurusxicommon.task.NetworkTask;
import org.taurusxi.taurusxicommon.utils.JSONHelper;
import org.taurusxi.taurusxilibrary.model.CodeStatus;
import org.taurusxi.taurusxilibrary.model.TXBaseModel;


/**
 * Created by xicheng on 15/5/11.
 * <p/>
 * 中间层 处理 业务 Model 容错
 */
public class NetworkMiddleHandler<MODEL extends TXBaseModel> implements NetworkResponce<NetworkTask, String, MODEL, TXBaseException> {

    private TXResponce<MODEL> TXResponce;
    private Class<MODEL> clazz;

    public NetworkMiddleHandler(Class<MODEL> clazz, TXResponce<MODEL> TXResponce) {
        this.TXResponce = TXResponce;
        this.clazz = clazz;
    }

    @Override
    public void onSuccessResponse(NetworkTask task, String responseData) {
        MODEL data = null;
        boolean isJsonParseExceptionFlag = true;
        try {
            data = JSONHelper.getInstance().fromGson(responseData, clazz);
        } catch (Exception e) {
            if (responseData != null && !responseData.startsWith("{")) {
                isJsonParseExceptionFlag = false;
            }
        }
        if (data == null) {
            if (isJsonParseExceptionFlag) {
                TXResponce.onError(new TXModelEmptyException(), null);
            } else {
                TXResponce.onError(new TXRouterException(), null);
            }
            return;
        }
        switch (data.code) {
            //TODO 统一处理 Code码
            case CodeStatus.SUCCESS:
                TXResponce.onSuccess(data);
                break;
            case CodeStatus.BUSI_ERROR:
                TXResponce.onError(new TXBusiFailureException(), data);
                break;
            case CodeStatus.NO_APPID:
                // TODO 没有 APP_ID
                break;
            case CodeStatus.NO_FILE:
                // TODO 没有 文件
                break;
            case CodeStatus.PRODUCTION_HAS_COLLECT:
                TXResponce.onSuccess(data);
                // TODO 作品已经收藏
                break;
            case CodeStatus.PIC_HAS_SAVE:
                // TODO 图片已经保存
                break;
            case CodeStatus.USER_HAS_ATTENTION:
                TXResponce.onSuccess(data);
                break;
            case CodeStatus.DYNAMIC_STATE_HAS_COLLECT:
                TXResponce.onSuccess(data);
                break;
            case CodeStatus.DYNAMIC_STATE_NO_COLLECT:
                TXResponce.onSuccess(data);
                break;
            default:
                //默认 抛出 未知异常，// 后续应该保持 未知异常的数据于 XML,根据事件（每次APP启动）将未知的异常信息传输到服务端
                //，便于日志分析
                TXResponce.onError(new TXUnKnownException(), data);
                break;
        }
    }

    @Override
    public void onError(NetworkTask task, TXBaseException errorException, MODEL errorModel) {
        TXResponce.onError(errorException, errorModel);
    }

    @Override
    public boolean networkFinish(NetworkTask task) {
        return TXResponce != null && TXResponce.onFinish(task);
    }
}
