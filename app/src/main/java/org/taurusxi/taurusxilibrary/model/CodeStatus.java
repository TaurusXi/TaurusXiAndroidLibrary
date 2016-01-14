package org.taurusxi.taurusxilibrary.model;

/**
 * Created by xicheng on 15/5/11.
 */
public class CodeStatus {
    /**
     * 服务端Code码
     */
    public final static int SUCCESS = 1000; //成功
    public final static int BUSI_ERROR = 1001; //失败 （接口调用成功，服务端内部业务处理失败）
    public final static int NO_APPID = 1002; //没有 APPID
    public final static int DEVICE_FORBID = 1003;
    public final static int NO_FILE = 1004; //没有文件
    public final static int NO_USER = 1007; //用户不存在
    public final static int USER_IGNORE_LOGIN = 1008; //用户禁止登录
    public final static int USER_ID_NOT_EXIST = 1009; //当前用户id不存在，当没有登录处理
    //TODO 目前状态码修改了 USER_AUTH_FEED_FAILED，USER_AUTH_COMMENT_FAILED修改为 1006，需要确认
    public final static int USER_AUTH_FEED_FAILED = 1010;      //url请求没有权限,可能该用户被禁止发手绘
    public final static int USER_AUTH_COMMENT_FAILED = 1011;   //url请求没有权限,可能该用户被禁止发评论

    public final static int PRODUCTION_HAS_COLLECT = 2503; //作品已经收藏
    public final static int PIC_HAS_SAVE = 2601; //图片已经保存
    public final static int USER_HAS_SIGNIN = 2701; //用户已经签到
    public final static int USER_NO_SIGNIN = 2702; //用户未签到

    /**
     * 动态模块响应码5001-6000
     */
    public final static int NO_PERMISSION_TO_USE = 5001; //官方动态普通用户不能使用
    public final static int ADD_CIRCLE_FAILE = 5004; //添加圈子失败
    public final static int KEEP_ATTENTION_CIRCLE_FAILED = 5005; //圈子关注失败
    public final static int PARAMETER_ERROR = 5006; //参数错误，格式转换失败
    public final static int DYNAMIC_STATE_HAS_COLLECT = 5007; //动态已经被用户收藏
    public final static int DYNAMIC_STATE_NO_COLLECT = 5008; //动态没有被用户收藏
    public final static int IMAGE_FORMAT_FAILED = 5009; //图片格式异常
    public final static int HAS_PRAISE = 5010; //该新鲜事已被当前用户赞过
    public final static int DYNAMIC_STATE_ADD_FAILED = 5400; //动态添加失败
    /**
     * 用户关注响应码6001-6099
     */
    public final static int USER_ATTENTION_EXCEPTION = 6002; //用户关注异常
    public final static int USER_HAS_ATTENTION = 6003; //用户已经关注
    public final static int USER_NOT_ATTENTION_HIMSELF = 6004; //用户不能关注自己

    /**
     * 客户端Code码10000以上
     */
    public final static int BITMAP_DECODE_EXCEPTION = 10001; // Bitmap 解析失败
    public final static int JSON_DECODE_EXCEPTION = 10002; // JOSN对象 解析失败
    public final static int FILE_DECODE_EXCEPTION = 10003; // File 解析失败
    public final static int FILE_NOT_EXITS = 10004; // 客户端本地文件不存在

    /**
     * 服务端没有返回Code码字段
     */
    public static final int NO_CODE = -101;
}
