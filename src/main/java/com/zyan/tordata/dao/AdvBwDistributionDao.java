package com.zyan.tordata.dao;

import com.zyan.tordata.domain.AdvBwDistribution;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface AdvBwDistributionDao {
    @Select("select id, traffic_date,p,all_bw,exits from adv_bw_distribution where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "p", column = "p"),
            @Result(property = "all", column = "all_bw"),
            @Result(property = "exits", column = "exits")
    })
    List<AdvBwDistribution> listAdvBwDis(@Param("start") String start, @Param("end") String end);

    @Select("select max(traffic_date) from adv_bw_distribution")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into adv_bw_distribution (traffic_date,p,all_bw,exits) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.p},#{item.all},#{item.exits})" +
            "</foreach> " +
            "</script>"})
    Integer insertAdvBwDis(List<AdvBwDistribution> list);
}
