package androidrubicktest.api;

/**
 * Created by frank.sun on 2014/9/24.
 */
public class APIConfig {
    /**
     * 服务器接口
     */
//    public static final String URL_PREFIX = "http://192.168.44.24:9000/neptune/";

    public static final String URL_PREFIX = BaseConfig.DOMAIN + "/neptune/";
    /**
     * 订单
     */
    public static final String GET_ORDERS = URL_PREFIX + "order/get_list/v1";
    public static final String GET_ORDER_DETAIL = URL_PREFIX + "order/get_detail/v1";
    public static final String POST_CREATE_ORDER = URL_PREFIX + "checkout/create_order/v1";
    public static final String POST_CANCEL_ORDER = URL_PREFIX + "order/cancel/v1";
    /**
     * 退货
     */
    public static final String POST_RETURN = URL_PREFIX + "order/return_order/v1";
    public static final String GET_RETURN_DETAIL=URL_PREFIX+"order/query_return_order/v1";
    /**
     * 物流
     */
    public static final String GET_LOGISTICS = URL_PREFIX + "order/get_order_track/v1";
    /**
     * 购物车相关的配置
     */
    public static final String CART_ADD_PRODUCT = URL_PREFIX + "cart/add/v1";
    public static final String CART_GET_PRODUCTS = URL_PREFIX + "cart/get/v1";
    public static final String CART_UPDATE_PRODUCT = URL_PREFIX + "cart/update/v1";
    public static final String CART_DELETE_PRODUCT = URL_PREFIX + "cart/delete/v1";
    public static final String CART_EXTENDCARTTIME = URL_PREFIX + "cart/extend_time/v1";
    public static final String GET_GIFTCARD = URL_PREFIX + "giftcard/get_user_account_situation/v1";

    public static final String GET_CART_HISTORY = URL_PREFIX + "cart/get_history/v1";
    public static final String POST_DELETE_CART_HISTORY = URL_PREFIX + "cart/del_history/v1";

    public static final String GET_COUPON_LIST = URL_PREFIX + "pms/get_list/v1";
    public static final String GET_USABLE_COUPON_LIST = URL_PREFIX + "pms/get_coupon_list/v1";


    /**
     * 结算相关的配置
     */
    public static final String POST_CALC_AMOUNT = URL_PREFIX + "checkout/calc_amount/v1";
    public static final String GET_ADDRESS_LIST = URL_PREFIX + "address/get_list/v1";
    public static final String GET_ADDRESS_DEFAULT = URL_PREFIX + "address/get_default/v1";
    public static final String GET_SMART_AREA = URL_PREFIX + "address/get_smart_area/v1";
    public static final String POST_ADDRESS_ADD = URL_PREFIX + "address/add/v1";
    public static final String POST_ADDRESS_UPDATE = URL_PREFIX + "address/update/v1";
    public static final String POST_ADDRESS_DELETE = URL_PREFIX + "address/delete/v1";
    public static final String POST_ADDRESS_VERIFY_IDCARD = URL_PREFIX + "user/verify_idcard/v1";
    public static final String GET_AREA_DATA = "http://n.myopen.vip.com/address/address";


    /**
     * 退货相关的
     */
    public static final String GET_BACKGOODS_AND_REASON = URL_PREFIX + "order/query_backgoods_and_reason/v1";


    /**
     * Session相关的配置
     */
    public static final String SESSION_URL_PREFIX = BaseConfig.SESSION_DOMAIN + "/neptune/";
    public static final String SESSION_FDS_URL_PREFIX = BaseConfig.SESSION_FDS_DOMAIN + "/neptune/";
    public static final String POST_SESSION_REGISTER = SESSION_URL_PREFIX + "user/register/v1";
    public static final String POST_SESSION_LOGIN = SESSION_URL_PREFIX + "user/login/v1";
    public static final String POST_SESSION_OTHER_LOGIN = SESSION_URL_PREFIX + "user/thrid_login/v1";
    public static final String GET_SESSION_PASSWORD_GET_RESET_VERIFICATION_CODE = SESSION_URL_PREFIX + "user/password/get_reset_verification_code/v1";
    public static final String POST_SESSION_PASSWORD_RESET = SESSION_URL_PREFIX + "user/password/reset/v1";
    public static final String POST_SESSION_SECURE_CHECK = SESSION_FDS_URL_PREFIX + "user/secure_check/v1";

