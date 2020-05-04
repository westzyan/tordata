package com.zyan.tordata.dao;

import com.zyan.tordata.domain.UserStatsBridgeCombined;
import com.zyan.tordata.domain.UserStatsBridgeVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsBridgeVersionDao {
    @Select("select id, bridge_date, version, user_number, frac userstats_bridge_version " +
            "where bridge_date > #{start} and bridge_date < #{end} and version = #{version}")
    @Results({
            @Result(property = "date", column = "bridge_date"),
            @Result(property = "users", column = "user_number")
    })
    public List<UserStatsBridgeVersion> listUserByStartAndEndAndVersion(@Param("start") String start, @Param("end") String end, @Param("version") String version);

    @Select("select * from userstats_bridge_version where version = #{version} ")
    @Results({
            @Result(property = "date", column = "bridge_date"),
            @Result(property = "users", column = "user_number")
    })
    public List<UserStatsBridgeVersion> listAllUserByVersion(@Param("version") String version);

    @Select("select max(bridge_date) from userstats_bridge_version")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into userstats_bridge_version(bridge_date, version, user_number, frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.version},#{item.users},#{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<UserStatsBridgeVersion> list);
}
