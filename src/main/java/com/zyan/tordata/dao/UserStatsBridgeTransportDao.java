package com.zyan.tordata.dao;


import com.zyan.tordata.domain.UserStatsBridgeTransport;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsBridgeTransportDao {
    @Select("select id, transport_type, user_number, frac, bridge_date from userstats_bridge_transport where bridge_date > #{start} and bridge_date < #{end} ")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "transport", column = "transport_type"),
            @Result(property = "date", column = "bridge_date")
    })
    public List<UserStatsBridgeTransport> listBridgeTransportByStartAndEnd(@Param("start") String start, @Param("end") String end);

    @Select("select id, transport_type, user_number, frac, bridge_date  from userstats_bridge_transport where transport_type = '!<OR>' ")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "transport", column = "transport_type"),
            @Result(property = "date", column = "bridge_date")
    })
    public List<UserStatsBridgeTransport> listAllUser();

    @Select("select max(bridge_date) from userstats_bridge_transport")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into userstats_bridge_transport(bridgedb_date, transport_type, user_number, frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.transport},#{item.users},#{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<UserStatsBridgeTransport> list);
}
