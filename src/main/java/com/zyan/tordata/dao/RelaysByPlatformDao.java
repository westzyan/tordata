package com.zyan.tordata.dao;

import com.zyan.tordata.domain.RelaysByPlatform;
import com.zyan.tordata.domain.RelaysByRelayFlag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface RelaysByPlatformDao {
    @Select("select id, servers_date, bsd, linux , macos, other, windows from relays_by_platform where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "bsd", column = "bsd"),
            @Result(property = "linux", column = "linux"),
            @Result(property = "macos", column = "macos"),
            @Result(property = "other", column = "other"),
            @Result(property = "windows", column = "windows")

    })
    List<RelaysByPlatform> listPlatform(@Param("start") String start, @Param("end") String end);


    @Select("select max(servers_date) from relays_by_platform")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into relays_by_platform(servers_date, bsd, linux , macos, other, windows) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.bsd},#{item.linux},#{item.macos},#{item.other},#{item.windows})" +
            "</foreach> " +
            "</script>"})
    Integer insertPlatform(List<RelaysByPlatform> list);
}
