package com.zyan.tordata.dao;

import com.zyan.tordata.domain.OnionPerfLatencies;
import com.zyan.tordata.domain.OnionPerfThroughput;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface OnionPerfThroughputDao {

    @Select("select * from onionperf_throughput where server = #{server} and perf_date >= #{start} and perf_date <= #{end}")
    @Results({
            @Result(property = "date", column = "perf_date")
    })
    public List<OnionPerfThroughput> listOnionPerfLatenciesByCondition(@Param("server") String server, @Param("start") String start, @Param("end") String end);

    @Select("select max(perf_date) from onionperf_throughput")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into onionperf_throughput(perf_date, source, server, low,  q1, md, q3, high) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.source}, #{item.server},#{item.low},#{item.q1},#{item.md},#{item.q3}, #{item.high})"+
            "</foreach> " +
            "</script>"})
    public Integer insertPerfThroughput(List<OnionPerfThroughput> list);
}
