package com.zxgs.dao;

import com.zxgs.entity.SubjectInfo;
import com.zxgs.entity.SubjectInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubjectInfoMapper {
    int countByExample(SubjectInfoExample example);

    int deleteByExample(SubjectInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubjectInfo record);

    int insertSelective(SubjectInfo record);

    List<SubjectInfo> selectByExampleWithBLOBs(SubjectInfoExample example);

    List<SubjectInfo> selectByExample(SubjectInfoExample example);

    SubjectInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubjectInfo record, @Param("example") SubjectInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") SubjectInfo record, @Param("example") SubjectInfoExample example);

    int updateByExample(@Param("record") SubjectInfo record, @Param("example") SubjectInfoExample example);

    int updateByPrimaryKeySelective(SubjectInfo record);

    int updateByPrimaryKeyWithBLOBs(SubjectInfo record);

    int updateByPrimaryKey(SubjectInfo record);
    

    /**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月8日 上午11:04:39
	 * @return
	 */
	List<SubjectInfo> selectAll();

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月10日 上午9:25:03
	 * @param parseInt
	 * @return 
	 */
	List<SubjectInfo> selectLeftSubject(Integer studenId);
}