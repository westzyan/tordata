package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class RelaysByRelayFlag {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private String flag;
    private Integer relays;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getRelays() {
        return relays;
    }

    public void setRelays(Integer relays) {
        this.relays = relays;
    }

    @Override
    public String toString() {
        return "RelaysByRelayFlag{" +
                "id=" + id +
                ", date=" + date +
                ", flag='" + flag + '\'' +
                ", relays=" + relays +
                '}';
    }
}
