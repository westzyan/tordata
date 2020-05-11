package com.zyan.tordata.dao;

import com.zyan.tordata.domain.AdvBwByIpVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface AdvBwByIpVersionDao {
    @Select("select id, traffic_date, total,total_guard,total_exit,reachable_guard,reachable_exit,exiting from adv_bw_by_ip_version where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "total", column = "total"),
            @Result(property = "totalGuard", column = "total_guard"),
            @Result(property = "totalExit", column = "total_exit"),
            @Result(property = "reachableGuard", column = "reachable_guard"),
            @Result(property = "reachableExit", column = "reachable_exit"),
            @Result(property = "exiting", column = "exiting")
    })
    List<AdvBwByIpVersion> listAdvBwByIpVersion(@Param("start") String start, @Param("end") String end);

    @Select("select max(traffic_date) from adv_bw_by_ip_version")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into adv_bw_by_ip_version(traffic_date,total,total_guard,total_exit,reachable_guard,reachable_exit,exiting) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.total},#{item.totalGuard},#{item.totalExit},#{item.reachableGuard},#{item.reachableExit},#{item.exiting})" +
            "</foreach> " +
            "</script>"})
    Integer insertAdvBwByIpVersion(List<AdvBwByIpVersion> list);
}
