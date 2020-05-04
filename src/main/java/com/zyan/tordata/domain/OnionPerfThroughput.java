package com.zyan.tordata.domain;

import java.util.Date;

public class OnionPerfThroughput {
    private Long id;
    private Date date;
    private String source;
    private String server;
    private Integer low;
    private Integer q1;
    private Integer md;
    private Integer q3;
    private Integer high;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getMd() {
        return md;
    }

    public void setMd(Integer md) {
        this.md = md;
    }

    public Integer getQ3() {
        return q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }
}
