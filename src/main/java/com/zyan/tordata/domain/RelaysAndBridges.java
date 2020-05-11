package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class RelaysAndBridges {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer relays;
    private Integer bridges;

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

    public Integer getRelays() {
        return relays;
    }

    public void setRelays(Integer relays) {
        this.relays = relays;
    }

    public Integer getBridges() {
        return bridges;
    }

    public void setBridges(Integer bridges) {
        this.bridges = bridges;
    }

    @Override
    public String toString() {
        return "RelaysAndBridges{" +
                "id=" + id +
                ", date=" + date +
                ", relays=" + relays +
                ", bridges=" + bridges +
                '}';
    }
}
