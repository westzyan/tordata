package com.zyan.tordata.dao;

import com.zyan.tordata.domain.OnionServiceTraffic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface OnionServiceTrafficDao {
    @Select("select * from onion_service_traffic where onion_date >= #{start} and onion_date <= #{end}")
    @Results({
            @Result(property = "date", column = "onion_date")
    })
    public List<OnionServiceTraffic> listOnionServiceTraffic(@Param("start") String start, @Param("end") String end);

    @Select("select max(onion_date) from onion_service_traffic")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into onion_service_traffic(onion_date, relayed,frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.relayed}, #{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertOnionServiceTraffic(List<OnionServiceTraffic> list);
}
