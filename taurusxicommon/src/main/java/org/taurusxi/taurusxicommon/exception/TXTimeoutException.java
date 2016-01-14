package org.taurusxi.taurusxicommon.exception;

/**
 * Created by xicheng on 15/5/11.
 * 连接超时异常
 */
public class TXTimeoutException extends TXBaseException {
    private static final long serialVersionUID = 1L;

    public TXTimeoutException(Exception e) {
        super(e);
    }
}
