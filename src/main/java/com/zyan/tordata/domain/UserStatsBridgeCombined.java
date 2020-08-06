package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserStatsBridgeCombined {
    private Long id;
    private String country;
    private String transport;
    private Integer low;
    private Integer high;
    private Integer frac;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getFrac() {
        return frac;
    }

    public void setFrac(Integer frac) {
        this.frac = frac;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserStatsBridgeCombined{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", transport='" + transport + '\'' +
                ", low=" + low +
                ", high=" + high +
                ", frac=" + frac +
                ", date=" + date +
                '}';
    }
}
