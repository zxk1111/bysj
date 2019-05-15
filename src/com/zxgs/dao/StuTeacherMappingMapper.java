package com.zxgs.dao;

import com.zxgs.entity.StuTeacherMapping;
import com.zxgs.entity.StuTeacherMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuTeacherMappingMapper {
    int countByExample(StuTeacherMappingExample example);

    int deleteByExample(StuTeacherMappingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StuTeacherMapping record);

    int insertSelective(StuTeacherMapping record);

    List<StuTeacherMapping> selectByExample(StuTeacherMappingExample example);

    StuTeacherMapping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StuTeacherMapping record, @Param("example") StuTeacherMappingExample example);

    int updateByExample(@Param("record") StuTeacherMapping record, @Param("example") StuTeacherMappingExample example);

    int updateByPrimaryKeySelective(StuTeacherMapping record);

    int updateByPrimaryKey(StuTeacherMapping record);
}