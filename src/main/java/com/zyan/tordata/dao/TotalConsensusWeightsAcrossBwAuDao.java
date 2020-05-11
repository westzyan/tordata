package com.zyan.tordata.dao;

import com.zyan.tordata.domain.RelaysByRelayFlag;
import com.zyan.tordata.domain.TotalConsensusWeightsAcrossBwAu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface TotalConsensusWeightsAcrossBwAuDao {
    @Select("select id, servers_date, nickname,totalcw from total_con_weights_across_bw_a where servers_date > #{start} and servers_date < #{end}")
    @Results({
            @Result(property = "date", column = "servers_date"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "totalcw", column = "totalcw")
    })
    List<TotalConsensusWeightsAcrossBwAu> listTotalConW(@Param("start") String start, @Param("end") String end);

    @Select("select * from total_con_weights_across_bw_a where nickname = #{nickname} ")
    @Results({
            @Result(property = "date", column = "servers_date")
    })
    List<RelaysByRelayFlag> listByAuth(@Param("nickname") String nickname);

    @Select("select max(servers_date) from total_con_weights_across_bw_a")
    Date getLastDate();

    @Insert({"<script> " +
            "insert into total_con_weights_across_bw_a (servers_date, nickname,totalcw) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.date},#{item.nickname},#{item.totalcw})" +
            "</foreach> " +
            "</script>"})
    Integer insertTotalCW(List<TotalConsensusWeightsAcrossBwAu> list);
}
