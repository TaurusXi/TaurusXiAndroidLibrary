package org.taurusxi.taurusxicommon.listener;

import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.task.NetworkTask;

/**
 * Created by Yomine on 2015/4/2.
 */
public interface TXResponce<MODEL> {
    void onSuccess(MODEL model);

    void onError(TXBaseException errorException, MODEL errorModel);

    boolean onFinish(NetworkTask task);
}
