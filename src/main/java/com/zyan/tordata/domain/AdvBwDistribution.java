package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class AdvBwDistribution {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer p;
    private String all;
    private String exits;

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

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getExits() {
        return exits;
    }

    public void setExits(String exits) {
        this.exits = exits;
    }

    @Override
    public String toString() {
        return "AdvBwDistribution{" +
                "id=" + id +
                ", date=" + date +
                ", p=" + p +
                ", all='" + all + '\'' +
                ", exits='" + exits + '\'' +
                '}';
    }
}
