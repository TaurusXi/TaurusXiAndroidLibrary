package org.taurusxi.taurusxicommon.network.listener;


import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.task.TXTask;

/**
 * Created on 15/2/13.
 *
 * @param TASK      必须继承TXTask，是 业务接口组装的 Task(网络为 NewNetworkTask)，方便做业务容错后的接口重试与分发。
 * @param OLD_MODEL 为 NetworkHelper 下发的旧Model对象，一般为 String 类型。
 * @param NEW_MODEL 为 经过 NetworkMiddleHandler 做 中间层处理后的 类型，一般为各业务Model。
 * @param EXCEPTION 为客户端自定义Exception，必须继承JoyBaseException，实现业务细分。
 * @author xicheng
 * @email 505591443@qq.com
 * @github https://github.com/TaurusXi
 */
public interface NetworkResponce<TASK extends TXTask, OLD_MODEL, NEW_MODEL, EXCEPTION extends TXBaseException> extends Cloneable {

    void onSuccessResponse(TASK task, OLD_MODEL model);

    void onError(TASK task, EXCEPTION exception, NEW_MODEL model);

    boolean networkFinish(TASK task);


}
