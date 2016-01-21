package org.taurusxi.taurusxicommon.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.task.NetworkTask;

/**
 * Created by wumin on 16/1/21.
 */
public class SimpleTXResponce<MODEL> implements TXResponce<MODEL> {


    @Override
    public void onSuccess(@NonNull MODEL model) {

    }

    @Override
    public void onError(@NonNull TXBaseException errorException,@Nullable MODEL errorModel) {

    }

    @Override
    public boolean onFinish(@NonNull NetworkTask task) {
        return false;
    }

}
