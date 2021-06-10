package com.zyan.tordata.dao;

import com.zyan.tordata.domain.Assessment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangyan
 * @date 2021/6/8 下午7:44
 */

@Mapper
@Repository
public interface AssessmentDao {
    @Select("select * from assessment where (node like #{condition} or type like #{condition} or description like #{condition})")
    List<Assessment> listAssessment(@Param("condition") String condition);
}
