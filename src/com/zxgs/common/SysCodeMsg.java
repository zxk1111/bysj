package com.zxgs.common;

/**
 * @ClassName: SysMsg
 */

public enum SysCodeMsg {
    SYS_ERROR(10000, "系统错误"),
    NOT_OPT_AUTH(10001, "无操作权限"),
    NOT_LOGIN(10002, "未登录或登录超时"),

    USER_IS_ERROR(10004, "用户名或密码错误"),

    PARAM_IS_NULL(10005, "必填参数未填"),
    PARAM_IS_ERROR(10006, "参数不符合规定"),

    USER_NOT_EXIST(10008, "用户不存在"),
    USER_IS_EXIST(10009, "用户名已存在"),
    ORG_IS_EXIST(10101, "部门名称已存在"),

    NOT_VISIT_AUTH(10102, "没有访问权限"),
    HAS_VISIT_AUTH(10103, "有访问权限"),

    USER_IS_RIGHT(10104, "合法用户"),
    USER_NOT_RIGHT(10105, "用户不合法"),
    
    AUTH_CODE_ERROR(10106, "验证码错误"),
    
    CATALOG_HAS_DATAITEM(10107, "资源目录中包含资源项，不能删除"),
    UNIQUE_ADMIN(10108, "已经有部门管理员"),
    WRONG_ORG(10109, "部门不存在或已被注销"),
    ORG_HAS_SON_ORG(10110, "部门中包含了子部门，不能删除"),
    URL_IS_ILLEGAL(10111, "访问请求不合法"),
    
    USER_IS_BIND(10112, "您已在其他设备上登录,请联系技术支持:023-67033881。"),
    EQ_IS_NOT_REG(10113, "设备未注册。"),

    RUNTIME_EXCEPTION(11000, "[服务器]运行时异常"),
    NULL_POINTER_EXCEPTION(11001, "[服务器]空值异常"),
    CLASS_CAST_EXCEPTION(11002, "[服务器]数据类型转换异常"),
    IO_EXCEPTION(11003, "[服务器]IO异常"),
    NO_SUCH_METHOD_EXCEPTION(11004, "[服务器]未知方法异常"),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(11005, "[服务器]数组越界异常"),
    SOCKET_EXCEPTION(11006, "[服务器]网络异常"),
    CONNECT_EXCEPTION(11007, "[服务器]连接异常"),
    SQL_EXCEPTION(11008, "[服务器]数据库异常"),
    
    OPERATION_FAILED(11009, "操作失败"),
    
    SYS_CHILD_IS_EXIST(11010, "子系统已存在"),
    
    SYS_CHILD_IS_ENABLE(11011, "子系统已停用"),
    FALL_USER(11012,"用户或密码错误"),

    NO_DEFAULT_ROLE_TYPE_1(11013,"未设置综合实情系统的默认角色，请设置"),
    NO_DEFAULT_ROLE_TYPE_2(11014,"未设置规划定位系统的默认角色，请设置"),
    NO_DEFAULT_ROLE_TYPE_3(11015,"未设置综合区情系统的默认角色，请设置"),
    
    WECHAT_ERROR(11016, "微信获取用户信息失败"),
    CHECK_ERRROR(11017, "只能大小写字母，数字和下划线组成，且开头不能是下划线"),
    
    FILE_TOO_LARGE(11018, "文件过大"),
    
    SUCCESS(20000, "成功"),
    FAIL(20001, "失败");

    private final Integer code;
    private final String describe;

    private SysCodeMsg(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static SysCodeMsg valueOf(Integer code) {
        for (SysCodeMsg type : SysCodeMsg.values()) {
            if (code.equals(type.code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("描述获取失败！");
    }

}
