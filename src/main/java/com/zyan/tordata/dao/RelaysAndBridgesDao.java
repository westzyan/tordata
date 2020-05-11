package com.zyan.tordata.dao;

import com.zyan.tordata.domain.RelaysAndBridges;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface RelaysAndBridgesDao {
    @Select("select id, servers_date, relays, bridges from relays_and_bridges where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "relays", column = "relays"),
            @Result(property = "bridges", column = "bridges")
    })
    List<RelaysAndBridges> listRelaysAndBridges(@Param("start") String start, @Param("end") String end);


    @Select("select max(servers_date) from relays_and_bridges")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into relays_and_bridges(servers_date, relays, bridges) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.relays},#{item.bridges})" +
            "</foreach> " +
            "</script>"})
    Integer insertRelaysAndBridges(List<RelaysAndBridges> list);
}
