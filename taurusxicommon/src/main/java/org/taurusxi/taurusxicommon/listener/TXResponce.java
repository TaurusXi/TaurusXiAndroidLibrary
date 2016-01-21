package org.taurusxi.taurusxicommon.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.task.NetworkTask;

/**
 * Created by Yomine on 2015/4/2.
 */
public interface TXResponce<MODEL> {
    void onSuccess(@NonNull MODEL model);

    void onError(@NonNull TXBaseException errorException,@Nullable MODEL errorModel);

    boolean onFinish(@NonNull NetworkTask task);
}
