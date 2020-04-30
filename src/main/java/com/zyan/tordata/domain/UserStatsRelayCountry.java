package com.zyan.tordata.domain;

import com.zyan.tordata.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserStatsRelayCountry {
    private Long id;
    private String country;
    private Integer users;
    private Integer lower;
    private Integer upper;
    private Integer frac;

    @JSONField(format="yyyy-MM-dd")
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

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getUpper() {
        return upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
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
        return "UserStatsRelayCountry{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", users=" + users +
                ", lower=" + lower +
                ", upper=" + upper +
                ", frac=" + frac +
                ", date=" + date +
                '}';
    }
}
