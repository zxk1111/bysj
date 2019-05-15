package com.zxgs.util;

import com.zxgs.common.SysCodeMsg;

/**
 * 客户端访问返回的基础类
 *
 */
public class ReturnData {

    /**
     * 返回代码
     */
    private Integer code = null;
    /**
     * 返回消息
     */
    private String message = null;
    /**
     * 返回的数据
     */
    private Object data = null;

    public ReturnData() {
        this.code = SysCodeMsg.SUCCESS.getCode();
        this.message = SysCodeMsg.SUCCESS.getDescribe();
        this.data = null;
    }

    public ReturnData(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ReturnData(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ReturnData(Object data) {
        this.code = SysCodeMsg.SUCCESS.getCode();
        this.message = SysCodeMsg.SUCCESS.getDescribe();
        this.data = data;
    }

    public static ReturnData ok(Object data) {
        return new ReturnData(data);
    }

    public static ReturnData ok() {
        return new ReturnData(null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将返回数据设置为系统错误状态
     */
    public void change2SysError() {
        this.code = SysCodeMsg.SYS_ERROR.getCode();
        this.message = SysCodeMsg.SYS_ERROR.getDescribe();
    }
    
    public void change2Fail() {
        this.code = SysCodeMsg.FAIL.getCode();
        this.message = SysCodeMsg.FAIL.getDescribe();
    }

}
