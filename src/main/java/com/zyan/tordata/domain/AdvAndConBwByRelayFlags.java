package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class AdvAndConBwByRelayFlags {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private String haveGuardFlag;
    private String haveExitFlag;
    private String advbw;
    private String bwhist;

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

    public String getHaveGuardFlag() {
        return haveGuardFlag;
    }

    public void setHaveGuardFlag(String haveGuardFlag) {
        this.haveGuardFlag = haveGuardFlag;
    }

    public String getHaveExitFlag() {
        return haveExitFlag;
    }

    public void setHaveExitFlag(String haveExitFlag) {
        this.haveExitFlag = haveExitFlag;
    }

    public String getAdvbw() {
        return advbw;
    }

    public void setAdvbw(String advbw) {
        this.advbw = advbw;
    }

    public String getBwhist() {
        return bwhist;
    }

    public void setBwhist(String bwhist) {
        this.bwhist = bwhist;
    }

    @Override
    public String toString() {
        return "AAndCBWByRelayFlags{" +
                "id=" + id +
                ", date=" + date +
                ", have_guard_flag='" + haveGuardFlag + '\'' +
                ", have_exit_flag='" + haveExitFlag + '\'' +
                ", advbw='" + advbw + '\'' +
                ", bwhist='" + bwhist + '\'' +
                '}';
    }
}
