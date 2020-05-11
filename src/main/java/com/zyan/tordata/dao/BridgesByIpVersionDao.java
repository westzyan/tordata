package com.zyan.tordata.dao;

import com.zyan.tordata.domain.BridgesByIpVersion;
import com.zyan.tordata.domain.RelaysByIpVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BridgesByIpVersionDao {
    @Select("select id, servers_date, total,announced from bridges_by_ip_version where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "total", column = "total"),
            @Result(property = "announced", column = "announced")
    })
    List<BridgesByIpVersion> listBridgesIpVersion(@Param("start") String start, @Param("end") String end);

    @Select("select max(servers_date) from bridges_by_ip_version")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into bridges_by_ip_version(servers_date,total,announced) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.total},#{item.announced})" +
            "</foreach> " +
            "</script>"})
    Integer insertBridgesIpVersion(List<BridgesByIpVersion> list);
}
