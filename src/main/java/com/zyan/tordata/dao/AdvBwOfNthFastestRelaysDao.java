package com.zyan.tordata.dao;

import com.zyan.tordata.domain.AdvBwOfNthFastestRelays;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface AdvBwOfNthFastestRelaysDao {
    @Select("select id, traffic_date,n,all_bw,exits from adv_bw_of_nth_fastest_relays where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "n", column = "n"),
            @Result(property = "all", column = "all_bw"),
            @Result(property = "exits", column = "exits")
    })
    List<AdvBwOfNthFastestRelays> listAdvBwOfNth(@Param("start") String start, @Param("end") String end);

    @Select("select max(traffic_date) from adv_bw_of_nth_fastest_relays")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into adv_bw_of_nth_fastest_relays (traffic_date,n,all_bw,exits) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.n},#{item.all},#{item.exits})" +
            "</foreach> " +
            "</script>"})
    Integer insertAdvBwOfNth(List<AdvBwOfNthFastestRelays> list);
}
