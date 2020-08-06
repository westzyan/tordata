package com.zyan.tordata.dao;

import com.zyan.tordata.domain.UserStatsBridgeCountry;
import com.zyan.tordata.domain.UserStatsRelayCountry;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsBridgeCountryDao {
    @Select("select * from userstats_bridge_country where country_code = #{country} and bridge_date >= #{start} and bridge_date <= #{end} ")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "country", column = "country_code"),
            @Result(property = "date", column = "bridge_date")
    })
    public List<UserStatsBridgeCountry> listUserByCountryAndDate(@Param("country") String country, @Param("start") String start, @Param("end") String end);

    @Select("select id, country_code, user_number,  frac, bridge_date from userstats_bridge_country where country_code = '' ")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "country", column = "country_code"),
            @Result(property = "date", column = "bridge_date")
    })
    public List<UserStatsBridgeCountry> listAllUser();


    @Select("SELECT * FROM userstats_bridge_country where bridge_date = #{start} and country_code != '' and country_code != '??' order by user_number desc;")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "country", column = "country_code"),
            @Result(property = "date", column = "bridge_date")
    })
    public List<UserStatsBridgeCountry> listTopBridge(@Param("start") String start);

    @Select("select max(bridge_date) from userstats_bridge_country")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into userstats_bridge_country(bridge_date, country_code, user_number, frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.country},#{item.users},#{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<UserStatsBridgeCountry> list);

    @Select("select * from userstats_bridge_country " +
            "where bridge_date = (select max(bridge_date) from tor.userstats_bridge_country) " +
            "order by user_number desc")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "country", column = "country_code"),
            @Result(property = "date", column = "bridge_date")

    })
    public List<UserStatsBridgeCountry> listBridgeUsersTopCountry();
}
