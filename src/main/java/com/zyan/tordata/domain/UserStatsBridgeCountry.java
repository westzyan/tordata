package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserStatsBridgeCountry {
    private Long id;
    private String country;
    private Integer users;
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

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
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
        return "UserStatsBridgeCountry{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", users=" + users +
                ", frac=" + frac +
                ", date=" + date +
                '}';
    }
}
