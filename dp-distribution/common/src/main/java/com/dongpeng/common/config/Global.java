package com.dongpeng.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 全局配置对象
 * <br/><strong>不可在其他使用@Configuration注解的类使用</strong
 */
@RefreshScope
@Configuration
public class Global  implements EnvironmentAware{

    private static final Logger logger = LoggerFactory.getLogger(Global.class);

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        logger.warn("开始加载 environment");
        this.env=environment;

        /** 加载常量 **/
        DICT_ROLE_ADMIN = getConfig("constant.dict.role.admin");
        DICT_ROLE_TEST = getConfig("constant.dict.role.test");
        DICT_ROLE_FRIEND = getConfig("constant.dict.role.friend");
        DICT_ROLE_EMPLOYEE = getConfig("constant.dict.role.employee");

        DICT_ORG_TYPE_FXS = getConfig("constant.dict.org.type.fxs");;
        DICT_ORG_TYPE_JXS = getConfig("constant.dict.org.type.jxs");;
        DICT_ORG_TYPE_ZB = getConfig("constant.dict.org.type.zb");;
        DICT_ORG_TYPE_MD = getConfig("constant.dict.org.type.md");;

        COUPON_STATUS_INACTIVE = getConfig("constant.dict.coupon.status.inactive");
        COUPON_STATUS_ACTIVE = getConfig("constant.dict.coupon.status.active");
        COUPON_STATUS_END = getConfig("constant.dict.coupon.status.end");
        COUPON_STATUS_INVALID = getConfig("constant.dict.coupon.status.invalid");

        SUBMIT = getConfig("constant.dict.review_status.submit");
        UNREVIEW = getConfig("constant.dict.review_status.unreview");
        IN_REVIEW = getConfig("constant.dict.review_status.in_review");
        REVIEWED = getConfig("constant.dict.review_status.reviewed");
        REVIEW_FAILURE = getConfig("constant.dict.review_status.review_failure");

        COUPON_RECEIVE_TYPE_PUBLIC = getConfig("constant.dict.coupon.receive_type.public");
        COUPON_RECEIVE_TYPE_STAFF = getConfig("constant.dict.coupon.receive_type.staff");
        COUPON_TYPE_MANJIAN = getConfig("constant.dict.coupon.type.manjian");
        COUPON_TYPE_MANSONG = getConfig("constant.dict.coupon.type.mansong");
        COUPON_TYPE_MANZHE = getConfig("constant.dict.coupon.type.manzhe");
        COUPON_TYPE_MEIMANJIAN = getConfig("constant.dict.coupon.type.meimanjian");
        COUPON_TYPE_MEIMANSONG = getConfig("constant.dict.coupon.type.meimansong");

        DICT_USER_TYPE_BACKSTAGE=getConfig("constant.dict.user.type.backstage");
        DICT_USER_TYPE_EMPLOYEE=getConfig("constant.dict.user.type.employee");
        DICT_USER_TYPE_FRIEND=getConfig("constant.dict.user.type.friend");

        COUPON_EXAMINE  = getConfig("constant.dict.examine.type.ce");
        EMPLOYEE_APPLY_COUPON_EXAMINE  = getConfig("constant.dict.examine.type.eace");
        USER_APPLY_EXAMINE  = getConfig("constant.dict.examine.type.uae");
        COUPON_USE_TYPE_MYSELF  = getConfig("constant.dict.coupon_use_type.type.cutmy");
        COUPON_USE_TYPE_FAMILY  = getConfig("constant.dict.coupon_use_type.type.cutfa");
        COUPON_USE_TYPE_FRIEND  = getConfig("constant.dict.coupon_use_type.type.cutfr");
        COUPON_USE_TYPE_CUSTOMER  = getConfig("constant.dict.coupon_use_type.type.cutcu");

        PERSON_COUPON_STATUS_NOT_WRITE_OFF = getConfig("constant.dict.person_coupon.status.not_write_off");
        PERSON_COUPON_STATUS_WRITE_OFF = getConfig("constant.dict.person_coupon.status.write_off");
        PERSON_COUPON_STATUS_INVALID = getConfig("constant.dict.person_coupon.status.invalid");

