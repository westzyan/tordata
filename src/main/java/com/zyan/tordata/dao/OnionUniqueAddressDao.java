package com.zyan.tordata.dao;

import com.zyan.tordata.domain.OnionUniqueAddress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
@Mapper
public interface OnionUniqueAddressDao {

    @Select("select * from onion_unique_address where onion_date >= #{start} and onion_date <= #{end}")
    @Results({
            @Result(property = "date", column = "onion_date")
    })
    public List<OnionUniqueAddress> listOnionAddress(@Param("start") String start, @Param("end") String end);

    @Select("select max(onion_date) from onion_unique_address")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into onion_unique_address(onion_date, onions,frac) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.onions}, #{item.frac})"+
            "</foreach> " +
            "</script>"})
    public Integer insertOnionAddress(List<OnionUniqueAddress> list);
}
