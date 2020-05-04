package com.zyan.tordata.dao;

import com.zyan.tordata.domain.BridgeDBDistributor;
import com.zyan.tordata.domain.BridgeDBTransport;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BridgeDBDistributorDao {
    @Select("select * from bridgedb_distributor " +
            "where bridgedb_date > #{start} and bridgedb_date < #{end}")
    @Results({
            @Result(property = "date", column = "bridgedb_date"),
    })
    public List<BridgeDBDistributor> listUserByStartAndEnd(@Param("start") String start, @Param("end") String end);

    @Select("select * from bridgedb_distributor where distributor = #{distributor} ")
    @Results({
            @Result(property = "date", column = "bridgedb_date"),
    })
    public List<BridgeDBDistributor> listAllUserByDistributor(@Param("distributor") String distributor);

    @Select("select max(bridgedb_date) from bridgedb_distributor")
    public Date getLastDate();

    @Insert({"<script> " +
            "insert into bridgedb_distributor(bridgedb_date, distributor, requests) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.date},#{item.distributor},#{item.requests})"+
            "</foreach> " +
            "</script>"})
    public Integer insertUsers(List<BridgeDBDistributor> list);
}
