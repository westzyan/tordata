package com.zyan.tordata.dao;


import com.zyan.tordata.domain.RelaysByRelayFlag;
import com.zyan.tordata.domain.RelaysByTorVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface RelaysByTorVersionDao {
    @Select("select id, servers_date, version,relays from relays_by_tor_version where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "relays", column = "relays"),
            @Result(property = "bridges", column = "bridges")
    })
    List<RelaysByTorVersion> listVersionAndRelays(@Param("start") String start, @Param("end") String end);

    @Select("select * from relays_by_tor_version where version = #{version} ")
    @Results({
            @Result(property = "date", column = "servers_date")
    })
    List<RelaysByRelayFlag> listByVersion(@Param("version") String version);

    @Select("select max(servers_date) from relays_by_tor_version")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into relays_by_tor_version(servers_date, version, relays) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.version},#{item.relays})" +
            "</foreach> " +
            "</script>"})
    Integer insertVersionAndRelays(List<RelaysByTorVersion> list);
}
