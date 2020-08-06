package com.zyan.tordata.dao;

import com.zyan.tordata.domain.UserStatsBridgeCombined;
import com.zyan.tordata.domain.UserStatsBridgeCountry;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserStatsBridgeCombinedDao {
    @Select("select * from userstats_bridge_combined " +
            "where bridge_date = #{start} and country_code = #{countryCode}")
    @Results({
            @Result(property = "date", column = "bridge_date"),
            @Result(property = "country", column = "country_code"),
            @Result(property = "transport", column = "transport_type"),
            @Result(property = "low", column = "low_number"),
            @Result(property = "high", column = "high_number")
    })
    public List<UserStatsBridgeCombined> listUserByCountryAndDate(@Param("start") String start, @Param("countryCode") String countryCode);

//    @Select("select * from userstats_bridge_country where country_code = '' ")
//    @Results({
//            @Result(property = "date", column = "bridge_date"),
//            @Result(property = "country", column = "country_code"),
//            @Result(property = "transport", column = "transport_type"),
//            @Result(property = "low", column = "low_number"),
//            @Result(property = "high", column = "high_number")
//    })
//    public List<UserStatsBridgeCombined> listAllUser();

    @Select("select max(bridge_date) from userstats_bridge_combined")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into userstats_bridge_combined(bridge_date, country_code, transport_type, low_number, high_number , frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.country},#{item.transport},#{item.low},#{item.high},#{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<UserStatsBridgeCombined> list);
}
