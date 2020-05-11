package com.zyan.tordata.dao;

import com.zyan.tordata.domain.AdvAndConBwByRelayFlags;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface AdvAndConBwByRelayFlagsDao {
    @Select("select id, traffic_date, have_guard_flag,have_exit_flag,advbw,bwhist from adv_and_con_bandwidth_by_relay_flags where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "haveGuardFlag", column = "have_guard_flag"),
            @Result(property = "haveExitFlag", column = "have_exit_flag"),
            @Result(property = "advbw", column = "advbw"),
            @Result(property = "bwhist", column = "bwhist")
    })
    List<AdvAndConBwByRelayFlags> listAdvAndConBw(@Param("start") String start, @Param("end") String end);


    @Select("select max(traffic_date) from adv_and_con_bandwidth_by_relay_flags")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into adv_and_con_bandwidth_by_relay_flags(traffic_date, have_guard_flag,have_exit_flag,advbw,bwhist) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.haveGuardFlag},#{item.haveExitFlag},#{item.advbw},#{item.bwhist})" +
            "</foreach> " +
            "</script>"})
    Integer insertAdvAndConBw(List<AdvAndConBwByRelayFlags> list);
}
