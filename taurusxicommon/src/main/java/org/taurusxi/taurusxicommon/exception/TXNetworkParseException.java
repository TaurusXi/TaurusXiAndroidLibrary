package org.taurusxi.taurusxicommon.exception;

/**
 * Created by xicheng on 15/5/11.
 * 网络 请求成功 数据 二进制数据流 解析失败
 */
public class TXNetworkParseException extends TXBaseException {
    private static final long serialVersionUID = 1L;

    public TXNetworkParseException(Exception e) {
        super(e);
    }
}
