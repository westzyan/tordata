package com.zyan.tordata.dao;

import com.zyan.tordata.domain.FracOfConn;
import com.zyan.tordata.domain.RelaysByRelayFlag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface FracOfConnDao {
    @Select("select id, traffic_date,direction,q1,md,q3 from frac_of_conn_used_uni_and_bidirectionally where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "direction", column = "direction"),
            @Result(property = "q1", column = "q1"),
            @Result(property = "md", column = "md"),
            @Result(property = "q3", column = "q3")
    })
    List<FracOfConn> listFracOfConn(@Param("start") String start, @Param("end") String end);

    @Select("select * from traffic_date where direction = #{direction} ")
    @Results({
            @Result(property = "date", column = "traffic_date")
    })
    List<RelaysByRelayFlag> listByDirection(@Param("direction") String direction);

    @Select("select max(traffic_date) from frac_of_conn_used_uni_and_bidirectionally")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into frac_of_conn_used_uni_and_bidirectionally (traffic_date,direction,q1,md,q3) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.direction},#{item.q1},#{item.md},#{item.q3})" +
            "</foreach> " +
            "</script>"})
    Integer insertFracOfConn(List<FracOfConn> list);
}