    /**
     * Wallet相关的配置
     */
    public static final String GET_WALLET_BASE_INFO = URL_PREFIX + "wallet/get_info/v1";
    public static final String GET_WALLET_DETAIL = URL_PREFIX + "wallet/get_detail/v1";
    public static final String GET_WALLET_BIND_VERCODE = URL_PREFIX + "user/mobile/get_bind_verification_code/v1";
    public static final String POST_WALLET_BIND_PHONE = URL_PREFIX + "user/mobile/bind_number/v1";
    /** 重新绑定前先检查当前绑定的手机号 */
    public static final String GET_WALLET_CHECK_REBIND_PHONE_VERCODE = URL_PREFIX + "user/mobile/get_check_verification_code/v1";
    public static final String POST_WALLET_CHECK_REBIND_PHONE = URL_PREFIX + "user/mobile/check_number/v1";
    public static final String GET_WALLET_REBIND_VERCODE = URL_PREFIX + "user/mobile/get_rebind_verification_code/v1";
    public static final String POST_WALLET_REBIND_PHONE = URL_PREFIX + "user/mobile/rebind_number/v1";

    public static final String POST_WALLET_GET_SETPASSWORD_VERIFYCODE = URL_PREFIX + "wallet/set_password/v1";
    public static final String POST_WALLET_SETPASSWORD = URL_PREFIX + "wallet/set_password/v1";
    public static final String POST_WALLET_GET_CHANGEPASSWORD_VERIFYCODE = URL_PREFIX + "wallet/update_password/v1";
    public static final String POST_WALLET_CHANGEPASSWORD = URL_PREFIX + "wallet/update_password/v1";

    /**
     * 提现的相关URL
     */
    /** 获取用户有已完成的订单里面的手机号码 */
    public static final String GET_MOBILE_LIST_BY_USER_ID = URL_PREFIX +"wallet/get_mobile_list_by_userid/v1";
    /** 去支付平台或者B2C或者订单里面查询开户人姓名 */
    public static final String GET_CANDIDATE_USER_NAME = URL_PREFIX + "wallet/get_candidate_user_name/v1";
     /** 获取银行列表 */
    public static final String GET_BANK_LIST = URL_PREFIX+ "wallet/get_bank_list/v1";
    /** 获取所有省 */
    public static final String GET_AREA_LIST =  URL_PREFIX+"wallet/get_area_list/v1";
    /** 获取市 */
    public static final String GET_CITY_LIST = URL_PREFIX+"wallet/get_city_list/v1";
    /** 根据银行所在地获取分行 */
    public static final String GET_BRANCH_BANK_LIST =  URL_PREFIX+"wallet/get_branch_list/v1";
    /** 执行绑定银行卡操作 */
    public static final String POST_BIND_BANK_CARD = URL_PREFIX + "wallet/bank_card/bind/v1";
    /** 查询用户是否绑定银行卡，有 则返回银行卡信息 */
    public static final String GET_BANK_CARD_INFO = URL_PREFIX + "wallet/get_bank_card_info/v1";
    /** 执行提现操作 */
    public static final String POST_WITHDRAW_APPLY = URL_PREFIX + "wallet/withdraw/v1";
    /** 绑定银行卡发送验证码 */
    public static final String GET_SEND_BIND_CARD_VERIFY_CODE = URL_PREFIX + "wallet/send_bind_card_verify_code/v1";
}
