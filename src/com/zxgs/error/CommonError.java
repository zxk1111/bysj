/**
 * 
 */
package com.zxgs.error;

/**
 * @Desc //TODO 添加描述
 * @author zxk
 *
 * @Date 2019年3月26日 上午10:23:38
 */
public interface CommonError {
	public String getErrCode();
    
	public String getErrMsg();
    
	public CommonError setErrMsg(String errMsg);
}
