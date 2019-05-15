/**
 * 
 */
package com.zxgs.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxgs.common.Constant;
import com.zxgs.entity.UserInfo;
import com.zxgs.error.BusinessException;
import com.zxgs.error.EmBusinessError;
import com.zxgs.js.JSBaseParameter;
import com.zxgs.service.IUserService;
import com.zxgs.tools.ToolsImpl;
import com.zxgs.util.PlClientBaseModel;

/**
 * @Desc //TODO 用户控制器
 * @author zxk
 *
 * @Date 2019年5月5日 上午9:56:30
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/sys/user")
public class UserController extends WebBaseController<JSBaseParameter>{

	@Autowired
	private IUserService iUserService;

	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * //TODO 注册
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午3:45:12
	 * @param request
	 * @param response
	 * @param userInfo
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response,
			UserInfo userInfo) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result = new PlClientBaseModel();
		//判空
		if(StringUtils.isBlank(userInfo.getOtpCode()) ||
				StringUtils.isBlank(userInfo.getPassword()) ||
				StringUtils.isBlank(userInfo.getTelphone()) ||
				StringUtils.isBlank(userInfo.getUserName()) ||
				userInfo.getGender() == null ||
				userInfo.getRole() == null){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else if(!userInfo.getOtpCode().equals(request.getSession().getAttribute(userInfo.getTelphone()))){
			//如果验证码不对
			result.setCode(EmBusinessError.OTPCODE_IS_WRONG.getErrCode());
			result.setData(EmBusinessError.OTPCODE_IS_WRONG.getErrMsg());
			result.setMessage("error");
		}else{
			String salt = new ToolsImpl().getRandomStr();
			userInfo.setSalt(salt);
			userInfo.setHpUrl("D:/hp/"+new ToolsImpl().getOneRandomNumber()+".jpg");
			Integer temp = iUserService.registe(userInfo);
			if(temp == -1){//插入失败
				result.setCode(EmBusinessError.REGISTER_ERROR.getErrCode());
				result.setData(EmBusinessError.REGISTER_ERROR.getErrMsg());
				result.setMessage("error");
			}else if(temp == 2){//用户账号已经存在
				result.setCode(EmBusinessError.USERACCOUNT_IS_EXIST.getErrCode());
				result.setData(EmBusinessError.USERACCOUNT_IS_EXIST.getErrMsg());
				result.setMessage("error");	
			}else if(temp == 3){//手机号已经存在
				result.setCode(EmBusinessError.TELPHONE_IS_INLEGAL.getErrCode());
				result.setData(EmBusinessError.TELPHONE_IS_INLEGAL.getErrMsg());
				result.setMessage("error");	
			}
		}	
		//信息返回给前台
		writeJSON(response, result);
	}

	/**
	 * 
	 * //TODO 通过用户名和密码登录
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午3:44:43
	 * @param request
	 * @param response
	 * @param userName
	 * @param password
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 * @throws ParseException 
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userName")String userName,
            @RequestParam(value = "password")String password) throws IOException, NoSuchAlgorithmException, BusinessException, ParseException {
		PlClientBaseModel result=new PlClientBaseModel();
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			UserInfo userInfo = iUserService.login(userName, password);
			if(userInfo == null){//数据不存在
				result.setCode(EmBusinessError.DATA_IS_NOT_EXIST.getErrCode());
				result.setData(EmBusinessError.DATA_IS_NOT_EXIST.getErrMsg());
				result.setMessage("error");
			}else if((new ToolsImpl().encodeByMd5(password)+userInfo.getSalt())
					.equals(userInfo.getPassword()+userInfo.getSalt())){
				//登录成功
				if(userInfo.getLastTime() != null){//不是第一次登陆
					userInfo.setLoginTime(userInfo.getLoginTime()+1);
				}
				else{
					userInfo.setLoginTime(1);//更新登录多少次
				}
				userInfo.setLastTime(new ToolsImpl().getCurrentTime());//更新最近一次登录时间
				iUserService.update(userInfo);//更新登录次数
				request.getSession().setAttribute(Constant.SESSION_SYS_USER, userInfo);
				System.out.println("用户为:"+userInfo.getUserName());
				result.setData(userInfo);
			}else{//登陆失败
				result.setCode(EmBusinessError.LOGIN_ERROR.getErrCode());
				result.setData(EmBusinessError.LOGIN_ERROR.getErrMsg());
				result.setMessage("error");
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 获取动态验证码
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午3:44:17
	 * @param request
	 * @param response
	 * @param telphone
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getOtpCode")
	public void getOtpCode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "telphone")String telphone) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		ToolsImpl toolsImpl = new ToolsImpl();
		if(StringUtils.isBlank(telphone)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			//获得随机验证码并且存入session
			String otpCode = toolsImpl.getRandomNumber();
			request.getSession().setAttribute(telphone, otpCode);
			result.setData(otpCode);
			System.out.println("otpCode:"+otpCode);
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 通过电话号码登录
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午3:43:55
	 * @param request
	 * @param response
	 * @param telphone
	 * @param otpCode
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 * @throws ParseException 
	 */
	@RequestMapping("/loginByTelphone")
	public void loginByTelphone(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "telphone")String telphone,
            @RequestParam(value = "otpCode")String otpCode) throws IOException, NoSuchAlgorithmException, BusinessException, ParseException {
		PlClientBaseModel result=new PlClientBaseModel();
		if(StringUtils.isBlank(telphone) || StringUtils.isBlank(otpCode)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			UserInfo userInfo = iUserService.loginByTelphone(telphone, otpCode);
			if(userInfo == null){//数据不存在
				result.setCode(EmBusinessError.DATA_IS_NOT_EXIST.getErrCode());
				result.setData(EmBusinessError.DATA_IS_NOT_EXIST.getErrMsg());
				result.setMessage("error");
			}else if(otpCode.equals(request.getSession().getAttribute("otpCode"))){
				//登录成功
				if(userInfo.getLastTime() != null){//不是第一次登陆
					userInfo.setLoginTime(userInfo.getLoginTime()+1);
				}
				else{
					userInfo.setLoginTime(1);//更新登录多少次
				}
				userInfo.setLastTime(new ToolsImpl().getCurrentTime());//更新最近一次登录时间
				iUserService.update(userInfo);//更新登录次数
				request.getSession().setAttribute(Constant.SESSION_SYS_USER, userInfo);
				System.out.println("用户为:"+userInfo.getUserName());
			}else{//登陆失败
				result.setCode(EmBusinessError.LOGIN_ERROR.getErrCode());
				result.setData(EmBusinessError.LOGIN_ERROR.getErrMsg());
				result.setMessage("error");
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 分页
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:30:33
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pageSize
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getPage")
	public void getPage(HttpServletRequest request, HttpServletResponse response,
			String pageIndex,String pageSize) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		
		if(StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageIndex)
				|| 0 == Integer.parseInt(pageSize) || 0 == Integer.parseInt(pageIndex)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			result = iUserService.getPage(result, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 获取用户信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:54:51
	 * @param request
	 * @param response
	 * @param id
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getUser")
	public void getUser(HttpServletRequest request, HttpServletResponse response, 
			String id) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		if(StringUtils.isBlank(id)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			UserInfo userInfo = iUserService.getUser(id);
			if(userInfo == null){//数据不存在
				result.setCode(EmBusinessError.DATA_IS_NOT_EXIST.getErrCode());
				result.setData(EmBusinessError.DATA_IS_NOT_EXIST.getErrMsg());
				result.setMessage("error");
			}else{
				result.setData(userInfo);
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 删除用户
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午3:03:24
	 * @param request
	 * @param response
	 * @param id
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, 
			String id) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		if(StringUtils.isBlank(id)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			Integer temp = iUserService.delete(id);
			if(temp == -1){//数据不存在
				result.setCode(EmBusinessError.DATA_IS_NOT_EXIST.getErrCode());
				result.setData(EmBusinessError.DATA_IS_NOT_EXIST.getErrMsg());
				result.setMessage("error");
			}else if(temp == 2){//数据存在但删除不成功
				result.setCode(EmBusinessError.DELETE_FAILURE.getErrCode());
				result.setData(EmBusinessError.DELETE_FAILURE.getErrMsg());
				result.setMessage("error");
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 更新
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午5:47:00
	 * @param request
	 * @param response
	 * @param userInfo
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, 
			UserInfo userInfo) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		
		if(StringUtils.isBlank(userInfo.getOtpCode()) &&
				StringUtils.isBlank(userInfo.getPassword()) &&
				StringUtils.isBlank(userInfo.getTelphone()) &&
				StringUtils.isBlank(userInfo.getUserName()) &&
				StringUtils.isBlank(userInfo.getHpUrl()) &&
				userInfo.getGender() == null &&
				userInfo.getRole() == null){//没有更新的属性
			result.setCode(EmBusinessError.NO_UPDATE_OPTION.getErrCode());
			result.setData(EmBusinessError.NO_UPDATE_OPTION.getErrMsg());
			result.setMessage("error");
		}else if(userInfo.getId() == null){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			Integer temp = iUserService.update(userInfo);
			if(temp == -1){//更新失败
				result.setCode(EmBusinessError.UPDAT_FAILURE.getErrCode());
				result.setData(EmBusinessError.UPDAT_FAILURE.getErrMsg());
				result.setMessage("error");
			}else if(temp == 2){//数据不存在
				result.setCode(EmBusinessError.DATA_IS_NOT_EXIST.getErrCode());
				result.setData(EmBusinessError.DATA_IS_NOT_EXIST.getErrMsg());
				result.setMessage("error");
			}else if(temp == 3){//用户名已经存在
				result.setCode(EmBusinessError.USERACCOUNT_IS_EXIST.getErrCode());
				result.setData(EmBusinessError.USERACCOUNT_IS_EXIST.getErrMsg());
				result.setMessage("error");
			}else{
				//更新成功，则更新session
				request.getSession().setAttribute(Constant.SESSION_SYS_USER, userInfo);
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	
	
	/**
	 * 
	 * //TODO 注销
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月5日 下午6:08:26
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/loginOut")
	public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		if(StringUtils.isNotBlank(Constant.SESSION_SYS_USER)){
			//删除session，注销当前用户
			request.getSession().removeAttribute(Constant.SESSION_SYS_USER);
		}else{
			result.setCode(EmBusinessError.SESSION_IS_NULL.getErrCode());
			result.setData(EmBusinessError.SESSION_IS_NULL.getErrMsg());
			result.setMessage("error");
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
}
