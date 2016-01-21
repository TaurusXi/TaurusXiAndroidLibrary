package org.taurusxi.taurusxilibrary.model

/**
 * Created by xicheng on 15/5/11.
 */
object CodeStatus {
    /**
     * 服务端Code码
     */
    val SUCCESS = 1000 //成功
    val BUSI_ERROR = 1001 //失败 （接口调用成功，服务端内部业务处理失败）
    val NO_APPID = 1002 //没有 APPID
    val DEVICE_FORBID = 1003
    val NO_FILE = 1004 //没有文件
    val NO_USER = 1007 //用户不存在
    val USER_IGNORE_LOGIN = 1008 //用户禁止登录
    val USER_ID_NOT_EXIST = 1009 //当前用户id不存在，当没有登录处理
    //TODO 目前状态码修改了 USER_AUTH_FEED_FAILED，USER_AUTH_COMMENT_FAILED修改为 1006，需要确认
    val USER_AUTH_FEED_FAILED = 1010      //url请求没有权限,可能该用户被禁止发手绘
    val USER_AUTH_COMMENT_FAILED = 1011   //url请求没有权限,可能该用户被禁止发评论

    val PRODUCTION_HAS_COLLECT = 2503 //作品已经收藏
    val PIC_HAS_SAVE = 2601 //图片已经保存
    val USER_HAS_SIGNIN = 2701 //用户已经签到
    val USER_NO_SIGNIN = 2702 //用户未签到

    /**
     * 动态模块响应码5001-6000
     */
    val NO_PERMISSION_TO_USE = 5001 //官方动态普通用户不能使用
    val ADD_CIRCLE_FAILE = 5004 //添加圈子失败
    val KEEP_ATTENTION_CIRCLE_FAILED = 5005 //圈子关注失败
    val PARAMETER_ERROR = 5006 //参数错误，格式转换失败
    val DYNAMIC_STATE_HAS_COLLECT = 5007 //动态已经被用户收藏
    val DYNAMIC_STATE_NO_COLLECT = 5008 //动态没有被用户收藏
    val IMAGE_FORMAT_FAILED = 5009 //图片格式异常
    val HAS_PRAISE = 5010 //该新鲜事已被当前用户赞过
    val DYNAMIC_STATE_ADD_FAILED = 5400 //动态添加失败
    /**
     * 用户关注响应码6001-6099
     */
    val USER_ATTENTION_EXCEPTION = 6002 //用户关注异常
    val USER_HAS_ATTENTION = 6003 //用户已经关注
    val USER_NOT_ATTENTION_HIMSELF = 6004 //用户不能关注自己

    /**
     * 客户端Code码10000以上
     */
    val BITMAP_DECODE_EXCEPTION = 10001 // Bitmap 解析失败
    val JSON_DECODE_EXCEPTION = 10002 // JOSN对象 解析失败
    val FILE_DECODE_EXCEPTION = 10003 // File 解析失败
    val FILE_NOT_EXITS = 10004 // 客户端本地文件不存在

    /**
     * 服务端没有返回Code码字段
     */
    val NO_CODE = -101
}
