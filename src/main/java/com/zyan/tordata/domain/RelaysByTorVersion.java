package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RelaysByTorVersion {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private String version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getRelays() {
        return relays;
    }

    public void setRelays(Integer relays) {
        this.relays = relays;
    }

    @Override
    public String toString() {
        return "RelaysByTorVersion{" +
                "id=" + id +
                ", date=" + date +
                ", version='" + version + '\'' +
                ", relays=" + relays +
                '}';
    }
}
