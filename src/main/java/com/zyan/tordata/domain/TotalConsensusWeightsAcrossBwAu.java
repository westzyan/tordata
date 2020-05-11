package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class TotalConsensusWeightsAcrossBwAu {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private String nickname;
    private Integer totalcw;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getTotalcw() {
        return totalcw;
    }

    public void setTotalcw(Integer totalcw) {
        this.totalcw = totalcw;
    }

    @Override
    public String toString() {
        return "TotalConsensusWeightsAcrossBwA{" +
                "id=" + id +
                ", date=" + date +
                ", nickname='" + nickname + '\'' +
                ", totalcw=" + totalcw +
                '}';
    }
}
