package org.taurusxi.taurusxicommon.exception;

/**
 * Created by xicheng on 15/5/11.
 * BaseException
 */
public abstract class TXBaseException extends Exception {
    private static final long serialVersionUID = 1L;

    public TXBaseException() {
    }

    public TXBaseException(Exception e) {
        super(e);
    }

    public TXBaseException(String detailMessage) {
        super(detailMessage);
    }

    public TXBaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TXBaseException(Throwable throwable) {
        super(throwable);
    }
}
