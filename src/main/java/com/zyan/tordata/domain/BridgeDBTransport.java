package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BridgeDBTransport {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private String transport;
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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Integer getRequests() {
        return requests;
    }

    public void setRequests(Integer requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "BridgeDBTransport{" +
                "id=" + id +
                ", date=" + date +
                ", transport='" + transport + '\'' +
                ", requests=" + requests +
                '}';
    }
}
