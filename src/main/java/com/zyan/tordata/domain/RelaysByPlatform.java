package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class RelaysByPlatform {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer bsd;
    private Integer linux;
    private Integer macos;
    private Integer other;
    private Integer windows;

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

    public Integer getBsd() {
        return bsd;
    }

    public void setBsd(Integer bsd) {
        this.bsd = bsd;
    }

    public Integer getLinux() {
        return linux;
    }

    public void setLinux(Integer linux) {
        this.linux = linux;
    }

    public Integer getMacos() {
        return macos;
    }

    public void setMacos(Integer macos) {
        this.macos = macos;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public Integer getWindows() {
        return windows;
    }

    public void setWindows(Integer windows) {
        this.windows = windows;
    }

    @Override
    public String toString() {
        return "RelaysByPlatform{" +
                "id=" + id +
                ", date=" + date +
                ", bsd=" + bsd +
                ", linux=" + linux +
                ", macos=" + macos +
                ", other=" + other +
                ", windows=" + windows +
                '}';
    }
}
