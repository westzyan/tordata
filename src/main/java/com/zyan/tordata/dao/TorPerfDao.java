package com.zyan.tordata.dao;

import com.zyan.tordata.domain.BridgeDBTransport;
import com.zyan.tordata.domain.TorPerf;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface TorPerfDao {

    @Select("select * from torperf where filesize = #{filesize} and server = #{server} and perf_date >= #{start} and perf_date <= #{end}")
    @Results({
            @Result(property = "date", column = "perf_date")
    })
    public List<TorPerf> listAllUserByCondition(@Param("filesize") int filesize, @Param("server") String server, @Param("start")String start, @Param("end")String end);

    @Select("select max(perf_date) from torperf")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into torperf(perf_date, filesize, source, server, q1, md, q3) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.filesize},#{item.source}, #{item.server},#{item.q1},#{item.md},#{item.q3})"+
            "</foreach> " +
            "</script>"})
    public Integer insertPerf(List<TorPerf> list);
}
