package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class AdvBwByIpVersion {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private String total;
    private String totalGuard;
    private String totalExit;
    private String reachableGuard;
    private String reachableExit;
    private String exiting;

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalGuard() {
        return totalGuard;
    }

    public void setTotalGuard(String totalGuard) {
        this.totalGuard = totalGuard;
    }

    public String getTotalExit() {
        return totalExit;
    }

    public void setTotalExit(String totalExit) {
        this.totalExit = totalExit;
    }

    public String getReachableGuard() {
        return reachableGuard;
    }

    public void setReachableGuard(String reachableGuard) {
        this.reachableGuard = reachableGuard;
    }

    public String getReachableExit() {
        return reachableExit;
    }

    public void setReachableExit(String reachableExit) {
        this.reachableExit = reachableExit;
    }

    public String getExiting() {
        return exiting;
    }

    public void setExiting(String exiting) {
        this.exiting = exiting;
    }

    @Override
    public String toString() {
        return "AdvBWByIpVersion{" +
                "id=" + id +
                ", date=" + date +
                ", total='" + total + '\'' +
                ", totalGuard='" + totalGuard + '\'' +
                ", totalExit='" + totalExit + '\'' +
                ", reachableGuard='" + reachableGuard + '\'' +
                ", reachableExit='" + reachableExit + '\'' +
                ", exiting='" + exiting + '\'' +
                '}';
    }
}
