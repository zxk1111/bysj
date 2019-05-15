package com.zxgs.error;


/**
 * 
 * @Desc //TODO 枚举类型的错误类
 * @author zxk
 * 
 * @Date 2019年3月26日 上午10:26:06
 */
public enum EmBusinessError implements CommonError{
    //通用错误类型1000开头
    PARAMETER_IS_NULL("10001","参数为空！"),
    REGISTER_ERROR("10002","注册失败！"),
    NUMBER_LESS_THAN_REQUEST("10003","请求数量大于总数量！"),
    REQUEST_PAGE_MORE_BIGGER("10004","请求页数过大！"),
    DATA_IS_NOT_EXIST("10005","数据不存在！"),
    DELETE_FAILURE("10006","删除失败！"),
    UPDAT_FAILURE("10007","更新失败！"),
    
    
    //用户错误类型2000开头
    LOGIN_ERROR("20001","账户或密码错误！"), 
    SESSION_IS_NULL("20002","用户未登录！"),
	USERACCOUNT_IS_EXIST("20003","用户账号已经存在！"),
	NO_UPDATE_OPTION("20004","没有更新的属性！"),
	OTPCODE_IS_WRONG("20005","验证码错误！"),
	TELPHONE_IS_INLEGAL("20006","手机号已经被注册！"),
	
	
	 //老师错误类型3000开头
	TOTOL_SCORE_WRONG("30001","总分相加不为100！"),
	SUBJECT_INSERT_FAILURE("30002","题目添加失败！"),
	TEACHER_NOT_PUBLISH_SUBJECT("30003","老师没有发布过题目！"),
	SUBJECT_PUBLISHED("30004","该老师发布题目名字重复！"),
	
    
	
	

	 //学生错误类型4000开头
	RUN_FAIL("40001","编译成功但运行不成功！"),
	COMPILER_FAIL("40002","编译失败！")
	
   ;

    private EmBusinessError(String errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private String errCode;
    private String errMsg;
    @Override
    public String getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    //通用错误（可能邮箱没传，可能参数没传）
    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
