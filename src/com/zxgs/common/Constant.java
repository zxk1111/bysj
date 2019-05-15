package com.zxgs.common;

/**
 * 
 */
public interface Constant {

	public static final String AUTH_CODE = "AUTH_CODE";

	public static final String SESSION_USER = "SESSION_USER";

	public static final String SESSION_SYS_USER = "SESSION_SYS_USER";
	
	public static final String UPLOAD_BASE_VERSION = "version";
	
	public static final String VERSION_PACKAGE = "czgis";
	
	public static final String BASETYPE_FOLDER="serviceMap"; 	//不同类型数据文件存储位置

	public static final String UPLOAD_BASE_ADDR="upload"; 	//不同类型数据文件存储位置
	
	public static final String SEPARATOR = "/";	//路径连接符
	
	public static final String CODEPATH = "D:\\bysj\\";	//代码路径

	public static final Integer SCORE = 0;	//分数
	
	public static final String SUBJECTID = "";	//课题id
	
	public static final String TEACHERID = "";	//老师id
	
	// 推送应用apikey
	public static final String ZXGSPUSH_APIKEY="zxgspush_apikey";
	
	//移动端状态码 code -1:系统错误  1:成功  4:用户名或密码不正确  2:设备已绑定 3 :参数不正确 
	public static final String SUCCESS = "1";	// 1:成功
	
	public static final String SYSTEM_ERROR = "-1";		//-1:系统错误
	
	public static final String SESSION_TIMEOUT = "-2";		//-1:session超时
	
	public static final String MODEL_BIND = "2";
	
	public static final String PARAM_ERROE = "3";
	
	public static final String USER_NAME_ERROR = "4";
	
	
}
