package org.taurusxi.taurusxicommon.exception;

/**
 * Created by xicheng on 15/5/13.
 * <p/>
 * 网络异常 --- 未连接异常
 */
public class TXNoConnectionException extends TXBaseException {

    public TXNoConnectionException() {

    }

    public TXNoConnectionException(Exception e) {
        super(e);
    }
}
