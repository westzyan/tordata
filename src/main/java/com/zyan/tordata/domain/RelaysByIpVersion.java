package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class RelaysByIpVersion {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer total;
    private Integer announced;
    private Integer reachable;
    private Integer exiting;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAnnounced() {
        return announced;
    }

    public void setAnnounced(Integer announced) {
        this.announced = announced;
    }

    public Integer getReachable() {
        return reachable;
    }

    public void setReachable(Integer reachable) {
        this.reachable = reachable;
    }

    public Integer getExiting() {
        return exiting;
    }

    public void setExiting(Integer exiting) {
        this.exiting = exiting;
    }

    @Override
    public String toString() {
        return "RelaysByIpVersion{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", announced=" + announced +
                ", reachable=" + reachable +
                ", exiting=" + exiting +
                '}';
    }
}