        SHARE_SECRET = getConfig("constant.share_secret");

        COUPON_SOURCE_PUBLIC = getConfig("constant.dict.coupon.source.public");
        COUPON_SOURCE_PRIVATE = getConfig("constant.dict.coupon.source.private");
        SHARING_COUPON_EXPENDITURE = getConfig("constant.dict.integral_type.sce");
        STORE_EXPENDITURE= getConfig("constant.dict.integral_type.se");

        DICT_SMS_LOGIN_REGISTER=getConfig("constant.dict.sms.loginOrRegister");


    }

    /*********** 分享签名盐常量 *************/
    public static String SHARE_SECRET;
    /**
     * 检测 env 是否为空，只有env不为空才可使用getConfig
     * @return true: env为null <br/> false: env已实例化
     */
    public static boolean isEnvNull(){
        return null==env;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        return env.getProperty(key);
    }

    /*********** 字典常量 *************/

    /**** 角色常量 ****/
    public static String DICT_ROLE_ADMIN ;
    public static String DICT_ROLE_TEST ;
    public static String DICT_ROLE_FRIEND ;
    public static String DICT_ROLE_EMPLOYEE ;


    /**** 组织字典常量 ***/
    /** 分销商 **/
    public static String DICT_ORG_TYPE_FXS ;
    /** 经销商 **/
    public static String DICT_ORG_TYPE_JXS ;
    /** 总部 **/
    public static String DICT_ORG_TYPE_ZB ;
    /** 门店 **/
    public static String DICT_ORG_TYPE_MD ;


    /*********** 优惠券状态常量 *************/
    public static String COUPON_STATUS_INACTIVE;// 未激活
    public static String COUPON_STATUS_ACTIVE;//已激活
    public static String COUPON_STATUS_END;//已过期
    public static String COUPON_STATUS_INVALID;//已作废

    /*********** 优惠券来源常量 *************/
    public static String COUPON_SOURCE_PUBLIC; //公共的
    public static String COUPON_SOURCE_PRIVATE; //私有的

    /*********** 用户优惠券状态常量 *************/
    public static String PERSON_COUPON_STATUS_NOT_WRITE_OFF;// 未核销
    public static String PERSON_COUPON_STATUS_WRITE_OFF;//已核销
    public static String PERSON_COUPON_STATUS_INVALID;//已失效

    /*********** 审核状态常量 *************/
    public static String SUBMIT;//提交
    public static String UNREVIEW;//未审核
    public static String IN_REVIEW;//审核中
    public static String REVIEWED; //审核通过
    public static String REVIEW_FAILURE;//审核不通过

    /*********** 优惠券领用类型常量 *************/
    public static String COUPON_RECEIVE_TYPE_PUBLIC; //公共券
    public static String COUPON_RECEIVE_TYPE_STAFF;  //员工券

    /*********** 优惠券类型常量 *************/
    public static String COUPON_TYPE_MANJIAN; //满减
    public static String COUPON_TYPE_MANSONG; //满送
    public static String COUPON_TYPE_MANZHE; //满折
    public static String COUPON_TYPE_MEIMANJIAN; //每满减
    public static String COUPON_TYPE_MEIMANSONG; //每满送

    /*********** 审批类型 *************/
    public static String COUPON_EXAMINE ; //优惠券审批类型
    public static String EMPLOYEE_APPLY_COUPON_EXAMINE ; //员工申请优惠券审批
    public static String USER_APPLY_EXAMINE; //用户申请审核

    /*********** 优惠券申请使用类型 *************/
    public static String COUPON_USE_TYPE_MYSELF; //为自己申请
    public static String COUPON_USE_TYPE_FAMILY; //为员工申请
    public static String COUPON_USE_TYPE_FRIEND; //为朋友申请
    public static String COUPON_USE_TYPE_CUSTOMER;//为客户申请

    /*********** 积分来源 *************/
    public static String SHARING_COUPON_EXPENDITURE; //分享优惠券消费
    public static String STORE_EXPENDITURE; //门店消费

    /*********** 短信模板常量 ***********/
    /** 登录注册模板 **/
    public static String DICT_SMS_LOGIN_REGISTER;

    /*********** 用户类型常量 ***********/
    /** 后台用户 **/
    public static String DICT_USER_TYPE_BACKSTAGE;
    /** 员工用户 **/
    public static String DICT_USER_TYPE_EMPLOYEE;
    /** 鹏友汇用户 **/
    public static String DICT_USER_TYPE_FRIEND;

    /******************************  属性名称  **********************************************/
    /** 缓存默认的region **/
    public static final String DEFAULT_REGION = "default";
    /** token缓存的region **/
    public static final String TOKEN_REGION = "token";
    /** 字典缓存的region **/
    public static final String DICT_REGION = "dicts";
    /** 用户权限缓存的region **/
    public static final String USER_MENU_REGION = "user_menu";
    /** 用户权限codes缓存的region **/
    public static final String USER_MENU_CODE_REGION = "user_menu_code";
    /** websocket缓存的region **/
    public static final String WEBSOCKET_REGION="websocket";
    /** 短信验证码缓存的region **/
    public static final String SMS_VERIFICATION_CODE_REGION="verification_code";
    /** 短信发送时间缓存的region **/
    public static final String SMS_VERIFICATION_CODE_TIME_REGION="verification_code_time";
    /** 微信accestoken缓存 的region **/
    public static final String WX_ACCESSTOKEN="wx_accesstoken";
    /** 微信accestoken缓存过期时间 的region **/
    public static final String WX_ACCESSTOKEN_TIMEOUT="wx_accesstoken_timeout";

    /** 主从数据库开启/关闭。true表示主从，false表示单数据库 **/
    public static final String DB_MASTER_SLAVE_ENABLE = "db.jdbc.masterSlave.enable";
    /** 超级管理员id **/
    public static final long ADMINISTRATOR_ID=1;
    /** 重设密码时默认的密码 **/
    public static final String DEFAULT_PASSWORD="default_password";
    /** 无需token验证的url **/
    public static final String SECURITY_TOKEN_EXCLUDE = "security.token.exclude";
    /** 当前用户ID名称 **/
    public static final String SECURITY_TOKEN_USERID = "currentUserId";
    /** 当前用户username名称 **/
    public static final String SECURITY_TOKEN_USERNAME = "currentUserName";

    /** 请求头部token key **/
    public static final String HEADER_TOKEN = "token";
    /** 请求头部权限key **/
    public static final String HEADER_PERMISSIONS = "permissions";
    /** 请求头部角色key **/
    public static final String HEADER_ROLES = "roles";

    /** modif类型为 新增 **/
    public static final String MODIFY_SAVE="新增";
    /** modif类型为 更新 **/
    public static final String MODIFY_UPDATE="更新";
    /** modif类型为 删除 **/
    public static final String MODIFY_DELETE="删除";
    /** modif类型为 逻辑删除 **/
    public static final String MODIFY_DELETE_LOGIC="逻辑删除";

    /** websocket 消息广播url **/
    public static final String WEBSOCKET_URL_SENDPUBLIC="/sendPublicMsg";
    /** websocket 广播消息订阅url **/
    public static final String WEBSOCKET_URL_TOPIC_PUBLIC="/topic/public";
    /** websocket 点对点消息发送url **/
    public static final String WEBSOCKET_URL_SEND="/sendMsg";
    /** websocket 点对点消息订阅url **/
    public static final String WEBSOCKET_URL_QUEUE_MESSAGE="/queue/message";
    /** websocket 点对点消息发送出错处理订阅url **/
    public static final String WEBSOCKET_URL_QUEUE_ERROR="/queue/errors";

    public static final String USER_SCORE_TORAL ="USER_SCORE_TORAL_";

    public static final String COUPON_SCORE_TORAL_ ="COUPON_SCORE_TORAL_";

    public  static final  String LOWRANK = "最低职级" ;

    public static final  String LOWRANK_ID = "58417025914912768" ;

    /** 分页属性 页码*/
    public static final String PAGE_NO = "pageNo";
    /** 分页属性 页面大小*/
    public static final String PAGE_SIZE = "pageSize";
    /** 分页属性 排序*/
    public static final String ORDER_BY="orderBy";

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";
}
