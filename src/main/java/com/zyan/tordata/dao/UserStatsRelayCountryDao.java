package com.zyan.tordata.dao;


import com.zyan.tordata.domain.UserStatsRelayCountry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsRelayCountryDao {

    @Select("select * from userstats_relay_country where country = '' ")
    public List<UserStatsRelayCountry> listAllUser();

    @Select("select Max(date) from userstats_relay_country")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into userstats_relay_country(date, country, users, lower, upper, frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.country},#{item.users},#{item.lower},#{item.upper},#{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<UserStatsRelayCountry> list);


}
