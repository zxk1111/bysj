/**
 * 
 */
package com.zxgs.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

/**
 * @Desc //TODO 添加描述
 * @author zxk
 *
 * @Date 2019年3月26日 上午10:45:19
 */
public interface ITools {
	String getRandomNumber();

	/**
	 * @Desc
	 * @auther zxk
	 * @Date 2019年3月26日 上午11:06:33
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException;

	/**
	 * @Desc
	 * @auther zxk
	 * @Date 2019年3月26日 上午11:06:43
	 * @param str
	 * @return
	 * @throws IOException
	 */
	String decodeByMd5(String str) throws IOException;
	


	Date getCurrentTime() throws ParseException;

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年4月1日 下午1:20:16
	 * @param fileName
	 * @return
	 */
	boolean delele(String fileName);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月8日 下午2:42:03
	 * @return
	 */
	String getOneRandomNumber();

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月14日 下午12:00:33
	 * @param code
	 * @return
	 */
	Boolean compiler(String path,String url);


	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月14日 下午2:54:00
	 * @param path
	 * @param conent
	 */
	void writeToFile(String path, String conent);







}
