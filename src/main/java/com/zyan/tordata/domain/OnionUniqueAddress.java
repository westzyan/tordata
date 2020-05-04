package com.zyan.tordata.domain;

import java.util.Date;

public class OnionUniqueAddress {
    private Long id;
    private Date date;
    private Integer onions;
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

    public Integer getOnions() {
        return onions;
    }

    public void setOnions(Integer onions) {
        this.onions = onions;
    }

    public Double getFrac() {
        return frac;
    }

    public void setFrac(Double frac) {
        this.frac = frac;
    }
}
