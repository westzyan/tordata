package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class BridgesByIpVersion {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer total;
    private Integer announced;

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

    @Override
    public String toString() {
        return "BridgesByIpVersion{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", announced=" + announced +
                '}';
    }
}
