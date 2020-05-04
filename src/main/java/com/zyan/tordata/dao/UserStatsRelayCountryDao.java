package com.zyan.tordata.dao;


import com.zyan.tordata.domain.UserStatsRelayCountry;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsRelayCountryDao {

    @Select("select id, country, user_number, lower_number, upper_number, frac, relay_date from userstats_relay_country where country = '' and start = #{start} and end =#{end} ")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "lower", column = "lower_number"),
            @Result(property = "upper", column = "upper_number"),
            @Result(property = "date", column = "relay_date")

    })
    public List<UserStatsRelayCountry> listAllUserByStartAndEnd(@Param("start") String start, @Param("end") String end);

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


    /**
     * 返回的是按照user_number从大到小的排序list，第一项为总数，从第二项到之后分别为每个国家的数据
     * @return
     */
    @Select("select * from userstats_relay_country " +
            "where relay_date = (select max(relay_date) from tor.userstats_relay_country) " +
            "order by user_number desc")
    @Results({
            @Result(property = "users", column = "user_number"),
            @Result(property = "lower", column = "lower_number"),
            @Result(property = "upper", column = "upper_number"),
            @Result(property = "date", column = "relay_date")

    })
    public List<UserStatsRelayCountry> listUsersTopCountry();


}
