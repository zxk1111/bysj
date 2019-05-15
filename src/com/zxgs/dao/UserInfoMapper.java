package com.zxgs.dao;

import com.zxgs.entity.UserInfo;
import com.zxgs.entity.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {
    int countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月8日 下午3:00:08
	 * @return
	 */
	List<UserInfo> selectAll();

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 下午5:27:47
	 * @param id
	 * @return 
	 */
	List<UserInfo> selectLeftTeacher(Integer id);
}