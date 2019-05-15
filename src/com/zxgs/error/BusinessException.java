package com.zxgs.error;


/**
 * 
 * @Desc //TODO 添加描述
 * @author zxk
 * 包装器业务异常类实现(专门的设计模式)
 * @Date 2019年3月26日 上午10:24:42
 */

public class BusinessException extends Exception implements CommonError {
    //强关联一个对应的CommonError，这里的commonError是EmBusinessError类
    private CommonError commonError;

    //直接接受EmBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError){
        //对应的Exception自身会有初始化的机制
        super();
        this.commonError = commonError;
    }

    //接受自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        //通过二次改写msg
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public String getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
