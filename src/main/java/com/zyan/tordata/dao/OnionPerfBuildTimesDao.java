package com.zyan.tordata.dao;

import com.zyan.tordata.domain.OnionPerfBuildTimes;
import com.zyan.tordata.domain.OnionPerfLatencies;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface OnionPerfBuildTimesDao {

    @Select("select * from onionperf_buildtimes where perf_date >= #{start} and perf_date <= #{end} and source = #{source}")
    @Results({
            @Result(property = "date", column = "perf_date")
    })
    public List<OnionPerfBuildTimes> listOnionPerfLatenciesByCondition(@Param("start") String start, @Param("end") String end, @Param("source")String source);

    @Select("select max(perf_date) from onionperf_buildtimes")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into onionperf_buildtimes(perf_date, source, position, q1, md, q3) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.source}, #{item.position},#{item.q1},#{item.md},#{item.q3})"+
            "</foreach> " +
            "</script>"})
    public Integer insertPerfBuildTimes(List<OnionPerfBuildTimes> list);
}
