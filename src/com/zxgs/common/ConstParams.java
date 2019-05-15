package com.zxgs.common;

public class ConstParams {
	/***
	 * 附近停车场查询范围,单位米
	 */
	public static final int parkinglot_near_distance = 5000;
	/***
	 * 系统Android平台类型
	 */
	public static final int SYSTEM_ANDROID = 1;
	/***
	 * 系统IOS平台类型
	 */
	public static final int SYSTEM_IOS = 2;
	/***
	 * 支付宝支付类型
	 */
	public static final Byte PAYTYPE_ALIPAY = 1;
	/***
	 * 微信支付类型
	 */
	public static final Byte PAYTYPE_WX = 2;
	/***
	 * 微信公众号支付类型
	 */
	public static final Byte PAYTYPE_WXGZH = 3;
	/***
	 * 停车场手动支付类型
	 */
	public static final Byte PAYTYPE_PARKINGLOT = 4;
	/***
	 * 优惠券支付类型
	 */
	public static final Byte PAYTYPE_COUPON = 5;
	/**
	 * 默认页码
	 */
	public static final int initPageno=1;
	/**
	 * 默认每页显示条数
	 */
	public static final int initPagesize=15;
	
	/**
	 * 注册验证码类型
	 */
	public static final int registCode=1;
	/**
	 * 重置密码验证码类型
	 */
	public static final int resetPasswdCode=2;
	
	/**
	 * 验证码过期时间,单位秒
	 */
	public static final int OUTDATE_TIME=5*60;
}
