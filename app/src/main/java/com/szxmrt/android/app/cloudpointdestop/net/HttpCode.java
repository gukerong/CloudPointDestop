package com.szxmrt.android.app.cloudpointdestop.net;

public class HttpCode {

    public static final int SUCCESS = 0;

    public static final int ERROR_PARAM = -10002;
    public static final int ERROR_LONIN_PWD = -10007; // 登录密码错误
    public static final int ERROR_NAME_NULL = -10012;// 用户名不能为空
    public static final int ERROR_MD5 = -10022;
    public static final int ERROR_PLACE_ERROR_UNDERSTOCK = -200140;
    public static final int ERROR_DEVICE_OFFLINE = -200580;
    public static final int ERROR_VENDOR_BREAK_DOWN = -200510;

    public static final int ERROR_NET_WORK = -9999999;
    public static final int ERROR_OTHER = - 9000001;


    public static final String GET_RSA_PUBKEY = "get_rsa_pubkey";// 获取key
    public static final String SAVE_DATA_KEY = "save_data_key";// 提交key
    public static final String VENDOR_LOGIN = "vendor_login";// 登录
    public static final String LOGIN_SAVE = "login_save";
    public static final String GET_HOME_DATA = "get_home_data";// 获取设备信息
    public static final String AISLE_RECORD = "aisle_record";// 补货
    public static final String GET_ORDER_INFO = "get_order_info";// 获取订单信息
    public static final String VENDOR_ORDER_SAVE = "vendor_order_save";// 下单
    public static final String REFUND = "refund";// 退款
    public static final String SAVE_TERMINAL_STATUS = "save_terminal_status";  // 终端在线处理
    public static final String WECHAT_QRCODE = "get_qrpay_code";        // 微信二维码
    public static final String ALI_QRCODE = "get_aly_qrpay_code";       // 支付宝二维码
    public static final String GET_ADVERT_LIST = "get_advert_list";  // 获取广告
    public static final String ORDER_SELL_FAIL_AISLE = "order_sell_fail_aisle"; //出货失败
    public static final String DOWNLOAD = "downloadFile";   //下载
    public static final String GET_APP_INFO = "get_app_info";   // 获取app版本信息
    public static final String SAVE_ORDER_SELL_STATUS = "save_order_sell_status";// 保存货道信息
    public static final String GET_ORDER_SELL_LIST = "get_order_sell_list";// 获取订单出货状态
    public static final String FAULT_SAVE = "fault_save";  // 停用货道和售货机
    public static final String GET_VENDOR_INFO = "get_vendor_info";  // 获取设备信息

    public static final String CMD_PING = "cmd_ping";   // startPing
    public static final String WEB_CONNECT = "#1509508494";
    public static final String CMD_SUBSCRIBE = "cmd_subscribe"; // 订阅
    public static final String CMD_TERMINF0 = "cmd_terminfo"; // 上报终端信息
    public static final String CMD_PUBLISH_ACK = "cmd_publish_ack"; // 发送回应
    public static final String CMD_PUBLISH = "cmd_publish";
}
