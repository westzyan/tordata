package com.zyan.tordata.dao;

import com.zyan.tordata.domain.RelaysByRelayFlag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface RelaysByRelayFlagDao {
    @Select("select id, servers_date, flag,relays from relays_by_relay_flag where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "flag", column = "flag"),
            @Result(property = "relays", column = "relays")
    })
    List<RelaysByRelayFlag> listRelaysAndFlag(@Param("start") String start, @Param("end") String end);

    @Select("select * from relays_by_relay_flag where flag = #{flag} ")
    @Results({
            @Result(property = "date", column = "servers_date")
    })
    List<RelaysByRelayFlag> listByFlag(@Param("flag") String flag);

    @Select("select max(servers_date) from relays_by_relay_flag")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into relays_by_relay_flag(servers_date, flag ,relays) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.flag},#{item.relays})" +
            "</foreach> " +
            "</script>"})
    Integer insertRelaysAndFlag(List<RelaysByRelayFlag> list);
}
