package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BridgeDBDistributor {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private String distributor;
    private Integer requests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public Integer getRequests() {
        return requests;
    }

    public void setRequests(Integer requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "BridgeDBDistributor{" +
                "id=" + id +
                ", date=" + date +
                ", distributor='" + distributor + '\'' +
                ", requests=" + requests +
                '}';
    }
}
