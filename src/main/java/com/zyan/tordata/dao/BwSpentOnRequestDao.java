package com.zyan.tordata.dao;

import com.zyan.tordata.domain.BwSpentOnRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BwSpentOnRequestDao {
    @Select("select id, traffic_date,dirread,dirwrite,dirauthread,dirauthwrite from bw_on_answering_directory_requests where traffic_date > #{start} and traffic_date < #{end}")
    @Results({
            @Result(property = "date", column = "traffic_date"),
            @Result(property = "dirread", column = "dirread"),
            @Result(property = "dirwrite", column = "dirwrite"),
            @Result(property = "dirauthread", column = "dirauthread"),
            @Result(property = "dirauthwrite", column = "dirauthwrite")
    })
    List<BwSpentOnRequest> listBwOnRequest(@Param("start") String start, @Param("end") String end);

    @Select("select max(traffic_date) from bw_on_answering_directory_requests")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into bw_on_answering_directory_requests (traffic_date,dirread,dirwrite,dirauthread,dirauthwrite) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.dirread},#{item.dirwrite},#{item.dirauthread},#{item.exits},#{item.dirauthwrite})" +
            "</foreach> " +
            "</script>"})
    Integer insertBwOnRequest(List<BwSpentOnRequest> list);
}
