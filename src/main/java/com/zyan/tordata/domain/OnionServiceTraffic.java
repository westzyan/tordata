package com.zyan.tordata.domain;

import java.util.Date;

public class OnionServiceTraffic {
    private Long id;
    private Date date;
    private Double relayed;
    private Double frac;

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

    public Double getRelayed() {
        return relayed;
    }

    public void setRelayed(Double relayed) {
        this.relayed = relayed;
    }

    public Double getFrac() {
        return frac;
    }

    public void setFrac(Double frac) {
        this.frac = frac;
    }
}
