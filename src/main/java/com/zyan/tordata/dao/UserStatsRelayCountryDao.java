package com.zyan.tordata.dao;


import com.zyan.tordata.domain.UserStatsRelayCountry;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsRelayCountryDao {

    @Select("select id, country, user_number, lower_number, upper_number, frac, relay_date from userstats_relay_country where country = '' ")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "lower", column = "lower_number"),
            @Result(property = "upper", column = "upper_number"),
            @Result(property = "date", column = "relay_date")

    })
    public List<UserStatsRelayCountry> listAllUser();

    @Select("select max(relay_date) from userstats_relay_country")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into userstats_relay_country(relay_date, country, user_number, lower_number, upper_number, frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.country},#{item.users},#{item.lower},#{item.upper},#{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<UserStatsRelayCountry> list);


}
