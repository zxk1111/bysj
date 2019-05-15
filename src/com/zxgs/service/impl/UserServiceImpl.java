/**
 * 
 */
package com.zxgs.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxgs.dao.UserInfoMapper;
import com.zxgs.entity.ObjectReturnMessage;
import com.zxgs.entity.UserInfo;
import com.zxgs.entity.UserInfoExample;
import com.zxgs.error.EmBusinessError;
import com.zxgs.service.IUserService;
import com.zxgs.tools.ToolsImpl;
import com.zxgs.util.PlClientBaseModel;

/**
 * @Desc //TODO 实现服务层
 * @author zxk
 *
 * @Date 2019年5月5日 上午10:03:23
 */
@Service("IUserService")
public class UserServiceImpl implements IUserService{
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月5日 上午10:03:23
	 */
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* 
	 * @auther zxk
	 * @2019年5月5日 上午10:57:13
	 * @param userInfo
	 * @return
	 */
	@Override
	public Integer registe(UserInfo userInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.or().andUserNameEqualTo(userInfo.getUserName());
		List<UserInfo> list = this.userInfoMapper.selectByExample(userInfoExample);//根据需求查找数据
		if(list.isEmpty()){//用户账号合法
			//密码加密md5+salt
			userInfoExample.or().andTelphoneEqualTo(userInfo.getTelphone());
			List<UserInfo> tempList = this.userInfoMapper.selectByExample(userInfoExample);//根据需求查找数据
			if(tempList.isEmpty()){
				userInfo.setPassword(new ToolsImpl().encodeByMd5(userInfo.getPassword()));
				if(userInfoMapper.insert(userInfo) > 0){
					return 1;//插入成功
				}else{
					return -1;//插入失败
				}
			}else{
				return 3;//用户手机号不合法
			}
		}else {
			return 2;//用户账号不合法
		}
	}

	/* 
	 * @auther zxk
	 * @2019年5月5日 上午11:38:03
	 * @param name
	 * @param password
	 * @return
	 */
	@Override
	public UserInfo login(String userName, String password) {
		// TODO Auto-generated method stub
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.or().andUserNameEqualTo(userName);
		List<UserInfo> list = this.userInfoMapper.selectByExample(userInfoExample);//根据需求查找数据
		return list.isEmpty() ? null : list.get(0);
	}
	
	/*
	 * 
	 * @auther zxk
	 * @2019年5月5日 下午5:27:39
	 * @param result
	 * @param requestPage
	 * @param requestNumber
	 * @return
	 */
	@Override
	public PlClientBaseModel getPage(PlClientBaseModel result, Integer requestPage, Integer requestNumber) {
		// TODO Auto-generated method stub
		List<UserInfo> requstList = new ArrayList<UserInfo>();//返回要求的数量
			List<UserInfo> list = userInfoMapper.selectAll();
			ObjectReturnMessage objectReturnMessage = new ObjectReturnMessage();
			if(list.size()<requestNumber){//请求数量大于总数量
				objectReturnMessage = new ObjectReturnMessage(list, EmBusinessError.NUMBER_LESS_THAN_REQUEST.getErrMsg(),1,list.size());
			}else if(requestNumber*requestPage>list.size()){//请求的页数过大
				int temp = 0;//总共多少页
				if(list.size()%requestNumber != 0)
					temp = list.size()/requestNumber+1;
				else
					temp = list.size()/requestNumber;
				if(temp == requestPage){
					//请求的页数与最后一页相等
					for(int i=0;i<list.size()%requestNumber;i++){
						requstList.add(list.get((temp-1)*requestNumber+i));
						objectReturnMessage = new ObjectReturnMessage(requstList,EmBusinessError.REQUEST_PAGE_MORE_BIGGER.getErrMsg(),1,list.size());
					}
				}else{
					//请求的页数不是最后一页,1页返回所有信息
					objectReturnMessage = new ObjectReturnMessage(list,EmBusinessError.REQUEST_PAGE_MORE_BIGGER.getErrMsg(),1,list.size());
				}				
				result.setMessage("success");
			}else{//有数据
				if((list.size()-(requestPage-1)*requestNumber)<requestNumber)//剩下的法规数小于需要的法规数
					for(int i=0;i<(list.size()-(requestPage-1)*requestNumber);i++)
						requstList.add(list.get((requestPage-1)*requestNumber+i));
				else{//剩下的数据大于等于请求数
					int temp = 0;//总共多少页
					for(int i=0;i<requestNumber;i++)
						requstList.add(list.get((requestPage-1)*requestNumber+i));
					if(list.size()%requestNumber != 0)
						temp = list.size()/requestNumber+1;
					else
						temp = list.size()/requestNumber;
					objectReturnMessage = new ObjectReturnMessage(requstList, "查询成功",temp,list.size());
				}
			}
		result.setData(objectReturnMessage);
		return result;
	}

	/* 
	 * @auther zxk
	 * @2019年5月5日 下午5:31:45
	 * @param id
	 * @return
	 */
	@Override
	public UserInfo getUser(String id) {
		// TODO Auto-generated method stub
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoMapper.selectByPrimaryKey(Integer.parseInt(id));
		if(userInfo != null){
			return userInfo;
		}else{
			return null;
		}
	}

	/* 
	 * @auther zxk
	 * @2019年5月5日 下午5:38:52
	 * @param id
	 * @return
	 */
	@Override
	public Integer delete(String id) {
		// TODO Auto-generated method stub
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoMapper.selectByPrimaryKey(Integer.parseInt(id));
		if(userInfo != null){
			if(userInfoMapper.deleteByPrimaryKey(Integer.parseInt(id)) > 0){
				return 1;//存在且删除成功
			}else{
				return 2;//存在但删除不成功
			}
		}else{//不存在该数据
			return -1;
		}
	}

	/* 
	 * @auther zxk
	 * @2019年5月5日 下午5:50:43
	 * @param id
	 * @return
	 */
	@Override
	public Integer update(UserInfo userInfo) {
		// TODO Auto-generated method stub
		if(userInfoMapper.selectByPrimaryKey(userInfo.getId()) != null){//更新的数据存在
			if(StringUtils.isNotBlank(userInfo.getUserName())){//如果更新的属性有用户名
				UserInfoExample userInfoExample = new UserInfoExample();
				userInfoExample.or().andUserNameEqualTo(userInfo.getUserName());
				List<UserInfo> list = this.userInfoMapper.selectByExample(userInfoExample);
				if(list.isEmpty() && userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0){
					return 1;//更新成功
				}else if(list.get(0).getId() != userInfo.getId()){//如果用户账号已经存在
					return 3;//用户账号已经存在
				}else if(list.get(0).getId() == userInfo.getId() && 
						userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0){//如果用户账号已经存在
					return 1;//用户账号已经存在
				}else{
					return -1;//更新失败
				}
			}else{//更新的属性没有用户账号
				if(userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0){
					return 1;//更新成功
				}else{
					return -1;//更新失败
				}
			}
		}else{
			return 2;//数据不存在
		}
		
	}

	/* 
	 * @auther zxk
	 * @2019年5月7日 下午3:17:27
	 * @param telphone
	 * @param otpCode
	 * @return
	 */
	@Override
	public UserInfo loginByTelphone(String telphone, String otpCode) {
		// TODO Auto-generated method stub
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.or().andTelphoneEqualTo(telphone);
		List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
		return list.isEmpty()? null : list.get(0);
	}

}
