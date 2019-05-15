package com.zxgs.dao;

import com.zxgs.entity.SubjectCase;
import com.zxgs.entity.SubjectCaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubjectCaseMapper {
    int countByExample(SubjectCaseExample example);

    int deleteByExample(SubjectCaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubjectCase record);

    int insertSelective(SubjectCase record);

    List<SubjectCase> selectByExample(SubjectCaseExample example);

    SubjectCase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubjectCase record, @Param("example") SubjectCaseExample example);

    int updateByExample(@Param("record") SubjectCase record, @Param("example") SubjectCaseExample example);

    int updateByPrimaryKeySelective(SubjectCase record);

    int updateByPrimaryKey(SubjectCase record);
}