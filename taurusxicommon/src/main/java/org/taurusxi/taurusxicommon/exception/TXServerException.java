package org.taurusxi.taurusxicommon.exception;

/**
 * Created by xicheng on 15/5/11.
 * 服务端 Server 异常，例如 500 错误
 */
public class TXServerException extends TXBaseException {
    private static final long serialVersionUID = 1L;

    public TXServerException(Exception e) {
        super(e);
    }
}
