package com.zyan.tordata.dao;

import com.zyan.tordata.domain.BridgesByIpVersion;
import com.zyan.tordata.domain.TotalRelayBandwidth;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface TotalRelayBandwidthDao {
    @Select("select id, traffic_date, advbw, bwhist from total_relay_bandwidth where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "advbw", column = "advbw"),
            @Result(property = "bwhist", column = "bwhist")
    })
    List<TotalRelayBandwidth> listAdvbwAndBwhists(@Param("start") String start, @Param("end") String end);

    @Select("select max(traffic_date) from total_relay_bandwidth")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into total_relay_bandwidth(traffic_date,advbw,bwhist) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.advbw},#{item.bwhist})" +
            "</foreach> " +
            "</script>"})
    Integer insertAdvbwAndBwhists(List<TotalRelayBandwidth> list);
}
