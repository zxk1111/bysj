/**
 * 
 */
package com.zxgs.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.zxgs.entity.UserInfo;
import com.zxgs.util.PlClientBaseModel;

/**
 * @Desc //TODO 用户服务层
 * @author zxk
 *
 * @Date 2019年5月5日 上午9:59:59
 */
public interface IUserService {

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 上午10:54:03
	 * @param userInfo
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	Integer registe(UserInfo userInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException;

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 上午11:37:44
	 * @param name
	 * @param password
	 */
	UserInfo login(String userName, String password);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:20:41
	 * @param result
	 * @param requestPage
	 * @param requestNumber
	 * @return
	 */
	PlClientBaseModel getPage(PlClientBaseModel result, Integer requestPage, Integer requestNumber);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:31:32
	 * @param id
	 */
	UserInfo getUser(String id);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:38:40
	 * @param id
	 * @return
	 */
	Integer delete(String id);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:50:24
	 * @param id
	 * @return
	 */
	Integer update(UserInfo userInfo);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午3:17:19
	 * @param telphone
	 * @param otpCode
	 * @return
	 */
	UserInfo loginByTelphone(String telphone, String otpCode);
	

}
