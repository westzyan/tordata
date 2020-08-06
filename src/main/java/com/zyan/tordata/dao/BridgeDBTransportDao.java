package com.zyan.tordata.dao;

import com.zyan.tordata.domain.BridgeDBTransport;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BridgeDBTransportDao {
    @Select("select * from bridgedb_transport " +
            "where bridgedb_date > #{start} and bridgedb_date < #{end}")
    @Results({
            @Result(property = "date", column = "bridgedb_date"),
            @Result(property = "transport", column = "transport_type")
    })
    public List<BridgeDBTransport> listUserByStartAndEnd(@Param("start") String start, @Param("end") String end);

    @Select("select * from bridgedb_transport where transport = #{transport} ")
    @Results({
            @Result(property = "date", column = "bridgedb_date"),
            @Result(property = "transport", column = "transport_type")
    })
    public List<BridgeDBTransport> listAllUserByTransport(@Param("transport") String transport);

    @Select("select max(bridgedb_date) from bridgedb_transport")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into bridgedb_transport(bridgedb_date, transport_type, requests) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.transport},#{item.requests})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<BridgeDBTransport> list);
}
