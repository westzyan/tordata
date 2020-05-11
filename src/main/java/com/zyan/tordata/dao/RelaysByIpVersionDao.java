package com.zyan.tordata.dao;

import com.zyan.tordata.domain.RelaysAndBridges;
import com.zyan.tordata.domain.RelaysByIpVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface RelaysByIpVersionDao {
    @Select("select id, servers_date, total,announced,reachable,exiting from relays_by_ip_version where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "total", column = "total"),
            @Result(property = "announced", column = "announced"),
            @Result(property = "reachable", column = "reachable"),
            @Result(property = "exiting", column = "exiting")
    })
    List<RelaysByIpVersion> listRelaysIpVersion(@Param("start") String start, @Param("end") String end);

    @Select("select max(servers_date) from relays_by_ip_version")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into relays_by_ip_version(servers_date, total,announced,reachable,exiting) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.total},#{item.announced},#{item.reachable},#{item.exiting})" +
            "</foreach> " +
            "</script>"})
    Integer insertRelaysIpVersion(List<RelaysByIpVersion> list);
}
